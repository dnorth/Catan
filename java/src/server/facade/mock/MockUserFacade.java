package server.facade.mock;

import server.exceptions.InvalidLoginException;
import server.exceptions.UsernameAlreadyTakenException;
import server.facade.iUserFacade;

public class MockUserFacade implements iUserFacade {

	@Override
	public int loginUser(String username, String password)
			throws InvalidLoginException {

		return 12345;
	}

	@Override
	public int registerUser(String username, String password)
			throws UsernameAlreadyTakenException {
		return 12345;
	}

}
