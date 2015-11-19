package server.commands.moves;

import client.models.Player;
import client.models.communication.MessageLine;
import server.Server;
import server.commands.IMovesCommand;
import server.exceptions.InvalidPlayerIndexException;
import server.model.ServerData;
import server.model.ServerGame;

// TODO: Auto-generated Javadoc
/**
 * The Class SendChatCommand.
 */
public class SendChatCommand implements IMovesCommand {
	ServerGame game;
	int playerIndex;
	String content;
	

	
	public SendChatCommand(ServerGame game, int playerIndex, String content) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.content = content;
	}



	/**
	 *  Sends a chat.
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws InvalidPlayerIndexException {
		
		Player p =game.getClientModel().getPlayers()[playerIndex];

		game.getClientModel().checkPlayerIndex(playerIndex);
		String playerName = game.getClientModel().getPlayers()[playerIndex].getName();
		game.getClientModel().getChat().getLines().add(new MessageLine(content,playerName ));
		game.getClientModel().getLog().getLines().add(new MessageLine(p.getName() + " sent a chat", p.getName()));
		game.getClientModel().increaseVersion();
	}

}
