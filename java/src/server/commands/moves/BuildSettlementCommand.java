package server.commands.moves;

import java.util.List;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.VertexObject;
import client.models.mapdata.Board;
import client.models.mapdata.EdgeLocation;
import server.commands.IMovesCommand;
import server.exceptions.CantBuildThereException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.exceptions.OutOfPiecesException;
import server.model.ServerGame;
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
	 * @throws InsufficientResourcesException 
	 * @throws CantBuildThereException 
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 * @throws OutOfPiecesException 
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws InsufficientResourcesException, CantBuildThereException, InvalidStatusException, NotYourTurnException, OutOfPiecesException, InvalidPlayerIndexException {
		Board board = game.getClientModel().getBoard();
		List<VertexObject> settlements = board.getSettlements();
		
		ClientModel model = game.getClientModel();
		if(!free) {
			model.checkStatus("Playing");
		}
		model.checkTurn(playerIndex);
		model.checkPlayerIndex(playerIndex);
		
		
		VertexObject settlement = new VertexObject(playerIndex, location);
		game.getClientModel().checkSettlement(playerIndex, new EdgeLocation(location));
		
		Player p = game.getClientModel().getPlayers()[playerIndex];
		Resources bank = game.getClientModel().getBank();
		

		
		if(!free) {
			p.payForSettlement(bank);
		}
		p.decSettlements();
		settlements.add(settlement);
		game.getClientModel().increaseVersion();
	}

}