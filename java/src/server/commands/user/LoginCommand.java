package server.commands.user;

import server.commands.IUserCommand;
import server.exceptions.InvalidLoginException;
import server.model.ServerData;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginCommand.
 */
public class LoginCommand implements IUserCommand {

	ServerData serverData;
	String username;
	String password;
	
	public LoginCommand(ServerData serverData, String username, String password) {
		this.serverData = serverData;
		this.username = username;
		this.password = password;
	}

	/**
	 *  Logs in a user.
	 */
	@Override
	public void execute() throws InvalidLoginException {
		if (serverData.getPlayerID(username, password) == -1) throw new InvalidLoginException();
	}

}
