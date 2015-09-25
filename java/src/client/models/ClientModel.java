package client.models;
import client.models.communication.MessageList;
import client.models.mapdata.Board;

/**
 * Client model interacts with Client Communicator (Server Proxy), holds pointers to all necessary data
 */
public class ClientModel
{
	private Resources bank;
	private MessageList chat;
	private MessageList log;
	private Board board;
	private Player[] players;
	private TradeOffer tradeOffer;
	private TurnTracker turnTracker;
	private int versionIndex;
	private int winnerIndex;
	
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
		return versionIndex;
	}
	public void setVersionIndex(int versionIndex) {
		this.versionIndex = versionIndex;
	}
	public int getWinnerIndex() {
		return winnerIndex;
	}
	public void setWinnerIndex(int winnerIndex) {
		this.winnerIndex = winnerIndex;
	}
}