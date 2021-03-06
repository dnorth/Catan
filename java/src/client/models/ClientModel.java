package client.models;
import java.util.Observable;

import server.exceptions.AlreadyPlayedDevCardException;
import server.exceptions.CantBuildThereException;
import server.exceptions.DontHaveDevCardException;
import server.exceptions.InvalidMaritimeTradeException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.model.ServerPlayer;
import shared.definitions.DevCard;
import shared.definitions.ResourceType;
import shared.locations.VertexLocation;
import client.facade.CanDoManager;
import client.models.communication.MessageList;
import client.models.mapdata.Board;
import client.models.mapdata.EdgeLocation;
import client.models.mapdata.HexLocation;
import client.models.mapdata.Road;

/**
 * Client model interacts with Client Communicator (Server Proxy), holds pointers to all necessary data
 */
public class ClientModel extends Observable
{

	private Resources bank;
	private MessageList chat;
	private MessageList log;
	private Board board;
	private Player[] players;
	private TradeOffer tradeOffer;
	private TurnTracker turnTracker;
	private int version;
	private int winner;
	private DevCards deck;
	boolean hasChanged = false;
	private boolean newCli;
	boolean playedDevCard=false;
	int playersToDiscard;

	public ClientModel() {
		super();
		newCli = true;

		bank = new Resources(24,24,24,24,24);
		deck = new DevCards(2,5,2,14,2);
		board = new Board();
		players = new Player[4];
		
		
	}

	public void fillClientModel() {
		chat = new MessageList();
		log = new MessageList();
		turnTracker = new TurnTracker();
		version = 0;
		winner = -1;
		playersToDiscard = 0;
	}

	public void update(ClientModel model) {
		newCli = false;
		this.setBank(model.getBank());
		this.setChat(model.getChat());
		this.setLog(model.getLog());
		this.setBoard(model.getBoard());
		this.setPlayers(model.getPlayers());
		this.setTradeOffer(model.getTradeOffer());
		this.setTurnTracker(model.getTurnTracker());
		this.setVersion(model.getVersion());
		this.setWinner(model.getWinner());
		this.setDeck(model.getDeck());
	}

	public void runUpdates() {
		this.setChanged();
		this.notifyObservers();
	}

