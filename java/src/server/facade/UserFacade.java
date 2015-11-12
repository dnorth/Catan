package server.facade;

import server.commands.user.LoginCommand;
import server.commands.user.RegisterCommand;
import server.model.ServerData;

// TODO: Auto-generated Javadoc
/**
 * The Class UserFacade.
 */
public class UserFacade implements iUserFacade {

	ServerData serverData;
	
	public UserFacade(ServerData serverData) {
		this.serverData = serverData;
	}

	/**
	 * Facade for the command user/login
	 */
	@Override
	public int loginUser(String username, String password) {
		return new LoginCommand(serverData, username, password).execute();
	}

	/**
	 * Facade for the command user/register
	 */
	@Override
	public int registerUser(String username, String password) {
		return new RegisterCommand(serverData, username, password).execute();
	}

}
