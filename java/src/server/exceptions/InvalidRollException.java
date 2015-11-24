package server.exceptions;

public class InvalidRollException extends Exception {
	public InvalidRollException() {
		super("Invalid Roll. (We're not using D&D dice, here!)");
	}
}
