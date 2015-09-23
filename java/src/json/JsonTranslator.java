package json;

import client.models.ClientModel;
import client.models.Player;
import client.models.ResourceList;
import client.models.TradeOffer;
import client.models.TurnTracker;
import client.models.communication.MessageList;
import client.models.mapdata.Board;

/**
 * Translates JSON documentation to class structure used in model
 *
 */

public class JsonTranslator {
	
	/**
	 * Parent method to translate entire model
	 * @return updated ClientModel
	 */
	public static ClientModel translateClientModel() {
		return null;
	}
	
	
	/**
	 * Translates bank to put in updated client model
	 * @return updated bank
	 */
	public static ResourceList translateBank() {
		return null;
	}
	
	/**
	 * Translates chat to put in updated client model
	 * @return updated chat
	 */
	public static MessageList translateChat() {
		return null;
	}
	
	/**
	 * Translates log to put in updated client model
	 * @return updated log
	 */
	public static MessageList translateLog() {
		return null;
	}
	
	/**
	 * Translates board to put in updated client model
	 * @return updated board
	 */
	public static Board translateBoard() {
		return null;
	}
	
	/**
	 * Translates player list and info to put in updated client model
	 * @return updated player list
	 */
	public static Player[] translatePlayers() {
		return null;
	}
	
	/**
	 * Translates trade offer to put in updated client model
	 * @return updated trade offer
	 */
	public static TradeOffer translateTradeOffer() {
		return null;
	}
	
	/**
	 * Translates turn tracker to put in updated client model
	 * @return updated turn tracker
	 */
	public static TurnTracker translateTurnTracker() {
		return null;
	}
	
	/**
	 * Obtains version number from JSON
	 * @return updated version number
	 */
	public static int translateVersion() {
		return 0;
	}
	
	/**
	 * Obtains winner index from JSON
	 * @return updated winner index
	 */
	public static int translateWinner() {
		return 0;
	}
}