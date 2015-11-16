package server.commands.moves;

import client.models.ClientModel;
import client.models.TurnTracker;
import server.commands.IMovesCommand;
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
	 */
	@Override
	public void execute() {
		TurnTracker t = game.getClientModel().getTurnTracker();
		t.setCurrentTurn(++playerIndex);
	}

}
