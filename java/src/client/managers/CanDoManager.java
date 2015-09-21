package client.managers;

import shared.locations.EdgeDirection;
import client.models.mapdata.Hex;

/**
 * Determines if certain actions can be taken
 *
 */

public class CanDoManager {
	
	/**
	 * Determines if a player has the resources needed to buy a road.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if a player can buy a road.
	 */
	public boolean canBuyRoad(int playerIndex){
		return true;
	}
	
	/**
	 * Determines if a player has a location where they can place a road
	 * anywhere on the board.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if a player can place a road on the board.
	 */
	public boolean canPlaceRoad(int playerIndex){
		return true;
	}
	
	/**
	 * Determines if a player can place a road on a specific hex edge.
	 * @param playerIndex Number of player to determine ability to take action
	 * @param hex Hex where road will be built
	 * @param edge Edge on hex where road will be built
	 * @return if the player can place a road at the HexLocation.
	 */
	public boolean canPlaceRoadAtLocation(int playerIndex, Hex hex, EdgeDirection edge){
		return true;
	}
	
	/**
	 * Determines if a player has a resource to trade and if it is their turn.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can offer a trade.
	 */
	public boolean canOfferTrade(int playerIndex){
		return true;
	}
	
	/**
	 * Determines if a player has the resources requested from the player offering the trade.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can accept the trade.
	 */
	public boolean canAcceptTrade(int playerIndex){	
		return true;
	}
	
	
	/**
	 * Determines if the player has a settlement in a port.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player has a settlement in a port.
	 */
	
	public boolean canMaritimeTrade(int playerIndex){
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
	 * Determines if there are any players to rob after the robber is placed.
	 * @return if there is a player that can be robbed.
	 */
	public boolean canRobPlayer(){
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
	
	/**
	 * Determines if the player can play the Year of Plenty development card.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can play the Year of Plenty development card.
	 */
	public boolean canPlayYearOfPlenty(int playerIndex){
		return true;
	}
	
	/**
	 * Determines if the player can play the Soldier development card.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can play the Soldier development card.
	 */
	
	public boolean canPlaySoldier(int playerIndex){
		return true;
	}
	
	/**
	 * Determines if the player can play the Monopoly development card.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can play the Monopoly development card.
	 */
	
	public boolean canPlayMonopoly(int playerIndex){
		return true;
	}
	
	/**
	 * Determines if the player can play the Monument development card.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can play the Monument development card.
	 */
	
	public boolean canPlayMonument(int playerIndex){
		return true;
	}

	/**
	 * Determines if the player can play the RoadBuilding development card.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can play the RoadBuilding development card.
	 */
	
	public boolean canPlayRoadBuilding(int playerIndex){
		return true;
	}

}
