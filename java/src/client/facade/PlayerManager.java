package client.facade;

import java.util.List;

import shared.locations.EdgeDirection;
import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.mapdata.EdgeValue;
import client.models.mapdata.Hex;

/**
 * Determines if certain actions can be taken
 *
 */

public class PlayerManager {
	
	private Player[] players;
	
	public PlayerManager(Player[] players) {
		this.players = players;
	}

	/**
	 * Determines if a player has the resources needed to buy a road.
	 * Player must have at least one brick and one wood
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if a player can buy a road.
	 */
	public boolean canBuyRoad(int playerIndex){
		Resources resources =  this.players[playerIndex].getResources();
		if (resources.getBrickCount() > 1 && resources.getWoodCount() > 1) return true;
		else return true;
	}
	
	/**
	 * Determines if a player has a resource to trade and if it is their turn.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can offer a trade.
	 */
	public boolean hasResources(int playerIndex){
		return true;
	}
	
	/**
	 * Determines if a player has the resources requested from the player offering the trade.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can accept the trade.
	 */
	public boolean hasSpecifiedResources(int playerIndex, TradeOffer tradeOffer){	
		return true;
	}
	
	
	/**
	 * Determines if a player has the resources necessary to buy a settlement.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can buy a settlement.
	 */
	public boolean canBuySettlement(int playerIndex){
		return true;
	}
	
	/**
	 * Determines if a player has the resources necessary to upgrade a settlement to a city.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can upgrade a settlement.
	 */
	public boolean canUpgradeSettlement(int playerIndex){
		return true;
	}
	
	/**
	 * Determines if a player has enough resources to buy a development card.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can buy a dev card.
	 */
	public boolean canBuyDevCard(int playerIndex){
		return true;
	}
	
	/**
	 * Determines if a player has any development cards to play.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can play any dev card.
	 */
	public boolean canPlayDevCard(int playerIndex){
		return true;
	}
	
//	/**
//	 * Determines if the player can play the Year of Plenty development card.
//	 * @param playerIndex Number of player to determine ability to take action
//	 * @return if the player can play the Year of Plenty development card.
//	 */
//	public boolean canPlayYearOfPlenty(int playerIndex){
//		return true;
//	}
//	
//	/**
//	 * Determines if the player can play the Soldier development card.
//	 * @param playerIndex Number of player to determine ability to take action
//	 * @return if the player can play the Soldier development card.
//	 */
//	
//	public boolean canPlaySoldier(int playerIndex){
//		return true;
//	}
//	
//	/**
//	 * Determines if the player can play the Monopoly development card.
//	 * @param playerIndex Number of player to determine ability to take action
//	 * @return if the player can play the Monopoly development card.
//	 */
//	
//	public boolean canPlayMonopoly(int playerIndex){
//		return true;
//	}
//	
//	/**
//	 * Determines if the player can play the Monument development card.
//	 * @param playerIndex Number of player to determine ability to take action
//	 * @return if the player can play the Monument development card.
//	 */
//	
//	public boolean canPlayMonument(int playerIndex){
//		return true;
//	}
//
//	/**
//	 * Determines if the player can play the RoadBuilding development card.
//	 * @param playerIndex Number of player to determine ability to take action
//	 * @return if the player can play the RoadBuilding development card.
//	 */
//	
//	public boolean canPlayRoadBuilding(int playerIndex){
//		return true;
//	}

}
