package jsonTranslator;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

import shared.definitions.*;
import shared.locations.*;

import com.google.gson.*;
import com.sun.net.httpserver.*;

import client.data.*;
import client.models.*;
import client.models.communication.MessageList;
import client.models.mapdata.Board;
import server.commands.IMovesCommand;
import server.commands.moves.*;
import server.exceptions.ContextNotFoundException;
import server.exceptions.InvalidJsonException;
import server.exceptions.MissingCookieException;
import server.model.*;

/**
 * Translates JSON documentation to class structure used in model
 *
 */

public class JSONToModel {
	/**
	 * Parent method to translate entire model
	 * @return updated ClientModel
	 */
	
	private static Gson g;
	
	public JSONToModel() {
		
		JSONToModel.g = new Gson();
		
	}
	
	public static IMovesCommand translateCommand(JsonObject data, ServerGame game, int commandNumber) {
		
		String type = data.get("type").getAsString();
		int playerIndex = data.get("playerIndex").getAsInt();
		IMovesCommand command = null;
		boolean bool1;
		String string1;
		String string2;
		JsonObject object1;
		JsonObject object2;
		int x;
		int y;
		VertexLocation vertLoc;
		EdgeLocation edgeLoc;
		EdgeLocation edgeLoc2;
		int brickCount;
		int oreCount;
		int sheepCount;
		int wheatCount;
		int woodCount;
		Resources resources;
		JsonObject resList;
		switch (type) {
		case "acceptTrade":
			bool1 = data.get("willAccept").getAsBoolean();
			command = new AcceptTradeCommand(game, playerIndex, bool1, commandNumber);
			break;
		case "buildCity":
			object1 = data.get("vertexLocation").getAsJsonObject();
			x = object1.get("x").getAsInt();
			y = object1.get("y").getAsInt();
			string1 = object1.get("direction").getAsString();
			vertLoc = new VertexLocation(new HexLocation(x,y), VertexDirection.getVertexDirectionFromString(string1));
			command = new BuildCityCommand(game, playerIndex, vertLoc, commandNumber);
			break;
		case "buildRoad":
			object1 = data.get("roadLocation").getAsJsonObject();
			x = object1.get("x").getAsInt();
			y = object1.get("y").getAsInt();
			string1 = object1.get("direction").getAsString();
			edgeLoc = new EdgeLocation(new HexLocation(x,y), EdgeDirection.getEdgeDirectionFromString(string1));
			
			bool1 = data.get("free").getAsBoolean();
			command = new BuildRoadCommand(game, playerIndex, edgeLoc, bool1, commandNumber);
			break;
		case "buildSettlement":
			object1 = data.get("vertexLocation").getAsJsonObject();
			x = object1.get("x").getAsInt();
			y = object1.get("y").getAsInt();
			string1 = object1.get("direction").getAsString();

			bool1 = data.get("free").getAsBoolean();
			vertLoc = new VertexLocation(new HexLocation(x,y), VertexDirection.getVertexDirectionFromString(string1));
			command = new BuildSettlementCommand(game, playerIndex, vertLoc, bool1, commandNumber);
			break;
		case "buyDevCard":
			command = new BuyDevCardCommand(game, playerIndex, commandNumber);
			break;
		case "discardCards":
			resList = data.get("discardedCards").getAsJsonObject();
			brickCount = resList.get("brick").getAsInt();
			oreCount = resList.get("ore").getAsInt();
			sheepCount = resList.get("sheep").getAsInt();
			wheatCount = resList.get("wheat").getAsInt();
			woodCount = resList.get("wood").getAsInt();
			resources = new Resources(woodCount, brickCount, sheepCount, wheatCount, oreCount);
			command = new DiscardCardsCommand(game, playerIndex, resources, commandNumber);
			break;
		case "finishTurn":
			
			command = new FinishTurnCommand(game, playerIndex, commandNumber);
			break;
		case "maritimeTrade":
			int ratio = data.get("ratio").getAsInt();
			string1 = data.get("inputResource").getAsString();
			string2 = data.get("outputResource").getAsString();
			command = new MaritimeTradeCommand(game, playerIndex, ratio, string1, string2, commandNumber);
			break;
		case "Monopoly":
			string1 = data.get("resource").getAsString();
			command = new MonopolyCommand(game, ResourceType.getResourceType(string1), playerIndex, commandNumber);
			break;
		case "Monument":
			command = new MonumentCommand(game, playerIndex, commandNumber);
			break;
		case "offerTrade":
			resList = data.get("offer").getAsJsonObject();
			brickCount = resList.get("brick").getAsInt();
			oreCount = resList.get("ore").getAsInt();
			sheepCount = resList.get("sheep").getAsInt();
			wheatCount = resList.get("wheat").getAsInt();
			woodCount = resList.get("wood").getAsInt();
			int receiver = data.get("receiver").getAsInt();
			resources = new Resources(woodCount, brickCount, sheepCount, wheatCount, oreCount);
			command = new OfferTradeCommand(game, playerIndex, resources, receiver, commandNumber);
			break;
		case "Road_Building":
			object1 = data.get("spot1").getAsJsonObject();
			x = object1.get("x").getAsInt();
			y = object1.get("y").getAsInt();
			string1 = object1.get("direction").getAsString();
			edgeLoc = new EdgeLocation(new HexLocation(x,y), EdgeDirection.getEdgeDirectionFromString(string1));
			
			object2 = data.get("spot2").getAsJsonObject();
			x = object2.get("x").getAsInt();
			y = object2.get("y").getAsInt();
			string2 = object2.get("direction").getAsString();
			edgeLoc2 = new EdgeLocation(new HexLocation(x,y), EdgeDirection.getEdgeDirectionFromString(string2));
			
			command = new RoadBuildingCommand(game, playerIndex, edgeLoc, edgeLoc2, commandNumber);
			break;
		case "robPlayer":
			int victimIndex = data.get("victimIndex").getAsInt();
			object1 = data.get("location").getAsJsonObject();
			x = object1.get("x").getAsInt();
			y = object1.get("y").getAsInt();
			command = new RobPlayerCommand(game, playerIndex, victimIndex, new HexLocation(x,y), commandNumber);
			break;
		case "rollNumber":
			int rollNumber = data.get("number").getAsInt();
			command = new RollNumberCommand(game, playerIndex, rollNumber, commandNumber);
			break;
		case "sendChat":
			string1 = data.get("content").getAsString();
			command = new SendChatCommand(game, playerIndex, string1, commandNumber);
			break;
		case "Soldier":
			object1 = data.get("location").getAsJsonObject();
			x = object1.get("x").getAsInt();
			y = object1.get("y").getAsInt();
			
			int victimIndex2 = data.get("victimIndex").getAsInt();
			command = new SoldierCommand(game, playerIndex, victimIndex2, new HexLocation(x,y), commandNumber);
			break;
		case "Year_Of_Plenty":
			string1 = data.get("resource1").getAsString();
			string2 = data.get("resource2").getAsString();
			command = new YearOfPlentyCommand(game, playerIndex, ResourceType.getResourceType(string1), ResourceType.getResourceType(string2), commandNumber);
			break;
		default:
			System.out.println("Why are we here? This is the default case in JSONToModel in the translate command switch");
		}
		return command;
	}
	
