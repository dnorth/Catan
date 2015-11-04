package server.facade;

import client.models.ClientModel;
import client.models.Resources;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class MovesFacade implements iMovesFacade {

	@Override
	public ClientModel sendChat(String type, int playerIndex, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel rollNumber(String type, int playerIndex, int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel robPlayer(String type, int playerIndex, int victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel finishTurn(String type, int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel buyDevCard(String type, int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel playYearOfPlenty(String type, int playerIndex, ResourceType resource1, ResourceType resource2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel playRoadBuilding(String type, int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel playSoldier(String type, int playerIndex, int victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel playMonopoly(String type, ResourceType resource, int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel playMonument(String type, int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel buildRoad(String type, int playerIndex, EdgeLocation roadLocation, boolean free) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel buildSettlement(String type, int playerIndex, VertexLocation vertexLocation, boolean free) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel buildCity(String type, int playerIndex, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel offerTrade(String type, int playerIndex, Resources offer, int receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel acceptTrade(String type, int playerIndex, boolean willAccept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel maritimeTrade(String type, int playerIndex, int ratio, String inputResource,
			String outputResource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel discardCards(String type, int playerIndex, Resources discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}

}
