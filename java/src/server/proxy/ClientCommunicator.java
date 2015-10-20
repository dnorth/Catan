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
	
	public ClientCommunicator(String hostname, int port) {
		this.host = hostname;
		this.port = port;
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
	public JsonObject addAI(String soldierType, JsonObject optionalCookies) {
		return gameProxy.addAI(soldierType, optionalCookies);
	}

	@Override
	public JsonObject listAI() {
		return gameProxy.listAI();
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
	public JsonObject saveGame(JsonObject data) { // int gameId, String fileName
		return gamesProxy.saveGame(data);
	}

	@Override
	public JsonObject loadGame(JsonObject gameName) {
		return gamesProxy.loadGame(gameName);
	}

	
	
	//MOVES API
	@Override
	public JsonObject sendChat(JsonObject data) { // int playerIndex, String content
		return movesProxy.sendChat(data);
	}

	@Override
	public JsonObject rollNumber() {
		return movesProxy.rollNumber();
	}

	@Override
	public JsonObject robPlayer(JsonObject data) { // int playerIndex, int victimIndex, HexLocation hexLocation
		return movesProxy.robPlayer(data);
	}

	@Override
	public JsonObject finishTurn(JsonObject data) { //int playerIndex
		return movesProxy.finishTurn(data);
	}

	@Override
	public JsonObject buyDevCard(JsonObject data) { //int playerIndex
		return movesProxy.buyDevCard(data);
	}

	@Override
	public JsonObject playYearOfPlenty(JsonObject data) { //int playerIndex, int resource1, int resource2
		return movesProxy.playYearOfPlenty(data);
	}

	@Override
	public JsonObject playRoadBuilding(JsonObject data) { //int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2
		return movesProxy.playRoadBuilding(data);
	}

	@Override
	public JsonObject playSoldier(JsonObject data) { // int playerIndex, int victimIndex, HexLocation newRobberHexLocation
		return movesProxy.playSoldier(data);
	}

	/*
	 * int playerIndex
	 * String resource
	 */
	@Override
	public JsonObject playMonopoly(JsonObject data) {
		return movesProxy.playMonopoly(data);
	}

	@Override
	public JsonObject playMonument(JsonObject data) { //int playerIndex
		return movesProxy.playMonument(data);
	}

	@Override
	public JsonObject buildRoad(JsonObject data) { //int playerIndex, EdgeLocation roadLocation, boolean free
		return movesProxy.buildRoad(data);
	}

	@Override
	public JsonObject buildSettlement(JsonObject data) { //int playerIndex, VertexLocation settlementLocation, boolean free
		return movesProxy.buildSettlement(data);
	}

	@Override
	public JsonObject buildCity(JsonObject data) { //int playerIndex, VertexLocation cityLocation
		return movesProxy.buildCity(data);
	}

	@Override
	public JsonObject offerTrade(JsonObject data) { //int playerIndex, Resources resourceList, int receivingPlayerIndex
		return movesProxy.offerTrade(data);
	}

	@Override
	public JsonObject acceptTrade(JsonObject data) { //int playerIndex, boolean willAccept
		return movesProxy.acceptTrade(data);
	}

	@Override
	public JsonObject maritimeTrade(JsonObject data) { //int playerIndex, int ratio, String inputResource, String outputResource
		return movesProxy.maritimeTrade(data);
	}

	@Override
	public JsonObject discardCards(JsonObject data) { //int playerIndex, Resources discardedCards
		return movesProxy.discardCards(data);
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
