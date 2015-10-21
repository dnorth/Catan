package jsonTranslator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import shared.definitions.CatanColor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;

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
		int version = serverModel.get("version").getAsInt();
		return version;
	}
	
	/**
	 * Obtains winner index from JSON
	 * @return updated winner index
	 */
	public static int translateWinner(JsonObject serverModel) {
		int winner = serverModel.get("winner").getAsInt();
		return winner;
	}
	
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
	
	
	public GameInfo translateGameInfo(JsonObject data) {
		GameInfo gameInfo = (GameInfo)g.fromJson(data.get("Response-body"), GameInfo.class);
		return gameInfo;
	}
	
	/**
	 * Translates Json into a list of games. Used for getGamesList()
	 */
	/*
	public static GameInfo[] translateGamesList(JsonObject object) {
		//System.out.println("In JSONtoModel translateGamesList function");
		//System.out.println("JSON Model games object" + object.toString());
		String json = object.get("Response-body").getAsString();
		ArrayList<String> games = new ArrayList<String>();
		int startIndex = -2;
		int endIndex = -2;
		while(json.indexOf("title") != -1) {
			startIndex = json.indexOf("title");
			endIndex = json.indexOf("]}");
			games.add(json.substring(startIndex-1, endIndex + 1));
			json = json.substring(endIndex + 1, json.length());
		}
		for(int i = 0; i < games.size(); i++) {
			//System.out.println(games.get(i));
		}

		GameInfo[] gameInfos = new GameInfo[games.size()];
		for(int i = 0; i < games.size(); i++) {
			gameInfos[i] = parseSingleGameInfo(games.get(i));
		}
		
		return gameInfos;

	}
	
	// I FREAKING HATE JSON TONIGHT! I HOPE SOMEONE CAN FIND AN EASIER WAY TO DO THIS. IT'S KILLING ME. Love Tommy.
	private static GameInfo parseSingleGameInfo(String input) {
		//System.out.println("I maed it into parseSingleGameInfo");
		System.out.println();
		System.out.println();
		//System.out.println();
		System.out.println(input);

		GameInfo gameInfo = new GameInfo();
		int titleBegin = 9;
		int titleEnd = input.indexOf('"', 9);
		String title = input.substring(titleBegin, titleEnd);
		//System.out.println("TitleBegin = " + titleBegin);
		//System.out.println("TitleEnd = " + titleEnd);
		//System.out.println("Title = " + title);
		
		int idIndexBegin = input.indexOf("id") + 4;
		int idIndexEnd = input.indexOf(',', idIndexBegin);
		String stringedId = input.substring(idIndexBegin, idIndexEnd);
		//System.out.println("ID StartIndex = " + idIndexBegin);
		//System.out.println("ID EndIndex = " + idIndexEnd);
		//System.out.println("StringyID = " + stringedId);
		int id = Integer.parseInt(input.substring(idIndexBegin, idIndexEnd));
		//System.out.println("ID = " + id);
		

		
		gameInfo.setTitle(title);
		gameInfo.setId(id);
		int nextPlayerStartIndex = input.indexOf('{');
		for(int i = 0; i < 4; i++) {
			int nextPlayerEndIndex = input.indexOf('}', nextPlayerStartIndex);
			System.out.println("NextPlayerStart = " + nextPlayerStartIndex);
			System.out.println("NextPlayerEnd = " + nextPlayerEndIndex);

			PlayerInfo player = new PlayerInfo();
			
			if(nextPlayerStartIndex != nextPlayerEndIndex - 1) {
				int colorBegin = input.indexOf("color", nextPlayerStartIndex) + 8;
				int colorEnd = input.indexOf('"', colorBegin);
				String color = input.substring(colorBegin, colorEnd);
				System.out.println("Color = " + color);
				
				CatanColor cc = null;
				try {
					cc = CatanColor.getCatanColor(color);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				System.out.println("CatanColor = " + cc);

				
				int nameBegin = input.indexOf("name", colorEnd) + 7;
				int nameEnd = input.indexOf('"', nameBegin);
				String name = input.substring(nameBegin, nameEnd);
				System.out.println("name = " + name + "\n");
				
				int playerIdBegin =  input.indexOf("id", nameEnd) + 4;
				int playerIdEnd = input.indexOf('}', playerIdBegin);
				String playerIdString = input.substring(playerIdBegin, playerIdEnd);
				System.out.println("playerIdBegin = " + playerIdBegin);
				System.out.println("playerIdEnd = " + playerIdEnd);
				System.out.println(playerIdString);
				int playerId = Integer.parseInt(playerIdString);

				//System.out.println("PlayerId = " + playerId);

				player.setPlayerIndex(i);
				player.setColor(cc);
				player.setName(name);
				player.setId(playerId);
				nextPlayerStartIndex = nextPlayerEndIndex + 2;
				gameInfo.addPlayer(player);
			}
			else {
				nextPlayerStartIndex += 3;
			}
		}
		return gameInfo;
	}
	*/
}