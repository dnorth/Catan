package server.facade;

import client.models.ClientModel;
import client.models.Resources;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public interface iMovesFacade {
	public ClientModel sendChat(String type, int playerIndex, String content);
	public ClientModel rollNumber(String type, int playerIndex, int number);
	public ClientModel robPlayer(String type, int playerIndex, int victimIndex, HexLocation location);
	public ClientModel finishTurn(String type, int playerIndex);
	public ClientModel buyDevCard(String type, int playerIndex);
	public ClientModel playYearOfPlenty(String type, int playerIndex, ResourceType resource1, ResourceType resource2);
	public ClientModel playRoadBuilding(String type, int playerIndex, EdgeLocation spot1, EdgeLocation spot2);
	public ClientModel playSoldier(String type, int playerIndex, int victimIndex, HexLocation location);
	public ClientModel playMonopoly(String type, ResourceType resource, int playerIndex);
	public ClientModel playMonument(String type, int playerIndex);
	public ClientModel buildRoad(String type, int playerIndex, EdgeLocation roadLocation, boolean free);
	public ClientModel buildSettlement(String type, int playerIndex, VertexLocation vertexLocation, boolean free);
	public ClientModel buildCity(String type, int playerIndex, VertexLocation vertexLocation);
	public ClientModel offerTrade(String type, int playerIndex, Resources offer, int receiver);
	public ClientModel acceptTrade(String type, int playerIndex, boolean willAccept);
	public ClientModel maritimeTrade(String type, int playerIndex, int ratio, String inputResource, String outputResource);
	public ClientModel discardCards(String type, int playerIndex, Resources discardedCards);
}
