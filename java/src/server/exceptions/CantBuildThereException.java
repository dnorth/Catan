package server.exceptions;

public class CantBuildThereException extends Exception {
	public CantBuildThereException() {
		super("You cannot build this structure at this location.");
	}
}
