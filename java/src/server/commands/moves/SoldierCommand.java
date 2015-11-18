package server.commands.moves;

import java.util.List;
import java.util.Random;

import client.models.Player;
import client.models.Resources;
import server.commands.IMovesCommand;
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
	 */
	@Override
	public void execute() {

		Player user = game.getClientModel().getPlayers()[playerIndex];
		if(user.getOldDevCards().hasSoldier()){
			Player victim =game.getClientModel().getPlayers()[victimIndex];
			if(victim.hasResource()){
				Resources victimResources = victim.getResources();
				Resources takerResources = game.getClientModel().getPlayers()[playerIndex].getResources();
				List<ResourceType> resourceTypes = victimResources.getResourceTypes();

				Random rand = new Random();
				ResourceType type = resourceTypes.get(rand.nextInt(resourceTypes.size()));
				victimResources.subtractResource(type, 1,takerResources);
				game.getClientModel().getBoard().setRobber(new client.models.mapdata.HexLocation(location));
				user.getOldDevCards().decSpecifiedDevCard(DevCard.SOLDIER);
			}

		}

	}

}
