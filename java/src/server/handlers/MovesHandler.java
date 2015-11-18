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
import server.model.ServerGame;

public class MovesHandler implements HttpHandler{

	Logger logger;
	MovesFacade movesFacade;
	JSONToModel jsonToModel = new JSONToModel();
	ModelToJSON modelToJSON = new ModelToJSON();

	public MovesHandler(MovesFacade movesFacade) {
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
			if(Authenticate.isValidCookie(cookie, true)) {	
				JsonObject object = jsonToModel.exchangeToJson(exchange);
				ClientModel jsonModel = null;
				int gameIndex = jsonToModel.getGameIndex(cookie);
				int playerIndex = jsonToModel.getPlayerIndex(object);
				System.out.println("*********************************** THE SPECIFIC CONTEXT IS: " + specificContext);
				switch (specificContext){
					case "sendChat":
						jsonModel = movesFacade.sendChat(gameIndex, playerIndex, jsonToModel.getContent(object));
						
						break;
					case "rollNumber":
						jsonModel = movesFacade.rollNumber(gameIndex, playerIndex, jsonToModel.getNumber(object));
						break;
					case "robPlayer":
						jsonModel = movesFacade.robPlayer(gameIndex, playerIndex, jsonToModel.getVictimIndex(object), jsonToModel.getHexLocation(object, "HexLocation"));
						break;
					case "finishTurn":
						jsonModel = movesFacade.finishTurn(gameIndex, playerIndex);
						break;
					case "buyDevCard":
						jsonModel = movesFacade.buyDevCard(gameIndex, playerIndex);
						break;
					case "Year_of_Plenty":
						//jsonModel = movesFacade.playYearOfPlenty(gameIndex, playerIndex, jsonToModel.get, resource2);

						break;
					case "Road_Building":
						//jsonModel = movesFacade.playRoadBuilding(gameIndex, playerIndex, spot1, spot2);

						break;
					case "Soldier":
						//jsonModel = movesFacade.playSoldier(gameIndex, playerIndex, victimIndex, location);

						break;
					case "Monopoly":
						//jsonModel = movesFacade.playMonopoly(resource, gameIndex, playerIndex);

						break;
					case "Monument":
						jsonModel = movesFacade.playMonument(gameIndex, playerIndex);

						break;
					case "buildRoad":
						jsonModel = movesFacade.buildRoad(gameIndex, playerIndex, jsonToModel.getEdgeLocation(object, "roadLocation"), jsonToModel.getFree(object));
						break;
					case "buildSettlement":
						//jsonModel = movesFacade.buildSettlement(gameIndex, playerIndex, vertexLocation, free);

						break;
					case "buildCity":
						//jsonModel = movesFacade.buildCity(gameIndex, playerIndex, location);

						break;
					case "offerTrade":
						//jsonModel = movesFacade.offerTrade(gameIndex, playerIndex, offer, receiver);

						break;
					case "acceptTrade":
						//jsonModel = movesFacade.acceptTrade(gameIndex, playerIndex, willAccept);

						break;
					case "maritimeTrade":
						//jsonModel = movesFacade.maritimeTrade(gameIndex, playerIndex, ratio, inputResource, outputResource);

						break;
					case "discardCards":
						//jsonModel = movesFacade.discardCards(gameIndex, playerIndex, discardedCards);

						break;
					default:
						throw new ContextNotFoundException("404 Context Not Found.");
				}
				
				JsonObject gameInfoObject = modelToJSON.translateModel(jsonModel);
				response = jsonModel.toString();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());			
				
			} else {
				throw new MissingCookieException("Invalid Cookie. Trying to break our code, eh? Nice try.");
			}


		} catch (MissingCookieException e) {
			//Return a bad request message
			//The catan.user HTTP cookie is missing.  You must login before calling this method.
			logger.log(Level.SEVERE, e.getMessage());
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
		} catch (IllegalStateException e) {
			//Not a valid JSON Object as post data
			response = "Invalid Request";
			logger.log(Level.SEVERE, e.getMessage());
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		} finally {
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
		}

		
	}	
}
