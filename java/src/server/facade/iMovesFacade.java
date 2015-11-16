package server.facade;

import client.models.ClientModel;
import client.models.Resources;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Interface iMovesFacade.
 */
public interface iMovesFacade {
	
	/**
	 * Send chat.
	 * @param gameIndex TODO
	 * @param playerIndex the player index
	 * @param content the content of the chat
	 *
	 * @return the client model
	 */
	public ClientModel sendChat(int gameIndex, int playerIndex, String content);
	
	/**
	 * Roll number.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of who's rolling
	 * @param number the number rolled
	 *
	 * @return the client model
	 */
	public ClientModel rollNumber(int gameIndex, int playerIndex, int number);
	
	/**
	 * Rob player.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of who is robbing
	 * @param victimIndex the victim index
	 * @param location the hex location of where the robber is going
	 *
	 * @return the client model
	 */
	public ClientModel robPlayer(int gameIndex, int playerIndex, int victimIndex, HexLocation location);
	
	/**
	 * Finish turn.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of whose turn is being finished
	 *
	 * @return the client model
	 */
	public ClientModel finishTurn(int gameIndex, int playerIndex);
	
	/**
	 * Buy dev card.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of the person buying a dev card
	 *
	 * @return the client model
	 */
	public ClientModel buyDevCard(int gameIndex, int playerIndex);
	
	/**
	 * Play year of plenty.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of the person playing the dev card
	 * @param resource1 the first resource type
	 * @param resource2 the second resource type
	 *
	 * @return the client model
	 */
	public ClientModel playYearOfPlenty(int gameIndex, int playerIndex, ResourceType resource1, ResourceType resource2);
	
	/**
	 * Play road building.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of the person playing the dev card
	 * @param spot1 the first edge location to build a road
	 * @param spot2 the second edge location to build a road
	 *
	 * @return the client model
	 */
	public ClientModel playRoadBuilding(int gameIndex, int playerIndex, EdgeLocation spot1, EdgeLocation spot2);
	
	/**
	 * Play soldier.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of the person playing the dev card
	 * @param victimIndex the victim index
	 * @param location the hex location of the robber being moved to
	 *
	 * @return the client model
	 */
	public ClientModel playSoldier(int gameIndex, int playerIndex, int victimIndex, HexLocation location);
	
	/**
	 * Play monopoly.
	 * @param resource the resource of the person playing the dev card
	 * @param gameIndex TODO
	 * @param playerIndex the player index
	 *
	 * @return the client model
	 */
	public ClientModel playMonopoly(ResourceType resource, int gameIndex, int playerIndex);
	
	/**
	 * Play monument.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of the person playing the dev card
	 *
	 * @return the client model
	 */
	public ClientModel playMonument(int gameIndex, int playerIndex);
	
	/**
	 * Builds the road.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of the person building the road
	 * @param roadLocation the Edge Location where the road is located
	 * @param free whether or not the road is built for free
	 *
	 * @return the client model
	 */
	public ClientModel buildRoad(int gameIndex, int playerIndex, EdgeLocation roadLocation, boolean free);
	
	/**
	 * Builds the settlement.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of the person building the settlement
	 * @param vertexLocation the vertex location where the settlement is placed
	 * @param free whether or no the settlement is built for free
	 *
	 * @return the client model
	 */
	public ClientModel buildSettlement(int gameIndex, int playerIndex, VertexLocation vertexLocation, boolean free);
	
	/**
	 * Builds the city.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of the person building the city
	 * @param vertexLocation the vertex location where the city will be placed
	 *
	 * @return the client model
	 */
	public ClientModel buildCity(int gameIndex, int playerIndex, VertexLocation vertexLocation);
	
	/**
	 * Offer trade.
	 * @param gameIndex TODO
	 * @param playerIndex the player index 
	 * @param offer the offer
	 * @param receiver the receiver
	 *
	 * @return the client model
	 */
	public ClientModel offerTrade(int gameIndex, int playerIndex, Resources offer, int receiver);
	
	/**
	 * Accept trade.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of the player accepting the trade
	 * @param willAccept whether or not the player accepts the trade
	 *
	 * @return the client model
	 */
	public ClientModel acceptTrade(int gameIndex, int playerIndex, boolean willAccept);
	
	/**
	 * Maritime trade.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of the player offering the trade
	 * @param ratio the ratio (Optional) of resources to trade
	 * @param inputResource the resource type that is being traded
	 * @param outputResource the resource type that is being received 
	 *
	 * @return the client model
	 */
	public ClientModel maritimeTrade(int gameIndex, int playerIndex, int ratio, String inputResource, String outputResource);
	
	/**
	 * Discard cards.
	 * @param gameIndex TODO
	 * @param playerIndex the player index of ther person discarding
	 * @param discardedCards the resource list of the discarded cards
	 *
	 * @return the client model
	 */
	public ClientModel discardCards(int gameIndex, int playerIndex, Resources discardedCards);
}
