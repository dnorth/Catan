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
	public JsonObject addAI(JsonObject AIObject, JsonObject optionalCookies) {
		return gameProxy.addAI(AIObject, optionalCookies);
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
	public JsonObject createGame(JsonObject inputGame, JsonObject optionalCookies) {
		return gamesProxy.createGame(inputGame, optionalCookies);
	}

	@Override
	public JsonObject joinGame(JsonObject gameData, JsonObject optionalCookies) {
		return gamesProxy.joinGame(gameData, optionalCookies);
	}

	@Override
	public JsonObject saveGame(JsonObject data, JsonObject optionalCookies) { // int gameId, String fileName
		return gamesProxy.saveGame(data, optionalCookies);
	}

	@Override
	public JsonObject loadGame(JsonObject gameName, JsonObject optionalCookies) {
		return gamesProxy.loadGame(gameName, optionalCookies);
	}

	
	
	//MOVES API
	@Override
	public JsonObject sendChat(JsonObject data, JsonObject optionalCookies) { // int playerIndex, String content
		return movesProxy.sendChat(data, optionalCookies);
	}

	@Override
	public JsonObject rollNumber(JsonObject data, JsonObject optionalCookies) {
		return movesProxy.rollNumber(data, optionalCookies);
	}

	@Override
	public JsonObject robPlayer(JsonObject data, JsonObject optionalCookies) { // int playerIndex, int victimIndex, HexLocation hexLocation
		return movesProxy.robPlayer(data, optionalCookies);
	}

	@Override
	public JsonObject finishTurn(JsonObject data, JsonObject optionalCookies) { //int playerIndex
		return movesProxy.finishTurn(data, optionalCookies);
	}

	@Override
	public JsonObject buyDevCard(JsonObject data, JsonObject optionalCookies) { //int playerIndex
		return movesProxy.buyDevCard(data, optionalCookies);
	}

	@Override
	public JsonObject playYearOfPlenty(JsonObject data, JsonObject optionalCookies) { //int playerIndex, int resource1, int resource2
		return movesProxy.playYearOfPlenty(data, optionalCookies);
	}

	@Override
	public JsonObject playRoadBuilding(JsonObject data, JsonObject optionalCookies) { //int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2
		return movesProxy.playRoadBuilding(data, optionalCookies);
	}

	@Override
	public JsonObject playSoldier(JsonObject data, JsonObject optionalCookies) { // int playerIndex, int victimIndex, HexLocation newRobberHexLocation
		return movesProxy.playSoldier(data, optionalCookies);
	}

	/*
	 * int playerIndex
	 * String resource
	 */
	@Override
	public JsonObject playMonopoly(JsonObject data, JsonObject optionalCookies) {
		return movesProxy.playMonopoly(data, optionalCookies);
	}

	@Override
	public JsonObject playMonument(JsonObject data, JsonObject optionalCookies) { //int playerIndex
		return movesProxy.playMonument(data, optionalCookies);
	}

	@Override
	public JsonObject buildRoad(JsonObject data, JsonObject optionalCookies) { //int playerIndex, EdgeLocation roadLocation, boolean free
		return movesProxy.buildRoad(data, optionalCookies);
	}

	@Override
	public JsonObject buildSettlement(JsonObject data, JsonObject optionalCookies) { //int playerIndex, VertexLocation settlementLocation, boolean free
		return movesProxy.buildSettlement(data, optionalCookies);
	}

	@Override
	public JsonObject buildCity(JsonObject data, JsonObject optionalCookies) { //int playerIndex, VertexLocation cityLocation
		return movesProxy.buildCity(data, optionalCookies);
	}

	@Override
	public JsonObject offerTrade(JsonObject data, JsonObject optionalCookies) { //int playerIndex, Resources resourceList, int receivingPlayerIndex
		return movesProxy.offerTrade(data, optionalCookies);
	}

	@Override
	public JsonObject acceptTrade(JsonObject data, JsonObject optionalCookies) { //int playerIndex, boolean willAccept
		return movesProxy.acceptTrade(data, optionalCookies);
	}

	@Override
	public JsonObject maritimeTrade(JsonObject data, JsonObject optionalCookies) { //int playerIndex, int ratio, String inputResource, String outputResource
		return movesProxy.maritimeTrade(data, optionalCookies);
	}

	@Override
	public JsonObject discardCards(JsonObject data, JsonObject optionalCookies) { //int playerIndex, Resources discardedCards
		return movesProxy.discardCards(data, optionalCookies);
	}

	
	//USER API
	@Override
	public JsonObject userLogin(JsonObject user, JsonObject optionalCookies) throws ClientException {
		return userProxy.userLogin(user, optionalCookies);
	}

	@Override
	public JsonObject userRegister(JsonObject user, JsonObject optionalCookies) throws ClientException {
		return userProxy.userRegister(user, optionalCookies);
	}

	
	//UTIL API
	@Override
	public void utilChangeLogLevel() {
		utilProxy.utilChangeLogLevel();
	}
	
}
