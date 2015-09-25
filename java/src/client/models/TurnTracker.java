package client.models;

/**
 * TurnTracker keeps track of which player is active in current turn
 */

public class TurnTracker{
	/**
	 * Number of active player in turn
	 */
	private int currentTurn;
	
	/**
	 * Indicator of what is happening now.  For example: Rolling, Robbing, Playing
	 */
	private String status;
	/**
	 * Player index who owns longest road, -1 if no one
	 */
	private int longestRoad;
	/**
	 * Player index who owns largest army, -1 if no one
	 */
	private int largestArmy;
	
	public int getCurrentTurn() {
		return currentTurn;
	}
	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getLongestRoad() {
		return longestRoad;
	}
	public void setLongestRoad(int longestRoad) {
		this.longestRoad = longestRoad;
	}
	public int getLargestArmy() {
		return largestArmy;
	}
	public void setLargestArmy(int largestArmy) {
		this.largestArmy = largestArmy;
	}
}