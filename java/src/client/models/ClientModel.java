package client.models;
import client.models.communication.MessageList;
import client.models.Player;

/**
 * Client model interacts with Client Communicator, holds pointers to all necessary data
 */
public class ClientModel
{
	ResourceList bank;
	MessageList chat;
	MessageList log;
	client.models.mapdata.Map map;
	Player[] players;
	TradeOffer tradeOffer;
	TurnTracker turnTracker;
	int versionIndex;
	int winnerIndex;
}