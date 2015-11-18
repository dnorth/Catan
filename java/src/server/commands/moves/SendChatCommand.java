package server.commands.moves;

import client.models.communication.MessageLine;
import server.Server;
import server.commands.IMovesCommand;
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
	

	
	public SendChatCommand(ServerGame game, int playerIndex,
			String content) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.content = content;
	}



	/**
	 *  Sends a chat.
	 */
	@Override
	public void execute() {
		String playerName =game.getClientModel().getPlayers()[playerIndex].getName();
		game.getClientModel().getChat().getLines().add(new MessageLine(content,playerName ));
		game.getClientModel().increaseVersion();
	}

}
