package server.exceptions;

/**
 * 
 * thrown when the JSON sent from client is invalid.
 *
 */
public class InvalidJsonException extends Exception  {

	public InvalidJsonException() {
		super("The given JSON is invalid.");
	}
	
}
