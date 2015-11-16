package server.handlers.user;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.authentication.Authenticate;
import server.exceptions.ContextNotFoundException;
import server.exceptions.InvalidLoginException;
import server.exceptions.UsernameAlreadyTakenException;
import server.facade.UserFacade;

public class UserHandler implements HttpHandler {

	Logger logger;
	Gson gson;
	JSONToModel jsonToModel = new JSONToModel();
	ModelToJSON modelToJSON = new ModelToJSON();
	UserFacade userFacade;
	
	public UserHandler(UserFacade userFacade) {
		this.userFacade = userFacade;
	}
	
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String response = "";

		try{
			URI address = exchange.getRequestURI();
			String specificContext = Authenticate.getSpecificContext(address.toString());
			logger = Logger.getLogger("Catan");
			logger.info("Handling User");

			JsonObject jsonObject = jsonToModel.exchangeToJson(exchange);
			String username = jsonToModel.getUsername(jsonObject);
			String password = jsonToModel.getPassword(jsonObject);
			int playerID = -1;
			
			
			exchange.getResponseHeaders().set("Content-Type", "application/json");
			
			logger.info(specificContext);

			switch (specificContext){
				case "register":
					playerID = userFacade.registerUser(username, password);
					break;
				case "login":
					playerID = userFacade.loginUser(username, password);
					break;
				default:
					throw new ContextNotFoundException("404 Context Not Found.");
			
			}
			//If we are successful, keep going
			response = "Success";
			JsonObject playerCookie = modelToJSON.generatePlayerCookie(username, password, playerID);
			Headers headers = exchange.getResponseHeaders();
			String header = Authenticate.dressCookie(playerCookie);
			logger.info("HEADER: " + header);
			headers.add("Set-cookie", header);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
			
		} catch (UsernameAlreadyTakenException e) {
			response = "Failed to register - someone already has that username.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());
		} catch (InvalidLoginException e) {
			response = "Failed to login - username or password incorrect.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());
		} catch (ContextNotFoundException e) {
			response = e.getMessage();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, response.length());
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
