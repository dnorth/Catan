package server.exceptions;

/**
 * thrown when the poller can't connect to the server.
 */
public class ConnectionErrorException extends Exception  {
	public ConnectionErrorException(String string) {
		super(string);
	}
	
	public ConnectionErrorException() {
		super("There was an error in the connection.");
	}
}
