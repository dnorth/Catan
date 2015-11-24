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
		JsonObject cookie;
		String response = "";
		try {
			cookie = jsonToModel.getCookieFromExchange(exchange);
			exchange.getResponseHeaders().set("Content-Type", "application/json");

			if(Authenticate.isValidCookie(cookie, false)) {
				int gID = cookie.get("game").getAsInt();
				ClientModel clientModel = gameFacade.getGameModel(gID);
				response = modelToJSON.translateModel(clientModel).toString();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
			} else {
				//Bad login information
				throw new MissingCookieException("Invalid Cookie. Trying to break our code, eh? Nice try.");
			}
		} catch(NullPointerException e) {
			exchange.getResponseHeaders().set("Content-Type", "text/html");
			logger.log(Level.SEVERE, "Null Pointer Exception.");
			response = "Invalid Keys for the requested operation.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());
		} catch (Exception e) {
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
