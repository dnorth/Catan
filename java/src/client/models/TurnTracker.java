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
	
	public TurnTracker() {
		this.currentTurn = 0;
		this.longestRoad = -1;
		this.largestArmy = -1;
		this.status = "FirstRound";
	}
	
	public int getCurrentTurn() {
		return currentTurn;
	}
	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	public void nextPlayerTurn() {
		this.currentTurn++;
		if(this.currentTurn == 4) {
			if(this.status.equals("FirstRound")) {
				this.currentTurn = 3;
			}
			else {
				this.currentTurn = 0;
			}
		}
	}
	public void previousPlayerTurn() { //for use with setup stage 2
		this.currentTurn--;
		if(this.currentTurn == -1) {
			this.currentTurn = 0;
		}
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
	@Override
	public String toString() {
		return "\tTurnTracker [currentTurn=" + currentTurn + ", status=" + status + ", longestRoad=" + longestRoad
				+ ", largestArmy=" + largestArmy + "]";
	}
}