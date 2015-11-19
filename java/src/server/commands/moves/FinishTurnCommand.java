package server.commands.moves;

import client.models.ClientModel;
import client.models.Player;
import client.models.TurnTracker;
import client.models.communication.MessageLine;
import server.commands.IMovesCommand;
import server.exceptions.InvalidPlayerIndexException;
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
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws InvalidStatusException, NotYourTurnException, InvalidPlayerIndexException {
		ClientModel model = game.getClientModel();
		System.out.println("CURRENT STATUS IS: " + model.getTurnTrackerStatus());
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		model.checkPlayerIndex(playerIndex);

		model.setPlayedDevCard(false);
		
		TurnTracker t = game.getClientModel().getTurnTracker();
		Player p = game.getClientModel().getPlayers()[playerIndex];
		p.transferDevCards();
		
		if(t.getStatus().equals("SecondRound")) {
			t.previousPlayerTurn();
		}
		else {
			t.nextPlayerTurn();
		}
		
		if (t.getStatus().equals("FirstRound")) {
			if (endOfRound(1)) t.setStatus("SecondRound");
		}
		else if (t.getStatus().equals("SecondRound")) {
			if (endOfRound(2)) t.setStatus("Rolling");
		}
		else {
			t.setStatus("Rolling");
		}
		game.getClientModel().getLog().getLines().add(new MessageLine(p.getName() + " done finished his turn", p.getName()));

		game.getClientModel().increaseVersion();
	}
	private boolean endOfRound(int count) {
		if (game.getClientModel().getBoard().getRoads().length == 4*count &&
				game.getClientModel().getBoard().getSettlements().size() == 4*count) return true;
		return false;
	}
}