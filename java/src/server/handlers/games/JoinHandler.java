package server.handlers.games;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.authentication.Authenticate;
import server.exceptions.MissingCookieException;
import server.facade.GamesFacade;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import jsonTranslator.JSONToModel;

// TODO: Auto-generated Javadoc
/**
 * The Class JoinHandler.
 */
public class JoinHandler implements HttpHandler {
	Logger logger;
	GamesFacade gamesFacade;
	JSONToModel jsonToModel = new JSONToModel();

	public JoinHandler(GamesFacade gamesFacade) {
		this.gamesFacade = gamesFacade;
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger = Logger.getLogger("Catan");
		JsonObject cookie;
		try {
			cookie = jsonToModel.getCookieFromExchange(exchange);
			logger.info(cookie.toString());
			if(Authenticate.isValidCookie(cookie, false)) {
				jsonToModel.exchangeToJson(exchange);
			}
		} catch (MissingCookieException e) {
			//Return a bad request message
			//The catan.user HTTP cookie is missing.  You must login before calling this method.
			logger.log(Level.SEVERE, e.getMessage());
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		

	}

}
