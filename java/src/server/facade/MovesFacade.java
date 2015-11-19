package server.facade;

import client.models.ClientModel;
import client.models.Resources;
import client.models.TurnTracker;
import client.models.communication.MessageLine;
import client.models.mapdata.Hex;
import server.Server;
import server.commands.moves.AcceptTradeCommand;
import server.commands.moves.BuildCityCommand;
import server.commands.moves.BuildRoadCommand;
import server.commands.moves.BuildSettlementCommand;
import server.commands.moves.BuyDevCardCommand;
import server.commands.moves.DiscardCardsCommand;
import server.commands.moves.FinishTurnCommand;
import server.commands.moves.MaritimeTradeCommand;
import server.commands.moves.MonopolyCommand;
import server.commands.moves.MonumentCommand;
import server.commands.moves.OfferTradeCommand;
import server.commands.moves.RoadBuildingCommand;
import server.commands.moves.RobPlayerCommand;
import server.commands.moves.RollNumberCommand;
import server.commands.moves.SendChatCommand;
import server.commands.moves.SoldierCommand;
import server.commands.moves.YearOfPlentyCommand;
import server.exceptions.AlreadyPlayedDevCardException;
import server.exceptions.CantBuildThereException;
import server.exceptions.DontHaveDevCardException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidMaritimeTradeException;
import server.exceptions.InvalidPlayerException;
import server.exceptions.InvalidRollException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NoTradeOfferedException;
import server.exceptions.NotYourTurnException;
import server.exceptions.OutOfPiecesException;
import server.exceptions.RobberIsAlreadyThereException;
import server.model.ServerData;
import server.model.ServerGame;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class MovesFacade.
 */
public class MovesFacade implements iMovesFacade {

	ServerData data;
	public MovesFacade() {
		data = Server.getServerData();
	}

