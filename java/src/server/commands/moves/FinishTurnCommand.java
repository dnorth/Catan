package server.commands.moves;

import client.models.ClientModel;
import client.models.TurnTracker;
import server.commands.IMovesCommand;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.model.ServerGame;

// TODO: Auto-generated Javadoc
/**
 * The Class FinishTurnCommand.
 */
public class FinishTurnCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	
	public FinishTurnCommand(ServerGame game, int playerIndex){
		this.game = game;
		this.playerIndex=playerIndex;
		}
	/**
	 *  Finishes a turn.
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 */
	@Override
	public void execute() throws InvalidStatusException, NotYourTurnException {
		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);

		TurnTracker t = game.getClientModel().getTurnTracker();
		game.getClientModel().getPlayers()[playerIndex].transferDevCards();
		t.nextPlayerTurn();
		if (t.getStatus().equals("FirstRound")) {
			if (endOfRound(1)) t.setStatus("SecondRound");
		}
		else if (t.getStatus().equals("SecondRound")) {
			if (endOfRound(2)) t.setStatus("Rolling");
		}
		else t.setStatus("Rolling");
		game.getClientModel().increaseVersion();
	}
	private boolean endOfRound(int count) {
		if (game.getClientModel().getBoard().getRoads().length == 4*count &&
				game.getClientModel().getBoard().getSettlements().size() == 4*count) return true;
		return false;
	}
}