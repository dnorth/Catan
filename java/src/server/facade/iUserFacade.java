package server.facade;

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
	 */
	public int loginUser(String username, String password);
	
	/**
	 * Register user.
	 *
	 * @param username the username
	 * @param password the password
	 * @return "Success" or an error if failed
	 */
	public int registerUser(String username, String password);
}
