package server.commands.moves;

import client.models.Player;
import client.models.Resources;
import server.commands.IMovesCommand;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidStatusException;
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
	 * @throws InsufficientResourcesException 
	 */
	@Override
	public void execute() throws InvalidStatusException, InsufficientResourcesException {
		
		if(game.getClientModel().getTurnTracker().getStatus().equals("Discarding")==false)
		{throw new InvalidStatusException();}
		
		
		Player player = game.getClientModel().getPlayers()[playerIndex];
		Resources playerResources = game.getClientModel().getPlayers()[playerIndex].getResources();
		
		
		if(player.needsToDiscard()==false)
		{throw new InvalidStatusException();}
		
		if(player.getResources().hasSpecifiedResources(discardedCards)==false){
			throw new InsufficientResourcesException();
		}
		
		boolean lastDiscard=true;
		for(Player p : game.getClientModel().getPlayers()){
			if(p.getPlayerIndex()==playerIndex)
			{
				continue;
			}
			else if (p.getResources().getTotalCount()>7){
				lastDiscard=false;
			}
			
		}
		
		if(lastDiscard){
			game.getClientModel().getTurnTracker().setStatus("Robbing");
		}
		

			
		Resources bank = game.getClientModel().getBank();
		
		playerResources.subtractResource(ResourceType.BRICK, discardedCards.getBrickCount(), bank);
		playerResources.subtractResource(ResourceType.ORE, discardedCards.getOreCount(), bank);
		playerResources.subtractResource(ResourceType.SHEEP, discardedCards.getSheepCount(), bank);
		playerResources.subtractResource(ResourceType.WHEAT, discardedCards.getWheatCount(), bank);
		playerResources.subtractResource(ResourceType.WOOD, discardedCards.getWoodCount(), bank);
		game.getClientModel().increaseVersion();
		
	}

}
