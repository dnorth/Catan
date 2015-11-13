package server.handlers.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.exceptions.UsernameAlreadyTakenException;
import server.facade.UserFacade;
import shared.locations.VertexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterHandler.
 */
public class RegisterHandler implements HttpHandler {
	Logger logger;
	JSONToModel jsonToModel = new JSONToModel();
	ModelToJSON modelToJSON = new ModelToJSON();
	UserFacade userFacade;
	
	public RegisterHandler(UserFacade userFacade) {
		this.userFacade = userFacade;
	}
	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger = Logger.getLogger("Catan");
		logger.info("Handling Register");
		try{
			JsonObject jsonObject = jsonToModel.exchangeToJson(exchange);
			String username = jsonToModel.getUsername(jsonObject);
			String password = jsonToModel.getPassword(jsonObject);
			
			logger.info("Username: " + username);
			logger.info("Password: " + password);
			
			String response = "";
			Headers headers = exchange.getResponseHeaders();
			headers.add("Content-Type", "application/json");
			if (userFacade == null) logger.info("UUUHHHH OOOOHHH");
			try {
				int playerID = userFacade.registerUser(username, password);
				response = "Success";
				JsonObject playerCookie = modelToJSON.generatePlayerCookie(username, password, playerID);
				String playerCookieHeader = "catan.user=" + URLEncoder.encode(playerCookie.toString(), "utf-8") + ";Path=/;";
				logger.info("PLAYER COOKIE HEADER: " + playerCookieHeader);
				headers.add("Set-cookie", playerCookieHeader);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
			} catch (UsernameAlreadyTakenException e) {
				response = "Failed to register - someone already has that username.";
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());
			}
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
		}
		catch( Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
	}
}


