package client.models;
import client.models.ResourceList;
import client.models.communication.MessageList;
import client.models.mapData.Map;
import client.models.Player;

public class ClientModel
{
	ResourceList bank;
	MessageList chat;
	MessageList log;
	client.models.mapData.Map map;
	Player[] players;
	TradeOffer tradeOffer;
	TurnTracker turnTracker;
	int versionIndex;
	int winnerIndex;





	
}