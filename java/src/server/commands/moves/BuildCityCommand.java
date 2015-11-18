package server.commands.moves;

import java.util.List;

import client.models.Player;
import client.models.Resources;
import client.models.VertexObject;
import client.models.mapdata.Board;
import server.commands.IMovesCommand;
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
	 */
	@Override
	public void execute() {
		Board board = game.getClientModel().getBoard();
		List<VertexObject> cities = board.getCities();
		List<VertexObject> settlements = board.getSettlements();
		
		VertexObject city = new VertexObject(playerIndex, location);
		if(!settlements.contains(city) || cities.contains(city)) // if building city where there is no settlement, or building city where one already exists, return
		{return;}
		
		Player p = game.getClientModel().getPlayers()[playerIndex];
		
		if(p.canBuildCity())
		{
			Resources bank = game.getClientModel().getBank();
			p.payForCity(bank);
			p.decCities();
		settlements.remove(city);
		cities.add(city);

		}
	}

}
