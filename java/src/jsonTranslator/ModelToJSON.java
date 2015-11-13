package jsonTranslator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import client.data.GameInfo;
import client.models.ClientModel;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.VertexObject;
import client.models.communication.MessageLine;
import client.models.communication.MessageList;
import client.models.mapdata.Board;
import client.models.mapdata.EdgeLocation;
import client.models.mapdata.Hex;
import client.models.mapdata.HexLocation;
import server.model.ServerGame;
import server.model.ServerPlayer;
import client.models.mapdata.Port;
import client.models.mapdata.Road;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;

/**
 * Translates Model class structure to JSON format
 *
 */
public class ModelToJSON {

	/**
	 * Takes the existing model and translates it into a JSON object to send to the server
	 *
	 */
	public JsonObject translateModel(ClientModel model) {
		JsonObject newModel = new JsonObject();
		JsonObject bank = this.translateBank(model.getBank());
		newModel.add("bank", bank);
		
		JsonObject chat = this.translateMessageList(model.getChat());
		newModel.add("chat", chat);
		
		JsonObject log = this.translateMessageList(model.getLog());
		newModel.add("log", log);
		
		JsonObject map = new JsonObject();
		newModel.add("map", map);
		
		JsonObject players = new JsonObject();
		newModel.add("players", players);
		
		JsonObject tradeOffer = new JsonObject();
		newModel.add("tradeOffer", tradeOffer);
		
		JsonObject turnTracker = new JsonObject();
		newModel.add("turnTracker", turnTracker);
		
		//add version num
		//add move (???)
		//add winner index
		
		return newModel;
	}
	
	private JsonObject translateBank(Resources modelBank){
		JsonObject bank = new JsonObject();
		bank.addProperty("brick", modelBank.getBrickCount());
		bank.addProperty("ore", modelBank.getOreCount());
		bank.addProperty("sheep", modelBank.getSheepCount());
		bank.addProperty("wheat", modelBank.getWheatCount());
		bank.addProperty("wood", modelBank.getWoodCount());
		return bank;
	}
	
	private JsonObject translateMessageList(MessageList modelChat){
		JsonObject chat = new JsonObject();
		JsonArray messages = new JsonArray();
		//I NEED TO CHECK THIS!!!!
		for(MessageLine message : modelChat.getLines()){
			JsonObject messageLine = new JsonObject();
			messageLine.addProperty("message", message.getMessage());
			messageLine.addProperty("source", message.getSource());
			messages.add(messageLine);
		}
		chat.add("lines", messages);
		return chat;
	}
	
	private JsonObject translateMap(Board board) {
		JsonObject map = new JsonObject();
		
		//translate hexes
		JsonObject hexes = translateHexes(board.getHexes());
		map.add("hexes", hexes);
		
		//translate ports
		JsonObject ports = translatePorts(board.getPorts());
		map.add("ports", ports);
		
		//translate roads
		JsonObject roads = translateRoads(board.getRoads());
		map.add("roads", roads);
		
		//translate settlements
		JsonObject settlements = translateSettlements(board.getSettlements());
		map.add("settlements", settlements);
		
		//translate cities
		JsonObject cities = translateCities(board.getCities());
		map.add("cities", cities);
		
		//add radius
		
		//add robber location
		
		return map;
	}
	
	private JsonObject translateRoads(Road[] roads){
		return null;
	}
	
	private JsonObject translateHexes(Hex[] hexes){
		return null;
	}
	
	private JsonObject translateSettlements(VertexObject[] settlements){
		return null;
	}
	
	private JsonObject translateCities(VertexObject[] cities){
		return null;
	}
	
	private JsonObject translatePorts(Port[] ports){
		return null;
	}
	
	
	//TRANSLATION OF COMMANDS
	
	public JsonObject createSendChatObject( int playerIndex, String message) {
		JsonObject object = new JsonObject();
		object.addProperty("type", "sendChat");
		object.addProperty("playerIndex", playerIndex);
		object.addProperty("content", message);
		return object;
	}
	
