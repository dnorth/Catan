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
	 * 
	 */
	String status;
	/**
	 * Current length of longest road
	 */
	int longestRoad;
	/**
	 * Current size of largest army
	 */
	int largestArmy;
}