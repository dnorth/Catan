package server.commands.moves;

import server.commands.IMovesCommand;
import server.model.ServerGame;

// TODO: Auto-generated Javadoc
/**
 * The Class MonumentCommand.
 */
public class MonumentCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;

	public MonumentCommand(ServerGame game, int playerIndex) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
	}

	/**
	 *  Plays a monument Card.
	 */
	@Override
	public void execute() {
		game.getClientModel().getPlayers()[playerIndex].incMonuments();
	}

}
