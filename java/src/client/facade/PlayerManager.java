package client.facade;

import client.models.ClientModel;
import client.models.DevCards;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;

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
		Player p = players[playerIndex];
		return p.hasBrick() && p.hasWood();
	}
	
	public int numResources(int playerIndex) {
		Player p = players[playerIndex];
		return p.numResources();
	}
	
	/**
	 * Determines if a player has a resource to trade and if it is their turn.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can offer a trade.
	 */
	public boolean hasResources(int playerIndex){
		Player p =players[playerIndex];
		return p.hasBrick() || p.hasOre() || p.hasSheep() || p.hasWheat() || p.hasWood();
	}
	
	public boolean hasBrick(int playerIndex) {
		Player p = players[playerIndex];
		return p.hasBrick();
	}
	
	public boolean hasOre(int playerIndex) {
		Player p = players[playerIndex];
		return p.hasOre();
	}
	
	public boolean hasSheep(int playerIndex) {
		Player p = players[playerIndex];
		return p.hasSheep();
	}
	
	public boolean hasWheat(int playerIndex) {
		Player p = players[playerIndex];
		return p.hasWheat();
	}
	
	public boolean hasWood(int playerIndex) {
		Player p = players[playerIndex];
		return p.hasWood();
	}

	/**
	 * Determines if a player has the resources requested from the player offering the trade.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can accept the trade.
	 */
	public boolean hasSpecifiedResources(int playerIndex, TradeOffer tradeOffer){	
		Player p = players[playerIndex];
		Resources offer = tradeOffer.getOffer();
		
		return p.hasBrick(offer.getBrickCount()) && 
			   p.hasOre(offer.getOreCount())     && 
			   p.hasSheep(offer.getSheepCount()) && 
			   p.hasWheat(offer.getWheatCount()) &&
			   p.hasWood(offer.getWoodCount());
	}

	
	/**
	 * Determines if a player has the resources necessary to buy a settlement.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can buy a settlement.
	 */
	public boolean canBuySettlement(int playerIndex){
		Player p = players[playerIndex];
		return p.hasBrick() && p.hasWheat() && p.hasSheep() && p.hasWood();
	}
	
	/**
	 * Determines if a player has the resources necessary to upgrade a settlement to a city.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can upgrade a settlement.
	 */
	public boolean canUpgradeSettlement(int playerIndex){
		Player p = players[playerIndex];
		return p.hasWheat(2) && p.hasOre(3);
	}
	
	/**
	 * Determines if a player has enough resources to buy a development card.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can buy a dev card.
	 */
	public boolean canBuyDevCard(int playerIndex){
		Player p = players[playerIndex];
		return (p.hasSheep() && p.hasWheat() && p.hasOre());
	}
	
	/**
	 * Determines if a player has any development cards to play.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can play any dev card.
	 */
	public boolean canPlayDevCard(int playerIndex){
		Player p = players[playerIndex];
		DevCards d = players[playerIndex].getCurrentDevCards();
		
		return p.hasMonopolyCard(d) || p.hasMonumentCard(d) || p.hasRoadBuildingCard(d) || 
				p.hasSoldierCard(d) || p.hasYearOfPlentyCard(d);
	}	
	
	public void updatePointersToNewModel(ClientModel newModel) {
		this.players = newModel.getPlayers();
	}
}