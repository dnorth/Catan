package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.authentication.Authenticate;
import server.exceptions.MissingCookieException;
import server.facade.GameFacade;
import server.facade.iGameFacade;
import client.models.ClientModel;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelHandler.
 */
public class GameHandler implements HttpHandler{
	Logger logger;
	iGameFacade gameFacade;
	JSONToModel jsonToModel = new JSONToModel();
	ModelToJSON modelToJSON = new ModelToJSON();

	public GameHandler(iGameFacade gameFacade) {
		this.gameFacade = gameFacade;
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger = Logger.getLogger("Catan");
		logger.info("Catan Model");
		JsonObject cookie;
		String response = "";
		try {
			cookie = jsonToModel.getCookieFromExchange(exchange);
			exchange.getResponseHeaders().set("Content-Type", "application/json");

			logger.info(cookie.toString());
			if(Authenticate.isValidCookie(cookie, false)) {
				int gID = cookie.get("game").getAsInt();
				ClientModel clientModel = gameFacade.getGameModel(gID);
				response = modelToJSON.translateModel(clientModel).toString();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
				logger.info("Valid Cookie!");
			} else {
				//Bad login information
				logger.info("Invalid Cookie.");
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
		} finally {
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
		}
	}

}
