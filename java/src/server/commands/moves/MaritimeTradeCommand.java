package server.commands.moves;

import client.models.Player;
import client.models.Resources;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.definitions.ResourceType;

// TODO: Auto-generated Javadoc
/**
 * The Class MaritimeTradeCommand.
 */
public class MaritimeTradeCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	int ratio;
	ResourceType inputResource;
	ResourceType outputResource;
	


	public MaritimeTradeCommand(ServerGame game, int playerIndex, int ratio,
			String inputResource, String outputResource) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.ratio = ratio;
		this.inputResource = ResourceType.getResourceType(inputResource);
		this.outputResource = ResourceType.getResourceType(outputResource);
	}



	/**
	 *  Does a Maritime Trade.
	 */
	@Override
	public void execute() {
		Player p = game.getClientModel().getPlayers()[playerIndex];
		Resources resources = p.getResources();
		if(resources.getAmountOfSpecificResource(inputResource)< ratio) // if player doesn't have enough resources to trade, return
		{return;}
		
		Resources bank = game.getClientModel().getBank();
		resources.subtractResource(inputResource, ratio, bank);
		resources.addResource(outputResource, 1, bank);
	}

}
