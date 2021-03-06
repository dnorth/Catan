package server.exceptions;

/**
 * thrown when the client model sent from the client is invalid.
 */
public class InvalidClientModelException extends Exception  {

	public InvalidClientModelException() {
		super("Client Model is invalid.");
	}
	
}
