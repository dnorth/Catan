package server.commands.moves;

import java.util.List;
import java.util.Random;

import client.models.Player;
import client.models.Resources;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class RobPlayerCommand.
 */
public class RobPlayerCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	int victimIndex;
	HexLocation location;


	public RobPlayerCommand(ServerGame game, int playerIndex, int victimIndex,
			HexLocation location) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.victimIndex = victimIndex;
		this.location = location;
	}


	/**
	 *  Robs a player.
	 */
	@Override
	public void execute() {
		Player p =game.getClientModel().getPlayers()[victimIndex];
		if(p.hasResource()){
			Resources victimResources = p.getResources();
			List<ResourceType> resourceTypes = victimResources.getResourceTypes();

			Random rand = new Random();
			ResourceType type = resourceTypes.get(rand.nextInt(resourceTypes.size()));
			victimResources.subtractResource(type, 1);

			game.getClientModel().getPlayers()[playerIndex].getResources().addOne(type);
		}
	}

}