	/**
	 * Facade for the command moves/sendChat
	 */
	@Override
	public ClientModel sendChat(int gameIndex, int playerIndex, String content) {
		ServerGame game =data.getGameByID(gameIndex);
		SendChatCommand command = new SendChatCommand(game,playerIndex,content);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/rollNumber
	 * @throws InsufficientResourcesException 
	 * @throws InvalidStatusException 
	 * @throws InvalidRollException 
	 */
	@Override
	public ClientModel rollNumber(int gameIndex, int playerIndex, int number) throws InvalidRollException, InvalidStatusException, InsufficientResourcesException {
		ServerGame game =data.getGameByID(gameIndex);
		RollNumberCommand command = new RollNumberCommand(game, playerIndex, number);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/robPlayer
	 * @throws RobberIsAlreadyThereException 
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 * @throws InsufficientResourcesException 
	 */
	@Override
	public ClientModel robPlayer(int gameIndex, int playerIndex, int victimIndex, HexLocation location) throws InsufficientResourcesException, InvalidStatusException, NotYourTurnException, RobberIsAlreadyThereException {
		ServerGame game =data.getGameByID(gameIndex);

		RobPlayerCommand command = new RobPlayerCommand(game, playerIndex, victimIndex, location);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/finishTurn
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 */
	@Override
	public ClientModel finishTurn(int gameIndex, int playerIndex) throws InvalidStatusException, NotYourTurnException {
		ServerGame game =data.getGameByID(gameIndex);
		FinishTurnCommand  command = new FinishTurnCommand(game, playerIndex);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/buyDevCard
	 * @throws InsufficientResourcesException 
	 */
	@Override
	public ClientModel buyDevCard(int gameIndex, int playerIndex) throws InsufficientResourcesException {
		ServerGame game =data.getGameByID(gameIndex);
		BuyDevCardCommand command = new BuyDevCardCommand(game, playerIndex);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/Year_of_Plenty
	 * @throws InsufficientResourcesException 
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 */
	@Override
	public ClientModel playYearOfPlenty(int gameIndex, int playerIndex, ResourceType resource1, ResourceType resource2) throws InvalidStatusException, NotYourTurnException, DontHaveDevCardException, AlreadyPlayedDevCardException, InsufficientResourcesException {
		ServerGame game =data.getGameByID(gameIndex);
		YearOfPlentyCommand command = new YearOfPlentyCommand(game, playerIndex, resource1, resource2);

		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/Road_Building
	 * @throws CantBuildThereException 
	 * @throws OutOfPiecesException 
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 */
	@Override
	public ClientModel playRoadBuilding(int gameIndex, int playerIndex, EdgeLocation spot1, EdgeLocation spot2) throws InvalidStatusException, NotYourTurnException, DontHaveDevCardException, AlreadyPlayedDevCardException, OutOfPiecesException, CantBuildThereException {
		ServerGame game =data.getGameByID(gameIndex);

		RoadBuildingCommand command = new RoadBuildingCommand(game, playerIndex, spot1, spot2);
		command.execute();

		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/Soldier
	 * @throws InsufficientResourcesException 
	 * @throws RobberIsAlreadyThereException 
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 */
	@Override
	public ClientModel playSoldier(int gameIndex, int playerIndex, int victimIndex, HexLocation location) throws InvalidStatusException, NotYourTurnException, DontHaveDevCardException, AlreadyPlayedDevCardException, RobberIsAlreadyThereException, InsufficientResourcesException {
		ServerGame game =data.getGameByID(gameIndex);

		SoldierCommand command = new SoldierCommand(game, playerIndex, victimIndex, location);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/Monopoly
	 * @throws InsufficientResourcesException 
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 */
	@Override
	public ClientModel playMonopoly(ResourceType resource, int gameIndex, int playerIndex) throws InvalidStatusException, NotYourTurnException, DontHaveDevCardException, AlreadyPlayedDevCardException, InsufficientResourcesException {
		ServerGame game =data.getGameByID(gameIndex);

		MonopolyCommand command = new MonopolyCommand(game, resource, playerIndex);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/Monument
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 */
	@Override
	public ClientModel playMonument(int gameIndex, int playerIndex) throws NotYourTurnException, InvalidStatusException, DontHaveDevCardException, AlreadyPlayedDevCardException {
		ServerGame game =data.getGameByID(gameIndex);

		MonumentCommand command = new MonumentCommand(game,playerIndex);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/buildRoad
	 * @throws OutOfPiecesException 
	 * @throws InsufficientResourcesException 
	 * @throws CantBuildThereException 
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 */
	@Override
	public ClientModel buildRoad(int gameIndex, int playerIndex, EdgeLocation roadLocation, boolean free) throws NotYourTurnException, InvalidStatusException, CantBuildThereException, InsufficientResourcesException, OutOfPiecesException {
		ServerGame game =data.getGameByID(gameIndex);

		BuildRoadCommand command = new BuildRoadCommand(game, playerIndex, roadLocation, free);
		command.execute();
		return game.getClientModel();
	}
	/**
	 * Facade for the command moves/buildCity
	 * @throws CantBuildThereException 
	 * @throws OutOfPiecesException 
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 * @throws InsufficientResourcesException 
	 */
	@Override
	public ClientModel buildCity(int gameIndex, int playerIndex, VertexLocation location) throws InsufficientResourcesException, NotYourTurnException, InvalidStatusException, OutOfPiecesException, CantBuildThereException {
		ServerGame game =data.getGameByID(gameIndex);

		BuildCityCommand command = new BuildCityCommand(game, playerIndex, location);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/buildSettlement
	 * @throws OutOfPiecesException 
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 * @throws CantBuildThereException 
	 * @throws InsufficientResourcesException 
	 */
	@Override
	public ClientModel buildSettlement(int gameIndex, int playerIndex, VertexLocation vertexLocation, boolean free) throws InsufficientResourcesException, CantBuildThereException, InvalidStatusException, NotYourTurnException, OutOfPiecesException {
		ServerGame game =data.getGameByID(gameIndex);

		BuildSettlementCommand command = new BuildSettlementCommand(game, playerIndex, vertexLocation, free);
		command.execute();
		return game.getClientModel();
	}


	/**
	 * Facade for the command moves/offerTrade
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 */
	@Override
	public ClientModel offerTrade(int gameIndex, int playerIndex, Resources offer, int receiver) throws InvalidStatusException, NotYourTurnException {
		ServerGame game =data.getGameByID(gameIndex);

		OfferTradeCommand command = new OfferTradeCommand(game, playerIndex, offer, receiver);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/acceptTrade
	 * @throws InsufficientResourcesException 
	 * @throws InvalidPlayerException 
	 * @throws NoTradeOfferedException 
	 */
	@Override
	public ClientModel acceptTrade(int gameIndex, int playerIndex, boolean willAccept) throws NoTradeOfferedException, InvalidPlayerException, InsufficientResourcesException {
		ServerGame game =data.getGameByID(gameIndex);

		AcceptTradeCommand command = new AcceptTradeCommand(game, playerIndex, willAccept);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/maritimeTrade
	 * @throws InvalidMaritimeTradeException 
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 * @throws InsufficientResourcesException 
	 */
	@Override
	public ClientModel maritimeTrade(int gameIndex, int playerIndex, int ratio, String inputResource,
			String outputResource) throws InsufficientResourcesException, InvalidStatusException, NotYourTurnException, InvalidMaritimeTradeException {
		ServerGame game =data.getGameByID(gameIndex);

		MaritimeTradeCommand command = new MaritimeTradeCommand(game, playerIndex, ratio, inputResource, outputResource);
		command.execute();
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/discardCards
	 * @throws InsufficientResourcesException 
	 * @throws InvalidStatusException 
	 */
	@Override
	public ClientModel discardCards(int gameIndex, int playerIndex, Resources discardedCards) throws InsufficientResourcesException, InvalidStatusException {
		ServerGame game =data.getGameByID(gameIndex);

		DiscardCardsCommand command = new DiscardCardsCommand(game, playerIndex, discardedCards);
		command.execute();

		return game.getClientModel();
	}

}
