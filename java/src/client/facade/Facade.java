package client.facade;

import java.util.Random;
import java.util.regex.Pattern;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
import client.models.TradeOffer;
import client.models.User;

/**
 * Facade class interfaces from GUI to Client Communicator
 */
public class Facade {
	private CanDoManager canDo;
	private ClientModel client;
	private User user;
	private ModelToJSON modelToJSON;
	private JSONToModel jsonToModel;
	private ClientCommunicator clientCommunicator;
	
	public Facade (ClientModel cli) {
		this.client = cli;
		this.canDo = new CanDoManager(client);
		this.clientCommunicator = new ClientCommunicator();
		this.jsonToModel = new JSONToModel();
		this.modelToJSON = new ModelToJSON();
		this.user = null;
	}
	
	public int getPlayerIndex() {
		return user.getPlayerIndex();
	}
	
	//CHAT CONTROLLER
	
	/**
	 * Used to send message, called by client, ChatController
	 * @param message received from client
	 */
	public void sendMessage (String message) {
		
		
	}
	
	//GAMEHISTORY CONTROLLER
	
	/**
	 * Called by client to receive updated model, from GameHistoryController, PointsController
	 * @return Client Model
	 */
	public ClientModel getModel() {
		return client;
	}
	
	//DEVCARD CONTROLLER
	
	/**
	 * Called by client to see if player can currently buy dev card
	 * @return true if player can buy dev card, false if not
	 */
	public boolean canBuyCard() {
		return canDo.canBuyDevCard(this.getPlayerIndex());
	}
	
	/**
	 * Called by client to see if player can currently play dev card
	 * @return true if player can play card, false if not
	 */
	public boolean canPlayCard() {
		return canDo.canPlayDevCard(this.getPlayerIndex());
	}
	
	/**
	 * Called by client when player wants to play Monopoly card
	 * @param resource The resource to take from other players
	 */
	public void playMonopolyCard(ResourceType resource) {
		clientCommunicator.playMonopoly(modelToJSON.createPlayMonopolyObject(this.getPlayerIndex(), resource));
	}
	
	/**
	 * Called by client when player wants to play Monument card
	 */
	public void playMonumentCard() {
		clientCommunicator.playMonopoly(modelToJSON.createPlayerIndex(this.getPlayerIndex()));
	}
	
