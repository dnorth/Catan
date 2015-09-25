package server.proxy;

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.ClientModel;
import client.models.Game;
import client.models.Resources;

public class MockServerProxy implements IProxy{

	
	//GAME API
	@Override
	public ClientModel getGameModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String resetGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeCommandList(ArrayList<String> commands) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getExecutedCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAI(String soldierType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> listAI() {
		// TODO Auto-generated method stub
		return null;
	}

	
	//GAMES PROXY
	@Override
	public ArrayList<Game> getGamesList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game createGame(boolean randomTiles, boolean randomNumbers,
			boolean randomPorts, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean joinGame(int gameId, String color) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveGame(int gameId, String fileName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Game loadGame(String gameName) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//MOVES API
	@Override
	public String sendChat(int playerIndex, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rollNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String robPlayer(int playerIndex, int victimIndex,
			HexLocation hexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String finishTurn(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buyDevCard(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String playYearOfPlenty(int playerIndex, int resource1, int resource2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String playRoadBuilding(int playerIndex, EdgeLocation edgeLocation1,
			EdgeLocation edgeLocation2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String playSoldier(int playerIndex, int victimIndex,
			HexLocation newRobberHexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String playMonopoly(int playerIndex, String resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String playMonument(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildRoad(int playerIndex, EdgeLocation roadLocation,
			boolean free) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildSettlement(int playerIndex,
			VertexLocation settlementLocation, boolean free) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildCity(int playerIndex, VertexLocation cityLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String offerTrade(int playerIndex, Resources resourceList,
			int receivingPlayerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String acceptTrade(int playerIndex, boolean willAccept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String maritimeTrade(int playerIndex, int ratio,
			String inputResource, String outputResource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String discardCards(int playerIndex, Resources discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//USER API
	@Override
	public boolean userLogin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userRegister() {
		// TODO Auto-generated method stub
		return false;
	}

	
	//UTIL API
	@Override
	public void utilChangeLogLevel() {
		// TODO Auto-generated method stub
		
	}

	

}
