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
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param content the content
	 * @return the client model
	 */
	public ClientModel sendChat(String type, int playerIndex, String content);
	
	/**
	 * Roll number.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param number the number
	 * @return the client model
	 */
	public ClientModel rollNumber(String type, int playerIndex, int number);
	
	/**
	 * Rob player.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param victimIndex the victim index
	 * @param location the location
	 * @return the client model
	 */
	public ClientModel robPlayer(String type, int playerIndex, int victimIndex, HexLocation location);
	
	/**
	 * Finish turn.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @return the client model
	 */
	public ClientModel finishTurn(String type, int playerIndex);
	
	/**
	 * Buy dev card.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @return the client model
	 */
	public ClientModel buyDevCard(String type, int playerIndex);
	
	/**
	 * Play year of plenty.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param resource1 the resource1
	 * @param resource2 the resource2
	 * @return the client model
	 */
	public ClientModel playYearOfPlenty(String type, int playerIndex, ResourceType resource1, ResourceType resource2);
	
	/**
	 * Play road building.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param spot1 the spot1
	 * @param spot2 the spot2
	 * @return the client model
	 */
	public ClientModel playRoadBuilding(String type, int playerIndex, EdgeLocation spot1, EdgeLocation spot2);
	
	/**
	 * Play soldier.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param victimIndex the victim index
	 * @param location the location
	 * @return the client model
	 */
	public ClientModel playSoldier(String type, int playerIndex, int victimIndex, HexLocation location);
	
	/**
	 * Play monopoly.
	 *
	 * @param type the type
	 * @param resource the resource
	 * @param playerIndex the player index
	 * @return the client model
	 */
	public ClientModel playMonopoly(String type, ResourceType resource, int playerIndex);
	
	/**
	 * Play monument.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @return the client model
	 */
	public ClientModel playMonument(String type, int playerIndex);
	
	/**
	 * Builds the road.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param roadLocation the road location
	 * @param free the free
	 * @return the client model
	 */
	public ClientModel buildRoad(String type, int playerIndex, EdgeLocation roadLocation, boolean free);
	
	/**
	 * Builds the settlement.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param vertexLocation the vertex location
	 * @param free the free
	 * @return the client model
	 */
	public ClientModel buildSettlement(String type, int playerIndex, VertexLocation vertexLocation, boolean free);
	
	/**
	 * Builds the city.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param vertexLocation the vertex location
	 * @return the client model
	 */
	public ClientModel buildCity(String type, int playerIndex, VertexLocation vertexLocation);
	
	/**
	 * Offer trade.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param offer the offer
	 * @param receiver the receiver
	 * @return the client model
	 */
	public ClientModel offerTrade(String type, int playerIndex, Resources offer, int receiver);
	
	/**
	 * Accept trade.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param willAccept the will accept
	 * @return the client model
	 */
	public ClientModel acceptTrade(String type, int playerIndex, boolean willAccept);
	
	/**
	 * Maritime trade.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param ratio the ratio
	 * @param inputResource the input resource
	 * @param outputResource the output resource
	 * @return the client model
	 */
	public ClientModel maritimeTrade(String type, int playerIndex, int ratio, String inputResource, String outputResource);
	
	/**
	 * Discard cards.
	 *
	 * @param type the type
	 * @param playerIndex the player index
	 * @param discardedCards the discarded cards
	 * @return the client model
	 */
	public ClientModel discardCards(String type, int playerIndex, Resources discardedCards);
}
