package client.facade;

import client.models.mapdata.Board;

public class BoardManager {
	private Board board;

	public BoardManager(Board board) {
		this.board = board;
	}
	
	/**
	 * Determines if the player has a settlement in a port.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player has a settlement in a port.
	 */
	
	public boolean canMaritimeTrade(int playerIndex){
		return true;
	}
}
