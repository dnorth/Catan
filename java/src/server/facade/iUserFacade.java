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
	public String loginUser(String username, String password);
	
	/**
	 * Register user.
	 *
	 * @param username the username
	 * @param password the password
	 * @return "Success" or an error if failed
	 */
	public String registerUser(String username, String password);
}
