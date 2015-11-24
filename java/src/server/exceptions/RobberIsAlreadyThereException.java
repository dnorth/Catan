package server.exceptions;

public class RobberIsAlreadyThereException extends Exception {
	
	public RobberIsAlreadyThereException() {
		super("Must move the robber.");
	}
}
