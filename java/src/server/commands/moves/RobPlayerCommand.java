package server.commands.moves;

import java.util.List;
import java.util.Random;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.communication.MessageLine;
import server.commands.IMovesCommand;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.exceptions.RobberIsAlreadyThereException;
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
	 * @throws InsufficientResourcesException 
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 * @throws RobberIsAlreadyThereException 
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws InsufficientResourcesException, InvalidStatusException, NotYourTurnException, RobberIsAlreadyThereException, InvalidPlayerIndexException {
		Player p2 = game.getClientModel().getPlayers()[playerIndex];
		
		ClientModel model = game.getClientModel();
		model.checkStatus("Robbing");
		model.checkTurn(playerIndex);
		model.checkPlayerIndex(playerIndex);
		
		
		if(model.getBoard().getRobber().Equals(location)){
			throw new RobberIsAlreadyThereException();	
		}
		
		game.getClientModel().getBoard().setRobber(new client.models.mapdata.HexLocation(location));
		
		System.out.println("DA VICTIM INDEX IS: " + String.valueOf(victimIndex));
		
		if(victimIndex == -1)
		{
			game.getClientModel().increaseVersion();
			return;
		}
		Player p = game.getClientModel().getPlayers()[victimIndex];
		
		if(p.hasResource()) {
			Resources victimResources = p.getResources();
			Resources takerResources = game.getClientModel().getPlayers()[playerIndex].getResources();
			List<ResourceType> resourceTypes = victimResources.getResourceTypes();

			Random rand = new Random();
			ResourceType type = resourceTypes.get(rand.nextInt(resourceTypes.size()));
			victimResources.subtractResource(type, 1,takerResources);

		}
		game.getClientModel().getTurnTracker().setStatus("Playing");
		game.getClientModel().getLog().getLines().add(new MessageLine(p2.getName() + " robbed " + p.getName(), p2.getName()));
		game.getClientModel().increaseVersion();
	}

}
