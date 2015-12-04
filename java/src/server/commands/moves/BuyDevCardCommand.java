package server.commands.moves;

import java.util.List;
import java.util.Random;

import jsonTranslator.ModelToJSON;

import com.google.gson.JsonObject;

import client.models.ClientModel;
import client.models.DevCards;
import client.models.Player;
import client.models.Resources;
import client.models.communication.MessageLine;
import server.commands.IMovesCommand;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.model.ServerGame;
import shared.definitions.DevCard;

// TODO: Auto-generated Javadoc
/**
 * The Class BuyDevCardCommand.
 */
public class BuyDevCardCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	int commandNumber;


	public BuyDevCardCommand(ServerGame game, int playerIndex, int commandNumber) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.commandNumber = commandNumber;
	}


	/**
	 *  Buys a DevCard.
	 * @throws InsufficientResourcesException 
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws InsufficientResourcesException, InvalidStatusException, NotYourTurnException, InvalidPlayerIndexException {
		Random rand = new Random();
		ClientModel model = game.getClientModel();
		model.checkStatus("Playing");
		model.checkTurn(playerIndex);
		model.checkPlayerIndex(playerIndex);

		DevCards deck = model.getDeck();
		List<DevCard> devCardTypes = deck.getDevCardTypes();

		DevCard selectedDevCard = devCardTypes.get(rand.nextInt(devCardTypes.size()));
		deck.addSpecifiedDevCard(selectedDevCard, -1);

		Resources bank = game.getClientModel().getBank();
		Player p = model.getPlayers()[playerIndex];

		if(p.canBuyDevCard()){
			p.payForDevCard(bank);
			game.getClientModel().getLog().getLines().add(new MessageLine(p.getName() + " bought a devcard", p.getName()));

			if(selectedDevCard==DevCard.MONUMENT)
			{
				p.getOldDevCards().addSpecifiedDevCard(selectedDevCard, 1);	
			}
			else{
				p.getNewDevCards().addSpecifiedDevCard(selectedDevCard, 1);
			}
			deck.decSpecifiedDevCard(selectedDevCard);
		}

		game.getClientModel().increaseVersion();


	}


	@Override
	public JsonObject toJSON() {
		ModelToJSON toJSON = new ModelToJSON();
		return toJSON.getBuyDevCardCommand(playerIndex);
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
