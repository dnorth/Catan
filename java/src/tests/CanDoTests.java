package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.facade.PlayerManager;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;

public class CanDoTests {

	@Test
	public void testCanBuyRoad() {
		
		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});
		
		System.out.println("Testing player 1 with "+p1.getResources().toString());
		p1.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.canBuyRoad(0));
		
		System.out.println("Testing player 1 with "+p1.getResources().toString());
		p1.setResources(new Resources(1,0,0,0,0));
		assertFalse(pm.canBuyRoad(0));

		System.out.println("Testing player 1 with "+p1.getResources().toString());
		p1.setResources(new Resources(1,1,0,0,0));
		assertTrue(pm.canBuyRoad(0));
		
		System.out.println("Testing player 2 with "+p2.getResources().toString());
		p2.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.canBuyRoad(1));
		
		System.out.println("Testing player 2 with"+p2.getResources().toString());
		p2.setResources(new Resources(1,0,0,0,0));
		assertFalse(pm.canBuyRoad(1));
		
		System.out.println("Testing player 2 with"+p2.getResources().toString());
		p2.setResources(new Resources(1,1,0,0,0));
		assertTrue(pm.canBuyRoad(1));
	}

	@Test
	public void testCanPlaceRoadAtLocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanOfferTrade() {
		Player p = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p,p2});
		
		System.out.println("Testing player 1 with No Resources");
		p.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.hasResources(0));
		
		System.out.println("Testing player 1 with 1 wood");
		p.setResources(new Resources(1,0,0,0,0));
		assertTrue(pm.hasResources(0));
		
		System.out.println("Testing player 1 with 1 ore");
		p.setResources(new Resources(0,0,0,0,1));
		assertTrue(pm.hasResources(0));
		
		System.out.println("Testing player 2 with No Resources");
		p2.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.hasResources(1));
		
		System.out.println("Testing player 2 with 1 wood");
		p2.setResources(new Resources(1,0,0,0,0));
		assertTrue(pm.hasResources(1));
		
		System.out.println("Testing player 2 with 1 ore");
		p2.setResources(new Resources(0,0,0,0,1));
		assertTrue(pm.hasResources(1));
	}

	@Test
	public void testCanAcceptTrade() {
		Player p = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p,p2});
		
		TradeOffer to = new TradeOffer(1,0,new Resources(1,0,0,0,1), new Resources(1,0,0,0,0));
		TradeOffer to2 = new TradeOffer(0,1,new Resources(1,0,0,0,1), new Resources(1,0,0,0,0));
		
		System.out.println("TradeOffer is one wood from sender for one wood and one ore from receiver");
		
		System.out.println("Testing player 1 with No Resources");
		p.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.hasSpecifiedResources(0, to));
		
		System.out.println("Testing player 1 with 1 wood");
		p.setResources(new Resources(1,0,0,0,0));
		assertFalse(pm.hasSpecifiedResources(0, to));
		
		System.out.println("Testing player 1 with 1 wood, ore");
		p.setResources(new Resources(1,0,0,0,1));
		assertTrue(pm.hasSpecifiedResources(0, to));
		
		System.out.println("Testing player 2 with No Resources");
		p2.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.hasSpecifiedResources(1, to2));
		
		System.out.println("Testing player 2 with 1 wood");
		p2.setResources(new Resources(1,0,0,0,0));
		assertFalse(pm.hasSpecifiedResources(1, to2));
		
		System.out.println("Testing player 2 with 1 wood, ore");
		p2.setResources(new Resources(1,0,0,0,1));
		assertTrue(pm.hasSpecifiedResources(1, to2));
		
	}

	@Test
	public void testCanMaritimeTrade() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanBuySettlement(){
		Player p = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p,p2});
		
		System.out.println("Testing player 1 with no resources");
		p.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.canBuySettlement(0));
		
		System.out.println("Testing player 1 with 1 wood");
		p.setResources(new Resources(1,0,0,0,0));
		assertFalse(pm.canBuySettlement(0));

		System.out.println("Testing player 1 with 1 wood, brick");
		p.setResources(new Resources(1,1,0,0,0));
		assertFalse(pm.canBuySettlement(0));
		
		System.out.println("Testing player 1 with 1 wood, brick, sheep");
		p.setResources(new Resources(1,1,1,0,0));
		assertFalse(pm.canBuySettlement(0));
		
		System.out.println("Testing player 1 with 1 wood, brick, sheep, wheat");
		p.setResources(new Resources(1,1,1,1,0));
		assertTrue(pm.canBuySettlement(0));
		
		System.out.println("Testing player 2 with no resources");
		p2.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.canBuySettlement(1));
		
		System.out.println("Testing player 2 with 1 wood");
		p2.setResources(new Resources(1,0,0,0,0));
		assertFalse(pm.canBuySettlement(1));

		System.out.println("Testing player 2 with 1 wood, brick");
		p2.setResources(new Resources(1,1,0,0,0));
		assertFalse(pm.canBuySettlement(1));
		
		System.out.println("Testing player 1 with 1 wood, brick, sheep");
		p2.setResources(new Resources(1,1,1,0,0));
		assertFalse(pm.canBuySettlement(1));
		
		System.out.println("Testing player 1 with 1 wood, brick, sheep, wheat");
		p2.setResources(new Resources(1,1,1,1,0));
		assertTrue(pm.canBuySettlement(1));
	}

	@Test
	public void testCanUpgradeSettlement() {
		Player p = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p,p2});
		
		System.out.println("Testing player 1 with no resources");
		p.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.canBuySettlement(0));
		
		System.out.println("Testing player 1 with 1 wood, ore");
		p.setResources(new Resources(1,0,0,0,1));
		assertFalse(pm.canBuySettlement(0));

		System.out.println("Testing player 1 with 1 wood, brick, wheat, 2 ore");
		p.setResources(new Resources(1,1,0,1,2));
		assertFalse(pm.canBuySettlement(0));
		
		System.out.println("Testing player 1 with 1 wood, brick, sheep, 3 wheat, ore");
		p.setResources(new Resources(1,1,1,2,3));
		assertTrue(pm.canBuySettlement(0));
		
		System.out.println("Testing player 1 with 1 wood and 1 brick and one sheep and one wheat and 3 ore");
		p.setResources(new Resources(1,1,1,3,4));
		assertTrue(pm.canBuySettlement(0));
		
		System.out.println("Testing player 2 with no resources");
		p2.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.canBuySettlement(1));
		
		System.out.println("Testing player 2 with 1 wood");
		p2.setResources(new Resources(1,0,0,0,0));
		assertFalse(pm.canBuySettlement(1));

		System.out.println("Testing player 2 with 1 wood and 1 brick");
		p2.setResources(new Resources(1,1,0,0,0));
		assertFalse(pm.canBuySettlement(1));
		
		System.out.println("Testing player 2 with 1 wood and 1 brick and one sheep");
		p2.setResources(new Resources(1,1,1,0,0));
		assertFalse(pm.canBuySettlement(1));
		
		System.out.println("Testing player 2 with 1 wood and 1 brick and one sheep and one wheat");
		p2.setResources(new Resources(1,1,1,1,0));
		assertTrue(pm.canBuySettlement(1));
		
		
		
	}

	@Test
	public void testCanBuyDevCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanPlayDevCard() {
		fail("Not yet implemented");
	}

}
