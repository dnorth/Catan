package server.commands.moves;

import java.util.List;
import java.util.Random;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import server.commands.IMovesCommand;
import server.exceptions.AlreadyPlayedDevCardException;
import server.exceptions.DontHaveDevCardException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.exceptions.RobberIsAlreadyThereException;
import server.model.ServerGame;
import shared.definitions.DevCard;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class SoldierCommand.
 */
public class SoldierCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	int victimIndex;
	HexLocation location;

	public SoldierCommand(ServerGame game, int playerIndex, int victimIndex,
			HexLocation location) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.victimIndex = victimIndex;
		this.location = location;
	}

	/**
	 * Plays a Soldier card.
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws RobberIsAlreadyThereException 
	 * @throws InsufficientResourcesException 
	 */
	@Override
	public void execute() throws InvalidStatusException, NotYourTurnException, DontHaveDevCardException, AlreadyPlayedDevCardException, RobberIsAlreadyThereException, InsufficientResourcesException {

		ClientModel model = game.getClientModel();
		Player victim = model.getPlayers()[victimIndex];
		Player user = model.getPlayers()[playerIndex];
		
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		model.checkDevCard(playerIndex, DevCard.SOLDIER);
		
		if(model.getBoard().getRobber().Equals(location)){
			throw new RobberIsAlreadyThereException();
			
		}
		game.getClientModel().getBoard().setRobber(new client.models.mapdata.HexLocation(location));
		
		if(victimIndex==-1)
		{return;}
		
		if(victim.hasResource()){
			Resources victimResources = victim.getResources();
			Resources takerResources = user.getResources();
			List<ResourceType> resourceTypes = victimResources.getResourceTypes();

			Random rand = new Random();
			ResourceType type = resourceTypes.get(rand.nextInt(resourceTypes.size()));
			victimResources.subtractResource(type, 1,takerResources);
		}

		user.incSoldiers();
		if(model.playerHasLargestArmy(user)){
			model.awardLargestArmy(user);
		}
		
		user.getOldDevCards().decSpecifiedDevCard(DevCard.SOLDIER);
		model.setPlayedDevCard(true);
		game.getClientModel().increaseVersion();

	}

}
