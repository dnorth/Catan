package server.commands;

import server.exceptions.GameFullException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IGamesCommand.
 */
public interface IGamesCommand {
	
	/**
	 * Execute.
	 * @throws GameFullException 
	 */
	public void execute() throws GameFullException;
}
