package server.proxy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.ClientModel;
import client.models.Game;
import client.models.Resources;

public class MockServerProxy implements IProxy{

	//GAME API
	@Override
	public JsonObject getGameModel(JsonObject optionalCookies) {
		
		JsonParser jsonParser = new JsonParser();
		JsonObject mockServerModel = new JsonObject();
		
		String s = null;
		try {
			s = readFile("sample_model2.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mockServerModel = (JsonObject)jsonParser.parse(s);
		
		return mockServerModel;
	}

	@Override
	public JsonObject resetGame(JsonObject optionalCookies) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject executeCommandList(JsonObject commands, JsonObject optionalCookies) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject getExecutedCommands(JsonObject optionalCookies) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAI(String soldierType, JsonObject optionalCookies) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JsonObject listAI(JsonObject optionalCookies) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//Games API
	@Override
	public JsonObject getGamesList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject createGame(JsonObject inputGame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject joinGame(JsonObject gameData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject saveGame(int gameId, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject loadGame(JsonObject gameName) {
		// TODO Auto-generated method stub
		return null;
	}



	//MOVES API
	@Override
	public JsonObject sendChat(int playerIndex, String content) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject rollNumber() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject robPlayer(int playerIndex, int victimIndex, HexLocation hexLocation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject finishTurn(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject buyDevCard(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject playYearOfPlenty(int playerIndex, int resource1, int resource2) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject playRoadBuilding(int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject playSoldier(int playerIndex, int victimIndex, HexLocation newRobberHexLocation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject playMonopoly(int playerIndex, String resource) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject playMonument(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject buildRoad(int playerIndex, EdgeLocation roadLocation, boolean free) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject buildSettlement(int playerIndex, VertexLocation settlementLocation, boolean free) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject buildCity(int playerIndex, VertexLocation cityLocation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject offerTrade(int playerIndex, Resources resourceList, int receivingPlayerIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject acceptTrade(int playerIndex, boolean willAccept) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject maritimeTrade(int playerIndex, int ratio, String inputResource, String outputResource) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JsonObject discardCards(int playerIndex, Resources discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//USER API
	@Override
	public JsonObject userLogin(JsonObject user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject userRegister(JsonObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	//UTIL API
		@Override
		public void utilChangeLogLevel() {
			// TODO Auto-generated method stub
			
		}


	private String readFile(String pathname) throws IOException {

	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}
}
