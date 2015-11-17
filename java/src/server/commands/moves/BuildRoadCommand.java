package server.commands.moves;

import client.models.Resources;
import client.models.mapdata.Board;
import client.models.mapdata.Road;
import server.commands.IMovesCommand;
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
	
	public BuildRoadCommand(ServerGame game, int playerIndex,
			EdgeLocation spot, boolean free) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.spot = spot;
		this.free = free;
	}





	/**
	 * Builds a road.
	 */
	@Override
	public void execute() {
		 		Board board =game.getClientModel().getBoard();
		 		if(!free){
		 		Resources resources =game.getClientModel().getPlayers()[playerIndex].getResources();
		 		resources.subtractOne(ResourceType.WOOD);
		 		resources.subtractOne(ResourceType.BRICK);
		 		}
		 		
		board.addRoad(new Road(playerIndex, spot));

	}

}
