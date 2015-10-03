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
		return this.getMockServerFileContents("sample_model.json");
	}

	@Override
	public JsonObject resetGame(JsonObject optionalCookies) {
		// **DON'T** implement for Phase 1
		return null;
	}

	@Override
	public JsonObject executeCommandList(JsonObject commands, JsonObject optionalCookies) {
		// **DON'T** implement for Phase 1
		return null;
	}

	@Override
	public JsonObject getExecutedCommands(JsonObject optionalCookies) {
		// **DON'T** implement for Phase 1
		return null;
	}

	@Override
	public JsonObject addAI(String soldierType, JsonObject optionalCookies) {
		return this.returnSuccess();
	}

	@Override
	public JsonObject listAI(JsonObject optionalCookies) {
		return this.getMockServerFileContents("listAI.json");
	}

	
	//Games API
	@Override
	public JsonObject getGamesList() {
		return this.getMockServerFileContents("getGamesList.json");
	}

	@Override
	public JsonObject createGame(JsonObject inputGame) {
		return this.getMockServerFileContents("createGame.json");
	}

	@Override
	public JsonObject joinGame(JsonObject gameData) {
		return this.returnSuccess();
	}

	@Override
	public JsonObject saveGame(int gameId, String fileName) {
		// **DON'T** implement for Phase 1
		return null;
	}

	@Override
	public JsonObject loadGame(JsonObject gameName) {
		// **DON'T** implement for Phase 1
		return null;
	}



	//MOVES API
	@Override
	public JsonObject sendChat(int playerIndex, String content) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject rollNumber() {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject robPlayer(int playerIndex, int victimIndex, HexLocation hexLocation) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject finishTurn(int playerIndex) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject buyDevCard(int playerIndex) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject playYearOfPlenty(int playerIndex, int resource1, int resource2) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject playRoadBuilding(int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject playSoldier(int playerIndex, int victimIndex, HexLocation newRobberHexLocation) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject playMonopoly(int playerIndex, String resource) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject playMonument(int playerIndex) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject buildRoad(int playerIndex, EdgeLocation roadLocation, boolean free) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject buildSettlement(int playerIndex, VertexLocation settlementLocation, boolean free) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject buildCity(int playerIndex, VertexLocation cityLocation) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject offerTrade(int playerIndex, Resources resourceList, int receivingPlayerIndex) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject acceptTrade(int playerIndex, boolean willAccept) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject maritimeTrade(int playerIndex, int ratio, String inputResource, String outputResource) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject discardCards(int playerIndex, Resources discardedCards) {
		return this.getMockServerFileContents("sample_model.json");
	}

	
	//USER API
	@Override
	public JsonObject userLogin(JsonObject user) {
		return this.returnSuccess();
	}

	@Override
	public JsonObject userRegister(JsonObject object) {
		return this.returnSuccess();
	}

	//UTIL API
		@Override
		public void utilChangeLogLevel() {
			// **DON'T** implement for Phase 1
		}
		
	private JsonObject returnSuccess() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("Success", true);
		return jsonObject;
	}

	private JsonObject getMockServerFileContents(String pathname) {
		JsonParser jsonParser = new JsonParser();
		JsonObject mockServerModel = new JsonObject();
		
		String s = null;
		try {
			s = readFile("mock_server/" + pathname);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mockServerModel = (JsonObject)jsonParser.parse(s);
		
		return mockServerModel;
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
