package server.facade;

public interface iUserFacade {
	public String loginUser(String username, String password);
	public String registerUser(String username, String password);
}
