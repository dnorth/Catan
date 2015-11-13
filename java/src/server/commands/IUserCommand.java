package server.commands;

import server.exceptions.InvalidLoginException;
import server.exceptions.UsernameAlreadyTakenException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IUserCommand.
 */
public interface IUserCommand {
	
	/**
	 * Execute.
	 * @return 
	 * @throws UsernameAlreadyTakenException 
	 * @throws InvalidLoginException 
	 */
	public void execute() throws UsernameAlreadyTakenException, InvalidLoginException;
}
