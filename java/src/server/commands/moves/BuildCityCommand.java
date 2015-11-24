package server.commands.moves;

import java.util.List;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.VertexObject;
import client.models.communication.MessageLine;
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
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
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
	 * @throws InsufficientResourcesException 
	 * @throws NotYourTurnException 
	 * @throws InvalidStatusException 
	 * @throws OutOfPiecesException 
	 * @throws CantBuildThereException 
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws InsufficientResourcesException, NotYourTurnException, InvalidStatusException, OutOfPiecesException, CantBuildThereException, InvalidPlayerIndexException {
		Board board = game.getClientModel().getBoard();
		List<VertexObject> cities = board.getCities();
		List<VertexObject> settlements = board.getSettlements();
		
		ClientModel model = game.getClientModel();
		model.checkPlayerIndex(playerIndex);
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		
		
		VertexObject city = new VertexObject(playerIndex, location);
		
		model.checkCity(playerIndex, new EdgeLocation(location));
		
		Player p = game.getClientModel().getPlayers()[playerIndex];
		
		if(p.canBuildCity())
		{
			Resources bank = game.getClientModel().getBank();
			p.payForCity(bank);
			p.decCities();
			//p.incSettlements();
			settlements.remove(city);
			cities.add(city);
			p.incrementVictoryPoints();
			game.getClientModel().getLog().getLines().add(new MessageLine(p.getName() + " built a city", p.getName()));
		}
		game.getClientModel().increaseVersion();
	}

}
