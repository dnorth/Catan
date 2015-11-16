package server.commands.moves;

import client.models.Player;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.definitions.ResourceType;

/**
 * The Class YearOfPlentyCommand.
 */
public class YearOfPlentyCommand implements IMovesCommand{

	ServerGame game;
	int playerIndex;
	ResourceType resource1;
	ResourceType resource2;
	


	public YearOfPlentyCommand(ServerGame game, int playerIndex,
			ResourceType resource1, ResourceType resource2) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.resource1 = resource1;
		this.resource2 = resource2;
	}



	/**
	 * Plays a Year of Plenty card. 
	 */
	
	@Override
	public void execute() {
		Player p = game.getClientModel().getPlayers()[playerIndex];
		p.getResources().addOne(resource1);
		p.getResources().addOne(resource2);
	}

}
