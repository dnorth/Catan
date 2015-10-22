package client.facade;

import java.util.Random;

import client.models.ClientModel;
import client.models.TurnTracker;
import client.models.mapdata.Hex;

public class TurnManager 
{
	private TurnTracker turnTracker;

	public TurnManager(TurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}

	public TurnTracker getTurnTracker() {
		return turnTracker;
	}

	public void setTurnTracker(TurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}

	/**
	 * rolls the dice.
	 * @return a random number between 2 and 12.
	 */
	public int rollDice()
	{
		Random rand = new Random();

		int  die1 = rand.nextInt(6) + 1;
		int  die2 = rand.nextInt(6) + 1;
		return die1+die2;
	}

	/**
	 * ends player's turn.
	 */
	public void endTurn()
	{
		int currentTurn = this.turnTracker.getCurrentTurn();
		currentTurn += 1;
		if (currentTurn > 3) currentTurn = 0;
		this.turnTracker.setCurrentTurn(currentTurn);
	}
	
	public void updatePointersToNewModel(ClientModel newModel) {
		this.turnTracker = newModel.getTurnTracker();
	}
}
