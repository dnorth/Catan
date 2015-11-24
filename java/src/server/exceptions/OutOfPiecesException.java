package server.exceptions;

public class OutOfPiecesException extends Exception {
	public OutOfPiecesException() {
		super("You do not have enough pieces to build this.");
	}
}
