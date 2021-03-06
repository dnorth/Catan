package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import client.data.GameInfo;
import client.models.ClientModel;
import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.authentication.Authenticate;
import server.exceptions.ContextNotFoundException;
import server.exceptions.MissingCookieException;
import server.facade.GameFacade;
import server.facade.GamesFacade;
import server.facade.MovesFacade;
import server.facade.iMovesFacade;
import server.model.ServerGame;

public class MovesHandler implements HttpHandler{

	Logger logger;
	iMovesFacade movesFacade;
	JSONToModel jsonToModel = new JSONToModel();
	ModelToJSON modelToJSON = new ModelToJSON();

	public MovesHandler(iMovesFacade movesFacade) {
		this.movesFacade = movesFacade;
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger = Logger.getLogger("Catan");
		String specificContext = Authenticate.getSpecificContextFromExchange(exchange);
		JsonObject cookie;
		String response = "";
		try {
			cookie = jsonToModel.getCookieFromExchange(exchange);
			exchange.getResponseHeaders().set("Content-Type", "application/json");
			if(Authenticate.isValidCookie(cookie, true)) {	
				JsonObject object = jsonToModel.exchangeToJson(exchange);
				ClientModel jsonModel = null;
				int gameIndex = jsonToModel.getGameIndex(cookie);
				int playerIndex = jsonToModel.getPlayerIndex(object);
				switch (specificContext){
					case "sendChat":
						jsonModel = movesFacade.sendChat(gameIndex, playerIndex, jsonToModel.getContent(object));
						break;
					case "rollNumber":
						jsonModel = movesFacade.rollNumber(gameIndex, playerIndex, jsonToModel.getNumber(object));
						break;
					case "robPlayer":
						jsonModel = movesFacade.robPlayer(gameIndex, playerIndex, jsonToModel.getVictimIndex(object), jsonToModel.getHexLocation(object, "location"));
						break;
					case "finishTurn":
						jsonModel = movesFacade.finishTurn(gameIndex, playerIndex);
						break;
					case "buyDevCard":
						jsonModel = movesFacade.buyDevCard(gameIndex, playerIndex);
						break;
					case "Year_of_Plenty":
						jsonModel = movesFacade.playYearOfPlenty(gameIndex, playerIndex, jsonToModel.getResourceType(object, "resource1"), jsonToModel.getResourceType(object, "resource2"));
						break;
					case "Road_Building":
						jsonModel = movesFacade.playRoadBuilding(gameIndex, playerIndex, jsonToModel.getEdgeLocation(object, "spot1"), jsonToModel.getEdgeLocation(object, "spot2"));
						break;
					case "Soldier":
						jsonModel = movesFacade.playSoldier(gameIndex, playerIndex, jsonToModel.getVictimIndex(object), jsonToModel.getHexLocation(object, "location"));
						break;
					case "Monopoly":
						jsonModel = movesFacade.playMonopoly(jsonToModel.getResourceType(object, "resource"), gameIndex, playerIndex);
						break;
					case "Monument":
						jsonModel = movesFacade.playMonument(gameIndex, playerIndex);
						break;
					case "buildRoad":
						jsonModel = movesFacade.buildRoad(gameIndex, playerIndex, jsonToModel.getEdgeLocation(object, "roadLocation"), jsonToModel.getFree(object));
						break;
					case "buildSettlement":
						jsonModel = movesFacade.buildSettlement(gameIndex, playerIndex, jsonToModel.getVertexLocation(object, "vertexLocation"), jsonToModel.getFree(object));
						break;
					case "buildCity":
						jsonModel = movesFacade.buildCity(gameIndex, playerIndex, jsonToModel.getVertexLocation(object, "vertexLocation"));
						break;
					case "offerTrade":
						jsonModel = movesFacade.offerTrade(gameIndex, playerIndex, jsonToModel.getResourceList(object, "offer"), jsonToModel.getReceiver(object));
						break;
					case "acceptTrade":
						jsonModel = movesFacade.acceptTrade(gameIndex, playerIndex, jsonToModel.getWillAccept(object));
						break;
					case "maritimeTrade":
						//TODO - ratio, input and output resource is optional. Have to handle that
						jsonModel = movesFacade.maritimeTrade(gameIndex, playerIndex, jsonToModel.getRatio(object), jsonToModel.getResource(object, "inputResource"), jsonToModel.getResource(object, "outputResource"));
						break;
					case "discardCards":
						jsonModel = movesFacade.discardCards(gameIndex, playerIndex, jsonToModel.getResourceList(object, "discardedCards"));
						break;
					default:
						throw new ContextNotFoundException("404 Context Not Found.");
				}
				
				JsonObject gameInfoObject = modelToJSON.translateModel(jsonModel);
				response = gameInfoObject.toString();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());			
				
			} else {
				throw new MissingCookieException("Invalid Cookie. Trying to break our code, eh? Nice try.");
			}


		} catch(NullPointerException e) {
			e.printStackTrace();
			exchange.getResponseHeaders().set("Content-Type", "text/html");
			logger.log(Level.SEVERE, "Null Pointer Exception.");
			response = "Invalid Keys for the requested operation.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());
		} catch (Exception e) {
			e.printStackTrace();
			exchange.getResponseHeaders().set("Content-Type", "text/html");
			logger.log(Level.SEVERE, e.getMessage());//, e);
			response = e.getMessage();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());
			return;
		} finally {
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
		}

		
	}	
}
