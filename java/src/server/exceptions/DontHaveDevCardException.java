package server.exceptions;

public class DontHaveDevCardException extends Exception {
	public DontHaveDevCardException() {
		super("You do not own this dev card.");
	}
}
