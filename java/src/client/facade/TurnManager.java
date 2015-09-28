package client.facade;

import java.util.Random;

import client.models.mapdata.Hex;

public class TurnManager 
{

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
	public void endTurn(){}

}