	public static ServerData translateServerData(JsonObject data) {
		
		ServerData serverData = new ServerData();
		
		List<ServerUser> users = translateServerUserList(data);
		List<ServerGame> games = translateServerGameList(data);
		int nextUserID = getNextUserID(data);
		int nextGameID = getNextGameID(data);
		int checkpoint = getCheckpoint(data);
		
		
		serverData.setNextUserID(nextUserID);
		serverData.setNextGameID(nextGameID);
		serverData.setCheckpoint(checkpoint);
		serverData.setUsers(users);
		serverData.setGames(games);
		
		
		return serverData;
	}
	
	public static ClientModel translateClientModel(JsonObject serverModel) {
		
		ClientModel clientModel = new ClientModel();
		
		//TODO Do this for every part of the client model
		Resources bank = translateBank(serverModel);
		MessageList chat = translateChat(serverModel);
		MessageList log = translateLog(serverModel);
		Board board = translateBoard(serverModel);
		TurnTracker turnTracker = translateTurnTracker(serverModel);
		TradeOffer tradeOffer = translateTradeOffer(serverModel);
		Player[] players = translatePlayers(serverModel);
		int version = translateVersion(serverModel);
		int winner = translateWinner(serverModel);
		DevCards deck = translateDeck(serverModel);
		
		clientModel.setBank(bank);
				
		clientModel.setChat(chat);
		
		clientModel.setLog(log);

		clientModel.setBoard(board);
		
		clientModel.setTurnTracker(turnTracker);
		
		clientModel.setTradeOffer(tradeOffer);
		
		clientModel.setPlayers(players);
		
		clientModel.setVersion(version);

		clientModel.setWinner(winner);
		
		clientModel.setDeck(deck);

		return clientModel;
	}
	
