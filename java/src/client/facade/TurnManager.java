package client.facade;

public class TurnManager 
{

	/**
	 * rolls the dice.
	 * @return a random number between 2 and 12.
	 */
	public int rollDice(){return 1;}
	
	/**
	 * delivers resources to players based on dice roll, calls moveRobber() and robPlayerCards() if the number is a 7.
	 * @param diceRoll
	 */
	public void getResources(int diceRoll){}
	
	/**
	 * moves the robber and steals a card from one of the surrounding players.
	 * @param playerIndex
	 */
	public void moveRobber(int playerIndex){}
	
	/**
	 * removes half of the cards of any player that has more than 7.
	 */
	public void robPlayerCards(){}
	
	/**
	 * initiates trade between two players.
	 * @param playerIndex1 
	 * @param playerIndex2
	 */
	public void startTradeOfferWithPlayer(int playerIndex1, int playerIndex2){}
	
	/**
	 * accepts or rejects trade offer.
	 * @param playerIndex1
	 * @param playerIndex2
	 */
	public void finishTradeOfferWithPlayer(int playerIndex1, int playerIndex2){}
	
	/**
	 * trade 4 resource cards with bank for any 1 resource or maritime trade.
	 * @param playerIndex
	 */
	public void tradeWithBank(int playerIndex){}

	/**
	 * pays for a road to be built.
	 * @param playerIndex
	 */
	public void buyRoad(int playerIndex){}
	/**
	 * buys road for player and places it.
	 * @param playerIndex
	 */
	public void buildRoad(int playerIndex){}
	
	/**
	 * builds settlement on a hex vertex.
	 * @param playerIndex
	 */
	public void buildSettlement(int playerIndex){}
	
	/**
	 * upgrades settlement to city.
	 * @param playerIndex
	 */
	public void upgradeSettlement(int playerIndex){}
	
	/**
	 * buys development card for player.
	 * @param playerIndex
	 */
	public void buyDevCard(int playerIndex){}
	
	/**
	 * chooses which dev card to use.
	 * @param playerIndex
	 */
	public void playDevCard(int playerIndex){}
	
	/**
	 * gives player two resource cards of their choice.
	 * @param playerIndex
	 */
	public void playYearOfPlenty(int playerIndex){}
	
	/**
	 * moves robber, but doesn't call robPlayerCards.
	 * @param playerIndex
	 */
	public void playSoldier(int playerIndex){moveRobber(playerIndex);}
	
	/**
	 * gives all of the selected resource to the player.
	 * @param playerIndex
	 */
	public void playMonopoly(int playerIndex){}
	
	/**
	 * adds monument to player.
	 * @param playerIndex
	 */
	public void playMonument(int playerIndex){}
	
	/**
	 * builds two roads for player.
	 * @param playerIndex
	 */
	public void playRoadBuilding(int playerIndex){buildRoad(playerIndex); buildRoad(playerIndex);}
	
}
