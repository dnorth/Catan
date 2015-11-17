package server.commands.moves;

import client.models.VertexObject;
import client.models.mapdata.Board;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.locations.VertexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class BuildCityCommand.
 */
public class BuildCityCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	VertexLocation location;

	public BuildCityCommand(ServerGame game, int playerIndex,
			VertexLocation location) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.location = location;
	}



	/**
	 *  Builds a city.
	 */
	@Override
	public void execute() {
		Board board = game.getClientModel().getBoard();
		

	}

}
