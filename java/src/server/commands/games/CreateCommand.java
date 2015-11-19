package server.commands.games;

import client.models.mapdata.Board;
import server.Server;
import server.commands.IGamesCommand;
import server.model.ServerData;
import server.model.ServerGame;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateCommand.
 */
public class CreateCommand implements IGamesCommand {
	boolean randomTiles;
	boolean randomNumbers;
	boolean randomPorts;
	String name;
	int gameID;

	public CreateCommand(ServerData serverData, boolean randomTiles,
			boolean randomNumbers, boolean randomPorts, String name, int gameID) {
		super();
		this.randomTiles = randomTiles;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.name = name;
		this.gameID = gameID;
	}



	/**
	 * 
	 *Creates a game. 
	 */
	@Override
	public void execute() {
		ServerGame game = new ServerGame(name, gameID);
		Board board = game.getClientModel().getBoard();

		if(randomTiles){
			board.CreateRandomTiles();
		}

		if(randomNumbers){
			board.CreateRandomNumbers();
		}

		if(randomPorts){
			board.CreateRandomPorts();
		}
		Server.getServerData().getGames().add(game);
	}

}
