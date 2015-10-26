package client.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;
import java.util.Random;
import java.util.regex.Pattern;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;

import com.google.gson.JsonObject;

import server.ServerPoller.ServerPoller;
import server.proxy.ClientCommunicator;
import server.proxy.ClientException;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.data.RobPlayerInfo;
import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.mapdata.Board;
import client.models.mapdata.Hex;
import client.models.mapdata.Port;
import client.models.mapdata.Road;

import com.google.gson.JsonObject;

/**
 * Facade class interfaces from GUI to Client Communicator
 */
public class Facade {
	private CanDoManager canDo;
	private ClientModel client;
	private PlayerInfo localPlayer;
	private ModelToJSON modelToJSON;
	private JSONToModel jsonToModel;
	private ClientCommunicator clientCommunicator;
	private GameInfo game; //This is set by the "startJoinGame()" and used by the "joinGame()"
	private GameInfo[] games;
	private ServerPoller poller;
	private HexLocation newRobberLocation; //this has to be here to store where the player wants to place robber
	private int playerWithLongestRoad;
	private int playerWithLargestArmy;

	public Facade (ClientModel cli) {
		this.client = cli;
		this.canDo = new CanDoManager(client);
		this.clientCommunicator = new ClientCommunicator();
		this.jsonToModel = new JSONToModel();
		this.modelToJSON = new ModelToJSON();
		this.localPlayer = null;
		this.newRobberLocation = null;
		playerWithLongestRoad = -1;
		playerWithLargestArmy = -1;
	}
	
	//FREEEEEEEAK I THINK THE SERVER DOES THIS ALL FOR US!!!! CRAP BUCKETS!! OKAY.....DONT DELETE THIS CODE. WE CAN USE IT WHEN WE WRITE THE SERVER SIDE.....
/*	public void distributeResourcesByRollNumber(int rollNumber) {
		ArrayList<Hex> hexes = this.client.getBoard().getHexesFromNumber(rollNumber);
		for(Hex h : hexes) {
			ResourceType resourceType = ResourceType.getResourceType(h.getResource());
			Integer[] owners = this.canDo.getBoardManager().getOwnersOnHex(h.getLocation());
			for(int owner : owners) {
				if(owner != -1) {
					clientCommunicator. ///////THIS IS WHERE I REALIZED HOW STUPID I AM :)
				}
			}
		}
		
	}
	*/
	
	public void setTradeOffer(TradeOffer tradeOffer) { //THIS FUNCTION SHOULD ONLY EVER GET CALLED BY THE OFFERINGTRADESTATE STUFF
		this.client.setTradeOffer(tradeOffer);
	}
	
	public int getPlayerIndex() {
		//TODO! Don't just attach this to the user's ID. This needs to be connected to the playerIndex for the game they joined.
		return localPlayer.getPlayerIndex();
	}
	
	public PlayerInfo getLocalPlayer() {
		return localPlayer;
	}
	
	/**
	 * Used to get playerinfo for robbing players
	 * @param players list of player indices
	 * @return list of player info who have corresponding indices
	 */
	private List<RobPlayerInfo> getPlayersInList(List<Integer> players) {
		List<RobPlayerInfo> playerList = new ArrayList<RobPlayerInfo>();
		List<PlayerInfo> allPlayers = game.getPlayers();
		for(Integer i : players) {
			for (PlayerInfo p : allPlayers) {
				if (p.getPlayerIndex() == i) {
					playerList.add(convertPlayerToRobInfo(p));
				}
			}
		}
		return playerList;
	}
	
	private RobPlayerInfo convertPlayerToRobInfo(PlayerInfo player) {
		RobPlayerInfo rob = new RobPlayerInfo();
		rob.setColor(player.getColor());
		rob.setId(player.getId());
		rob.setName(player.getName());
		rob.setNumCards(this.canDo.numResources(player.getPlayerIndex()));
		rob.setPlayerIndex(player.getPlayerIndex());
		rob.setUserCookie(player.getUserCookie());
		return rob;
	}
	
	//CHAT CONTROLLER
	
	/**
	 * Used to send message, called by client, ChatController
	 * @param message received from client
	 */
	public void sendMessage (String message) {
		this.clientCommunicator.sendChat(this.modelToJSON.createSendChatObject(this.getPlayerIndex(), message), this.getFullCookie());
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
		clientCommunicator.playMonopoly(modelToJSON.getPlayMonopolyCommand(this.getPlayerIndex(), resource), modelToJSON.createUserAndGameCookie(localPlayer.getUserCookie(), game.getId()));
	}
	
