package jsonTranslator;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.models.ClientModel;
import client.models.DevCards;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.TurnTracker;
import client.models.VertexObject;
import client.models.communication.MessageLine;
import client.models.communication.MessageList;
import client.models.mapdata.Board;
import client.models.mapdata.EdgeLocation;
import client.models.mapdata.Hex;
import client.models.mapdata.HexLocation;
import client.models.mapdata.Port;
import client.models.mapdata.Road;
import server.model.ServerGame;
import server.model.ServerPlayer;
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
		JsonObject bank = this.translateResources(model.getBank());
		newModel.add("bank", bank);

		JsonObject deck = this.translateDevCards(model.getDeck());
		newModel.add("deck", deck);

		JsonObject chat = this.translateMessageList(model.getChat());
		newModel.add("chat", chat);

		JsonObject log = this.translateMessageList(model.getLog());
		newModel.add("log", log);

		JsonObject map = this.translateMap(model.getBoard());
		newModel.add("map", map);

		JsonArray players = this.translatePlayers(model.getPlayers());
		newModel.add("players", players);
		
		if (model.getTradeOffer() != null) {
			JsonObject tradeOffer = this.translateTradeOffer(model.getTradeOffer());
			newModel.add("tradeOffer", tradeOffer);			
		}
		
		JsonObject turnTracker = this.translateTurnTracker(model.getTurnTracker());
		newModel.add("turnTracker", turnTracker);
		
		//add version num
		newModel.addProperty("version", model.getVersion());
		
		//add winner index
		newModel.addProperty("winner", model.getWinner());
		
		return newModel;
	}
	
	private JsonObject translateTurnTracker(TurnTracker tracker) {
		JsonObject turnTracker = new JsonObject();
		turnTracker.addProperty("currentTurn", tracker.getCurrentTurn());
		turnTracker.addProperty("status", tracker.getStatus());
		turnTracker.addProperty("longestRoad", tracker.getLongestRoad());
		turnTracker.addProperty("largestArmy", tracker.getLargestArmy());
		return turnTracker;
	}
	
	private JsonObject translateTradeOffer(TradeOffer offer) {
		JsonObject trade = new JsonObject();
		trade.addProperty("sender", offer.getSender());
		trade.addProperty("receiver", offer.getReceiver());
		trade.add("offer", this.translateResources(offer.getOffer()));
		return trade;
	}
	
	private JsonArray translatePlayers(Player[] players) {
		JsonArray list = new JsonArray();
		for (Player p : players) {

			if (p != null){
				JsonObject player = new JsonObject();

				player.addProperty("cities", p.getCities());
				player.addProperty("color", p.getColor());
				player.addProperty("discarded", p.isDiscarded());
				player.addProperty("monuments", p.getMonuments());
				player.addProperty("name", p.getName());
				player.add("newDevCards", this.translateDevCards(p.getNewDevCards()));
				player.add("oldDevCards", this.translateDevCards(p.getOldDevCards()));
				player.addProperty("playerIndex", p.getPlayerIndex());
				player.addProperty("playedDevCard", p.isPlayedDevCard());
				player.addProperty("playerID", p.getPlayerID());
				player.add("resources", this.translateResources(p.getResources()));
				player.addProperty("roads", p.getRoads());
				player.addProperty("settlements", p.getSettlements());
				player.addProperty("soldiers", p.getSoldiers());
				player.addProperty("victoryPoints", p.getVictoryPoints());

				list.add(player);
			}
			else{
				list.add(null);
			}
			
		}
		return list;
	}
	
	private JsonObject translateDevCards(DevCards cards) {
		JsonObject cardList = new JsonObject();
		cardList.addProperty("monopoly", cards.getMonopolyCount());
		cardList.addProperty("monument", cards.getMonumentCount());
		cardList.addProperty("roadBuilding", cards.getRoadBuildingCount());
		cardList.addProperty("soldier", cards.getSoldierCount());
		cardList.addProperty("yearOfPlenty", cards.getYearOfPlentyCount());
		return cardList;
	}
	
	private JsonObject translateResources(Resources modelBank){
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
		List<MessageLine> chatLines = modelChat.getLines();
		for(MessageLine message : chatLines){
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
		JsonArray hexes = translateHexes(board.getHexes());
		map.add("hexes", hexes);
		
		//translate ports
		JsonArray ports = translatePorts(board.getPorts());
		map.add("ports", ports);
		
		//translate roads
		JsonArray roads = translateRoads(board.getRoads());
		map.add("roads", roads);
		
		//translate settlements
		JsonArray settlements = translateVertexObjects(board.getSettlements());
		map.add("settlements", settlements);
		
		//translate cities
		JsonArray cities = translateVertexObjects(board.getCities());
		map.add("cities", cities);
		
		//add radius
		JsonObject radius = new JsonObject();
		map.addProperty("radius", 3);
		
		//add robber location
		map.add("robber", this.translateHexLocation(board.getRobber()));
		
		return map;
	}
	
	private JsonArray translateRoads(Road[] roads){
		JsonArray roadsList = new JsonArray();
		for (Road r : roads) {
			JsonObject newRoad = new JsonObject();
			newRoad.addProperty("owner", r.getOwner());
			
			JsonObject roadLocation = new JsonObject();
			roadLocation.addProperty("x", r.getLocation().getXcoord());
			roadLocation.addProperty("y", r.getLocation().getYcoord());
			roadLocation.addProperty("direction", r.getLocation().getDirection());
			
			newRoad.add("location", roadLocation);
			
			roadsList.add(newRoad);
		}
		
		return roadsList;
	}
	
	private JsonArray translateHexes(Hex[] hexes){
		JsonArray hexList = new JsonArray();
		for (Hex h : hexes) {
			JsonObject newHex = new JsonObject();
			
			JsonObject location = translateHexLocation(h.getLocation());
			newHex.add("location", location);
			
			if (h.getResource() != null) {
				newHex.addProperty("resource", h.getResource());
				
				newHex.add("location", location);
				
				newHex.addProperty("number", h.getNumberToken());
			}
			else newHex.add("location", location);
			
			hexList.add(newHex);
		}
		return hexList;
	}
	
	private JsonObject translateHexLocation(HexLocation loc) {
		JsonObject hexLoc = new JsonObject();
		
		hexLoc.addProperty("x", loc.getX());
		hexLoc.addProperty("y", loc.getY());
		return hexLoc;
	}
	
	private JsonArray translateVertexObjects(List<VertexObject> vertObjects) {
		JsonArray objs = new JsonArray();
		for(VertexObject v : vertObjects) {
			JsonObject obj = new JsonObject();
			obj.addProperty("owner", v.getOwner());
			JsonObject edge = new JsonObject();
			edge.addProperty("x", v.getLocation().getXcoord());
			edge.addProperty("y", v.getLocation().getYcoord());
			edge.addProperty("direction", v.getLocation().getDirection());
			obj.add("location", edge);
			
			objs.add(obj);
		}
		return objs;
	}
	
	private JsonArray translatePorts(Port[] ports) {
		JsonArray portList = new JsonArray();
		for(Port p : ports) {
			JsonObject port = new JsonObject();
			if(p.getResource() != null) {
				port.addProperty("resource", p.getResource());
			}
			
			port.add("location", this.translateHexLocation(p.getLocation()));
			port.addProperty("direction", p.getDirection());
			port.addProperty("ratio", p.getRatio());
			
			portList.add(port);
		}
		return portList;
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
		
	public JsonObject generateGameInfoObject(GameInfo game) {
		JsonObject returnObject = new JsonObject();
		returnObject.addProperty("title", game.getTitle());
		returnObject.addProperty("id", game.getId());
		game.getPlayers();
		JsonArray playersArray = new JsonArray();
		for (PlayerInfo p : game.getPlayers()) {
			JsonObject playerObject = new JsonObject();
			playerObject.addProperty("color", p.getColor().toString().toLowerCase());
			playerObject.addProperty("name", p.getName());
			playerObject.addProperty("id", p.getId());
			playersArray.add(playerObject);
		}
		returnObject.add("players", playersArray);
		
		return returnObject;
	}
	
}