	public static List<ServerUser> translateServerUserList(JsonObject data) {
		final JsonArray playersArray = data.get("users").getAsJsonArray();
		List<ServerUser>users = new ArrayList<ServerUser>();
		
		for (int i = 0; i < playersArray.size(); i++) {
		      users.add((ServerUser)g.fromJson(playersArray.get(i), ServerUser.class));
		}

		return users;
	}
	
	public static ServerGame translateServerGame(JsonObject data) {
		return (ServerGame)g.fromJson(data, ServerGame.class);
	}
	
	public static List<ServerGame> translateServerGameList(JsonObject data) {
		final JsonArray playersArray = data.get("games").getAsJsonArray();
		List<ServerGame>games = new ArrayList<ServerGame>();
		
		for (int i = 0; i < playersArray.size(); i++) {
		      games.add((ServerGame)g.fromJson(playersArray.get(i), ServerGame.class));
		}

		return games;
	}
	
	public static DevCards translateDeck(JsonObject serverModel) {
		DevCards deck = (DevCards)g.fromJson(serverModel.get("deck"), DevCards.class);
		return deck;
	}
	
	/**
	 * Translates bank to put in updated client model
	 * @return updated bank
	 */
	public static Resources translateBank(JsonObject serverModel) {
		Resources bank = (Resources)g.fromJson(serverModel.get("bank"), Resources.class);
		return bank;
	}
	
	public static TradeOffer translateTradeOffer(JsonObject serverModel) {
		TradeOffer tradeOffer = (TradeOffer)g.fromJson(serverModel.get("tradeOffer"), TradeOffer.class);
		return tradeOffer;
	}
	
	/**
	 * Translates chat to put in updated client model
	 * @return updated chat
	 */
	public static MessageList translateChat(JsonObject serverModel) {
		MessageList chat = (MessageList)g.fromJson(serverModel.get("chat"), MessageList.class);
		return chat;
	}
	
	/**
	 * Translates log to put in updated client model
	 * @return updated log
	 */
	public static MessageList translateLog(JsonObject serverModel) {
		MessageList log = (MessageList)g.fromJson(serverModel.get("log"), MessageList.class);
		return log;
	}
	
	/**
	 * Translates board to put in updated client model
	 * @return updated board
	 */
	public static Board translateBoard(JsonObject serverModel) {
		Board board = (Board)g.fromJson(serverModel.get("map"), Board.class);
		return board;
	}
	
	/**
	 * Translates player list and info to put in updated client model
	 * @return updated player list
	 */
	
	public static Player[] translatePlayers(JsonObject serverModel) {
		final JsonArray playersArray = serverModel.get("players").getAsJsonArray();
		Player[] players = new Player[playersArray.size()];
		
		for (int i = 0; i < players.length; i++) {
		      players[i] = (Player)g.fromJson(playersArray.get(i), Player.class);
		}

		return players;
	}
	
	/**
	 * Translates turn tracker to put in updated client model
	 * @return updated turn tracker
	 */
	public static TurnTracker translateTurnTracker(JsonObject serverModel) {
		TurnTracker turnTracker = (TurnTracker)g.fromJson(serverModel.get("turnTracker"), TurnTracker.class);
		return turnTracker;
	}
	
	/**
	 * Obtains version number from JSON
	 * @return updated version number
	 */
	public static int translateVersion(JsonObject serverModel) {
		return serverModel.get("version").getAsInt();
	}
	
	public static int translateNumberOfPlayers(JsonObject serverModel) {
		JsonArray array = serverModel.get("players").getAsJsonArray();
		int playerCounter = 0;
		for(int i = 0; i < array.size(); i++) {
			if(!array.get(i).isJsonNull()) {
				playerCounter++;
			}
		}
		return playerCounter;
	}
	
