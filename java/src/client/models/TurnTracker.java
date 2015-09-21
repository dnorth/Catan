package client.models;

/**
 * TurnTracker keeps track of which player is active in current turn
 */

public class TurnTracker{
	/**
	 * Number of active player in turn
	 */
	int currentTurn;
	
	/**
	 * Indicator of what is happening now.  For example: Rolling, Robbing, Playing
	 */
	String status;
	/**
	 * Player index who owns longest road, -1 if no one
	 */
	int longestRoad;
	/**
	 * Player index who owns largest army, -1 if no one
	 */
	int largestArmy;
}