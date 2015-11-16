package server.commands.moves;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import client.models.ClientModel;
import client.models.DevCards;
import client.models.Player;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.definitions.DevCard;

// TODO: Auto-generated Javadoc
/**
 * The Class BuyDevCardCommand.
 */
public class BuyDevCardCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	
	
	public BuyDevCardCommand(ServerGame game, int playerIndex) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
	}


	/**
	 *  Buys a DevCard.
	 */
	@Override
	public void execute() {
		Random rand = new Random();
		ClientModel model = game.getClientModel();
		DevCards deck = model.getDeck();
		List<DevCard> devCardTypes = deck.getDevCardTypes();
		
		DevCard selectedDevCard = devCardTypes.get(rand.nextInt(devCardTypes.size()));
		deck.addSpecifiedDevCard(selectedDevCard, -1);
		
		Player p = model.getPlayers()[playerIndex];
		p.getNewDevCards().addSpecifiedDevCard(selectedDevCard, 1);
	}

}
