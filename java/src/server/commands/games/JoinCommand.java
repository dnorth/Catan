package server.commands.games;

import server.commands.IGamesCommand;
import server.model.ServerData;

// TODO: Auto-generated Javadoc
/**
 * The Class JoinCommand.
 */
public class JoinCommand implements IGamesCommand {

	ServerData serverData;
	int id;
	String color;
	
	public JoinCommand(ServerData serverData, int id, String color) {
		this.serverData = serverData;
		this.id=id;
		this.color=color;
	}

	/**
	 * Joins a game.
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
