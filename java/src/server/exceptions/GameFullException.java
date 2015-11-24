package server.exceptions;

public class GameFullException extends Exception {
	
	public GameFullException() {
		super("Cannot join game. It is currently full.");
	}
	
}