	public static int translateNumberOfRoads(JsonObject serverModel) {
		JsonArray array = serverModel.get("map").getAsJsonObject().get("roads").getAsJsonArray();
		return array.size();
	}
	
	/**
	 * Obtains winner index from JSON
	 * @return updated winner index
	 */
	public static int translateWinner(JsonObject serverModel) {
		int winner = serverModel.get("winner").getAsInt();
		return winner;
	}
	
	
	//If this has any point of failure there are 2 ways we could change this:
	//Make it pull out players seperately and deal with those in a smaller function
	//Change the PlayerInfo color to a String instead of a CatanColor object. Make a new CatanColor object called "catanColor"
	//In getCatanColor() we would return the CatanColor instead of the string. In setCatanColor() we would make 2 functions with 
	//arguments either being setCatanColor(CatanColor color) or setCatanColor(String color) 
	public static GameInfo[] translateGamesList(JsonObject object) {
		
		final JsonArray gameArray = object.get("Response-body").getAsJsonArray();
		GameInfo[] gameInfos = new GameInfo[gameArray.size()];
		
		for (int x = 0; x < gameInfos.length; x++) {
		      gameInfos[x] = (GameInfo)g.fromJson(gameArray.get(x), GameInfo.class);
		      ArrayList<PlayerInfo> players = new ArrayList<PlayerInfo>(gameInfos[x].getPlayers());
		      Iterator<PlayerInfo> iter = players.iterator();
		      int i=0;
		      while(iter.hasNext()) {
		    	  PlayerInfo play = iter.next();
					if (play.getId() == -1) {
						iter.remove();
					} else {
						String color = gameArray.get(x).getAsJsonObject().get("players").getAsJsonArray().get(i).getAsJsonObject().get("color").getAsString();
						try {
							play.setColor(CatanColor.getCatanColor(color));
						} catch (Exception e) {
							play.setColor(null);
						}
					}
				i++;
				}
				gameInfos[x].setPlayers(players);
		}
		
		return gameInfos;
	}
	
	
	/**
	 * Translates Json into a list of games. Used for getGamesList()
	 */
	public GameInfo translateGameInfo(JsonObject data) {
		GameInfo gameInfo = (GameInfo)g.fromJson(data.get("Response-body"), GameInfo.class);
		return gameInfo;
	}
	
	public CatanColor getMyColor(JsonObject cookies, int playerIndex) {
		Player[] players = translatePlayers(cookies);
		try {
			return CatanColor.getCatanColor(players[playerIndex].getColor());
		} catch (Exception e) {
			return null;
		}
	}
	
	// HERETO LIES THE SERVER METHODS
	public JsonObject exchangeToJson(HttpExchange exchange) {
		String exchangeData = getExchangeData(exchange.getRequestBody());
		JsonObject registerObject = new JsonParser().parse(exchangeData).getAsJsonObject();
		return registerObject;
	}
	
	public JsonObject getCookieFromExchange(HttpExchange exchange) throws MissingCookieException, UnsupportedEncodingException {
		Headers headers = exchange.getRequestHeaders();
		if (headers.containsKey("Cookie")) {
			String cookie = urlDecodeString(headers.get("Cookie").get(0));
			
			if(cookie.indexOf("catan.user=") == -1) {
				throw new MissingCookieException("The catan.user HTTP cookie is missing.  You must login before calling this method.");
			}
			
			String[] cookieList = seperateCookies(cookie);
//			for (String s : cookieList) System.out.println("SEPARATE COOKIE: " + s);
			
			JsonObject cookieJson = new JsonParser().parse(cookieList[0]).getAsJsonObject();
			
			//This means we also have a game cookie
			if( cookieList.length > 1) {
				cookieJson.addProperty("game", cookieList[1]);
			}
			
			return cookieJson;
		} else {
			throw new MissingCookieException("The catan.user HTTP cookie is missing.  You must login before calling this method.");
		}
	}
	
	public String[] seperateCookies(String fullCookie) {
//		System.out.println("FULL COOKIE: " + fullCookie);
		String split1 = fullCookie.split("catan.user=")[1];
		String[] finalSplit = split1.split("; catan.game=");
		return finalSplit;

	}
	public String urlDecodeString(String data) throws UnsupportedEncodingException {
		return URLDecoder.decode(data, "utf-8");
	}
	
