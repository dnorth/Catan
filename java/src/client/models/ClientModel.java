package client.models;
import java.awt.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import client.models.communication.MessageList;
import client.models.mapdata.Board;

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
	private int version = -1;
	private int winner;
	private DevCards deck;
	boolean hasChanged = false;
	
	public ClientModel() {
		super();
	}
	
	public void update(ClientModel model) {
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
		System.out.println("ClientModel.update()");
		this.setChanged();
		this.notifyObservers();
	}
	
	public int getVersion() {
		return version;
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
	
}