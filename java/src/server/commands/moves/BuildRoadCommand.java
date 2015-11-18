package server.commands.moves;

import client.models.Player;
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
		 		Player p = game.getClientModel().getPlayers()[playerIndex];
		 		Resources bank = game.getClientModel().getBank();
		 		
		 		if(free==false && p.canBuyRoad()){
		 		p.payForRoad(bank);
		 		p.decRoads();
				board.addRoad(new Road(playerIndex, spot));
		 		}
		 		else if(free==true){
		 			p.decRoads();
		 			board.addRoad(new Road(playerIndex, spot));
		 		}
	}

}
