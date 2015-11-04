package server.facade;

import client.data.GameInfo;

// TODO: Auto-generated Javadoc
/**
 * The Interface iGamesFacade.
 */
public interface iGamesFacade {
	
	/**
	 * List games.
	 *
	 * @return the game info[]
	 */
	public GameInfo[] listGames();
	
	/**
	 * Creates the game.
	 *
	 * @param randomTiles the random tiles
	 * @param randomNumbers the random numbers
	 * @param randomPorts the random ports
	 * @param name the name
	 * @return the game info
	 */
	public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);
	
	/**
	 * Join game.
	 *
	 * @param id the id
	 * @param color the color
	 * @return the string
	 */
	public String joinGame(int id, String color);
}
