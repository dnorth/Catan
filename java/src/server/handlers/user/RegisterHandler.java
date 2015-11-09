package server.handlers.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import shared.locations.VertexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterHandler.
 */
public class RegisterHandler implements HttpHandler {
	Logger logger;
	Gson gson; 
	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger = Logger.getLogger("Catan");
		logger.info("Handling Register");
		try{
			String requestBody = getString(exchange);
			logger.info("MESSAGE: " + requestBody);
			JsonObject registerObject = new JsonParser().parse(requestBody).getAsJsonObject();
		
			logger.info("Username" + registerObject.get("username").getAsString());
			
		// TODO Auto-generated method stub
			exchange.getResponseHeaders().set("Content-Type", "application/json");
			String response = "Success";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
		}
		catch( Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
	}
	
	public String getString(HttpExchange exchange) {
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(exchange.getRequestBody(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 BufferedReader br = new BufferedReader(isr);
		 String value = "NOT WORKING";
		try {
			value = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

}


