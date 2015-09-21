package server.proxy;

import java.util.ArrayList;
import java.util.List;

import client.models.Game;

public class GamesProxy extends ServerProxy{


	/**
	 * @pre NA
	 * @post NA
	 * @return An ArrayList of all games in progress
	 */
	public ArrayList<Game> getGamesList (){
		ArrayList<Game> games = null;
		try {
			games = (ArrayList<Game>)doGet("/games/list");
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
	public Game createGame (boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name){
	
		
		Game newGame = null;
		try {
			newGame = (Game)doPost("/games/create", null);
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
	public boolean joinGame (int gameId, String color){
		
		boolean success = false;
		try {
			success = (boolean)doPost("/games/join", null);
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
	public boolean saveGame (int gameId, String fileName){

        boolean success = false;
        
        try {
			success = (boolean)doPost("/games/save", null);
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
	public Game loadGame (String gameName){

		Game game = null;
		
		try {
			game = (Game) doPost("/games/load", gameName);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return game;        
    }
}
