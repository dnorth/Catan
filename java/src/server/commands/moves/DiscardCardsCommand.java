package server.commands.moves;

import client.models.Resources;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.definitions.ResourceType;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscardCardsCommand.
 */
public class DiscardCardsCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	Resources discardedCards;

	public DiscardCardsCommand(ServerGame game, int playerIndex,
			Resources discardedCards) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.discardedCards = discardedCards;
	}



	/**
	 * Discards Cards.
	 */
	@Override
	public void execute() {
		
		Resources playerResources = game.getClientModel().getPlayers()[playerIndex].getResources();
		
		if(playerResources.getTotalCount()<=7)
		{return;}
		
		Resources bank = game.getClientModel().getBank();
		
		playerResources.subtractResource(ResourceType.BRICK, discardedCards.getBrickCount(), bank);
		playerResources.subtractResource(ResourceType.ORE, discardedCards.getOreCount(), bank);
		playerResources.subtractResource(ResourceType.SHEEP, discardedCards.getSheepCount(), bank);
		playerResources.subtractResource(ResourceType.WHEAT, discardedCards.getWheatCount(), bank);
		playerResources.subtractResource(ResourceType.WOOD, discardedCards.getWoodCount(), bank);
	}

}