	public JsonObject createAcceptTradeObject(TradeOffer tradeOffer, boolean willAccept) {
		JsonObject object = new JsonObject();
		object.addProperty("type", "acceptTrade");
		object.addProperty("playerIndex", tradeOffer.getReceiver());
		object.addProperty("willAccept", willAccept);
		return object;
	}
	
	public JsonObject createOfferTradeObject(TradeOffer tradeOffer) {
		JsonObject object = new JsonObject();
		
		  object.addProperty("type", "offerTrade");
		  object.addProperty("playerIndex", tradeOffer.getSender());
		  
		  JsonObject offer = new JsonObject();
		  offer.addProperty("brick", tradeOffer.getBrickCount());
		  offer.addProperty("ore", tradeOffer.getOreCount());
		  offer.addProperty("sheep", tradeOffer.getSheepCount());
		  offer.addProperty("wheat", tradeOffer.getWheatCount());
		  offer.addProperty("wood", tradeOffer.getWoodCount());
		  
		  object.add("offer", offer);
		  object.addProperty("receiver", tradeOffer.getReceiver());
			
		return object;
	}
	
	public JsonObject createRollNumberObject(int playerIndex, int rollNumber) {
		JsonObject object = new JsonObject();
		object.addProperty("type", "rollNumber");
		object.addProperty("playerIndex", playerIndex);
		object.addProperty("number", rollNumber);
		return object;
	}
	
	public JsonObject createJoinGameObject(GameInfo game, CatanColor color, String userCookie) {
		JsonObject object = new JsonObject();
		object.addProperty("id", game.getId());
		object.addProperty("color", color.toString().toLowerCase());
		object.addProperty("User-cookie", userCookie);
		return object;
	}
	
	public JsonObject createGameObject(String title, boolean useRandomHexes, boolean useRandomNumbers, boolean useRandomPorts) {
		JsonObject object = new JsonObject();
		object.addProperty("randomTiles", useRandomHexes);
		object.addProperty("randomNumbers", useRandomNumbers);
		object.addProperty("randomPorts", useRandomPorts);
		object.addProperty("name", title);
		return object;
	}
	
	public JsonObject createUserObject(String username, String password) {
		JsonObject user = new JsonObject();
		user.addProperty("username", username);
		user.addProperty("password", password);
		return user;
	}
	
	public JsonObject createMessageObject(String message)
	{
		JsonObject object = new JsonObject();
		object.addProperty("message", message);
		return object;
	}
	
	public JsonObject createPlayerIndex(int playerIndex)
	{
		JsonObject object = new JsonObject();
		object.addProperty("Player Index", playerIndex);
		return object;
	}
	
	public JsonObject createPlayRoadBuildingObject(int playerIndex, EdgeLocation roadLocation, EdgeLocation roadLocation2, boolean free)
	{
		JsonObject object = new JsonObject();
		object.addProperty("type", "Road_Building");
		object.addProperty("playerIndex", playerIndex);
		
		JsonObject spot1 = new JsonObject();
		spot1.addProperty("x", roadLocation.getXcoord());
		spot1.addProperty("y", roadLocation.getYcoord());
		spot1.addProperty("direction", roadLocation.getDirection());
		
		JsonObject spot2 = new JsonObject();
		spot2.addProperty("x", roadLocation2.getXcoord());
		spot2.addProperty("y", roadLocation2.getYcoord());
		spot2.addProperty("direction", roadLocation2.getDirection());
		
		object.add("spot1", spot1);
		object.add("spot2", spot2);
		return object;
	}
	
	//MOVES COMMANDS
	
