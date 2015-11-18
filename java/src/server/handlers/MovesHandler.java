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
				JsonObject jsonObject = jsonToModel.exchangeToJson(exchange);
				ClientModel jsonModel = null;
				switch (specificContext){
					case "sendChat":
						
						
						break;
					case "rollNumber":

						break;
					case "robPlayer":

						break;
					case "finishTurn":

						break;
					case "buyDevCard":

						break;
					case "Year_of_Plenty":

						break;
					case "Road_Building":

						break;
					case "Soldier":

						break;
					case "Monopoly":

						break;
					case "Monument":

						break;
					case "buildRoad":
						jsonModel = movesFacade.buildRoad(jsonToModel.getGameIndex(jsonObject), jsonToModel.getPlayerIndex(jsonObject), jsonToModel.getEdgeLocation(jsonObject, "roadLocation"), jsonToModel.getFree(jsonObject));
						break;
					case "buildSettlement":

						break;
					case "buildCity":

						break;
					case "offerTrade":

						break;
					case "acceptTrade":

						break;
					case "maritimeTrade":

						break;
					case "discardCards":

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
