package server.facade;

import client.models.ClientModel;
import client.models.Resources;
import server.Server;
import server.model.ServerData;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class MovesFacade.
 */
public class MovesFacade implements iMovesFacade {
	
	public MovesFacade() {
	}

	/**
	 * Facade for the command moves/sendChat
	 */
	@Override
	public ClientModel sendChat(String type, int gameIndex, int playerIndex, String content) {
		
		
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/rollNumber
	 */
	@Override
	public ClientModel rollNumber(String type, int gameIndex, int playerIndex, int number) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/robPlayer
	 */
	@Override
	public ClientModel robPlayer(String type, int gameIndex, int playerIndex, int victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/finishTurn
	 */
	@Override
	public ClientModel finishTurn(String type, int gameIndex, int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/buyDevCard
	 */
	@Override
	public ClientModel buyDevCard(String type, int gameIndex, int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/Year_of_Plenty
	 */
	@Override
	public ClientModel playYearOfPlenty(String type, int gameID, int playerIndex, ResourceType resource1, ResourceType resource2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/Road_Building
	 */
	@Override
	public ClientModel playRoadBuilding(String type, int gameIndex, int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/Soldier
	 */
	@Override
	public ClientModel playSoldier(String type, int playerIndex, int victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/Monopoly
	 */
	@Override
	public ClientModel playMonopoly(String type, ResourceType resource, int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/Monument
	 */
	@Override
	public ClientModel playMonument(String type, int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/buildRoad
	 */
	@Override
	public ClientModel buildRoad(String type, int playerIndex, EdgeLocation roadLocation, boolean free) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Facade for the command moves/buildCity
	 */
	@Override
	public ClientModel buildCity(String type, int playerIndex, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/buildSettlement
	 */
	@Override
	public ClientModel buildSettlement(String type, int playerIndex, VertexLocation vertexLocation, boolean free) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Facade for the command moves/offerTrade
	 */
	@Override
	public ClientModel offerTrade(String type, int playerIndex, Resources offer, int receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/acceptTrade
	 */
	@Override
	public ClientModel acceptTrade(String type, int playerIndex, boolean willAccept) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/maritimeTrade
	 */
	@Override
	public ClientModel maritimeTrade(String type, int playerIndex, int ratio, String inputResource,
			String outputResource) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Facade for the command moves/discardCards
	 */
	@Override
	public ClientModel discardCards(String type, int playerIndex, Resources discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}

}