	public JsonObject getSendChatCommand(int playerIndex, String content) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "sendChat");
		command.addProperty("playerIndex", playerIndex);
		command.addProperty("content", content);
		return command;
	}
	
	public JsonObject getRollNumberCommand(int playerIndex, int numRolled) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "rollNumber");
		command.addProperty("playerIndex", playerIndex);
		command.addProperty("number", numRolled);
		return command;
	}
	
	public JsonObject getRobPlayerCommand(int playerIndex, int victimIndex, HexLocation hex) {
		JsonObject object = new JsonObject();
		object.addProperty("type", "robPlayer");
		object.addProperty("playerIndex", playerIndex);
		object.addProperty("victimIndex", victimIndex);
		
		JsonObject hexLoc = new JsonObject();
		hexLoc.addProperty("x", hex.getX());
		hexLoc.addProperty("y", hex.getY());
		
		object.add("location", hexLoc);
		return object;
	}
	
	public JsonObject getFinishTurnCommand(int playerIndex) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "finishTurn");
		command.addProperty("playerIndex", playerIndex);
		return command;
	}

	public JsonObject getBuyDevCardCommand(int playerIndex) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "buyDevCard");
		command.addProperty("playerIndex", playerIndex);
		return command;
	}
	
	//in Swagger UI it says the data types are "resource" - but I'm assuming they're actually strings
	public JsonObject getPlayYearOfPlentyCommand(int playerIndex, ResourceType resource1, ResourceType resource2) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "Year_of_Plenty");
		command.addProperty("playerIndex", playerIndex);
		command.addProperty("resource1", ResourceType.getResourceName(resource1));
		command.addProperty("resource2", ResourceType.getResourceName(resource2));
		return command;
	}
	
	public JsonObject getPlayRoadBuildingCommand(int playerIndex, EdgeLocation edge1, EdgeLocation edge2) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "Road_Building");
		command.addProperty("playerIndex", playerIndex);
		
		JsonObject edgeLoc1 = new JsonObject();
		edgeLoc1.addProperty("x", edge1.getXcoord());
		edgeLoc1.addProperty("y", edge1.getYcoord());
		
		JsonObject edgeLoc2 = new JsonObject();
		edgeLoc2.addProperty("x", edge2.getXcoord());
		edgeLoc2.addProperty("y", edge2.getYcoord());
		
		command.add("spot1", edgeLoc1);
		command.add("spot2", edgeLoc2);
		return command;
	}
	
	public JsonObject getPlaySoldierCommand(int playerIndex, int victimIndex, HexLocation hex) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "Soldier");
		command.addProperty("playerIndex", playerIndex);
		command.addProperty("victimIndex", victimIndex);
		
		JsonObject hexLoc = new JsonObject();
		hexLoc.addProperty("x", String.valueOf(hex.getX()));
		hexLoc.addProperty("y", String.valueOf(hex.getY()));
		
		command.add("location", hexLoc);
		return command;
	}
	
	public JsonObject getPlayMonopolyCommand(int playerIndex, ResourceType resource) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "Monopoly");
		command.addProperty("resource", ResourceType.getResourceName(resource));
		command.addProperty("playerIndex", playerIndex);
		return command;
	}
	
	public JsonObject getPlayMonumentCommand(int playerIndex) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "Monument");
		command.addProperty("playerIndex", playerIndex);
		return command;
	}

	public JsonObject getBuildRoadCommand(int playerIndex, EdgeLocation edge, boolean free) {
		JsonObject object = new JsonObject();
		object.addProperty("type", "buildRoad");
		object.addProperty("playerIndex", playerIndex);
		
		JsonObject edgeLoc = new JsonObject();
		edgeLoc.addProperty("x", edge.getXcoord());
		edgeLoc.addProperty("y", edge.getYcoord());
		edgeLoc.addProperty("direction", edge.getDirection());
		object.add("roadLocation", edgeLoc);
		
		if (free) object.addProperty("free", true);
		else object.addProperty("free", false);
		return object;
	}
	
	public JsonObject getBuildSettlementCommand(int playerIndex, EdgeLocation edge, boolean free) {
		JsonObject object = new JsonObject();
		object.addProperty("type", "buildSettlement");
		object.addProperty("playerIndex", playerIndex);

		JsonObject vertex = new JsonObject();
		vertex.addProperty("x", edge.getXcoord());
		vertex.addProperty("y", edge.getYcoord());
		vertex.addProperty("direction", edge.getDirection());
		object.add("vertexLocation", vertex);
		
		if (free) object.addProperty("free", true);
		else object.addProperty("free", false);
		return object;
	}
	
	public JsonObject getBuildCityCommand(int playerIndex, EdgeLocation edge) {
		JsonObject object = new JsonObject();
		object.addProperty("type", "buildCity");
		object.addProperty("playerIndex", playerIndex);
		
		JsonObject vertex = new JsonObject();
		vertex.addProperty("x", edge.getXcoord());
		vertex.addProperty("y", edge.getYcoord());
		vertex.addProperty("direction", edge.getDirection());
		
		object.add("vertexLocation", vertex);
		return object;
	}
	
	public JsonObject getOfferTradeCommand(int playerIndex, int receiver, Resources resourceList) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "offerTrade");
		command.addProperty("playerIndex", playerIndex);
		
		JsonObject resList = new JsonObject();
		resList.addProperty("brick", resourceList.getBrickCount());
		resList.addProperty("ore", resourceList.getOreCount());
		resList.addProperty("sheep", resourceList.getSheepCount());
		resList.addProperty("wheat", resourceList.getWheatCount());
		resList.addProperty("wood", resourceList.getWoodCount());
		
		command.add("offer", resList);
		command.addProperty("receiver", receiver);		
		return command;
	}
	
	public JsonObject getAcceptTradeCommand(int playerIndex, boolean willAccept) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "acceptTrade");
		command.addProperty("playerIndex", playerIndex);
		command.addProperty("willAccept", willAccept);
		return command;
	}

	//swagger says everything but playerindex is optional, how is that possible?
	public JsonObject getMaritimeTradeCommand(int playerIndex, int ratio, ResourceType inputResource, ResourceType outputResource) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "maritimeTrade");
		command.addProperty("playerIndex", playerIndex);
		command.addProperty("ratio", ratio);
		command.addProperty("inputResource", inputResource.name().toLowerCase());
		command.addProperty("outputResource", outputResource.name().toLowerCase());
		return command;
	}
	
	public JsonObject getDiscardCardsCommand(int playerIndex, Resources discardList) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "discardCards");
		command.addProperty("playerIndex", playerIndex);
		
		JsonObject resList = new JsonObject();
		resList.addProperty("brick", discardList.getBrickCount());
		resList.addProperty("ore", discardList.getOreCount());
		resList.addProperty("sheep", discardList.getSheepCount());
		resList.addProperty("wheat", discardList.getWheatCount());
		resList.addProperty("wood", discardList.getWoodCount());
		
		command.add("discardedCards", resList);
		return command;
	}
	
	public JsonObject createUserAndGameCookie(String userCookie, int gameCookie)
	{
		JsonObject object = new JsonObject();
		object.addProperty("User-cookie", userCookie);
		object.addProperty("Game-cookie", gameCookie);
		return object;
	}
	
	public JsonObject generatePlayerCookie(String username, String password, int playerID) {
		JsonObject object = new JsonObject();
		object.addProperty("authentication", "-1669576507");
		object.addProperty("name", username);
		object.addProperty("password", password);
		object.addProperty("playerID", playerID);
		return object;
	}

	public JsonArray generateServerGamesObject(List<ServerGame> games) {
		JsonObject returnObject = new JsonObject();
		JsonArray gamesArray = new JsonArray();
		for (ServerGame g : games) {
			JsonObject gameObject = new JsonObject();
			gameObject.addProperty("title", g.getTitle());
			gameObject.addProperty("id", g.getId());
			JsonArray playersArray = new JsonArray();
			for (ServerPlayer p : g.getPlayers()) {
				JsonObject playerObject = new JsonObject();
				playerObject.addProperty("color", p.getColor());
				playerObject.addProperty("name", p.getName());
				playerObject.addProperty("id", p.getId());
				playersArray.add(playerObject);
			}
			gameObject.add("players", playersArray);
			gamesArray.add(gameObject);
		}
		returnObject.add("Response-body", gamesArray);
		return gamesArray;
	}
		
	
	
}
