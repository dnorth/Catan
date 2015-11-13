package server.facade;

import server.commands.user.LoginCommand;
import server.commands.user.RegisterCommand;
import server.exceptions.InvalidLoginException;
import server.exceptions.UsernameAlreadyTakenException;
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
	 * @throws InvalidLoginException 
	 */
	@Override
	public int loginUser(String username, String password) throws InvalidLoginException {
		LoginCommand command = new LoginCommand(serverData, username, password);
		command.execute();
		return serverData.getPlayerID(username, password);
	}

	/**
	 * Facade for the command user/register
	 * @throws UsernameAlreadyTakenException 
	 */
	@Override
	public int registerUser(String username, String password) throws UsernameAlreadyTakenException {
		RegisterCommand command = new RegisterCommand(serverData, username, password);
		command.execute();
		return serverData.getPlayerID(username, password);
	}

}
