package server.handlers.user;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.authentication.Authenticate;
import server.exceptions.InvalidLoginException;
import server.facade.UserFacade;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginHandler.
 */
public class LoginHandler implements HttpHandler {
	Logger logger;
	Gson gson;
	JSONToModel jsonToModel = new JSONToModel();
	ModelToJSON modelToJSON = new ModelToJSON();
	UserFacade userFacade;
	
	public LoginHandler(UserFacade userFacade) {
		this.userFacade = userFacade;
	}
	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger = Logger.getLogger("Catan");
		logger.info("Handling Login");
		try {
			JsonObject jsonObject = jsonToModel.exchangeToJson(exchange);
			String username = jsonToModel.getUsername(jsonObject);
			String password = jsonToModel.getPassword(jsonObject);
			
			logger.info("Username: " + username);
			logger.info("Password: " + password);
			
			String response = "";
			exchange.getResponseHeaders().set("Content-Type", "application/json");
			if (userFacade == null) logger.info("UUUHHHH OOOOHHH");
			try {
				int playerID = userFacade.loginUser(username, password);
				response = "Success";
				JsonObject playerCookie = modelToJSON.generatePlayerCookie(username, password, playerID);
				Headers headers = exchange.getResponseHeaders();
				String header = Authenticate.dressCookie(playerCookie);
				logger.info("HEADER: " + header);
				headers.add("Set-cookie", header);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
			} catch (InvalidLoginException e) {
				response = "Failed to login - username or password incorrect.";
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
