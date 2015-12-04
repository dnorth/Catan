package jsonTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.models.ClientModel;
import client.models.DevCards;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.TurnTracker;
import client.models.communication.MessageList;
import client.models.mapdata.Board;
import server.exceptions.InvalidJsonException;
import server.exceptions.MissingCookieException;
import server.model.ServerData;
import server.model.ServerGame;
import server.model.ServerUser;

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
		
		this.g = new Gson();
		
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