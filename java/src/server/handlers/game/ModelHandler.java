package server.handlers.game;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.authentication.Authenticate;
import server.exceptions.MissingCookieException;
import server.facade.GameFacade;
import client.models.ClientModel;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelHandler.
 */
public class ModelHandler implements HttpHandler{
	Logger logger;
	GameFacade gameFacade;
	JSONToModel jsonToModel = new JSONToModel();
	ModelToJSON modelToJSON = new ModelToJSON();

	public ModelHandler(GameFacade gameFacade) {
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
			logger.info(cookie.toString());
			if(Authenticate.isValidCookie(cookie, false)) {
				int gID = cookie.get("game").getAsInt();
				JsonObject jsonObject = jsonToModel.exchangeToJson(exchange);
				int version = jsonToModel.getVersionNumber(jsonObject);
				ClientModel clientModel = gameFacade.getGameModel(gID);
				if (version == clientModel.getVersion()) response = "true";
				else {
					JsonObject modelObject = modelToJSON.translateModel(clientModel);
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, modelObject.toString().length());
				}
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
		} finally {
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
		}
	}

}
