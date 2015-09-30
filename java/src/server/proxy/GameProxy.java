package server.proxy;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import client.models.ClientModel;

public class GameProxy extends ServerProxy{

	/**
	 * @return 
	 * @pre Must log in and join a game. This comes from the model, not the server???
	 * @post null if there is no game
	 * 
	 */
	public JsonObject getGameModel (String userCookie){
        
		JsonObject inputObject = new JsonObject();
		inputObject.addProperty("Cookie", userCookie);
		JsonObject model = null; //The API says "GameModel" Is this the same thing??
		try {
			model = doPost("/game/model", inputObject);
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
	public JsonObject resetGame (){
		JsonObject clientModelJSON = null;
        try {
            clientModelJSON = doPost("/game/reset", null);
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
	public JsonObject executeCommandList (JsonObject commands){
		JsonObject clientModelJSON = null;
        try {
            clientModelJSON = doPost("/game/commands", commands);
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
	public JsonObject getExecutedCommands (){
		JsonObject executedCommands = null;
        try {
            executedCommands = doGet("/game/commands");
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
	public JsonObject listAI (){
		JsonObject AItypes = null;
        try {
            AItypes = doGet("/game/listAI");
        } catch (ClientException e) {
        	e.printStackTrace();
        }
        return AItypes;
    }
}