	public int getVersion() {
		return version;
	}
	public void increaseVersion() {
		setVersion(version+1);
	}
	public void setVersion(int version) {
		if(this.version == version) {
			hasChanged = false;
		}
		else {
			hasChanged = true;
		}
		this.version = version;
	}
	public int getWinner() {
		return winner;
	}
	public void setWinner(int winner) {
		this.winner = winner;
	}
	public DevCards getDeck() {
		return deck;
	}
	public void setDeck(DevCards deck) {
		this.deck = deck;
	}
	public Resources getBank() {
		return bank;
	}
	public void setBank(Resources bank) {
		this.bank = bank;
	}
	public MessageList getChat() {
		return chat;
	}
	public void setChat(MessageList chat) {
		this.chat = chat;
	}
	public MessageList getLog() {
		return log;
	}
	public void setLog(MessageList log) {
		this.log = log;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public Player[] getPlayers() {
		return players;
	}
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	public TradeOffer getTradeOffer() {
		return tradeOffer;
	}
	public void setTradeOffer(TradeOffer tradeOffer) {
		this.tradeOffer = tradeOffer;
	}
	public TurnTracker getTurnTracker() {
		return turnTracker;
	}
	public void setTurnTracker(TurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}
	public String getTurnTrackerStatus() {
		return this.turnTracker.getStatus();
	}
	public void setTurnTrackerStatus(String status) {
		this.turnTracker.setStatus(status);
	}
	public int getVersionIndex() {
		return version;
	}
	public void setVersionIndex(int versionIndex) {
		this.version = versionIndex;
	}
	public int getWinnerIndex() {
		return winner;
	}
	public void setWinnerIndex(int winnerIndex) {
		this.winner = winnerIndex;
	}

	public boolean needToDiscard(){
		for (Player p : players) {
			if (p.getResources().getTotalCount() > 7)
				return true;
		}
		return false;
	}

	public int getNumToDiscard(){
		return this.playersToDiscard;
	}

	public void decNumToDiscard(){
		this.playersToDiscard--;
	}

	public void setNumToDiscard(){
		this.playersToDiscard = 0;
		for (Player p : players) {
			if (p.getResources().getTotalCount() > 7){
				this.playersToDiscard++;
			}
		}
	}

	public String playersToString() {
		StringBuilder sb = new StringBuilder();

		for(int i=0; i < players.length; i++) {
			sb.append(players[i].toString() + "\n");
		}

		return sb.toString();
	}

	@Override
	public boolean hasChanged() {
		return hasChanged;
	}

	public boolean newCli() {
		return newCli;
	}

	public void setNewCli(boolean newCli) {
		this.newCli = newCli;
	}

	/**
	 * Adds player to list of players
	 * @param newPlayer - new player info
	 */
	public void addPlayer(int playerIndex, ServerPlayer newPlayer)
	{
		Player p = new Player(newPlayer, playerIndex);
		players[playerIndex] = p;
	}


	/**
	 * Sends chat to player from player
	 * @param sendPlayerIndex - index of player sending message
	 * @param receivePlayerIndex - index of player receiving message
	 * @param message - message to send
	 */
	public void sendChat(int sendPlayerIndex, int receivePlayerIndex, String message) {}

	/**
	 * Updates dice rolled, gives players nece//		
//		try{
//			TradeOffer offer = new TradeOffer(0, 1, new Resources(0,1,2,3,4));
//			game.getClientModel().getTurnTracker().setStatus("Playing");
//		facade.offerTrade(0, 0, offer.getOffer(),1);
//		facade.acceptTrade(0, 1, true);
//		
//		
//		}
//		catch ( InvalidStatusException
//				| NotYourTurnException 
//				| InvalidPlayerIndexException e) {}
//		
//		ssary resources
	 * @param numRolled - number rolled - between 2-12
	 * @param playerIndex - player that rolled dice
	 */
	public void rollNumber(int numRolled, int playerIndex){}

	/**
	 * Places robber in new location, and takes one resource from victim player and adds it to player
	 * @param playerIndex - index of player who is robbing
	 * @param victimIndex - index of player getting robbed
	 * @param hex - new location of robber
	 */
	public void robPlayer(int playerIndex, int victimIndex, HexLocation hex) {}

	/**
	 * Sets current turn to next player
	 * @param playerIndex - index of player sending command
	 */
	public void finishTurn(int playerIndex){}

	/**
	 * Takes one devcard from bank and gives it to player, decreases players resources
	 * @param playerIndex - player buying devcard
	 */
	public void buyDevCard(int playerIndex){}

	/**
	 * Plays Year of Plenty devcard - removes YOP card from players hand, increments indicated resources
	 * @param playerIndex - index of player playing card
	 * @param resource1 - first resource to be added
	 * @param resource2 - second resource to be added
	 */
	public void playYearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2){}

	/**
	 * Plays Road Building devcard - removes RB card from player's hand, places roads in indicated locations
	 * @param playerIndex - index of player playing card
	 * @param edge1 - location of first road
	 * @param edge2 - location of second road
	 */
	public void playRoadBuilding(int playerIndex, EdgeLocation edge1, EdgeLocation edge2){}

	/**
	 * Plays Soldier devcard - removes S card from player's hand, robs player and sets robber
	 * @param playerIndex - index of player playing card
	 * @param victimIndex - index of victim
	 * @param hex - new location of robber
	 */
	public void playSoldier(int playerIndex, int victimIndex, HexLocation hex){}

	/**
	 * Plays Monopoly devcard - removes Monopoly card from player's hand, moves indicated resource from other players' hands to this player's hand
	 * @param playerIndex - index of player playing card
	 * @param resource - resource to be taken
	 */
	public void playMonopoly(int playerIndex, ResourceType resource){}

	/**
	 * Plays Monument devcard - removes Monument card from player's hand, adds victory point
	 * @param playerIndex - index of player playing card
	 */
	public void playMonument(int playerIndex){}

