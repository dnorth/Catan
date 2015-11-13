package server.facade;

import server.exceptions.InvalidLoginException;
import server.exceptions.UsernameAlreadyTakenException;

// TODO: Auto-generated Javadoc
/**
 * The Interface iUserFacade.
 */
public interface iUserFacade {
	
	/**
	 * Login user.
	 *
	 * @param username the username
	 * @param password the password
	 * @return "Success" or an error if failed
	 * @throws InvalidLoginException 
	 */
	public int loginUser(String username, String password) throws InvalidLoginException;
	
	/**
	 * Register user.
	 *
	 * @param username the username
	 * @param password the password
	 * @return "Success" or an error if failed
	 * @throws UsernameAlreadyTakenException 
	 */
	public int registerUser(String username, String password) throws UsernameAlreadyTakenException;
}
