package server.commands.moves;

import client.models.Player;
import client.models.Resources;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.definitions.DevCard;
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
		if(p.getOldDevCards().hasYearOfPlenty()){
		Resources bank = game.getClientModel().getBank();
		p.getResources().addResource(resource1,1,bank);
		p.getResources().addResource(resource2,1,bank);
		p.getOldDevCards().decSpecifiedDevCard(DevCard.YEAROFPLENTY);
		}
		game.getClientModel().increaseVersion();
	}

}