	public boolean hasHeader(Headers headers, String headerName) {
		return headers.containsKey(headerName);
	}
	
	public String getExchangeData(InputStream exchangeData) {
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(exchangeData,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 BufferedReader br = new BufferedReader(isr);
		 int b;
		 StringBuilder buf = new StringBuilder(512);
		 try {
			while ((b = br.read()) != -1) {
				 buf.append((char) b);
			 }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		 
		return buf.toString();
	}
	
	public String getUsername(JsonObject object) {
		return object.get("username").getAsString();
	}
	public String getPassword(JsonObject object) {
		return object.get("password").getAsString();
	}
	public String getColor(JsonObject object) {
		return object.get("color").getAsString();
	}
	public String getName(JsonObject object) {
		return object.get("name").getAsString();
	}
	public String getResource(JsonObject object, String name) {
		return object.get(name).getAsString();
	}
	public boolean getRandomTiles(JsonObject object) throws InvalidJsonException {
		String r = object.get("randomTiles").getAsString();
		if (!r.equals("true") && !r.equals("false")) throw new InvalidJsonException("Invalid boolean");
		boolean l = object.get("randomTiles").getAsBoolean();
		System.out.println("Random Tiles: " + l);
		return l;
	}
	public boolean getRandomNumbers(JsonObject object) throws InvalidJsonException {
		String r = object.get("randomNumbers").getAsString();
		if (!r.equals("true") && !r.equals("false")) throw new InvalidJsonException("Invalid boolean");
		return object.get("randomNumbers").getAsBoolean();
	}
	public boolean getRandomPorts(JsonObject object) throws InvalidJsonException {
		String r = object.get("randomPorts").getAsString();
		if (!r.equals("true") && !r.equals("false")) throw new InvalidJsonException("Invalid boolean");
		return object.get("randomPorts").getAsBoolean();
	}
	public boolean getWillAccept(JsonObject object) {
		return object.get("willAccept").getAsBoolean();
	}
	public int getGameIndex(JsonObject object) {
		return object.get("game").getAsInt();
	}
	public int getRatio(JsonObject object) {
		return object.get("ratio").getAsInt();
	}
	public int getReceiver(JsonObject object) {
		return object.get("receiver").getAsInt();
	}
	public EdgeLocation getEdgeLocation(JsonObject object, String name) {
		client.models.mapdata.EdgeLocation loc = (client.models.mapdata.EdgeLocation) g
				.fromJson(object.get(name),
						client.models.mapdata.EdgeLocation.class);
		return loc.getSharedEdgeLocation();
	}
	public ResourceType getResourceType(JsonObject object, String name) {
		return ResourceType.getResourceType(object.get(name).getAsString());
	}
	public HexLocation getHexLocation(JsonObject object, String name) {
		return (HexLocation)g.fromJson(object.get(name), HexLocation.class);
	}
	public VertexLocation getVertexLocation(JsonObject object, String name) {
		client.models.mapdata.EdgeLocation loc = (client.models.mapdata.EdgeLocation) g
				.fromJson(object.get(name),
						client.models.mapdata.EdgeLocation.class);
		return loc.getSharedVertexLocation();
	}
	public Resources getResourceList(JsonObject object, String name) {
		return (Resources)g.fromJson(object.get(name), Resources.class);
	}
	public boolean getFree(JsonObject object) {
		return object.get("free").getAsBoolean();
	}
	public int getVersionNumber(JsonObject object) {
		if (object.get("version") == null) {
			return -1;
		}
		else {
			return object.get("version").getAsInt();
		}
	}
	public String getContent(JsonObject object) {
		return object.get("content").getAsString();
	}
	public int getNumber(JsonObject object) {
		return object.get("number").getAsInt();
	}
	public int getPlayerIndex(JsonObject object) {
		return object.get("playerIndex").getAsInt();
	}
	public int getVictimIndex(JsonObject object) {
		return object.get("victimIndex").getAsInt();
	}

	public int getGameID(JsonObject object) {
		return object.get("id").getAsInt();
	}
	public static int getNextUserID(JsonObject object) {
		return object.get("nextUserID").getAsInt();
	}
	public static int getNextGameID(JsonObject object) {
		return object.get("nextGameID").getAsInt();
	}
	public static int getCheckpoint(JsonObject object) {
		return object.get("checkpoint").getAsInt();
	}
	
	
}