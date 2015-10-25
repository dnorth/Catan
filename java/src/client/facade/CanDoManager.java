package client.facade;

import java.util.ArrayList;

import client.models.ClientModel;
import client.models.TradeOffer;
import client.models.mapdata.EdgeLocation;
import client.models.mapdata.HexLocation;

public class CanDoManager {

	private ClientModel clientModel;
	private PlayerManager playerManager;
	private TurnManager turnManager;
	private BoardManager boardManager;
	
	public CanDoManager(ClientModel clientModel) {
		this.clientModel = clientModel;
		this.playerManager = new PlayerManager(this.clientModel.getPlayers());
		this.boardManager = new BoardManager(this.clientModel.getBoard());
		this.turnManager = new TurnManager(this.clientModel.getTurnTracker());
	}
	
	/**
	 * Determines if a player has the resources needed to buy a road.
	 * Player must have at least one brick and one wood
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if a player can buy a road.
	 */
	public boolean canBuyRoad(int playerIndex){
		return this.playerManager.canBuyRoad(playerIndex);
	}
	
	/**
	 * Determines if a player can place a road on a specific hex edge.
	 * @param playerIndex Number of player to determine ability to take action
	 * @param hex Hex where road will be built
	 * @param edge Edge on hex where road will be built
	 * @return if the player can place a road at the HexLocation.
	 */
	public boolean canPlaceRoadAtLocation(int playerIndex, EdgeLocation edge){
		return this.boardManager.canPlaceRoadAtLocation(playerIndex, edge);
	}
	
	public boolean canPlaceInitialRoad(int playerIndex, EdgeLocation edge) {
		return this.boardManager.canPlaceInitialRoad(playerIndex, edge);
	}
	
	public int numResources(int playerIndex) {
		return this.playerManager.numResources(playerIndex);
	}
	
	/**
	 * Determines if player has any resources, in order to trade at all
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can offer trade
	 */
	public boolean canOfferTrade(int playerIndex){
		return this.playerManager.hasResources(playerIndex);
	}
	
	/**
	 * Determines if a player has the resources requested from the player offering the trade.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can accept the trade.
	 */
	public boolean canAcceptTrade(int playerIndex, TradeOffer tradeOffer){	
		return this.playerManager.hasSpecifiedResources(playerIndex, tradeOffer);
	}
	
	/**
	 * Determines if the player has a settlement in a port.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player has a settlement in a port.
	 */
	
	public boolean canMaritimeTrade(int playerIndex){
		return this.boardManager.canMaritimeTrade(playerIndex);
	}
	
	/**
	 * Determines if a player has the resources necessary to buy a settlement.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can buy a settlement.
	 */
	public boolean canBuySettlement(int playerIndex){
		return this.playerManager.canBuySettlement(playerIndex);
	}
	
	public boolean canPlaceSettlementAtLocation(int playerIndex, EdgeLocation edge) {
		return boardManager.canPlaceSettlementAtLocation(playerIndex, edge);
	}
	
	/**
	 * Determines if a player has the resources necessary to upgrade a settlement to a city.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can upgrade a settlement.
	 */
	public boolean canUpgradeSettlement(int playerIndex){
		if (!this.playerManager.canUpgradeSettlement(playerIndex)) return false;
		return true;
	}
	
	public boolean canUpgradeSettlementAtLocation(int playerIndex, EdgeLocation edge) {
		return this.boardManager.canUpgradeSettlementAtLocation(playerIndex, edge);
	}
	
	/**
	 * Determines if a player has enough resources to buy a development card.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can buy a dev card.
	 */
	public boolean canBuyDevCard(int playerIndex){
		return this.playerManager.canBuyDevCard(playerIndex);
	}
	
	/**
	 * Determines if a player has any development cards to play.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player can play any dev card.
	 */
	public boolean canPlayDevCard(int playerIndex){
		return this.playerManager.canPlayDevCard(playerIndex);
	}
	
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return boardManager.canPlaceRobber(hexLoc);
	}
	
	public ArrayList<Integer> whoCanIRob(int playerIndex, HexLocation hexLoc) {
		Integer[] owners = this.boardManager.getOwnersOnHex(hexLoc);
		ArrayList<Integer> theseOwners = new ArrayList<Integer>();
		for(Integer i : owners) {
			if (i != -1 && i != playerIndex && this.playerManager.hasResources(i) && !theseOwners.contains(i)) {
				theseOwners.add(i);
			}
		}
		return theseOwners;
	}
	
	public void updatePointersToNewModel(ClientModel newModel) {
//		this.clientModel = newModel;
		this.boardManager.updatePointersToNewModel(newModel);
		this.playerManager.updatePointersToNewModel(newModel);
		this.turnManager.updatePointersToNewModel(newModel);
	}

	public ClientModel getClientModel() {
		return clientModel;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public TurnManager getTurnManager() {
		return turnManager;
	}

	public BoardManager getBoardManager() {
		return boardManager;
	}
	
	
}
