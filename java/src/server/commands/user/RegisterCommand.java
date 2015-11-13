package server.commands.user;

import server.commands.IUserCommand;
import server.exceptions.UsernameAlreadyTakenException;
import server.model.ServerData;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterCommand.
 */
public class RegisterCommand implements IUserCommand {
	
	String username;
	String password;
	ServerData serverData;

	public RegisterCommand(ServerData serverData, String username, String password) {
		this.serverData = serverData;
		this.username = username;
		this.password = password;
	}

	/**
	 * Registers a user.
	 */
	
	@Override
	public void execute() throws UsernameAlreadyTakenException {
		if (serverData.addUser(username, password) == -1) throw new UsernameAlreadyTakenException();
	}

}
