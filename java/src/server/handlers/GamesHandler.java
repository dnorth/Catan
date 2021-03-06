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
import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.authentication.Authenticate;
import server.exceptions.ContextNotFoundException;
import server.exceptions.MissingCookieException;
import server.facade.GamesFacade;
import server.facade.iGamesFacade;
import server.model.ServerGame;

public class GamesHandler implements HttpHandler{

	Logger logger;
	iGamesFacade gamesFacade;
	JSONToModel jsonToModel = new JSONToModel();
	ModelToJSON modelToJSON = new ModelToJSON();

	public GamesHandler(iGamesFacade gamesFacade) {
		this.gamesFacade = gamesFacade;
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
			exchange.getResponseHeaders().set("Content-Type", "application/json");
			switch (specificContext){
			case "list":
				List<ServerGame> games = gamesFacade.listGames();
				JsonArray serverGamesObject = modelToJSON.generateServerGamesObject(games);
				response = serverGamesObject.toString();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
				break;
			case "join":
				exchange.getResponseHeaders().set("Content-Type", "text/html");
				cookie = jsonToModel.getCookieFromExchange(exchange);
				if(Authenticate.isValidCookie(cookie, false)) {	
					JsonObject jsonObject = jsonToModel.exchangeToJson(exchange);
					int pID = gamesFacade.getServerData().getPlayerID(jsonToModel.getName(cookie), jsonToModel.getPassword(cookie));
					int gID = jsonToModel.getGameID(jsonObject);
					String color = jsonToModel.getColor(jsonObject);
					gamesFacade.joinGame(pID, gID, color);
					Headers headers = exchange.getResponseHeaders();
					String header = "catan.game=" + String.valueOf(gID) + ";Path=/;";
					response = "Success";
					headers.add("Set-cookie", header);
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
				
				} else {
					throw new MissingCookieException("Invalid Cookie. Trying to break our code, eh? Nice try.");
				}
				
				break;
			case "create":
				JsonObject jsonObject = jsonToModel.exchangeToJson(exchange);
				GameInfo gameInfo = gamesFacade.createGame(jsonToModel.getRandomTiles(jsonObject), jsonToModel.getRandomNumbers(jsonObject), jsonToModel.getRandomPorts(jsonObject), jsonToModel.getName(jsonObject));
				JsonObject gameInfoObject = modelToJSON.generateGameInfoObject(gameInfo);
				response = gameInfoObject.toString();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
				break;
			default:
				throw new ContextNotFoundException("404 Context Not Found.");
		
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
