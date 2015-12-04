package server.commands.moves;

import jsonTranslator.ModelToJSON;

import com.google.gson.JsonObject;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.TurnTracker;
import client.models.communication.MessageLine;
import server.commands.IMovesCommand;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.model.ServerGame;

/**
 * The Class OfferTradeCommand.
 */
public class OfferTradeCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	Resources offer;
	int receiver;
	int commandNumber;


	public OfferTradeCommand(ServerGame game, int playerIndex, Resources offer,
			int receiver, int commandNumber) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.offer = offer;
		this.receiver = receiver;
		this.commandNumber = commandNumber;
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
		TurnTracker t = game.getClientModel().getTurnTracker();

		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		model.checkPlayerIndex(playerIndex);
		
		TradeOffer tradeOffer = new TradeOffer(playerIndex, receiver, offer);
		
		game.getClientModel().setTradeOffer(tradeOffer);
		
		
		game.getClientModel().getLog().getLines().add(new MessageLine(offerer.getName() + " offered a trade", offerer.getName()));
		game.getClientModel().increaseVersion();
	}
	
	@Override
	public JsonObject toJSON() {
		ModelToJSON toJSON = new ModelToJSON();
		return toJSON.getOfferTradeCommand(playerIndex, receiver, offer);
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
