package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.facade.BoardManager;
import client.facade.PlayerManager;
import client.models.DevCards;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.mapdata.Board;
import client.models.mapdata.EdgeLocation;
import client.models.mapdata.Hex;
import client.models.mapdata.HexLocation;
import client.models.mapdata.Port;

public class CanDoTests {
	
	BoardManager bm = new BoardManager(new Board());

	public void printPlayerResources(int playerNumber, Player p)
	{
		System.out.println("Testing player "+ Integer.toString(playerNumber) + "with "+ p.getResources().toString());
	}
	
	public void printPlayerDevCards(int playerNumber, DevCards d)
	{
		System.out.println("Testing player "+ Integer.toString(playerNumber) + "with "+ d.toString());
	}
	
	public Board createDefaultBoard()
	{
		Board board = new Board();
		board.setHexes(new Hex[]
				{
				new Hex(new HexLocation(0,0),-1 ),
				new Hex(new HexLocation(0,-1),"Brick",4 ),
				new Hex(new HexLocation(0,-2),"wood",11 ),
				
				new Hex(new HexLocation(1,0),"Wood",3 ),
				new Hex(new HexLocation(1,-1),"Ore",6 ),
				new Hex(new HexLocation(1,-2),"Sheep",12 ),
				new Hex(new HexLocation(1,1),"Brick",8 ),
				
				new Hex(new HexLocation(2,0),"Wheat",11 ),
				new Hex(new HexLocation(2,-1),"Brick",5 ),
				new Hex(new HexLocation(2,-2),"Wheat",9 ),
				new Hex(new HexLocation(2,1),"Sheep",10 ),
				new Hex(new HexLocation(2,2),"Ore",5 ),
				
				new Hex(new HexLocation(3,0),"Wood",4 ),
				new Hex(new HexLocation(3,-1),"Sheep",10 ),
				new Hex(new HexLocation(3,1),"Sheep",9 ),
				new Hex(new HexLocation(3,2),"Wheat",2 ),
				
				new Hex(new HexLocation(4,0),"Wheat",8 ),
				new Hex(new HexLocation(4,1),"Ore",3 ),
				new Hex(new HexLocation(4,2),"Wood",6 )
				});

		
		board.setPorts(new Port[]
				{
						new Port("",new HexLocation(0,-1), "Ore",2),
						new Port("",new HexLocation(0,-2), "All",3),
						new Port("",new HexLocation(1,1), "Wheat",2),
						new Port("",new HexLocation(1,-2), "Sheep",2),
						new Port("",new HexLocation(2,2), "All",3),
						new Port("",new HexLocation(3,-1), "All",3),
						new Port("",new HexLocation(3,2), "Wood",2),
						new Port("",new HexLocation(4,0), "All",3),
						new Port("",new HexLocation(4,1), "Brick",2)
				});
		board.setRobber(board.getHexes()[0].getLocation());
		return board;
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
		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});
		Board board = createDefaultBoard();
		BoardManager bm = new BoardManager(board);
		
		

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

		p1.setCurrentDevCards(new DevCards(0,0,0,0,0));
		printPlayerDevCards(1,p1.getCurrentDevCards());
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
		
		
		p2.setCurrentDevCards(new DevCards(0,0,0,0,0));		
		printPlayerDevCards(2,p2.getCurrentDevCards());
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
