package server.commands.moves;

import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import server.commands.IMovesCommand;
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
	 */
	@Override
	public void execute() {
	
		if(willAccept){
		TradeOffer offer = game.getClientModel().getTradeOffer();
		
		Player offerer = game.getClientModel().getPlayers()[offer.getSender()];
		Player receiver = game.getClientModel().getPlayers()[offer.getReceiver()];
		
		offerer.getResources().addResource(ResourceType.BRICK, offer.getBrickCount(), receiver.getResources());
		offerer.getResources().addResource(ResourceType.ORE, offer.getOreCount(), receiver.getResources());
		offerer.getResources().addResource(ResourceType.SHEEP, offer.getSheepCount(), receiver.getResources());
		offerer.getResources().addResource(ResourceType.WHEAT, offer.getWheatCount(), receiver.getResources());
		offerer.getResources().addResource(ResourceType.WOOD, offer.getWoodCount(), receiver.getResources());
		}
		else{
			game.getClientModel().setTradeOffer(new TradeOffer());
		}
		game.getClientModel().increaseVersion();
	}

}
