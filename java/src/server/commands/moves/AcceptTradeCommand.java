package server.commands.moves;

import jsonTranslator.ModelToJSON;

import com.google.gson.JsonObject;

import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.communication.MessageLine;
import server.commands.IMovesCommand;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.NoTradeOfferedException;
import server.exceptions.InvalidPlayerException;
import server.model.ServerGame;
import shared.definitions.ResourceType;

// TODO: Auto-generated Javadoc
/**
 * The Class AcceptTradeCommand.
 */
public class AcceptTradeCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	boolean willAccept;
	int commandNumber;


	public AcceptTradeCommand(ServerGame game, int playerIndex,
			boolean willAccept, int commandNumber) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
		this.commandNumber = commandNumber;
	}


	/**
	 * Accepts a trade.
	 * @throws InvalidPlayerException 
	 * @throws InsufficientResourcesException 
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws NoTradeOfferedException, InvalidPlayerException, InsufficientResourcesException, InvalidPlayerIndexException {

		game.getClientModel().checkPlayerIndex(playerIndex);
		if(playerIndex!= game.getClientModel().getTradeOffer().getReceiver()){
			throw new InvalidPlayerException("Invalid player to trade with.");
		}

		if(game.getClientModel().getTradeOffer()==null)
		{
			throw new NoTradeOfferedException();
		}

		TradeOffer offer = game.getClientModel().getTradeOffer();

		Player offerer = game.getClientModel().getPlayers()[offer.getSender()];
		Player receiver = game.getClientModel().getPlayers()[offer.getReceiver()];
		
		if(willAccept){
			if(receiver.hasSpecifiedResources(offer)){
				offerer.getResources().subtractResource(ResourceType.BRICK, offer.getBrickCount(), receiver.getResources());
				offerer.getResources().subtractResource(ResourceType.ORE, offer.getOreCount(), receiver.getResources());
				offerer.getResources().subtractResource(ResourceType.SHEEP, offer.getSheepCount(), receiver.getResources());
				offerer.getResources().subtractResource(ResourceType.WHEAT, offer.getWheatCount(), receiver.getResources());
				offerer.getResources().subtractResource(ResourceType.WOOD, offer.getWoodCount(), receiver.getResources());
				game.getClientModel().setTradeOffer(null);
				game.getClientModel().getLog().getLines().add(new MessageLine(receiver.getName() + " accepted a trade from " + offerer.getName(), receiver.getName()));
			}
			else{
				throw new InsufficientResourcesException("Insufficient Resources.");
			}
		}
		else{
			game.getClientModel().setTradeOffer(null);
			game.getClientModel().getLog().getLines().add(new MessageLine(receiver.getName() + " declined a trade from " + offerer.getName(), receiver.getName()));
		}
		game.getClientModel().increaseVersion();

	}


	@Override
	public JsonObject toJSON() {
		ModelToJSON toJSON = new ModelToJSON();
		return toJSON.getAcceptTradeCommand(playerIndex, willAccept);
	}


	@Override
	public int getCommandNumber() {
		return commandNumber;
	}


	@Override
	public int getGameID() {
		return game.getId();
	}

}
