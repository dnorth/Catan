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

	private String userCookie = "catan.user=%7B%22authentication%22%3A%22-484719082%22%2C%22name%22%3A%22Tommy420112513%22%2C%22password%22%3A%22Williams%22%2C%22playerID%22%3A20%7D";
	private String gameCookie = "; catan.game= 1";
	
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
		return this.returnSuccess(false, false);
	}

	@Override
	public JsonObject listAI() {
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
		return this.returnSuccess(true, true);
	}

	@Override
	public JsonObject saveGame(JsonObject data) { //int gameId, String fileName
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
	public JsonObject sendChat(JsonObject data, JsonObject optionalCookies) { //int playerIndex, String content
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject rollNumber(JsonObject optionalCookies) {
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject robPlayer(JsonObject data, JsonObject optionalCookies) { //int playerIndex, int victimIndex, HexLocation hexLocation
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject finishTurn(JsonObject data, JsonObject optionalCookies) { //int playerIndex
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject buyDevCard(JsonObject data, JsonObject optionalCookies) { //int playerIndex
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject playYearOfPlenty(JsonObject data, JsonObject optionalCookies) { //int playerIndex, int resource1, int resource2
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject playRoadBuilding(JsonObject data, JsonObject optionalCookies) { //int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject playSoldier(JsonObject data, JsonObject optionalCookies) { //int playerIndex, int victimIndex, HexLocation newRobberHexLocation
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject playMonopoly(JsonObject data, JsonObject optionalCookies) { //int playerIndex, String resource
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject playMonument(JsonObject data, JsonObject optionalCookies) { //int playerIndex
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject buildRoad(JsonObject data, JsonObject optionalCookies) { //int playerIndex, EdgeLocation roadLocation, boolean free
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject buildSettlement(JsonObject data, JsonObject optionalCookies) { //int playerIndex, VertexLocation settlementLocation, boolean free
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject buildCity(JsonObject data, JsonObject optionalCookies) { //int playerIndex, VertexLocation cityLocation
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject offerTrade(JsonObject data, JsonObject optionalCookies) { //int playerIndex, Resources resourceList, int receivingPlayerIndex
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject acceptTrade(JsonObject data, JsonObject optionalCookies) { //int playerIndex, boolean willAccept
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject maritimeTrade(JsonObject data, JsonObject optionalCookies) { //int playerIndex, int ratio, String inputResource, String outputResource
		return this.getMockServerFileContents("sample_model.json");
	}


	@Override
	public JsonObject discardCards(JsonObject data, JsonObject optionalCookies) { //int playerIndex, Resources discardedCards
		return this.getMockServerFileContents("sample_model.json");
	}

	
	//USER API
	@Override
	public JsonObject userLogin(JsonObject user) {
		return this.returnSuccess(true, false);
	}

	@Override
	public JsonObject userRegister(JsonObject object) {
		return this.returnSuccess(true, false);
	}

	//UTIL API
		@Override
		public void utilChangeLogLevel() {
			// **DON'T** implement for Phase 1
		}
		
	private JsonObject returnSuccess(boolean returnUserCookie, boolean returnGameCookie) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("Response-body", "Success");
		if(returnUserCookie) {
			if(returnGameCookie) {
				jsonObject.addProperty("Cookies", userCookie+gameCookie);
			}
			else {
				jsonObject.addProperty("Cookies", userCookie);
			}
		}
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
	    System.out.println(file.getAbsolutePath());
	    System.out.println(file.exists());
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
