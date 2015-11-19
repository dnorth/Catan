package server.commands.moves;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import server.commands.IMovesCommand;
import server.exceptions.AlreadyPlayedDevCardException;
import server.exceptions.DontHaveDevCardException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
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
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws InsufficientResourcesException 
	 */
	
	@Override
	public void execute() throws InvalidStatusException, NotYourTurnException, DontHaveDevCardException, AlreadyPlayedDevCardException, InsufficientResourcesException {
		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		model.checkDevCard(playerIndex, DevCard.YEAROFPLENTY);
		
		Player p = game.getClientModel().getPlayers()[playerIndex];
		
		Resources bank = game.getClientModel().getBank();
		p.getResources().addResource(resource1,1,bank);
		p.getResources().addResource(resource2,1,bank);
		p.getOldDevCards().decSpecifiedDevCard(DevCard.YEAROFPLENTY);
		model.setPlayedDevCard(true);
		game.getClientModel().increaseVersion();
	}

}
