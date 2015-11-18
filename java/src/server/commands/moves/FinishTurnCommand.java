package server.commands.moves;

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
		game.getClientModel().getPlayers()[playerIndex].transferDevCards();
		if (t.getStatus().equals("FirstRound")) {
			if (endOfRound(1)) {
				t.setStatus("SecondRound");
				t.setCurrentTurn(3);
			}
			else t.nextPlayerTurn();
		}
		else if (t.getStatus().equals("SecondRound")) {
			if (endOfRound(2)) t.setStatus("Rolling");
			else t.previousPlayerTurn();
		}
		else {
			t.nextPlayerTurn();
			t.setStatus("Rolling");
		}
		game.getClientModel().increaseVersion();
	}
	private boolean endOfRound(int count) {
		if (game.getClientModel().getBoard().getRoads().length == 4*count &&
				game.getClientModel().getBoard().getSettlements().size() == 4*count) return true;
		return false;
	}
}
