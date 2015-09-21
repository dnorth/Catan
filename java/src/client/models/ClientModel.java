package client.models;
import client.models.communication.MessageList;
import client.models.mapdata.Board;

/**
 * Client model interacts with Client Communicator (Server Proxy), holds pointers to all necessary data
 */
public class ClientModel
{
	ResourceList bank;
	MessageList chat;
	MessageList log;
	Board board;
	Player[] players;
	TradeOffer tradeOffer;
	TurnTracker turnTracker;
	int versionIndex;
	int winnerIndex;
}