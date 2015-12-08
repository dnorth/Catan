package server.commands.moves;

import jsonTranslator.ModelToJSON;

import com.google.gson.JsonObject;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.communication.MessageLine;
import client.models.mapdata.Board;
import client.models.mapdata.Road;
import server.commands.IMovesCommand;
import server.exceptions.CantBuildThereException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidPlayerIndexException;
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
	int commandNumber;
	
	public BuildRoadCommand(ServerGame game, int playerIndex, EdgeLocation spot, boolean free, int commandNumber) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.spot = spot;
		this.free = free;
		this.commandNumber = commandNumber;
	}





	/**
	 * Builds a road.
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 * @throws CantBuildThereException 
	 * @throws InsufficientResourcesException 
	 * @throws OutOfPiecesException 
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws NotYourTurnException, InvalidStatusException, CantBuildThereException, InsufficientResourcesException, OutOfPiecesException, InvalidPlayerIndexException {
		
		ClientModel model = game.getClientModel();
		if(!free) {
			model.checkStatus("Playing");			
		}
		model.checkTurn(playerIndex);
		model.checkPlayerIndex(playerIndex);
		
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
		
		game.getClientModel().getLog().getLines().add(new MessageLine(p.getName() + " built a road", p.getName()));
		
		if(model.playerHasLongestRoad(p)){
			
			model.awardLongestRoad(p);
		}
		model.increaseVersion();
	}





	@Override
	public JsonObject toJSON() {
		ModelToJSON toJSON = new ModelToJSON();
		return toJSON.getBuildRoadCommand(playerIndex, spot, free);
	}





	@Override
	public int getCommandNumber() {
		return commandNumber;
	}





	@Override
	public int getGameID() {
		return game.getId();
	}

	@Override
	public void setGame(ServerGame game) {
		this.game = game;
	}
}
