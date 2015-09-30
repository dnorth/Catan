package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.facade.PlayerManager;
import client.models.Player;
import client.models.Resources;

public class CanDoTests {

	@Test
	public void testCanBuyRoad() {
		
		Player p = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p,p2});
		
		p.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.canBuyRoad(0));
		
		p.setResources(new Resources(1,0,0,0,0));
		assertFalse(pm.canBuyRoad(0));
		
		p.setResources(new Resources(1,1,0,0,0));
		assertTrue(pm.canBuyRoad(0));
		
		p2.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.canBuyRoad(1));
		
		p2.setResources(new Resources(1,0,0,0,0));
		assertFalse(pm.canBuyRoad(1));
		
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
		
		
		p.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.canBuyRoad(0));
		
		p.setResources(new Resources(1,0,0,0,0));
		assertTrue(pm.canBuyRoad(0));
		
		p.setResources(new Resources(1,0,0,0,0));
		assertTrue(pm.canBuyRoad(0));
		
		p2.setResources(new Resources(0,0,0,0,0));
		assertFalse(pm.canBuyRoad(1));
		
		p2.setResources(new Resources(1,0,0,0,0));
		assertFalse(pm.canBuyRoad(1));
		
		p2.setResources(new Resources(1,0,0,0,0));
		assertTrue(pm.canBuyRoad(1));
	}

	@Test
	public void testCanAcceptTrade() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanMaritimeTrade() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanBuySettlement() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanUpgradeSettlement() {
		fail("Not yet implemented");
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
