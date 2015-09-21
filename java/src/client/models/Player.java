package client.models;

import java.util.List;

import shared.locations.EdgeDirection;
import client.models.mapdata.Hex;



public class Player
{
	int citiesNum;
	String color;
	boolean discarded;
	int monuments;
	String name;
	List<DevCardList> newDevCards;
	List<DevCardList> oldDevCards;
	int playerIndex;
	boolean playedDevCard;
	int playerID;
	List<ResourceList> resources;
	int roads;
	int settlements;
	int soldiers;
	int victoryPoints;
	
	/**
	 * Determines if a player has the resources needed to buy a road.
	 * @return if a player can buy a road.
	 */
	public boolean canBuyRoad(){
		return true;
	}
	
	/**
	 * Determines if a player has a location where they can place a road
	 * anywhere on the board.
	 * @return if a player can place a road on the board.
	 */
	public boolean canPlaceRoad(){
		return true;
	}
	
	/**
	 * Determines if a player can place a road on a specific hex edge.
	 * @return if the player can place a road at the HexLocation.
	 */
	public boolean canPlaceRoadAtLocation(Hex hex, EdgeDirection h){
		return true;
	}
	
	/**
	 * Determines if a player has a resource to trade and if it is their turn.
	 * @return if the player can offer a trade.
	 */
	public boolean canOfferTrade(){
		return true;
	}
	
	/**
	 * Determines if a player has the resources requested from the player offering the trade.
	 * @return if the player can accept the trade.
	 */
	public boolean canAcceptTrade(){	
		return true;
	}
	
	
	/**
	 * Determines if the player has a settlement in a port.
	 * @return if the player has a settlement in a port.
	 */
	
	public boolean canMaritimeTrade(){
		return true;
	}
	
	/**
	 * Determines if a player has the resources necessary to buy a settlement.
	 * @return if the player can buy a settlement.
	 */
	public boolean canBuySettlement(){
		return true;
	}
	
	/**
	 * Determines if a player has the resources necessary to upgrade a settlement to a city.
	 * @return if the player can upgrade a settlement.
	 */
	public boolean canUpgradeSettlement(){
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
	 * @return if the player can buy a dev card.
	 */
	public boolean canBuyDevCard(){
		return true;
	}
	
	/**
	 * Determines if a player has any development cards to play.
	 * @return if the player can play any dev card.
	 */
	public boolean canPlayDevCard(){
		return true;
	}
	
	/**
	 * Determines if the player can play the Year of Plenty development card.
	 * @return if the player can play the Year of Plenty development card.
	 */
	public boolean canPlayYearOfPlenty(){
		return true;
	}
	
	/**
	 * Determines if the player can play the Soldier development card.
	 * @return if the player can play the Soldier development card.
	 */
	
	public boolean canPlaySoldier(){
		return true;
	}
	
	/**
	 * Determines if the player can play the Monopoly development card.
	 * @return if the player can play the Monopoly development card.
	 */
	
	public boolean canPlayMonopoly(){
		return true;
	}
	
	/**
	 * Determines if the player can play the Monument development card.
	 * @return if the player can play the Monument development card.
	 */
	
	public boolean canPlayMonument(){
		return true;
	}

	/**
	 * Determines if the player can play the RoadBuilding development card.
	 * @return if the player can play the RoadBuilding development card.
	 */
	
	public boolean canPlayRoadBuilding(){
		return true;
	}

}