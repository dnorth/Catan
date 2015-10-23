package jsonTranslator;

import java.util.ArrayList;
import java.util.Iterator;

import shared.definitions.CatanColor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.TurnTracker;
import client.models.communication.MessageList;
import client.models.mapdata.Board;

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
	
	public static ClientModel translateClientModel(JsonObject serverModel) {
		
		ClientModel clientModel = new ClientModel();
		
		//TODO Do this for every part of the client model
		Resources bank = translateBank(serverModel);
		MessageList chat = translateChat(serverModel);
		MessageList log = translateLog(serverModel);
		Board board = translateBoard(serverModel);
		TurnTracker turnTracker = translateTurnTracker(serverModel);
		Player[] players = translatePlayers(serverModel);
		int version = translateVersion(serverModel);
		int winner = translateWinner(serverModel);
		
		clientModel.setBank(bank);
				
		clientModel.setChat(chat);
		
		clientModel.setLog(log);

		clientModel.setBoard(board);
		
		clientModel.setTurnTracker(turnTracker);
		
		clientModel.setPlayers(players);
		
		clientModel.setVersion(version);

		clientModel.setWinner(winner);

		return clientModel;
	}
	
	
	/**
	 * Translates bank to put in updated client model
	 * @return updated bank
	 */
	public static Resources translateBank(JsonObject serverModel) {
		Resources bank = (Resources)g.fromJson(serverModel.get("bank"), Resources.class);
		return bank;
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
	
	//TODO GET PLAYERS TO WORK - THIS IS HARDER BECAUSE ITS A LIST OF PLAYERS
	public static Player[] translatePlayers(JsonObject serverModel) {
		final JsonArray playersArray = serverModel.get("players").getAsJsonArray();
		Player[] players = new Player[playersArray.size()];
		
		for (int i = 0; i < players.length; i++) {
		      players[i] = (Player)g.fromJson(playersArray.get(i), Player.class);
		}

		return players;
	}
	
	//TODO Is this REALLY part of the client model? How will we integrate it?
	/**
	 * Translates trade offer to put in updated client model
	 * @return updated trade offer
	 */
	public static TradeOffer translateTradeOffer(JsonObject serverModel) {
		return null;
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
		System.out.println("JSONTOMODEL NUMBER OF PLAYERS IN MODEL = " + playerCounter);
		return playerCounter;
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
}