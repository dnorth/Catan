package server.handlers;

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
import server.facade.iUserFacade;

public class UserHandler implements HttpHandler {

	Logger logger;
	Gson gson;
	JSONToModel jsonToModel = new JSONToModel();
	ModelToJSON modelToJSON = new ModelToJSON();
	iUserFacade userFacade;
	
	public UserHandler(iUserFacade userFacade) {
		this.userFacade = userFacade;
	}
	
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String response = "";

		try{
			String specificContext = Authenticate.getSpecificContextFromExchange(exchange);
			logger = Logger.getLogger("Catan");
			logger.info("Handling User");

			JsonObject jsonObject = jsonToModel.exchangeToJson(exchange);
			String username = jsonToModel.getUsername(jsonObject);
			String password = jsonToModel.getPassword(jsonObject);
			int playerID = -1;
			
			
			exchange.getResponseHeaders().set("Content-Type", "text/html");
			
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
			headers.add("Set-cookie", header);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
			
		} catch(NullPointerException e) {
			exchange.getResponseHeaders().set("Content-Type", "text/html");
			logger.log(Level.SEVERE, "Null Pointer Exception.");
			response = "Invalid Keys for the requested operation.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());
		} catch (Exception e) {
			exchange.getResponseHeaders().set("Content-Type", "text/html");
			logger.log(Level.SEVERE, e.getMessage(), e);
			response = e.getMessage();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());
		} finally {
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
		}
	}

}
