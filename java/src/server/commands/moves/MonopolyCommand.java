package server.commands.moves;

import client.models.Player;
import client.models.Resources;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.definitions.ResourceType;

public class MonopolyCommand implements IMovesCommand  {

	ServerGame game;
	ResourceType type;
	int playerIndex;
	
	
	public MonopolyCommand(ServerGame game, ResourceType type,
			int playerIndex) {
		super();
		this.game = game;
		this.type = type;
		this.playerIndex = playerIndex;
	}

	/**
	 * plays a monopoly card.
	 */
	
	@Override
	public void execute() {
		Player[] players = game.getClientModel().getPlayers();
		Resources userResources = players[playerIndex].getResources();
		
		for(Player p : players){
		Resources resources = p.getResources();
		int count = resources.getAmountOfSpecificResource(type);
		
		resources.subtractResource(type, count, userResources);  //for each player, take all of the resourceType and give it to userResources
		}
		
		
	}

}