	/**
	 * Called by client when player wants to play Road Building card
	 */
	public void playRoadBuildCard() {
		
	}
	
	
	/**
	 * Called by client when player wants to play Year of Plenty card
	 * @param resource1 First resource to gain
	 * @param resource2 Second resource to gain
	 */
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		clientCommunicator.playYearOfPlenty(modelToJSON.createYearOfPlentyObject(resource1, resource2));
	}
	
	// DISCARD CONTROLLER
	/**
	 * This method is called when the user increases the amount of the specified
	 * resource.
	 * 
	 * @param resource
	 *            The resource that was increased
	 */
	public void increaseAmount(ResourceType resource) {
	}
	
	/**
	 * This method is called when the user decreases the amount of the specified
	 * resource.
	 * 
	 * @param resource
	 *            The resource that was decreased
	 */
	public void decreaseAmount(ResourceType resource) {
	}
	
	/**
	 * This method is called when the user clicks the discard button.
	 */
	public void discard() {
	}
	
	
	// DOMESTIC TRADE CONTROLLER
	/**
	 * Called by the domestic trade view when the user clicks the domestic trade
	 * button.
	 */
	public boolean canDomesticTrade() {
		return false;
	}
	
	
	/**
	 * Called by the domestic trade overlay when the user decreases the amount
	 * of a resource.
	 * 
	 * @param resource
	 *            The resource whose amount is being decreased
	 */
	public void decreaseDomesticTradeResourceAmount(ResourceType resource) {
	}
	
	/**
	 * Called by the domestic trade overlay when the user increases the amount
	 * of a resource.
	 * 
	 * @param resource
	 *            The resource whose amount is being increased
	 */
	public void increaseDomesticTradeResourceAmount(ResourceType resource) {
	}
	
	/**
	 * Called by the domestic trade overlay when the user clicks the trade
	 * button.
	 */
	public void sendTradeOffer() {
	}
	
	/**
	 * Called by the domestic trade overlay when the user selects a player to
	 * trade with.
	 * 
	 * @param playerIndex
	 *            The index [0, 3] of the selected trading partner, or -1 if
	 *            "None" was selected
	 */
	public void setPlayerToTradeWith(int playerIndex) {
	}
	
	/**
	 * Called by the domestic trade overlay when the user selects a resource to
	 * be received.
	 * 
	 * @param resource
	 *            The resource to be received
	 */
	public void setResourceToReceive(ResourceType resource) {
	}
	
	/**
	 * Called by the domestic trade overlay when the user selects a resource to
	 * be sent.
	 * 
	 * @param resource
	 *            The resource to be sent
	 */
	public void setResourceToSend(ResourceType resource) {
	}
	
	/**
	 * Called by the domestic trade overlay when user selects "none" for a
	 * resource.
	 * 
	 * @param resource
	 *            The resource for which "none" was selected
	 */
	public void unsetResource(ResourceType resource) {
	}
	
	/**
	 * Called by the accept trade overlay when the user either accepts or
	 * rejects a trade.
	 * 
	 * @param willAccept
	 *            Whether or not the user accepted the trade
	 */
	public void acceptTrade(boolean willAccept) {
	}
	
	
	// JOIN GAME CONTROLLER
	/**
	 * Displays the join game view
	 */
	public boolean canJoinGame() { //we changed this from start()
		return false;
	}
	
	/**
	 * Called by the join game view when the user clicks "Create new game"
	 * button. Displays the new game view.
	 */
	public boolean canStartCreateNewGame() {
		return false;
	}
	
	/**
	 * Called by the new game view when the user clicks the "Create Game" button
	 */
	public void createNewGame() {
	}
	
	/**
	 * Called by the join game view when the user clicks a "Join" or "Re-join"
	 * button. Displays the select color view.
	 * 
	 * @param game
	 *            The game that the user is joining
	 */
	public void startJoinGame(GameInfo game) {
	}
	
	/**
	 * Called by the select color view when the user clicks the "Join Game"
	 * button
	 * 
	 * @param color
	 *            The color selected by the user
	 */
	public void joinGame(CatanColor color) {
	}
	
	
	
	
	// PLAYER WAITING CONTROLLER
	/**
	 * Displays the player waiting view
	 */
	public boolean canStartPlayerWaitingView() { //changed from start()
		return false;
	}
	
	/**
	 * Called when the "Add AI" button is clicked in the player waiting view
	 */
	public void addAI() {
	}
	
