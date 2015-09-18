package server.proxy;

public class GameProxy {

	public void getGameModel (){
        try {
            doPost("/game/model", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
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
