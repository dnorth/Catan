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

public class MonopolyCommand implements IMovesCommand  {

	ServerGame game;
	ResourceType type;
	int playerIndex;


	public MonopolyCommand(ServerGame game, ResourceType type,
			int playerIndex) {
		super();
		this.game = game;
		this.type = type;
		this.playerIndex = playerIndex;
	}

	/**
	 * plays a monopoly card.
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws InsufficientResourcesException 
	 */

	@Override
	public void execute() throws InvalidStatusException, NotYourTurnException, DontHaveDevCardException, AlreadyPlayedDevCardException, InsufficientResourcesException {
		Player[] players = game.getClientModel().getPlayers();
		Player user = game.getClientModel().getPlayers()[playerIndex];

		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		model.checkDevCard(playerIndex, DevCard.MONOPOLY);


		Resources userResources = players[playerIndex].getResources();

		for(Player p : players){
			Resources resources = p.getResources();
			int count = resources.getAmountOfSpecificResource(type);

			resources.subtractResource(type, count, userResources);  //for each player, take all of the resourceType and give it to userResources
		}
		user.getOldDevCards().decSpecifiedDevCard(DevCard.MONOPOLY);
		model.setPlayedDevCard(true);
		
		game.getClientModel().increaseVersion();
	}

}
