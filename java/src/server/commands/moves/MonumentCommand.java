package server.commands.moves;

import client.models.Player;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.definitions.DevCard;

// TODO: Auto-generated Javadoc
/**
 * The Class MonumentCommand.
 */
public class MonumentCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;

	public MonumentCommand(ServerGame game, int playerIndex) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
	}

	/**
	 *  Plays a monument Card.
	 */
	@Override
	public void execute() {

		Player user = game.getClientModel().getPlayers()[playerIndex];
		if(user.getOldDevCards().hasMonument() || user.getNewDevCards().hasMonument()){
			user.incMonuments();

			if(user.getNewDevCards().hasMonument()){
				user.getNewDevCards().decSpecifiedDevCard(DevCard.MONUMENT);	
			}

			else{
				user.getOldDevCards().decSpecifiedDevCard(DevCard.MONUMENT);	
			}
		}
		game.getClientModel().increaseVersion();
	}
}
