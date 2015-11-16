package server.facade;

import java.util.List;

import server.model.ServerGame;
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
	public List<ServerGame> listGames();
	
	/**
	 * Creates the game.
	 *
	 * @param randomTiles whether or not to randomize tiles
	 * @param randomNumbers whether or not to randomize numbers
	 * @param randomPorts whether or not to randomize ports
	 * @param name the name of the game
	 * @return the game info
	 */
	public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);
	
	/**
	 * Join game.
	 *
	 * @param id the id of the game being chosen
	 * @param color the color of the player
	 * @return "Success" or an error if failed
	 */
	public String joinGame(int pID, int gID, String color);
}
