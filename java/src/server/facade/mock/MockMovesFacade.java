package server.facade.mock;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import server.exceptions.AlreadyPlayedDevCardException;
import server.exceptions.CantBuildThereException;
import server.exceptions.DontHaveDevCardException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidMaritimeTradeException;
import server.exceptions.InvalidPlayerException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidRollException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NoTradeOfferedException;
import server.exceptions.NotYourTurnException;
import server.exceptions.OutOfPiecesException;
import server.exceptions.RobberIsAlreadyThereException;
import server.facade.iMovesFacade;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class MockMovesFacade implements iMovesFacade{

	@Override
	public ClientModel sendChat(int gameIndex, int playerIndex, String content)
			throws InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel rollNumber(int gameIndex, int playerIndex, int number)
			throws InvalidRollException, InvalidStatusException,
			InsufficientResourcesException, NotYourTurnException,
			InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel robPlayer(int gameIndex, int playerIndex,
			int victimIndex, HexLocation location)
			throws InsufficientResourcesException, InvalidStatusException,
			NotYourTurnException, RobberIsAlreadyThereException,
			InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel finishTurn(int gameIndex, int playerIndex)
			throws InvalidStatusException, NotYourTurnException,
			InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel buyDevCard(int gameIndex, int playerIndex)
			throws InsufficientResourcesException, InvalidStatusException,
			NotYourTurnException, InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel playYearOfPlenty(int gameIndex, int playerIndex,
			ResourceType resource1, ResourceType resource2)
			throws InvalidStatusException, NotYourTurnException,
			DontHaveDevCardException, AlreadyPlayedDevCardException,
			InsufficientResourcesException, InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel playRoadBuilding(int gameIndex, int playerIndex,
			EdgeLocation spot1, EdgeLocation spot2)
			throws InvalidStatusException, NotYourTurnException,
			DontHaveDevCardException, AlreadyPlayedDevCardException,
			OutOfPiecesException, CantBuildThereException,
			InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel playSoldier(int gameIndex, int playerIndex,
			int victimIndex, HexLocation location)
			throws InvalidStatusException, NotYourTurnException,
			DontHaveDevCardException, AlreadyPlayedDevCardException,
			RobberIsAlreadyThereException, InsufficientResourcesException,
			InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel playMonopoly(ResourceType resource, int gameIndex,
			int playerIndex) throws InvalidStatusException,
			NotYourTurnException, DontHaveDevCardException,
			AlreadyPlayedDevCardException, InsufficientResourcesException,
			InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel playMonument(int gameIndex, int playerIndex)
			throws NotYourTurnException, InvalidStatusException,
			DontHaveDevCardException, AlreadyPlayedDevCardException,
			InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel buildRoad(int gameIndex, int playerIndex,
			EdgeLocation roadLocation, boolean free)
			throws NotYourTurnException, InvalidStatusException,
			CantBuildThereException, InsufficientResourcesException,
			OutOfPiecesException, InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel buildSettlement(int gameIndex, int playerIndex,
			VertexLocation vertexLocation, boolean free)
			throws InsufficientResourcesException, CantBuildThereException,
			InvalidStatusException, NotYourTurnException, OutOfPiecesException,
			InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel buildCity(int gameIndex, int playerIndex,
			VertexLocation vertexLocation)
			throws InsufficientResourcesException, NotYourTurnException,
			InvalidStatusException, OutOfPiecesException,
			CantBuildThereException, InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel offerTrade(int gameIndex, int playerIndex,
			Resources offer, int receiver) throws InvalidStatusException,
			NotYourTurnException, InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel acceptTrade(int gameIndex, int playerIndex,
			boolean willAccept) throws NoTradeOfferedException,
			InvalidPlayerException, InsufficientResourcesException,
			InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel maritimeTrade(int gameIndex, int playerIndex, int ratio,
			String inputResource, String outputResource)
			throws InsufficientResourcesException, InvalidStatusException,
			NotYourTurnException, InvalidMaritimeTradeException,
			InvalidPlayerIndexException {
		return initializeClientModel();
	}

	@Override
	public ClientModel discardCards(int gameIndex, int playerIndex,
			Resources discardedCards) throws InsufficientResourcesException,
			InvalidStatusException, InvalidPlayerIndexException {
		return initializeClientModel();
	}
	

	
	private ClientModel initializeClientModel(){
		ClientModel clientModel = new ClientModel();
		
		Player[] players = new Player[4];
		
		players[0] = new Player();
		players[1] = new Player();
		players[2] = new Player();
		players[3] = new Player();
		clientModel.setPlayers(players);
		return clientModel;
	}
	
	
}
