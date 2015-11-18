package server.commands.moves;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import server.commands.IMovesCommand;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
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
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 */
	@Override
	public void execute() throws InvalidStatusException, NotYourTurnException {

		Player offerer = game.getClientModel().getPlayers()[playerIndex];

		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		
		TradeOffer tradeOffer = new TradeOffer(playerIndex, receiver, offer);
		if(offerer.hasSpecifiedResources(tradeOffer.reverseOffer())){
			game.getClientModel().setTradeOffer(tradeOffer);
		}
		game.getClientModel().increaseVersion();
	}

}
