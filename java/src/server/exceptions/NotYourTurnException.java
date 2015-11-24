package server.exceptions;

public class NotYourTurnException extends Exception{
	public NotYourTurnException() {
		super("It's not your turn! Please be patient.");
	}
}
