package server.proxy;

import java.util.ArrayList;

import client.models.ClientModel;

public class GameProxy extends ServerProxy{

	/**
	 * @return 
	 * @pre Must log in and join a game
	 * @post null if there is no game
	 * 
	 */
	public ClientModel getGameModel (){
        
		ClientModel model = null; //The API says "GameModel" Is this the same thing??
		try {
			model = (ClientModel)doPost("game/model", null);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return model;
    }
	
	/**
	 * @pre Must log in and join a game
	 * @post if default game, reset to initial placement. If user-created, reset to very beginning.
	 * @return clientModel JSON
	 */
	public String resetGame (){
		String clientModelJSON = null;
        try {
            clientModelJSON = (String)doPost("/game/reset", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
        return clientModelJSON;
    }

	/**
	 * @pre commands are obtained through getGameCommends() method. Must login and join a game
	 * @post all commands have been executed
	 * @param ArrayList of commands to be executed
	 * @return clientModel JSON (identical to /game/model)
	 */
	public String executeCommandList (ArrayList<String> commands){
		String clientModelJSON = null;
        try {
            clientModelJSON = (String)doPost("/game/commands", commands);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
		return clientModelJSON;
    }

	/**
	 * @pre Must log in and join game
	 * @post no change
	 * @return ArrayList of executed commands
	 */
	public ArrayList<String> getExecutedCommands (){
		ArrayList<String> executedCommands = null;
        try {
            executedCommands = (ArrayList<String>)doGet("/game/commands");
        } catch (ClientException e) {
        	e.printStackTrace();
        }
		return executedCommands;
    }

	/**
	 * @pre SoldierType is "LARGEST_ARMY"
	 * @post a new AI has been added to the game
	 * @param soldierType
	 */
	public void addAI (String soldierType){
        try {
            doPost("/game/addAI", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }

	/**
	 * @pre Must log in and join game
	 * @post this will return a list with only 1 entry which will be "LARGEST_ARMY"
	 * @return the supported types of AI
	 */
	public ArrayList<String> listAI (){
		ArrayList<String> AItypes = null;
        try {
            AItypes = (ArrayList<String>)doGet("/game/listAI");
        } catch (ClientException e) {
        	e.printStackTrace();
        }
        return AItypes;
    }
}
