package server.exceptions;

public class AlreadyPlayedDevCardException extends Exception{
	public AlreadyPlayedDevCardException() {
		super("A dev card has already been played.");
	}
}
