package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.facade.PlayerManager;
import client.models.DevCards;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;

public class CanDoTests {

	public void printPlayerResources(int playerNumber, Player p)
	{
		System.out.println("Testing player "+ Integer.toString(playerNumber) + "with "+ p.getResources().toString());
	}
	
	public void printPlayerDevCards(int playerNumber, DevCards d)
	{
		System.out.println("Testing player "+ Integer.toString(playerNumber) + "with "+ d.toString());
	}

	@Test
	public void testCanBuyRoad() {

		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});

		p1.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuyRoad(0));

		p1.setResources(new Resources(1,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuyRoad(0));

		p1.setResources(new Resources(1,1,0,0,0));
		printPlayerResources(1,p1);
		assertTrue(pm.canBuyRoad(0));

		p2.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuyRoad(1));

		p2.setResources(new Resources(1,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuyRoad(1));

		p2.setResources(new Resources(1,1,0,0,0));
		printPlayerResources(2,p2);
		assertTrue(pm.canBuyRoad(1));
	}

	@Test
	public void testCanPlaceRoadAtLocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanOfferTrade() {
		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});

		p1.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.hasResources(0));

		p1.setResources(new Resources(1,0,0,0,0));
		printPlayerResources(1,p1);
		assertTrue(pm.hasResources(0));

		p1.setResources(new Resources(0,0,0,0,1));
		printPlayerResources(1,p1);
		assertTrue(pm.hasResources(0));

		p2.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.hasResources(1));

		p2.setResources(new Resources(1,0,0,0,0));
		printPlayerResources(2,p2);
		assertTrue(pm.hasResources(1));

		p2.setResources(new Resources(0,0,0,0,1));
		printPlayerResources(2,p2);
		assertTrue(pm.hasResources(1));
	}

	@Test
	public void testCanAcceptTrade() {
		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});

		TradeOffer to = new TradeOffer(1,0,new Resources(1,0,0,0,1), new Resources(1,0,0,0,0));
		TradeOffer to2 = new TradeOffer(0,1,new Resources(1,0,0,0,1), new Resources(1,0,0,0,0));

		System.out.println("TradeOffer is one wood from sender for one wood and one ore from receiver");

		p1.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.hasSpecifiedResources(0, to));

		p1.setResources(new Resources(1,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.hasSpecifiedResources(0, to));


		p1.setResources(new Resources(1,0,0,0,1));
		printPlayerResources(1,p1);
		assertTrue(pm.hasSpecifiedResources(0, to));

		p2.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.hasSpecifiedResources(1, to2));

		p2.setResources(new Resources(1,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.hasSpecifiedResources(1, to2));

		p2.setResources(new Resources(1,0,0,0,1));
		printPlayerResources(2,p2);
		assertTrue(pm.hasSpecifiedResources(1, to2));

	}

	@Test
	public void testCanMaritimeTrade() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanBuySettlement(){
		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});

		p1.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuySettlement(0));

		p1.setResources(new Resources(1,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuySettlement(0));

		p1.setResources(new Resources(1,1,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuySettlement(0));

		p1.setResources(new Resources(1,1,1,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuySettlement(0));

		p1.setResources(new Resources(1,1,1,1,0));
		printPlayerResources(1,p1);
		assertTrue(pm.canBuySettlement(0));

		p2.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuySettlement(1));

		p2.setResources(new Resources(1,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuySettlement(1));

		p2.setResources(new Resources(1,1,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuySettlement(1));

		p2.setResources(new Resources(1,1,1,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuySettlement(1));

		p2.setResources(new Resources(1,1,1,1,0));
		printPlayerResources(2,p2);
		assertTrue(pm.canBuySettlement(1));
	}

	@Test
	public void testCanUpgradeSettlement() {
		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});

		p1.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuySettlement(0));

		p1.setResources(new Resources(1,0,0,0,1));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuySettlement(0));

		p1.setResources(new Resources(1,1,0,1,2));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuySettlement(0));

		p1.setResources(new Resources(1,1,1,2,3));
		printPlayerResources(1,p1);
		assertTrue(pm.canBuySettlement(0));

		p1.setResources(new Resources(1,1,1,3,4));
		printPlayerResources(1,p1);
		assertTrue(pm.canBuySettlement(0));

		p2.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuySettlement(1));

		p2.setResources(new Resources(1,0,0,0,1));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuySettlement(1));

		p2.setResources(new Resources(1,1,0,1,2));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuySettlement(1));


		p2.setResources(new Resources(1,1,1,2,3));
		printPlayerResources(2,p2);
		assertTrue(pm.canBuySettlement(1));

		p2.setResources(new Resources(1,1,1,3,4));
		printPlayerResources(2,p2);
		assertTrue(pm.canBuySettlement(1));



	}

	@Test
	public void testCanBuyDevCard() {
		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});

		p1.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuySettlement(0));

		p1.setResources(new Resources(0,0,1,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuySettlement(0));

		p1.setResources(new Resources(0,0,1,1,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuySettlement(0));

		p1.setResources(new Resources(0,0,1,1,1));
		printPlayerResources(1,p1);
		assertTrue(pm.canBuySettlement(0));


		p2.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuySettlement(0));

		p2.setResources(new Resources(0,0,1,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuySettlement(1));

		p2.setResources(new Resources(0,0,1,1,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuySettlement(1));

		p2.setResources(new Resources(0,0,1,1,1));
		printPlayerResources(2,p2);
		assertTrue(pm.canBuySettlement(1));

	}

	@Test
	public void testCanPlayDevCard() {

		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});

		printPlayerDevCards(1,p1.getCurrentDevCards());
		p1.setCurrentDevCards(new DevCards(0,0,0,0,0));
		assertFalse(pm.canPlayDevCard(0));

		printPlayerDevCards(1,p1.getCurrentDevCards());
		p1.setCurrentDevCards(new DevCards(1,0,0,0,0));
		assertTrue(pm.canPlayDevCard(0));
		
		printPlayerDevCards(1,p1.getCurrentDevCards());
		p1.setCurrentDevCards(new DevCards(0,1,0,0,0));
		assertTrue(pm.canPlayDevCard(0));
		
		printPlayerDevCards(1,p1.getCurrentDevCards());
		p1.setCurrentDevCards(new DevCards(0,0,1,0,0));
		assertTrue(pm.canPlayDevCard(0));
		
		printPlayerDevCards(1,p1.getCurrentDevCards());
		p1.setCurrentDevCards(new DevCards(0,0,0,1,0));
		assertTrue(pm.canPlayDevCard(0));
		
		printPlayerDevCards(1,p1.getCurrentDevCards());
		p1.setCurrentDevCards(new DevCards(0,0,0,0,1));
		assertTrue(pm.canPlayDevCard(0));
		
		printPlayerDevCards(2,p2.getCurrentDevCards());
		p2.setCurrentDevCards(new DevCards(0,0,0,0,0));
		assertFalse(pm.canPlayDevCard(1));

		printPlayerDevCards(2,p2.getCurrentDevCards());
		p2.setCurrentDevCards(new DevCards(1,0,0,0,0));
		assertTrue(pm.canPlayDevCard(1));
		
		printPlayerDevCards(2,p2.getCurrentDevCards());
		p2.setCurrentDevCards(new DevCards(0,1,0,0,0));
		assertTrue(pm.canPlayDevCard(1));
		
		printPlayerDevCards(2,p2.getCurrentDevCards());
		p2.setCurrentDevCards(new DevCards(0,0,1,0,0));
		assertTrue(pm.canPlayDevCard(1));
		
		printPlayerDevCards(2,p2.getCurrentDevCards());
		p2.setCurrentDevCards(new DevCards(0,0,0,1,0));
		assertTrue(pm.canPlayDevCard(1));
		
		printPlayerDevCards(2,p2.getCurrentDevCards());
		p2.setCurrentDevCards(new DevCards(0,0,0,0,1));
		assertTrue(pm.canPlayDevCard(1));
	}
	

}
