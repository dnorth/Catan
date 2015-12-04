package server.commands.moves;

import jsonTranslator.ModelToJSON;

import com.google.gson.JsonObject;

import client.models.ClientModel;
import client.models.Player;
import client.models.communication.MessageLine;
import server.commands.IMovesCommand;
import server.exceptions.AlreadyPlayedDevCardException;
import server.exceptions.DontHaveDevCardException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.model.ServerGame;
import shared.definitions.DevCard;

// TODO: Auto-generated Javadoc
/**
 * The Class MonumentCommand.
 */
public class MonumentCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	int commandNumber;

	public MonumentCommand(ServerGame game, int playerIndex, int commandNumber) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.commandNumber = commandNumber;
	}

	/**
	 *  Plays a monument Card.
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws NotYourTurnException, InvalidStatusException, DontHaveDevCardException, AlreadyPlayedDevCardException, InvalidPlayerIndexException {

		Player user = game.getClientModel().getPlayers()[playerIndex];
		
		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		model.checkDevCard(playerIndex, DevCard.MONUMENT);
		model.checkPlayerIndex(playerIndex);
		int numberOfMonumentCards = user.getOldDevCards().getMonumentCount();
		for(int i=0; i < numberOfMonumentCards; i++){
			user.getOldDevCards().decSpecifiedDevCard(DevCard.MONUMENT);
			user.incMonuments();	
			user.incrementVictoryPoints();
		}
			
		game.getClientModel().getLog().getLines().add(new MessageLine(user.getName() + " played a monument card", user.getName()));
		game.getClientModel().increaseVersion();
	}
	
	@Override
	public JsonObject toJSON() {
		ModelToJSON toJSON = new ModelToJSON();
		return toJSON.getPlayMonumentCommand(playerIndex);
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
