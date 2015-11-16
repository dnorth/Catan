package server.facade;

import client.models.ClientModel;
import client.models.Resources;
import client.models.communication.MessageLine;
import server.Server;
import server.commands.moves.SendChatCommand;
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
	 */
	@Override
	public ClientModel rollNumber(int gameIndex, int playerIndex, int number) {
		ServerGame game =data.getGameByID(gameIndex);
		

		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/robPlayer
	 */
	@Override
	public ClientModel robPlayer(int gameIndex, int playerIndex, int victimIndex, HexLocation location) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/finishTurn
	 */
	@Override
	public ClientModel finishTurn(int gameIndex, int playerIndex) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/buyDevCard
	 */
	@Override
	public ClientModel buyDevCard(int gameIndex, int playerIndex) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/Year_of_Plenty
	 */
	@Override
	public ClientModel playYearOfPlenty(int gameIndex, int playerIndex, ResourceType resource1, ResourceType resource2) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/Road_Building
	 */
	@Override
	public ClientModel playRoadBuilding(int gameIndex, int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/Soldier
	 */
	@Override
	public ClientModel playSoldier(int gameIndex, int playerIndex, int victimIndex, HexLocation location) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/Monopoly
	 */
	@Override
	public ClientModel playMonopoly(ResourceType resource, int gameIndex, int playerIndex) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/Monument
	 */
	@Override
	public ClientModel playMonument(int gameIndex, int playerIndex) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/buildRoad
	 */
	@Override
	public ClientModel buildRoad(int gameIndex, int playerIndex, EdgeLocation roadLocation, boolean free) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}
	/**
	 * Facade for the command moves/buildCity
	 */
	@Override
	public ClientModel buildCity(int gameIndex, int playerIndex, VertexLocation vertexLocation) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/buildSettlement
	 */
	@Override
	public ClientModel buildSettlement(int gameIndex, int playerIndex, VertexLocation vertexLocation, boolean free) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}


	/**
	 * Facade for the command moves/offerTrade
	 */
	@Override
	public ClientModel offerTrade(int gameIndex, int playerIndex, Resources offer, int receiver) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/acceptTrade
	 */
	@Override
	public ClientModel acceptTrade(int gameIndex, int playerIndex, boolean willAccept) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/maritimeTrade
	 */
	@Override
	public ClientModel maritimeTrade(int gameIndex, int playerIndex, int ratio, String inputResource,
			String outputResource) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

	/**
	 * Facade for the command moves/discardCards
	 */
	@Override
	public ClientModel discardCards(int gameIndex, int playerIndex, Resources discardedCards) {
		ServerGame game =data.getGameByID(gameIndex);
		
		
		return game.getClientModel();
	}

}
