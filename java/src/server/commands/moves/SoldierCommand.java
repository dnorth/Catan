package server.commands.moves;

import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.locations.HexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class SoldierCommand.
 */
public class SoldierCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	int victimIndex;
	HexLocation location;
	
	public SoldierCommand(ServerGame game, int playerIndex, int victimIndex,
			HexLocation location) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.victimIndex = victimIndex;
		this.location = location;
	}

	/**
	 * Plays a Soldier card.
	 */
	@Override
	public void execute() {
		

	}

}
