package jsonTranslator;

import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import client.data.GameInfo;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.mapdata.EdgeLocation;
import client.models.mapdata.HexLocation;

import com.google.gson.JsonObject;

/**
 * Translates Model class structure to JSON format
 *
 */
public class ModelToJSON {

	/**
	 * Takes the existing model and translates it into a JSON object to send to the server
	 *
	 */
	public JsonObject translateModel() {
		return null;
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
	
	public JsonObject createPlayMonopolyObject(int playerIndex, ResourceType resource)
	{
		JsonObject object = new JsonObject();
		object.addProperty("Player Index", playerIndex);
		object.addProperty("ResourceType", resource.name());
		return object;
	}
	
	public JsonObject createPlayRoadBuildingObject(int playerIndex, EdgeLocation roadLocation, EdgeLocation roadLocation2, boolean free)
	{
//		JsonObject object = new JsonObject();
//		object.addProperty("Player Index", playerIndex);
//		object.addProperty("Road Location 1", roadLocation);
		return null;
	}
	
	public JsonObject createYearOfPlentyObject(ResourceType resource1, ResourceType resource2){
		JsonObject object = new JsonObject();
		object.addProperty("ResourceType", resource1.name());
		object.addProperty("ResourceType", resource2.name());
		return  object;
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
		command.addProperty("resource1", resource1.name());
		command.addProperty("resource2", resource2.name());
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
		hexLoc.addProperty("x", hex.getX());
		hexLoc.addProperty("y", hex.getY());
		
		command.add("location", hexLoc);
		return command;
	}
	
	public JsonObject getPlayMonopolyCommand(int playerIndex, ResourceType resource) {
		JsonObject command = new JsonObject();
		command.addProperty("type", "Monopoly");
		command.addProperty("resource", resource.name());
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
		command.addProperty("inputResource", inputResource.name());
		command.addProperty("outputResource", outputResource.name());
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
	
	
	
	
	
	
	
	
	
	
}
