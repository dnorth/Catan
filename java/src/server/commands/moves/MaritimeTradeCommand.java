package server.commands.moves;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.communication.MessageLine;
import server.commands.IMovesCommand;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidMaritimeTradeException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
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
	 * @throws InsufficientResourcesException 
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 * @throws InvalidMaritimeTradeException 
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws InsufficientResourcesException, InvalidStatusException, NotYourTurnException, InvalidMaritimeTradeException, InvalidPlayerIndexException {
		Player p = game.getClientModel().getPlayers()[playerIndex];
		Resources resources = p.getResources();
		Resources bank = game.getClientModel().getBank();
		
		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		model.checkMaritimeTrade(playerIndex, inputResource, ratio);
		model.checkPlayerIndex(playerIndex);
		
		
		if(resources.hasResource(inputResource, ratio)== false || bank.hasResource(outputResource, 1) == false) // if player doesn't have enough resources to trade, return
		{throw new InsufficientResourcesException("Insufficient Resources.");}
		
		
		resources.subtractResource(inputResource, ratio, bank);  // player gives ratio # of inputResource to bank
		resources.addResource(outputResource, 1, bank);          // player gets 1 outputResource from bank
		game.getClientModel().getLog().getLines().add(new MessageLine(p.getName() + " maritime traded", p.getName()));
		game.getClientModel().increaseVersion();
	}

}
