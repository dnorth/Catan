package server.commands.moves;

import client.models.Player;
import client.models.mapdata.Board;
import client.models.mapdata.Road;
import server.commands.IMovesCommand;
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
	
	
	public RoadBuildingCommand(ServerGame game, int playerIndex,
			EdgeLocation spot1, EdgeLocation spot2) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.spot1 = spot1;
		this.spot2 = spot2;
	}

	/**
	 *  Plays RoadBuilding Card.
	 */
	@Override
	public void execute() {
		Player p = game.getClientModel().getPlayers()[playerIndex];
		
		if(p.getOldDevCards().hasRoadBuilding()){
		Board board =game.getClientModel().getBoard();
		board.addRoad(new Road(playerIndex, spot1));
		board.addRoad(new Road(playerIndex, spot2));
		p.getOldDevCards().decSpecifiedDevCard(DevCard.ROADBUILDING);
		}
		game.getClientModel().increaseVersion();
	}

}