//	// LOGIN CONTROLLER
//	/**
//	 * Displays the login view
//	 */
//	public boolean canLogin() { //changed from start()
//		return false;
//	}
	
	/**
	 * Called when the user clicks the "Sign in" button in the login view
	 */
	public boolean signIn(String username, String password) {
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		if (p.matcher(username).find() || p.matcher(password).find()) return false;
		
		JsonObject userObject = this.modelToJSON.createUserObject(username, password);
		JsonObject returnedJson = this.clientCommunicator.userLogin(userObject);
		
		if(returnedJson.get("Response-body").getAsString().equals("Success")) {
			String userCookie = returnedJson.get("User-cookie").getAsString();
			int playerID = returnedJson.get("Set-cookie").getAsJsonObject().get("playerID").getAsInt();
			User newUser = new User(username, password, userCookie, playerID);
			this.user = newUser;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Called when the user clicks the "Register" button in the login view
	 */
	public boolean register(String username, String password) {
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		if (p.matcher(username).find() || p.matcher(password).find()) return false;
		
		JsonObject userObject = this.modelToJSON.createUserObject(username, password);
		JsonObject returnedJson = this.clientCommunicator.userRegister(userObject);
		
		if(returnedJson.get("Response-body").getAsString().equals("Success")) {
			String userCookie = returnedJson.get("User-cookie").getAsString();
			int playerID = returnedJson.get("Set-cookie").getAsJsonObject().get("playerID").getAsInt();
			User newUser = new User(username, password, userCookie, playerID);
			this.user = newUser;
			return true;
		}
		
		return false;
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
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		client.models.mapdata.EdgeLocation edge = new client.models.mapdata.EdgeLocation(edgeLoc);
		return canDo.canPlaceRoadAtLocation(this.getPlayerIndex(), edge);
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
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		if (canDo.canBuySettlement(this.getPlayerIndex())) {
			return canDo.canPlaceSettlementAtLocation(this.getPlayerIndex(), new client.models.mapdata.EdgeLocation(vertLoc));
		}
		else return false;
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
	public boolean canPlaceCity(VertexLocation vertLoc) {
		if (canDo.canUpgradeSettlement(this.getPlayerIndex())) {
			return canDo.canUpgradeSettlementAtLocation(this.getPlayerIndex(), new client.models.mapdata.EdgeLocation(vertLoc));			
		}
		else return false;
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
	public boolean canPlaceRobber(HexLocation hexLoc) {
		client.models.mapdata.HexLocation hex = new client.models.mapdata.HexLocation(hexLoc.getX(), hexLoc.getY());
		return canDo.canPlaceRobber(hex);
	}
	
	/**
	 * This method is called when the user clicks the mouse to place a road.
	 * 
	 * @param edgeLoc
	 *            The road location
	 */
	public void placeRoad(EdgeLocation edgeLoc) {
	}
	
	/**
	 * This method is called when the user clicks the mouse to place a
	 * settlement.
	 * 
	 * @param vertLoc
	 *            The settlement location
	 */
	public void placeSettlement(VertexLocation vertLoc) {
	}
	
	/**
	 * This method is called when the user clicks the mouse to place a city.
	 * 
	 * @param vertLoc
	 *            The city location
	 */
	public void placeCity(VertexLocation vertLoc) {
	}
	
	/**
	 * This method is called when the user clicks the mouse to place the robber.
	 * 
	 * @param hexLoc
	 *            The robber location
	 */
	public void placeRobber(HexLocation hexLoc) {
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
	public void startMove(PieceType pieceType, boolean isFree,
				   boolean allowDisconnected) {
	}
	
	/**
	 * This method is called when the user plays a "soldier" development card.
	 * It should initiate robber placement.
	 */
	public void playSoldierCard() {
	}
	
	/**
	 * This method is called when the user plays a "road building" progress
	 * development card. It should initiate the process of allowing the player
	 * to place two roads.
	 */
	public void playRoadBuildingCard() {
	}
	
	/**
	 * This method is called by the Rob View when a player to rob is selected
	 * via a button click.
	 * 
	 * @param victim
	 *            The player to be robbed
	 */
	public void robPlayer(RobPlayerInfo victim) {
	}
	
	
	
	
	
	// MARITIME TRADE CONTROLLER
	/**
	 * Called by the maritime trade view when the user clicks the maritime trade
	 * button.
	 */
	public void startMaritimeTrade() {
	}
	
	/**
	 * Make the specified trade with the bank.
	 */
	public void makeMaritimeTrade() {
	}
	
	/**
	 * Called when the user selects the resource to get.
	 * 
	 * @param resource
	 *            The selected "get" resource
	 */
	public void setGetResource(ResourceType resource) {
	}
	
	/**
	 * Called when the user selects the resource to give.
	 * 
	 * @param resource
	 *            The selected "give" resource
	 */
	public void setGiveResource(ResourceType resource) {
	}
	
	/**
	 * Called when the player "undoes" their get selection.
	 */
	public void unsetGetValue() {
	}
	
	/**
	 * Called when the player "undoes" their give selection.
	 */
	public void unsetGiveValue() {
	}
	
	
	// POINTS CONTROLLER
	//There wasn't anything to put here in the facade
	
	
	
	// RESOURCE BAR CONTROLLER

	/**
	 * Called by the view then the user requests to build a road
	 */
	public void buildRoad(/*data*/) {
		/*jsonData = modelToJSON.placeroad(modelData)
		serverjsonData = clientCommunicator.buildRoad(jsonData)
		client.update(JSONToModel.updateModel(serverjsonData))
		*/
	}
	
	/**
	 * Called by the view then the user requests to build a settlement
	 */
	public void buildSettlement() {
	}
	
	/**
	 * Called by the view then the user requests to build a city
	 */
	public void buildCity() {
	}
	
	/**
	 * Called by the view then the user requests to buy a card
	 */
	public void buyCard() {
	}
	
	/**
	 * Called by the view then the user requests to play a card
	 */
	public void playCard() {
	}
	
	public boolean canAcceptTrade(TradeOffer tradeOffer){ return canDo.canAcceptTrade(this.getPlayerIndex(), tradeOffer) ;}
	
	
	// ROLL CONTROLLER
	
	/**
	 * Called when the user clicks the "Roll!" button in the roll view
	 */
	public int rollDice() {
		Random rand = new Random();

		int  die1 = rand.nextInt(6) + 1;
		int  die2 = rand.nextInt(6) + 1;
		return die1+die2;
	}
	
	
	
	// TURN TRACKER CONTROLLER
	
	/**
	 * This is called when the local player ends their turn
	 */
	public void endTurn() {
	}
}
