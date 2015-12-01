package server.commands.moves;

import client.models.ClientModel;
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
		ClientModel model = game.getClientModel();
		model.checkPlayerIndex(playerIndex);
		String playerName = model.getPlayers()[playerIndex].getName();
		model.getChat().getLines().add(new MessageLine(content,playerName ));
		model.getLog().getLines().add(new MessageLine(p.getName() + " sent a chat", p.getName()));
		model.increaseVersion();
	}
}
