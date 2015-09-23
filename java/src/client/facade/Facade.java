package client.facade;

import server.proxy.ClientCommunicator;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.data.GameInfo;
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
	
	// DISCARD CONTROLLER
	/**
	 * This method is called when the user increases the amount of the specified
	 * resource.
	 * 
	 * @param resource
	 *            The resource that was increased
	 */
	void increaseAmount(ResourceType resource) {
	}
	
	/**
	 * This method is called when the user decreases the amount of the specified
	 * resource.
	 * 
	 * @param resource
	 *            The resource that was decreased
	 */
	void decreaseAmount(ResourceType resource) {
	}
	
	/**
	 * This method is called when the user clicks the discard button.
	 */
	void discard() {
	}
	
	
	// DOMESTIC TRADE CONTROLLER
	/**
	 * Called by the domestic trade view when the user clicks the domestic trade
	 * button.
	 */
	void startTrade() {
	}
	
	/**
	 * Called by the domestic trade overlay when the user decreases the amount
	 * of a resource.
	 * 
	 * @param resource
	 *            The resource whose amount is being decreased
	 */
	void decreaseResourceAmount(ResourceType resource) {
	}
	
	/**
	 * Called by the domestic trade overlay when the user increases the amount
	 * of a resource.
	 * 
	 * @param resource
	 *            The resource whose amount is being increased
	 */
	void increaseResourceAmount(ResourceType resource) {
	}
	
	/**
	 * Called by the domestic trade overlay when the user clicks the trade
	 * button.
	 */
	void sendTradeOffer() {
	}
	
	/**
	 * Called by the domestic trade overlay when the user selects a player to
	 * trade with.
	 * 
	 * @param playerIndex
	 *            The index [0, 3] of the selected trading partner, or -1 if
	 *            "None" was selected
	 */
	void setPlayerToTradeWith(int playerIndex) {
	}
	
	/**
	 * Called by the domestic trade overlay when the user selects a resource to
	 * be received.
	 * 
	 * @param resource
	 *            The resource to be received
	 */
	void setResourceToReceive(ResourceType resource) {
	}
	
	/**
	 * Called by the domestic trade overlay when the user selects a resource to
	 * be sent.
	 * 
	 * @param resource
	 *            The resource to be sent
	 */
	void setResourceToSend(ResourceType resource) {
	}
	
	/**
	 * Called by the domestic trade overlay when user selects "none" for a
	 * resource.
	 * 
	 * @param resource
	 *            The resource for which "none" was selected
	 */
	void unsetResource(ResourceType resource) {
	}
	
	/**
	 * Called by the domestic trade overlay when the user cancels a trade.
	 */
	void cancelTrade() {
	}
	
	/**
	 * Called by the accept trade overlay when the user either accepts or
	 * rejects a trade.
	 * 
	 * @param willAccept
	 *            Whether or not the user accepted the trade
	 */
	void acceptTrade(boolean willAccept) {
	}
	
	
	// JOIN GAME CONTROLLER
	/**
	 * Displays the join game view
	 */
	void start() {
	}
	
	/**
	 * Called by the join game view when the user clicks "Create new game"
	 * button. Displays the new game view.
	 */
	void startCreateNewGame() {
	}
	
	/**
	 * Called by the new game view when the user clicks the "Cancel" button
	 */
	void cancelCreateNewGame() {
	}
	
	/**
	 * Called by the new game view when the user clicks the "Create Game" button
	 */
	void createNewGame() {
	}
	
	/**
	 * Called by the join game view when the user clicks a "Join" or "Re-join"
	 * button. Displays the select color view.
	 * 
	 * @param game
	 *            The game that the user is joining
	 */
	void startJoinGame(GameInfo game) {
	}
	
	/**
	 * Called by the select color view when the user clicks the "Cancel" button
	 */
	void cancelJoinGame() {
	}
	
	/**
	 * Called by the select color view when the user clicks the "Join Game"
	 * button
	 * 
	 * @param color
	 *            The color selected by the user
	 */
	void joinGame(CatanColor color) {
	}
	
	
	
	
	// PLAYER WAITING CONTROLLER
	/**
	 * Displays the player waiting view
	 */
	void start() {
	}
	
	/**
	 * Called when the "Add AI" button is clicked in the player waiting view
	 */
	void addAI() {
	}
	
	// LOGIN CONTROLLER
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
	
	
	
	
	// MAP CONTROLLER
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
	
	
	
	
	
	// MARITIME TRADE CONTROLLER
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
	
	
	// POINTS CONTROLLER
	//There wasn't anything to put here in the facade
	
	
	
	// RESOURCE BAR CONTROLLER

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
	
	
	
	// ROLL CONTROLLER
	
	/**
	 * Called when the user clicks the "Roll!" button in the roll view
	 */
	void rollDice() {
	}
	
	
	
	// TURN TRACKER CONTROLLER
	
	/**
	 * This is called when the local player ends their turn
	 */
	void endTurn() {
	}
}
