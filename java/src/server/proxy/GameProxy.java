package server.proxy;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import client.models.ClientModel;

public class GameProxy extends ServerProxy{

	public GameProxy(String host, int port) {
		super(host, port);
	}

	/**
	 * @return 
	 * @pre Must log in and join a game. This comes from the model, not the server???
	 * @post null if there is no game
	 * 
	 */
	public JsonObject getGameModel (JsonObject optionalCookies){
        
		JsonObject model = null; //The API says "GameModel" Is this the same thing??
		try {
			model = doGet("/game/model", optionalCookies).get("Response-body").getAsJsonObject();
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
	public JsonObject resetGame (JsonObject optionalCookies){
		JsonObject clientModelJSON = null;
        try {
            clientModelJSON = doPost("/game/reset", null, optionalCookies);
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
	public JsonObject executeCommandList (JsonObject commands, JsonObject optionalCookies){
		JsonObject clientModelJSON = null;
        try {
            clientModelJSON = doPost("/game/commands", commands, optionalCookies);
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
	public JsonObject getExecutedCommands (JsonObject optionalCookies){
		JsonObject executedCommands = null;
        try {
            executedCommands = doGet("/game/commands", optionalCookies);
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
	public JsonObject addAI (JsonObject AIObject, JsonObject optionalCookies){
        JsonObject addAI = null;
		try {
            addAI = doPost("/game/addAI", AIObject, optionalCookies);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
		return addAI;
    }

	/**
	 * @pre Must log in and join game
	 * @post this will return a list with only 1 entry which will be "LARGEST_ARMY"
	 * @return the supported types of AI
	 */
	public JsonObject listAI (){
		JsonObject AItypes = null;
        try {
            AItypes = doGet("/game/listAI", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
        return AItypes;
    }
}
