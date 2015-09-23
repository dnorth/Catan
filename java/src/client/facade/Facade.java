package client.facade;

import server.proxy.ClientCommunicator;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.data.RobPlayerInfo;
import client.models.ClientModel;

/**
 * Facade class interfaces from GUI to Client Communicator
 */
public class Facade {
	private PlayerManager playerManager;
	private TurnManager turnManager;
	private ClientModel client;
	private ClientCommunicator clientCommunicator;
	
	public Facade (ClientModel cli) {
		
	}
	
	/**
	 * Displays the login view
	 */
	void start() {
	}
	
	/**
	 * Called when the user clicks the "Sign in" button in the login view
	 */
	void signIn() {
	}
	
	/**
	 * Called when the user clicks the "Register" button in the login view
	 */
	void register() {
	}
	
	/**
	 * This method is called whenever the user is trying to place a road on the
	 * map. It is called by the view for each "mouse move" event. The returned
	 * value tells the view whether or not to allow the road to be placed at the
	 * specified location.
	 * 
	 * @param edgeLoc
	 *            The proposed road location
	 * @return true if the road can be placed at edgeLoc, false otherwise
	 */
	boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return false;
	}
	
	/**
	 * This method is called whenever the user is trying to place a settlement
	 * on the map. It is called by the view for each "mouse move" event. The
	 * returned value tells the view whether or not to allow the settlement to
	 * be placed at the specified location.
	 * 
	 * @param vertLoc
	 *            The proposed settlement location
	 * @return true if the settlement can be placed at vertLoc, false otherwise
	 */
	boolean canPlaceSettlement(VertexLocation vertLoc) {
		return false;
	}
	
	/**
	 * This method is called whenever the user is trying to place a city on the
	 * map. It is called by the view for each "mouse move" event. The returned
	 * value tells the view whether or not to allow the city to be placed at the
	 * specified location.
	 * 
	 * @param vertLoc
	 *            The proposed city location
	 * @return true if the city can be placed at vertLoc, false otherwise
	 */
	boolean canPlaceCity(VertexLocation vertLoc) {
		return false;
	}
	
	/**
	 * This method is called whenever the user is trying to place the robber on
	 * the map. It is called by the view for each "mouse move" event. The
	 * returned value tells the view whether or not to allow the robber to be
	 * placed at the specified location.
	 * 
	 * @param hexLoc
	 *            The proposed robber location
	 * @return true if the robber can be placed at hexLoc, false otherwise
	 */
	boolean canPlaceRobber(HexLocation hexLoc) {
		return false;
	}
	
	/**
	 * This method is called when the user clicks the mouse to place a road.
	 * 
	 * @param edgeLoc
	 *            The road location
	 */
	void placeRoad(EdgeLocation edgeLoc) {
	}
	
	/**
	 * This method is called when the user clicks the mouse to place a
	 * settlement.
	 * 
	 * @param vertLoc
	 *            The settlement location
	 */
	void placeSettlement(VertexLocation vertLoc) {
	}
	
	/**
	 * This method is called when the user clicks the mouse to place a city.
	 * 
	 * @param vertLoc
	 *            The city location
	 */
	void placeCity(VertexLocation vertLoc) {
	}
	
	/**
	 * This method is called when the user clicks the mouse to place the robber.
	 * 
	 * @param hexLoc
	 *            The robber location
	 */
	void placeRobber(HexLocation hexLoc) {
	}
	
	/**
	 * This method is called when the user requests to place a piece on the map
	 * (road, city, or settlement)
	 * 
	 * @param pieceType
	 *            The type of piece to be placed
	 * @param isFree
	 *            true if the piece should not cost the player resources, false
	 *            otherwise. Set to true during initial setup and when a road
	 *            building card is played.
	 * @param allowDisconnected
	 *            true if the piece can be disconnected, false otherwise. Set to
	 *            true only during initial setup.
	 */
	void startMove(PieceType pieceType, boolean isFree,
				   boolean allowDisconnected) {
	}
	
	/**
	 * This method is called from the modal map overlay when the cancel button
	 * is pressed.
	 */
	void cancelMove() {
	}
	
	/**
	 * This method is called when the user plays a "soldier" development card.
	 * It should initiate robber placement.
	 */
	void playSoldierCard() {
	}
	
	/**
	 * This method is called when the user plays a "road building" progress
	 * development card. It should initiate the process of allowing the player
	 * to place two roads.
	 */
	void playRoadBuildingCard() {
	}
	
	/**
	 * This method is called by the Rob View when a player to rob is selected
	 * via a button click.
	 * 
	 * @param victim
	 *            The player to be robbed
	 */
	void robPlayer(RobPlayerInfo victim) {
	}
	
	/**
	 * Called by the maritime trade view when the user clicks the maritime trade
	 * button.
	 */
	void startTrade() {
	}
	
	/**
	 * Make the specified trade with the bank.
	 */
	void makeTrade() {
	}
	
	/**
	 * Called by the maritime trade overlay when the user cancels a trade.
	 */
	void cancelTrade() {
	}
	
	/**
	 * Called when the user selects the resource to get.
	 * 
	 * @param resource
	 *            The selected "get" resource
	 */
	void setGetResource(ResourceType resource) {
	}
	
	/**
	 * Called when the user selects the resource to give.
	 * 
	 * @param resource
	 *            The selected "give" resource
	 */
	void setGiveResource(ResourceType resource) {
	}
	
	/**
	 * Called when the player "undoes" their get selection.
	 */
	void unsetGetValue() {
	}
	
	/**
	 * Called when the player "undoes" their give selection.
	 */
	void unsetGiveValue() {
	}
	
	/**
	 * Called by the view then the user requests to build a road
	 */
	void buildRoad() {
	}
	
	/**
	 * Called by the view then the user requests to build a settlement
	 */
	void buildSettlement() {
	}
	
	/**
	 * Called by the view then the user requests to build a city
	 */
	void buildCity() {
	}
	
	/**
	 * Called by the view then the user requests to buy a card
	 */
	void buyCard() {
	}
	
	/**
	 * Called by the view then the user requests to play a card
	 */
	void playCard() {
	}
	
	/**
	 * Called when the user clicks the "Roll!" button in the roll view
	 */
	void rollDice() {
	}
	
	/**
	 * This is called when the local player ends their turn
	 */
	void endTurn() {
	}
}
