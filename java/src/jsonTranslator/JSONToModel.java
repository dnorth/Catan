package jsonTranslator;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
		Player[] players = null;
		JsonElement playerIterator = serverModel.get("players");
		//for(Player p : playerIterator)
		//{
		//	
		//}

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
}