	/**
	 * Places road on indicated edge, ownership of indicated player.  If necessary, decrements player's resources.
	 * @param playerIndex - index of player placing road
	 * @param edge - location of road
	 * @param free - if road is free (should only be in first two (setup) turns
	 */
	public void buildRoad(int playerIndex, EdgeLocation edge, boolean free){}

	/**
	 * Places settlement on indicated vertex, ownership of indicated player.  If necessary, decrements player's resources.
	 * @param playerIndex - index of player placing road
	 * @param vertex - location of settlement
	 * @param free - if settlement is free (should only be in first two (setup) turns
	 */
	public void buildSettlement(int playerIndex, EdgeLocation vertex, boolean free){}

	/**
	 * Builds the city. Decrements player's resources.
	 *
	 * @param type "buildCity"
	 * @param playerIndex the player index of the person building the city
	 * @param vertexLocation the vertex location where the city will be placed
	 */
	public void buildCity(String type, int playerIndex, VertexLocation vertexLocation){}

	/**
	 * Offers a trade. If the trade is accepted, the positive numbered resources are taken from the player index o
	 * the person offering the trade, and the negative numbered resources are taken from the person receiving it
	 * 
	 * @param type "offerTrade"
	 * @param playerIndex the player index 
	 * @param offer the offer
	 * @param receiver the receiver
	 */
	public void offerTrade(String type, int playerIndex, Resources offer, int receiver){}

	/**
	 * Whether or not to Accept a trade. 
	 *
	 * @param type "acceptTrade"
	 * @param playerIndex the player index of the player accepting the trade
	 * @param willAccept whether or not the player accepts the trade
	 */
	public void acceptTrade(String type, int playerIndex, boolean willAccept){}

	/**
	 * Maritime trade. Decrements the inputResource and increments the output resource based on 
	 * the ratio. Or 4:1 if no ratio is specified
	 * @param type "maritimeTrade"
	 * @param playerIndex the player index of the player offering the trade
	 * @param ratio the ratio (Optional) of resources to trade
	 * @param inputResource the resource type that is being traded
	 * @param outputResource the resource type that is being received 
	 */
	public void maritimeTrade(String type, int playerIndex, int ratio, String inputResource, String outputResource){}

	/**
	 * Discard cards. Decrements resources
	 *
	 * @param type "discardCards"
	 * @param playerIndex the player index of ther person discarding
	 * @param discardedCards the resource list of the discarded cards
	 */
	public void discardCards(String type, int playerIndex, Resources discardedCards){}


	public void checkPlayerIndex(int playerIndex) throws InvalidPlayerIndexException{
		if(playerIndex<0 || playerIndex>3){
			throw new InvalidPlayerIndexException();
		}
	}
	public void checkStatus(String status) throws InvalidStatusException{
		if(!getTurnTrackerStatus().equals(status) && !getTurnTrackerStatus().equals("FirstRound") && !getTurnTrackerStatus().equals("SecondRound")){
			throw new InvalidStatusException();
		}
	}
	public void checkTurn(int playerIndex) throws NotYourTurnException{
		if(this.getTurnTracker().getCurrentTurn()!=playerIndex){
			throw new NotYourTurnException();
		}
	}

	public void checkRoad(Road road) throws CantBuildThereException
	{
		CanDoManager cd = new CanDoManager(this);
		if(cd.canPlaceRoadAtLocation(road.getOwner(), road.getLocation())==false){
			throw new CantBuildThereException();
		}
	}

	public void checkInitialRoad(Road road) throws CantBuildThereException
	{
		CanDoManager cd = new CanDoManager(this);
		if(cd.canPlaceInitialRoad(road.getOwner(), road.getLocation())==false){
			throw new CantBuildThereException();
		}
	}

	public void checkSettlement(int playerIndex, EdgeLocation edge) throws CantBuildThereException
	{
		CanDoManager cd = new CanDoManager(this);
		if(cd.canPlaceSettlementAtLocation(playerIndex, edge)==false){
			throw new CantBuildThereException();
		}
	}

