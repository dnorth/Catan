package server.commands.moves;

import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
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


	public AcceptTradeCommand(ServerGame game, int playerIndex,
			boolean willAccept) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
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
			throw new InvalidPlayerException();
		}

		else if(game.getClientModel().getTradeOffer()==null)
		{throw new NoTradeOfferedException();}

		else if(willAccept){
			TradeOffer offer = game.getClientModel().getTradeOffer();

			Player offerer = game.getClientModel().getPlayers()[offer.getSender()];
			Player receiver = game.getClientModel().getPlayers()[offer.getReceiver()];

			if(receiver.hasSpecifiedResources(offer)){
				offerer.getResources().addResource(ResourceType.BRICK, offer.getBrickCount(), receiver.getResources());
				offerer.getResources().addResource(ResourceType.ORE, offer.getOreCount(), receiver.getResources());
				offerer.getResources().addResource(ResourceType.SHEEP, offer.getSheepCount(), receiver.getResources());
				offerer.getResources().addResource(ResourceType.WHEAT, offer.getWheatCount(), receiver.getResources());
				offerer.getResources().addResource(ResourceType.WOOD, offer.getWoodCount(), receiver.getResources());
				game.getClientModel().setTradeOffer(null);
			}
			else{
				throw new InsufficientResourcesException();
			}
		}
		else{
			game.getClientModel().setTradeOffer(null);
		}
		game.getClientModel().increaseVersion();

	}

}
