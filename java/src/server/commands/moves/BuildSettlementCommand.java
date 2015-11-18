package server.commands.moves;

import java.util.List;

import client.models.Player;
import client.models.Resources;
import client.models.VertexObject;
import client.models.mapdata.Board;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class BuildSettlementCommand.
 */
public class BuildSettlementCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	VertexLocation location;
	boolean free;
	
	public BuildSettlementCommand(ServerGame game, int playerIndex,
			VertexLocation location, boolean free) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.location = location;
		this.free = free;
	}


	/**
	 *  Builds a settlement.
	 */
	@Override
	public void execute() {
		Board board = game.getClientModel().getBoard();
		List<VertexObject> cities = board.getCities();
		List<VertexObject> settlements = board.getSettlements();
		
		VertexObject settlement = new VertexObject(playerIndex, location);
		if(settlements.contains(settlement) || cities.contains(settlement)) // if building settlment where city or settlement already exists, return
		{return;}
		
		
		Player p = game.getClientModel().getPlayers()[playerIndex];
		Resources bank = game.getClientModel().getBank();
		

		
		if(free==false && p.canBuySettlement()) {
			p.payForSettlement(bank);
			p.decSettlements();
			settlements.add(settlement);
		}
		else if(free==true)
		{
			p.decSettlements();
			settlements.add(settlement);
			if (p.getSettlements() == 3) {
				HexLocation[] neighborHexes = settlement.getHexes();
				for (HexLocation h : neighborHexes){
					game.getClientModel().addHexResourceToPlayer(h, playerIndex);
				}
			}
		}
		game.getClientModel().increaseVersion();
	}

}