	public void checkCity(int playerIndex, EdgeLocation edge) throws CantBuildThereException
	{
		CanDoManager cd = new CanDoManager(this);
		if(cd.canUpgradeSettlementAtLocation(playerIndex, edge)==false){
			throw new CantBuildThereException();
		}
	}

	public void checkMaritimeTrade(int playerIndex, ResourceType type, int ratio) throws InvalidMaritimeTradeException{
		if(ratio==4) {
			return;
		}
		Player p = players[playerIndex];
		if(p.getPortTrade().getCost(type)!=ratio){
			throw new InvalidMaritimeTradeException();
		}
	}

	public void checkDevCard(int playerIndex, DevCard type) throws DontHaveDevCardException, AlreadyPlayedDevCardException{
		Player p = players[playerIndex];
		if (type == DevCard.MONUMENT) {
			if (!p.getNewDevCards().hasCard(type) && !p.getOldDevCards().hasCard(type)) {
				throw new DontHaveDevCardException();
			}
		}
		else if(!p.getOldDevCards().hasCard(type)){
			throw new DontHaveDevCardException();
		}
		if(playedDevCard==true){
			throw new AlreadyPlayedDevCardException();
		}
	}

	public void updateMaritimeTradeCosts(int playerIndex){
		CanDoManager canDo = new CanDoManager(this);

		canDo.getBoardManager().updatePlayerMaritimeTradeCosts(players[playerIndex], board);
	}
	public boolean playerHasLongestRoad(Player p){
		int roadsPlayed = 15-p.getRoads();

		if(roadsPlayed > getMostRoads(p.getPlayerIndex()))
		{
			return roadsPlayed>=5;
		}
		else {
			return false;
		}
	}

	private int getMostRoads(int playerIndexToIgnore)
	{
		int roads=0;
		for(Player p: players){
			if(p.getPlayerIndex()==playerIndexToIgnore){
				continue;
			}
			int playerRoads = 15 - p.getRoads();
			if(playerRoads> roads){roads=playerRoads;}
		}
		return roads;
	}

	public void awardLongestRoad(Player p){
		int longestRoadIndex = turnTracker.getLongestRoad();
		if(longestRoadIndex==p.getPlayerIndex()){return;}

		else if(longestRoadIndex!=-1){
			Player loser = players[longestRoadIndex];	
			loser.setVictoryPoints(loser.getVictoryPoints()-2);

		}	
		p.setVictoryPoints(p.getVictoryPoints()+2);
		turnTracker.setLongestRoad(p.getPlayerIndex());
	}

	public boolean playerHasLargestArmy(Player p){
		int soldiersPlayed = p.getSoldiers();

		if(soldiersPlayed > getMostSoldiers(p.getPlayerIndex()))
		{
			return soldiersPlayed>=3;
		}
		else {
			return false;
		}
	}

	private int getMostSoldiers(int playerIndexToIgnore)
	{
		int soldierss=0;
		for(Player p: players){
			if(p.getPlayerIndex()==playerIndexToIgnore){
				continue;
			}
			int playerRoads = p.getSoldiers();
			if(playerRoads> soldierss){soldierss=playerRoads;}
		}
		return soldierss;
	}
	
	public void awardLargestArmy(Player p){
		int largestArmyIndex = turnTracker.getLargestArmy();
		if(largestArmyIndex==p.getPlayerIndex()){return;}

		else if(largestArmyIndex!=-1){
			Player loser = players[largestArmyIndex];	
			loser.setVictoryPoints(loser.getVictoryPoints()-2);

		}	
		p.setVictoryPoints(p.getVictoryPoints()+2);
		turnTracker.setLargestArmy(p.getPlayerIndex());
	}

	public boolean isInitializingPhase()
	{
		if (turnTracker.getStatus().equals("FirstRound")|| turnTracker.getStatus().equals("SecondRound")) {
			return true;
		}
		else{
			return false;
		}
	}

	public boolean havePlayedDevCard() {
		return playedDevCard;
	}

	public void setPlayedDevCard(boolean playedDevCard) {
		this.playedDevCard = playedDevCard;
	}


}
