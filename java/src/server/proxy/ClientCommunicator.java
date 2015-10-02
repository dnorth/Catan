package server.proxy;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.ClientModel;
import client.models.Game;
import client.models.Resources;

public class ClientCommunicator implements IProxy{

	private GameProxy gameProxy;
	private GamesProxy gamesProxy;
	private MovesProxy movesProxy;
	private UtilProxy utilProxy;
	private UserProxy userProxy;
	private ClientModel clientModel;
	
	private String host;
	private int port;
	
	
	/**
	 * 
	 */
	public ClientCommunicator() {
		host = "localhost";
		port = 8081;
		initialize();
	}
	
	private void initialize() {
		gameProxy = new GameProxy();
		gamesProxy = new GamesProxy();
		movesProxy = new MovesProxy();
		utilProxy = new UtilProxy();
		userProxy = new UserProxy();
	}
	
	/**
	 * @param newModel
	 */
	public void setClient(ClientModel newModel) {
		clientModel = newModel;
	}
	
	
	//GAME API
	@Override
	public JsonObject getGameModel(JsonObject optionalCookies) {
		return gameProxy.getGameModel(optionalCookies);
	}

	@Override
	public JsonObject resetGame(JsonObject optionalCookies) {
		return gameProxy.resetGame(optionalCookies);
	}

	@Override
	public JsonObject executeCommandList(JsonObject commands, JsonObject optionalCookies) {
		return gameProxy.executeCommandList(commands, optionalCookies);
	}

	@Override
	public JsonObject getExecutedCommands(JsonObject optionalCookies) {
		return gameProxy.getExecutedCommands(optionalCookies);
	}

	@Override
	public void addAI(String soldierType, JsonObject optionalCookies) {
		gameProxy.addAI(soldierType, optionalCookies);
	}

	@Override
	public JsonObject listAI(JsonObject optionalCookies) {
		return gameProxy.listAI(optionalCookies);
	}

	
	//GAMES API
	@Override
	public JsonObject getGamesList() {
		return gamesProxy.getGamesList();
	}

	@Override
	public JsonObject createGame(JsonObject inputGame) {
		return gamesProxy.createGame(inputGame);
	}

	@Override
	public JsonObject joinGame(JsonObject gameData) {
		return gamesProxy.joinGame(gameData);
	}

	@Override
	public JsonObject saveGame(int gameId, String fileName) {
		return gamesProxy.saveGame(gameId, fileName);
	}

	@Override
	public JsonObject loadGame(JsonObject gameName) {
		return gamesProxy.loadGame(gameName);
	}

	
	
	//MOVES API
	@Override
	public JsonObject sendChat(int playerIndex, String content) {
		return movesProxy.sendChat(playerIndex, content);
	}

	@Override
	public JsonObject rollNumber() {
		return movesProxy.rollNumber();
	}

	@Override
	public JsonObject robPlayer(int playerIndex, int victimIndex,
			HexLocation hexLocation) {
		return movesProxy.robPlayer(playerIndex, victimIndex, hexLocation);
	}

	@Override
	public JsonObject finishTurn(int playerIndex) {
		return movesProxy.finishTurn(playerIndex);
	}

	@Override
	public JsonObject buyDevCard(int playerIndex) {
		return movesProxy.buyDevCard(playerIndex);
	}

	@Override
	public JsonObject playYearOfPlenty(int playerIndex, int resource1, int resource2) {
		return movesProxy.playYearOfPlenty(playerIndex, resource1, resource2);
	}

	@Override
	public JsonObject playRoadBuilding(int playerIndex, EdgeLocation edgeLocation1,
			EdgeLocation edgeLocation2) {
		return movesProxy.playRoadBuilding(playerIndex, edgeLocation1, edgeLocation2);
	}

	@Override
	public JsonObject playSoldier(int playerIndex, int victimIndex,
			HexLocation newRobberHexLocation) {
		return movesProxy.playSoldier(playerIndex, victimIndex, newRobberHexLocation);
	}

	@Override
	public JsonObject playMonopoly(int playerIndex, String resource) {
		return movesProxy.playMonopoly(playerIndex, resource);
	}

	@Override
	public JsonObject playMonument(int playerIndex) {
		return movesProxy.playMonument(playerIndex);
	}

	@Override
	public JsonObject buildRoad(int playerIndex, EdgeLocation roadLocation,
			boolean free) {
		return movesProxy.buildRoad(playerIndex, roadLocation, free);
	}

	@Override
	public JsonObject buildSettlement(int playerIndex,
			VertexLocation settlementLocation, boolean free) {
		return movesProxy.buildSettlement(playerIndex, settlementLocation, free);
	}

	@Override
	public JsonObject buildCity(int playerIndex, VertexLocation cityLocation) {
		return movesProxy.buildCity(playerIndex, cityLocation);
	}

	@Override
	public JsonObject offerTrade(int playerIndex, Resources resourceList,
			int receivingPlayerIndex) {
		return movesProxy.offerTrade(playerIndex, resourceList, receivingPlayerIndex);
	}

	@Override
	public JsonObject acceptTrade(int playerIndex, boolean willAccept) {
		return movesProxy.acceptTrade(playerIndex, willAccept);
	}

	@Override
	public JsonObject maritimeTrade(int playerIndex, int ratio,
			String inputResource, String outputResource) {
		return movesProxy.maritimeTrade(playerIndex, ratio, inputResource, outputResource);
	}

	@Override
	public JsonObject discardCards(int playerIndex, Resources discardedCards) {
		return movesProxy.discardCards(playerIndex, discardedCards);
	}

	
	//USER API
	@Override
	public JsonObject userLogin(JsonObject user) {
		return userProxy.userLogin(user);
	}

	@Override
	public JsonObject userRegister(JsonObject user) {
		return userProxy.userRegister(user);
	}

	
	//UTIL API
	@Override
	public void utilChangeLogLevel() {
		utilProxy.utilChangeLogLevel();
	}
	
}
