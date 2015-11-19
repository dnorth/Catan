package server.commands.moves;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.mapdata.Board;
import client.models.mapdata.Road;
import server.commands.IMovesCommand;
import server.exceptions.CantBuildThereException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.exceptions.OutOfPiecesException;
import server.model.ServerGame;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class BuildRoadCommand.
 */
public class BuildRoadCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	EdgeLocation spot;
	boolean free;
//<<<<<<< Updated upstream

	public BuildRoadCommand(ServerGame game, int playerIndex,
			EdgeLocation spot, boolean free) {
//=======
//	
//	public BuildRoadCommand(ServerGame game, int playerIndex, EdgeLocation spot, boolean free) {
//>>>>>>> Stashed changes
//		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.spot = spot;
		this.free = free;
	}





	/**
	 * Builds a road.
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 * @throws CantBuildThereException 
	 * @throws InsufficientResourcesException 
	 * @throws OutOfPiecesException 
	 */
	@Override
	public void execute() throws NotYourTurnException, InvalidStatusException, CantBuildThereException, InsufficientResourcesException, OutOfPiecesException {
		System.out.println("IN ROAD COMMAND");
		System.out.println("SPOT: " + spot.toString());

		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);

		if(model.isInitializingPhase()){
			model.checkInitialRoad(new Road(playerIndex, spot));
		}
		else{
			model.checkRoad(new Road(playerIndex, spot));
		}

		Board board = model.getBoard();
		Player p = model.getPlayers()[playerIndex];
		Resources bank = model.getBank();

		if (free == false) {
			p.payForRoad(bank);
		}
		p.decRoads();
		board.addRoad(new Road(playerIndex, spot));

		if(model.playerHasLongestRoad(p)){
			model.awardLongestRoad(p);
		}
		model.increaseVersion();
	}

}
