package server.proxy;

import com.google.gson.JsonObject;

public class GamesProxy extends ServerProxy{

	public GamesProxy(String host, int port) {
		super(host, port);
	}

	/**
	 * @pre NA
	 * @post NA
	 * @return An ArrayList of all games in progress
	 */
	public JsonObject getGamesList (){
		JsonObject games = null;
		try {
			games = doGet("/games/list", null);
		} catch (ClientException e) {
			e.printStackTrace();
		}
        return games;
    }

	/**
	 * @pre NA
	 * @post New game has been created
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * @param name
	 * @return newly created game
	 */
	public JsonObject createGame (JsonObject inputGame, JsonObject optionalCookies){
	
		
		JsonObject newGame = null;
		try {
			newGame = doPost("/games/create", inputGame,  optionalCookies);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
		return newGame;
	}
	

	/**
	 * @pre this is called with a specific player
	 * @post catan.game HTTP cookie for player set
	 * @param gameId
	 * @param color
	 * @return
	 */
	public JsonObject joinGame(JsonObject gameData, JsonObject optionalCookies){
		
		JsonObject success = null;
		try {
			success = doPost("/games/join", gameData, optionalCookies);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
		
    }

	/**
	 * @pre there is a current state of the specified game to be saved
	 * @post Game is saved
	 * @param gameId
	 * @param fileName
	 * @return boolean stating whether or not the save was successful
	 */
	public JsonObject saveGame (JsonObject data, JsonObject optionalCookies){ //int gameId, String fileName

		JsonObject success = null;
        
        try {
			success = doPost("/games/save", data, optionalCookies);
		} catch (ClientException e) {
			e.printStackTrace();
		}
        return success;
    }

	/**
	 * @pre There is a game to be loaded with intended name
	 * @post Game is loaded correctly
	 * @param gameName
	 * @return Game that was originally saved
	 */
	public JsonObject loadGame (JsonObject gameName, JsonObject optionalCookies){

		JsonObject game = null;
		
		try {
			game = doPost("/games/load", gameName, optionalCookies);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return game;        
    }
}