	/**
	 * Called by client when player wants to play Monument card
	 */
	public void playMonumentCard() {
		clientCommunicator.playMonument(modelToJSON.getPlayMonumentCommand(getPlayerIndex()), getFullCookie());
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
		clientCommunicator.playYearOfPlenty(modelToJSON.getPlayYearOfPlentyCommand(getPlayerIndex(), resource1, resource2), getFullCookie());
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
	public void discard(Resources toDiscard) {
		clientCommunicator.discardCards(this.modelToJSON.getDiscardCardsCommand(this.getPlayerIndex(), toDiscard), this.getFullCookie());
	}
	
	
	// DOMESTIC TRADE CONTROLLER
	/**
	 * Called by the domestic trade view when the user clicks the domestic trade
	 * button.
	 */
	public boolean canDomesticTrade() {
		if(localPlayer.getPlayerIndex() == this.client.getTurnTracker().getCurrentTurn()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Called by the domestic trade overlay when the user clicks the trade
	 * button.
	 */
	public void sendTradeOffer(TradeOffer offer) {
		clientCommunicator.offerTrade(this.modelToJSON.createOfferTradeObject(offer), this.getFullCookie());
	}

	/**
	 * Called by the accept trade overlay when the user either accepts or
	 * rejects a trade.
	 * 
	 * @param willAccept
	 *            Whether or not the user accepted the trade
	 */
	public void acceptTrade(boolean willAccept) {
		clientCommunicator.acceptTrade(this.modelToJSON.createAcceptTradeObject(this.client.getTradeOffer(), willAccept), this.getFullCookie());
	}
	
	
	// JOIN GAME CONTROLLER
	/**
	 * Gets a list of all the games
	 */
	public GameInfo[] getGamesList() {
		JsonObject object = clientCommunicator.getGamesList();
		GameInfo[] gameInfos = JSONToModel.translateGamesList(object);
		return gameInfos;
	}
	
	/**
	 * Displays the join game view
	 */
	public boolean canJoinGame() { //we changed this from start()
		return true;
	}
	
	/**
	 * Called by the join game view when the user clicks "Create new game"
	 * button. Displays the new game view.
	 */
	public boolean canStartCreateNewGame() {
		return true;
	}
	
	/**
	 * Called by the new game view when the user clicks the "Create Game" button
	 */
	public boolean createNewGame(String title, boolean useRandomHexes, boolean useRandomNumbers, boolean useRandomPorts) {
		
		//System.out.println("IN CLIENTCOMMUNICATOR CREATENEWGAME FUNCTION: " + title);
		Pattern p = Pattern.compile("[^a-zA-Z0-9\\s]");
		if (p.matcher(title).find() || title.length() < 1) return false;
		JsonObject inputGame = this.modelToJSON.createGameObject(title, useRandomHexes, useRandomNumbers, useRandomPorts);
		JsonObject returnedJson = this.clientCommunicator.createGame(inputGame, null);
		//TODO is this really a good way to determine if this was changed? What if it fails?
		if(returnedJson.get("Response-body").getAsJsonObject().get("title").getAsString().equals(title)) {
			game = jsonToModel.translateGameInfo(returnedJson);
			//System.out.println("GAME: " + game);
			return true;
		}		
		return false;
	}
	
	/**
	 * Called by the join game view when the user clicks a "Join" or "Re-join"
	 * button. Displays the select color view.
	 * 
	 * @param game
	 *            The game that the user is joining
	 */
	public void startJoinGame(GameInfo game) {
		this.game = game;
	}
	
	/**
	 * Called by the select color view when the user clicks the "Join Game"
	 * button
	 * 
	 * @param color
	 *            The color selected by the user
	 */
	public void joinGame(CatanColor color) {
		
		JsonObject joinGameObject = this.modelToJSON.createJoinGameObject(this.game, color, this.localPlayer.getUserCookie());
		JsonObject returned = clientCommunicator.joinGame(joinGameObject, getFullCookie());
		String responseBody = returned.get("Response-body").getAsString();
		if(!responseBody.equals("Success")) {
			//System.out.println("FAILED FAILED FAILED JoinGame() in the Facade. What should I do?????");
		}
		//System.out.println("JOIN GAME OBJECT: " + returned);
		
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
	public void addAI(String AIType) {
		JsonObject object = new JsonObject();
		object.addProperty("User-cookie", localPlayer.getUserCookie());
		object.addProperty("Game-cookie", game.getId());
		object.addProperty("AIType", AIType);
		//System.out.println(object.toString());
		clientCommunicator.addAI(object, getFullCookie());
	}
	
	/**
	 * Called for the combobox with different AI options
	 */
	public String[] listAI() {
		JsonObject object = clientCommunicator.listAI();
		String[] AI = new String[1];
		AI[0] = "LARGEST_ARMY";
		return AI;
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
	public boolean signIn(String username, String password) throws ClientException {
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		if (p.matcher(username).find() || p.matcher(password).find()) return false;
		
		JsonObject userObject = this.modelToJSON.createUserObject(username, password);
		JsonObject returnedJson = this.clientCommunicator.userLogin(userObject, null);
		
		if(returnedJson.get("Response-body").getAsString().equals("Success")) {
			return loginUser(returnedJson, username);
		}
		
		return false;
	}
	
	/**
	 * Called when the user clicks the "Register" button in the login view
	 * @throws ClientException 
	 */
	public boolean register(String username, String password) throws ClientException {
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		if (p.matcher(username).find() || p.matcher(password).find()) return false;
		
		JsonObject userObject = this.modelToJSON.createUserObject(username, password);
		JsonObject returnedJson = this.clientCommunicator.userRegister(userObject, null);
		
		if(returnedJson.get("Response-body").getAsString().equals("Success")) {
			return loginUser(returnedJson, username);
		}
		
		return false;
	}
	
	public boolean loginUser(JsonObject returnedJson, String username) {
		String userCookie = returnedJson.get("User-cookie").getAsString();
		int playerID = returnedJson.get("Set-cookie").getAsJsonObject().get("playerID").getAsInt();
		PlayerInfo newUser = new PlayerInfo(playerID, -1, username, null, userCookie);
		this.localPlayer = newUser;
		return true;
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
	
	public boolean canPlaceInitialRoad(EdgeLocation edgeLoc) {
		client.models.mapdata.EdgeLocation edge = new client.models.mapdata.EdgeLocation(edgeLoc);
		return canDo.canPlaceInitialRoad(this.getPlayerIndex(), edge);
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
		return canDo.canPlaceSettlementAtLocation(this.getPlayerIndex(), new client.models.mapdata.EdgeLocation(vertLoc));
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
		return canDo.canUpgradeSettlementAtLocation(this.getPlayerIndex(), new client.models.mapdata.EdgeLocation(vertLoc));			
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
		client.models.mapdata.EdgeLocation edge = new client.models.mapdata.EdgeLocation(edgeLoc);
		JsonObject roadCommand = modelToJSON.getBuildRoadCommand(this.getPlayerIndex(), edge, false);
		JsonObject cookie = this.getFullCookie();
		clientCommunicator.buildRoad(roadCommand, cookie);
	}
	
	
	/**
	 * This method is called to place road in initial turns.
	 * @param edgeLoc
	 */
	public void placeFreeRoad(EdgeLocation edgeLoc) {
		client.models.mapdata.EdgeLocation edge = new client.models.mapdata.EdgeLocation(edgeLoc);
		JsonObject roadCommand = modelToJSON.getBuildRoadCommand(this.getPlayerIndex(), edge, true);
		JsonObject cookie = this.getFullCookie();
		//System.out.println("COMMAND: " + roadCommand);
		//System.out.println("COOKIE: " + cookie);
		clientCommunicator.buildRoad(roadCommand, cookie);
//		this.poller.setForceUpdate(true);
	}
	
	/**
	 * This method is called when the user clicks the mouse to place a
	 * settlement.
	 * 
	 * @param vertLoc
	 *            The settlement location
	 */
	public void placeSettlement(VertexLocation vertLoc) {
		client.models.mapdata.EdgeLocation edge = new client.models.mapdata.EdgeLocation(vertLoc);
		JsonObject settlementCommand = modelToJSON.getBuildSettlementCommand(this.getPlayerIndex(), edge, false);
		JsonObject cookie = this.getFullCookie();
		clientCommunicator.buildSettlement(settlementCommand, cookie);
	}
	
	
	/**
	 * This method is called to place settlement in initial turns.
	 * @param vertLoc
	 */
	public void placeFreeSettlement(VertexLocation vertLoc) {
		client.models.mapdata.EdgeLocation edge = new client.models.mapdata.EdgeLocation(vertLoc);
		JsonObject settlementCommand = modelToJSON.getBuildSettlementCommand(this.getPlayerIndex(), edge, true);
		JsonObject cookie = this.getFullCookie();
		clientCommunicator.buildSettlement(settlementCommand, cookie);
	}
	
	/**
	 * This method is called when the user clicks the mouse to place a city.
	 * 
	 * @param vertLoc
	 *            The city location
	 */
	public void placeCity(VertexLocation vertLoc) {
		client.models.mapdata.EdgeLocation edge = new client.models.mapdata.EdgeLocation(vertLoc);
		JsonObject cityCommand = modelToJSON.getBuildCityCommand(this.getPlayerIndex(), edge);
		JsonObject cookie = this.getFullCookie();
		clientCommunicator.buildCity(cityCommand, cookie);
	}
	
	
	
	/**
	 * This method is called when the user clicks the mouse to place the robber.
	 * 
	 * @param hexLoc
	 *            The robber location
	 */
	public RobPlayerInfo[] placeRobber(HexLocation hexLoc) {
		client.models.mapdata.HexLocation hex = new client.models.mapdata.HexLocation(hexLoc);
		//temporarily sets new robber location until command is sent in robPlayer
		newRobberLocation = hexLoc;
		ArrayList<Integer> candidates = canDo.whoCanIRob(this.getPlayerIndex(), hex);
		
		List<RobPlayerInfo> candidateVictimList = this.getPlayersInList(candidates);
		
		RobPlayerInfo[] candidateVictims = new RobPlayerInfo[candidateVictimList.size()];
		for (int i = 0; i < candidateVictimList.size(); i++) {
			candidateVictims[i] = candidateVictimList.get(i);
		}
		return candidateVictims;
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
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		// TODO IT APPEARS THAT WE BYPASSED THIS METHOD AND JUST HARD-CODED STUFF. ARE WE OKAY WITH THAT??
	}
	
	/**
	 * This method is called when the user plays a "soldier" development card.
	 * It should initiate robber placement.
	 * @param victim 
	 * @param newRobberHexLoc 
	 */
	public void playSoldierCard(RobPlayerInfo victim, HexLocation hexLoc) {
		JsonObject commandCookie = modelToJSON.getPlaySoldierCommand(
				getPlayerIndex(), victim.getPlayerIndex(),
				new client.models.mapdata.HexLocation(hexLoc));
		clientCommunicator.playSoldier(commandCookie, getFullCookie());
	}
	
	/**
	 * This method is called when the user plays a "road building" progress
	 * development card. It should initiate the process of allowing the player
	 * to place two roads.
	 * @param roadBuildingRoads 
	 */
	public void playRoadBuildingCard(Road[] roads) {
		this.clientCommunicator.playRoadBuilding(this.modelToJSON
				.createPlayRoadBuildingObject(getPlayerIndex(),
						roads[0].getLocation(), roads[1].getLocation(), true),
				getFullCookie());
	}
	
	/**
	 * This method is called by the Rob View when a player to rob is selected
	 * via a button click.
	 * 
	 * @param victim
	 *            The player to be robbed
	 */
	public void robPlayer(RobPlayerInfo victim) {
		if(newRobberLocation != null) {
			client.models.mapdata.HexLocation hex = new client.models.mapdata.HexLocation(newRobberLocation);
			JsonObject command = this.modelToJSON.getRobPlayerCommand(this.getPlayerIndex(), victim.getPlayerIndex(), hex);
			JsonObject cookie = this.getFullCookie();
			newRobberLocation = null;
			clientCommunicator.robPlayer(command, cookie);
		}
	}
	
	
	
	
	
	// MARITIME TRADE CONTROLLER
	/**
	 * Called by the maritime trade view when the user clicks the maritime trade
	 * button.
	 */
	

	
	public List<ResourceType> startMaritimeTrade() 
	{
		List<ResourceType> resources = new ArrayList<ResourceType>();
		Player p = client.getPlayers()[getPlayerIndex()];
		canDo.getBoardManager().updatePlayerMaritimeTradeCosts(p, client.getBoard());
		
		if(p.hasBrick(p.getPortTrade().getBrickCost())){resources.add(ResourceType.BRICK);}
		if(p.hasWood(p.getPortTrade().getWoodCost())){resources.add(ResourceType.WOOD);}
		if(p.hasWheat(p.getPortTrade().getWheatCost())){resources.add(ResourceType.WHEAT);}
		if(p.hasOre(p.getPortTrade().getOreCost())){resources.add(ResourceType.ORE);}
		if(p.hasSheep(p.getPortTrade().getSheepCost())){resources.add(ResourceType.SHEEP);}
		
		return resources;
	}
	
	/**
	 * Make the specified trade with the bank.
	 */
	public void makeMaritimeTrade(ResourceType giveResource, ResourceType getResource, int ratio) {
		JsonObject object = this.modelToJSON.getMaritimeTradeCommand(getPlayerIndex(), ratio, giveResource, getResource);
		clientCommunicator.maritimeTrade(object, this.getFullCookie());
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
	public void buildRoad(/*data*/) {  //THIS APPEARS TO HAVE BEEN REPLACED BY THE PLACEROAD AND PLACEFREEROAD FUNCTIONS
		/*jsonData = modelToJSON.placeroad(modelData)
		serverjsonData = clientCommunicator.buildRoad(jsonData)
		client.update(JSONToModel.updateModel(serverjsonData))
		*/
	}
	
	/**
	 * Called by the view then the user requests to build a settlement
	 */
	public void buildSettlement() { //THIS APPEARS TO HAVE BEEN REPLACED BY PLACESETTLEMENT AND PLACEFREESETTLEMENT
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
		if (canDo.canBuyDevCard(getPlayerIndex())) clientCommunicator.buyDevCard(this.modelToJSON.getBuyDevCardCommand(getPlayerIndex()), this.getFullCookie());
//		else 
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
		clientCommunicator.rollNumber(this.modelToJSON.createRollNumberObject(this.getPlayerIndex(), die1+die2), this.getFullCookie());
		return die1+die2;
		//uncomment to debug robber
		//clientCommunicator.rollNumber(this.modelToJSON.createRollNumberObject(this.getPlayerIndex(), 7), this.getFullCookie());
		//return 7;
	}
	
	// TURN TRACKER CONTROLLER
	
	/**
	 * This is called when the local player ends their turn
	 */
	public void endTurn() {
		this.clientCommunicator.finishTurn(this.modelToJSON.getFinishTurnCommand(this.getPlayerIndex()), this.getFullCookie());
	}
	
	public void setServerPoller(ServerPoller serv) {
		this.poller = serv;
	}

	public void setModel(ClientModel client) {
		this.client = client;
	}

	public CanDoManager getCanDo() {
		return canDo;
	}

	public void setCanDo(CanDoManager canDo) {
		this.canDo = canDo;
	}

	public ModelToJSON getModelToJSON() {
		return modelToJSON;
	}

	public void setModelToJSON(ModelToJSON modelToJSON) {
		this.modelToJSON = modelToJSON;
	}
	
	public JsonObject getUserAndGameCookie() {
		try {
			return this.modelToJSON.createUserAndGameCookie(this.localPlayer.getUserCookie(), this.game.getId());
		} catch (NullPointerException e) {
			return null;
		}
	}

	public JSONToModel getJsonToModel() {
		return jsonToModel;
	}

	public void setJsonToModel(JSONToModel jsonToModel) {
		this.jsonToModel = jsonToModel;
	}

	public ClientCommunicator getClientCommunicator() {
		return clientCommunicator;
	}

	public void setClientCommunicator(ClientCommunicator clientCommunicator) {
		this.clientCommunicator = clientCommunicator;
	}

	public GameInfo getGame() {
		return game;
	}

	public void setGame(GameInfo game) {
		this.game = game;
	}

	public GameInfo[] getGames() {
		return games;
	}

	public void setGames(GameInfo[] gamesList) {
		this.games = gamesList;
	}

	public ServerPoller getPoller() {
		return poller;
	}

	public void setPoller(ServerPoller poller) {
		this.poller = poller;
	}

	public void setLocalPlayer(PlayerInfo newLocalPlayer) {
		this.localPlayer = newLocalPlayer;
	}
	
	public int getPlayerWithLongestRoad() {
		return playerWithLongestRoad;
	}

	public int getPlayerWithLargestArmy() {
		return playerWithLargestArmy;
	}

	public void setPlayerWithLongestRoad(int playerWithLongestRoad) {
		this.playerWithLongestRoad = playerWithLongestRoad;
	}

	public void setPlayerWithLargestArmy(int playerWithLargestArmy) {
		this.playerWithLargestArmy = playerWithLargestArmy;
	}

	public void addObserver(Observer o) {
		this.client.addObserver(o);
	}
	
	public void updatePointersToNewModel() {
//		this.client = newModel;
		this.game.setPlayers(this.client.getPlayers());
		this.canDo.updatePointersToNewModel(this.client);
	}
	
	public JsonObject getFullCookie() {
		return modelToJSON.createUserAndGameCookie(localPlayer.getUserCookie(), game.getId());
	}

	public void placeLocalRoad(EdgeLocation edgeLoc) {
		this.client.getBoard().addRoad(new Road(this.getPlayerIndex(), new client.models.mapdata.EdgeLocation(edgeLoc)));
	}
}
