package server.proxy;

import client.models.ClientModel;

public class GameProxy extends ServerProxy{

	public void getGameModel (){
        
		ClientModel model = null; //The API says "GameModel" Is this the same thing??
		
    }
	
	public void resetGame (){
        try {
            doPost("/game/reset", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }

	public void executeCommandList (){
        try {
            doPost("/game/commands", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }

	public void getExecutedCommands (){
        try {
            doGet("/game/commands");
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }

	public void addAI (){
        try {
            doPost("/game/addAI", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }

	public void listAI (){
        try {
            doGet("/game/listAI");
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }
}
