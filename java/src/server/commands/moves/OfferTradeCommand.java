package server.commands.moves;

import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import server.commands.IMovesCommand;
import server.model.ServerGame;

// TODO: Auto-generated Javadoc
/**
 * The Class OfferTradeCommand.
 */
public class OfferTradeCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	Resources offer;
	int receiver;


	public OfferTradeCommand(ServerGame game, int playerIndex, Resources offer,
			int receiver) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.offer = offer;
		this.receiver = receiver;
	}



	/**
	 *  Offers a trade.
	 */
	@Override
	public void execute() {

		Player offerer = game.getClientModel().getPlayers()[playerIndex];


		TradeOffer tradeOffer = new TradeOffer(playerIndex, receiver, offer);
		if(offerer.hasSpecifiedResources(tradeOffer.reverseOffer())){
			game.getClientModel().setTradeOffer(tradeOffer);
		}
		game.getClientModel().increaseVersion();
	}

}
