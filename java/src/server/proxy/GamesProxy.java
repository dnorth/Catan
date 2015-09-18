package server.proxy;

import java.util.ArrayList;
import java.util.List;

import client.models.Game;

public class GamesProxy extends ServerProxy{


	/**
	 * @pre Server is running
	 * @post 
	 * @return
	 */
	public List<Game> getGamesList (){
		ArrayList<Game> games = null;
		try {
			games = (ArrayList<Game>)doGet("/games/list");
		} catch (ClientException e) {
			e.printStackTrace();
		}
        return games;
    }

	/**
	 * @pre
	 * @post
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * @param name
	 * @return
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
	 * @pre
	 * @post
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
	 * @pre
	 * @post
	 * @param gameId
	 * @param fileName
	 * @return
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
	 * @pre
	 * @post
	 * @param gameName
	 * @return
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
