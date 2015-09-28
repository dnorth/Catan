package server.proxy;

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import com.google.gson.JsonObject;

import client.models.ClientModel;
import client.models.Game;
import client.models.Resources;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class MockProxy implements IProxy{


	@Override
	public void utilChangeLogLevel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JsonObject getGameModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject resetGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject executeCommandList(JsonObject commands) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject getExecutedCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAI(String soldierType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JsonObject listAI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject getGamesList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject joinGame(int gameId, String color) {
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

	@Override
	public JsonObject userLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject userRegister(JsonObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
