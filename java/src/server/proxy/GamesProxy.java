package server.proxy;

public class GamesProxy {


	public void getGamesList (){
        try {
            doGet("/games/list");
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }

	public void createGame (){
        try {
            doPost("/games/create", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }
	

	public void joinGame (){
        try {
            doPost("/games/join", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }

	public void saveGame (){
        try {
            doPost("/games/save", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }

	public void loadGame (){
        try {
            doPost("/games/load", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }
}
