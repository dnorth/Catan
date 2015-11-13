package server.commands.games;

import server.commands.IGamesCommand;
import server.model.ServerData;
import server.model.ServerGame;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateCommand.
 */
public class CreateCommand implements IGamesCommand {
ServerData serverData;
boolean randomTiles;
boolean randomNumbers;
boolean randomPorts;
String name;
int gameID;

	public CreateCommand(ServerData serverData, boolean randomTiles,
		boolean randomNumbers, boolean randomPorts, String name, int gameID) {
	super();
	this.serverData = serverData;
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
		
	}

}
