package server.handlers.games;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.facade.GamesFacade;
import server.model.ServerGame;

import com.google.gson.JsonArray;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class ListHandler.
 */
public class ListHandler implements HttpHandler {
	Logger logger;
	JSONToModel jsonToModel = new JSONToModel();
	ModelToJSON modelToJSON = new ModelToJSON();
	GamesFacade gamesFacade;
	
	public ListHandler(GamesFacade gamesFacade) {
		this.gamesFacade = gamesFacade;
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 * **NOTE** Does NOT check for authentication.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		logger = Logger.getLogger("Catan");
		logger.info("Handling List");
		String response = "";
		try{
			if (gamesFacade == null) logger.info("gamesFacade is null...");
			List<ServerGame> games = gamesFacade.listGames();
			JsonArray serverGamesObject = modelToJSON.generateServerGamesObject(games);
//			logger.info("Server Games Object: " + serverGamesObject.toString());
			response = serverGamesObject.toString();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
		}
		catch( Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			response = "Error listing games";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, response.length());
			return;
		} finally {
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
		}
	}
}
