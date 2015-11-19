package server.commands.moves;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.communication.MessageLine;
import server.commands.IMovesCommand;
import server.exceptions.InvalidPlayerIndexException;
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
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws InvalidStatusException, NotYourTurnException, InvalidPlayerIndexException {

		Player offerer = game.getClientModel().getPlayers()[playerIndex];

		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		model.checkPlayerIndex(playerIndex);
		
		TradeOffer tradeOffer = new TradeOffer(playerIndex, receiver, offer);
		if(offerer.hasSpecifiedResources(tradeOffer.reverseOffer())){
			game.getClientModel().setTradeOffer(tradeOffer);
		}
		game.getClientModel().getLog().getLines().add(new MessageLine(offerer.getName() + " offered a trade", offerer.getName()));
		game.getClientModel().increaseVersion();
	}

}
