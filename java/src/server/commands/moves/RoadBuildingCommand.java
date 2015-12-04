package server.commands.moves;

import jsonTranslator.ModelToJSON;

import com.google.gson.JsonObject;

import client.models.ClientModel;
import client.models.Player;
import client.models.communication.MessageLine;
import client.models.mapdata.Board;
import client.models.mapdata.Road;
import server.commands.IMovesCommand;
import server.exceptions.AlreadyPlayedDevCardException;
import server.exceptions.CantBuildThereException;
import server.exceptions.DontHaveDevCardException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.exceptions.OutOfPiecesException;
import server.model.ServerGame;
import shared.definitions.DevCard;
import shared.locations.EdgeLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class RoadBuildingCommand.
 */
public class RoadBuildingCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	EdgeLocation spot1;
	EdgeLocation spot2;
	int commandNumber;


	public RoadBuildingCommand(ServerGame game, int playerIndex,
			EdgeLocation spot1, EdgeLocation spot2, int commandNumber) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.spot1 = spot1;
		this.spot2 = spot2;
		this.commandNumber = commandNumber;
	}

	/**
	 *  Plays RoadBuilding Card.
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws OutOfPiecesException 
	 * @throws CantBuildThereException 
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws InvalidStatusException, NotYourTurnException, DontHaveDevCardException, AlreadyPlayedDevCardException, OutOfPiecesException, CantBuildThereException, InvalidPlayerIndexException {

		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		model.checkDevCard(playerIndex, DevCard.ROADBUILDING);
		model.checkPlayerIndex(playerIndex);


		Player p = game.getClientModel().getPlayers()[playerIndex];

		Board board =game.getClientModel().getBoard();

		Road road1 = new Road(playerIndex, spot1);
		Road road2 = new Road(playerIndex, spot2);

		model.checkRoad(road1);
		p.decRoads();
		board.addRoad(road1);

		model.checkRoad(road2);
		p.decRoads();
		board.addRoad(road2);

		if(model.playerHasLongestRoad(p)){
			model.awardLongestRoad(p);
		}

		p.getOldDevCards().decSpecifiedDevCard(DevCard.ROADBUILDING);
		model.setPlayedDevCard(true);
		
		game.getClientModel().getLog().getLines().add(new MessageLine(p.getName() + " built a road", p.getName()));
		game.getClientModel().increaseVersion();
	}

	@Override
	public JsonObject toJSON() {
		ModelToJSON toJSON = new ModelToJSON();
		return toJSON.getPlayRoadBuildingCommand(playerIndex, this.spot1, this.spot2);
	}


	@Override
	public int getCommandNumber() {
		return commandNumber;
	}


	@Override
	public int getGameID() {
		return game.getId();
	}
}
