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
	public ClientModel getGameModel() {
		return gameProxy.getGameModel();
	}

	@Override
	public String resetGame() {
		return gameProxy.resetGame();
	}

	@Override
	public String executeCommandList(ArrayList<String> commands) {
		return gameProxy.executeCommandList(commands);
	}

	@Override
	public ArrayList<String> getExecutedCommands() {
		return gameProxy.getExecutedCommands();
	}

	@Override
	public void addAI(String soldierType) {
		gameProxy.addAI(soldierType);
	}

	@Override
	public ArrayList<String> listAI() {
		return gameProxy.listAI();
	}

	
	//GAMES API
	@Override
	public ArrayList<Game> getGamesList() {
		return gamesProxy.getGamesList();
	}

	@Override
	public Game createGame(boolean randomTiles, boolean randomNumbers,
			boolean randomPorts, String name) {
		return gamesProxy.createGame(randomTiles, randomNumbers, randomPorts, name);
	}

	@Override
	public boolean joinGame(int gameId, String color) {
		return gamesProxy.joinGame(gameId, color);
	}

	@Override
	public boolean saveGame(int gameId, String fileName) {
		return gamesProxy.saveGame(gameId, fileName);
	}

	@Override
	public Game loadGame(String gameName) {
		return gamesProxy.loadGame(gameName);
	}

	
	
	//MOVES API
	@Override
	public String sendChat(int playerIndex, String content) {
		return movesProxy.sendChat(playerIndex, content);
	}

	@Override
	public String rollNumber() {
		return movesProxy.rollNumber();
	}

	@Override
	public String robPlayer(int playerIndex, int victimIndex,
			HexLocation hexLocation) {
		return movesProxy.robPlayer(playerIndex, victimIndex, hexLocation);
	}

	@Override
	public String finishTurn(int playerIndex) {
		return movesProxy.finishTurn(playerIndex);
	}

	@Override
	public String buyDevCard(int playerIndex) {
		return movesProxy.buyDevCard(playerIndex);
	}

	@Override
	public String playYearOfPlenty(int playerIndex, int resource1, int resource2) {
		return movesProxy.playYearOfPlenty(playerIndex, resource1, resource2);
	}

	@Override
	public String playRoadBuilding(int playerIndex, EdgeLocation edgeLocation1,
			EdgeLocation edgeLocation2) {
		return movesProxy.playRoadBuilding(playerIndex, edgeLocation1, edgeLocation2);
	}

	@Override
	public String playSoldier(int playerIndex, int victimIndex,
			HexLocation newRobberHexLocation) {
		return movesProxy.playSoldier(playerIndex, victimIndex, newRobberHexLocation);
	}

	@Override
	public String playMonopoly(int playerIndex, String resource) {
		return movesProxy.playMonopoly(playerIndex, resource);
	}

	@Override
	public String playMonument(int playerIndex) {
		return movesProxy.playMonument(playerIndex);
	}

	@Override
	public String buildRoad(int playerIndex, EdgeLocation roadLocation,
			boolean free) {
		return movesProxy.buildRoad(playerIndex, roadLocation, free);
	}

	@Override
	public String buildSettlement(int playerIndex,
			VertexLocation settlementLocation, boolean free) {
		return movesProxy.buildSettlement(playerIndex, settlementLocation, free);
	}

	@Override
	public String buildCity(int playerIndex, VertexLocation cityLocation) {
		return movesProxy.buildCity(playerIndex, cityLocation);
	}

	@Override
	public String offerTrade(int playerIndex, Resources resourceList,
			int receivingPlayerIndex) {
		return movesProxy.offerTrade(playerIndex, resourceList, receivingPlayerIndex);
	}

	@Override
	public String acceptTrade(int playerIndex, boolean willAccept) {
		return movesProxy.acceptTrade(playerIndex, willAccept);
	}

	@Override
	public String maritimeTrade(int playerIndex, int ratio,
			String inputResource, String outputResource) {
		return movesProxy.maritimeTrade(playerIndex, ratio, inputResource, outputResource);
	}

	@Override
	public String discardCards(int playerIndex, Resources discardedCards) {
		return movesProxy.discardCards(playerIndex, discardedCards);
	}

	
	//USER API
	@Override
	public boolean userLogin() {
		return userProxy.userLogin();
	}

	@Override
	public boolean userRegister(JsonObject object) {
		return userProxy.userRegister(object);
	}

	
	//UTIL API
	@Override
	public void utilChangeLogLevel() {
		utilProxy.utilChangeLogLevel();
	}
	
}
