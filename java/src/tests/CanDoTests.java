package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.facade.BoardManager;
import client.facade.PlayerManager;
import client.models.DevCards;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.VertexObject;
import client.models.mapdata.Board;
import client.models.mapdata.EdgeLocation;
import client.models.mapdata.Hex;
import client.models.mapdata.HexLocation;
import client.models.mapdata.Port;
import client.models.mapdata.Road;

public class CanDoTests {

	BoardManager bm = new BoardManager(new Board());

	public void printPlayerResources(int playerNumber, Player p)
	{
		System.out.println("Testing player "+ Integer.toString(playerNumber) + " with "+ p.getResources().toString());
	}

	public void printPlayerDevCards(int playerNumber, DevCards d)
	{
		System.out.println("Testing player "+ Integer.toString(playerNumber) + " with "+ d.toString());
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
						new Port("Ore",new HexLocation(2,-2), "NE",2),
						new Port("All",new HexLocation(1,-2), "N",3),
						new Port("Wheat",new HexLocation(-1,-1), "N",2),
						new Port("Sheep",new HexLocation(-2,0), "NW",2),
						new Port("All",new HexLocation(-2,2), "NW",3),
						new Port("All",new HexLocation(-1,2), "SW",3),
						new Port("Wood",new HexLocation(0,2), "S",2),
						new Port("All",new HexLocation(1,1), "SE",3),
						new Port("Brick",new HexLocation(2,-1), "SE",2)
				});
		board.setRobber(board.getHexes()[0].getLocation());
		return board;
	}

	@Test
	public void testCanBuyRoad() {

		System.out.println("Testing canBuyRoad()");
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

		System.out.println();
	}

	@Test
	public void testCanPlaceRoadAtLocation() {
		System.out.println("Testing canPlaceRoadAtLocation()");

		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});
		Board board = createDefaultBoard();
		BoardManager bm = new BoardManager(board);


		System.out.println("Testing Player 1 with a settlement N trying to build a road SW on hex 0,0");
		board.setSettlements(new VertexObject[]{new VertexObject(0,new EdgeLocation(0,0,"NW"))});
		board.setRoads(new Road[]{});
		assertFalse(bm.canPlaceRoadAtLocation(0, new EdgeLocation(0,0,"SW")));

		System.out.println("Testing Player 1 with a settlement N trying to build a road NE on hex 0,0)");
		assertTrue(bm.canPlaceRoadAtLocation(0, new EdgeLocation(0,0,"N")));


		System.out.println("Testing Player 2 with a settlement N trying to build a road SW on hex 1,0");
		board.setSettlements(new VertexObject[]{new VertexObject(1,new EdgeLocation(1,0,"NW"))});
		board.setRoads(new Road[]{});
		assertFalse(bm.canPlaceRoadAtLocation(1, new EdgeLocation(1,0,"S")));

		System.out.println("Testing Player 2 with a settlement N trying to build a road NE on hex 1,0)");
		assertTrue(bm.canPlaceRoadAtLocation(1, new EdgeLocation(1,0,"NW")));
		System.out.println();
	}

	@Test
	public void testCanOfferTrade() {
		System.out.println("Testing canOfferTrade()");
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

		System.out.println();
	}

	@Test
	public void testCanAcceptTrade() {

		System.out.println("Testing canAcceptTrade()");
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

		System.out.println();
	}

//	@Test
//	public void testCanMaritimeTrade() {
//
//		System.out.println("Testing canMaritimeTrade()");
//		
////		Player p1 = new Player();
////		Player p2 = new Player();
////		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});
//		Board board = createDefaultBoard();
//		BoardManager bm = new BoardManager(board);
//
//
//		System.out.println("Testing Player index 0 with settlement N on a port trying to maritime trade");
//		board.setSettlements(new VertexObject[]{new VertexObject(0,new EdgeLocation(4,0,"N"))});
////		board.setRoads(new Road[]{});
//		assertFalse(bm.canPlaceRoadAtLocation(0, new EdgeLocation(0,0,"SW")));
//
//		System.out.println("Testing Player index 1 with no settlements on ports trying to maritime trade");
//		assertTrue(bm.canPlaceRoadAtLocation(0, new EdgeLocation(0,0,"NE")));
//
//
//		System.out.println("Testing Player 2 with a settlement N trying to build a road SW on hex 1,0");
//		board.setSettlements(new VertexObject[]{new VertexObject(1,new EdgeLocation(1,0,"N"))});
//		board.setRoads(new Road[]{});
//		assertFalse(bm.canPlaceRoadAtLocation(1, new EdgeLocation(1,0,"SW")));
//
//		System.out.println("Testing Player 2 with a settlement N trying to build a road NE on hex 1,0)");
//		assertTrue(bm.canPlaceRoadAtLocation(1, new EdgeLocation(1,0,"NE")));
//		System.out.println();
//	}

	@Test
	public void testCanBuySettlement(){
		System.out.println("Testing canBuySettlement()");

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

		System.out.println();
	}

	@Test
	public void testCanUpgradeSettlement() {
		System.out.println("Testing canUpgradeSettlement()");
		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});

		p1.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canUpgradeSettlement(0));

		p1.setResources(new Resources(1,0,0,0,1));
		printPlayerResources(1,p1);
		assertFalse(pm.canUpgradeSettlement(0));

		p1.setResources(new Resources(1,1,0,1,2));
		printPlayerResources(1,p1);
		assertFalse(pm.canUpgradeSettlement(0));

		p1.setResources(new Resources(1,1,1,2,3));
		printPlayerResources(1,p1);
		assertTrue(pm.canUpgradeSettlement(0));

		p1.setResources(new Resources(1,1,1,3,4));
		printPlayerResources(1,p1);
		assertTrue(pm.canUpgradeSettlement(0));

		p2.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canUpgradeSettlement(1));

		p2.setResources(new Resources(1,0,0,0,1));
		printPlayerResources(2,p2);
		assertFalse(pm.canUpgradeSettlement(1));

		p2.setResources(new Resources(1,1,0,1,2));
		printPlayerResources(2,p2);
		assertFalse(pm.canUpgradeSettlement(1));


		p2.setResources(new Resources(1,1,1,2,3));
		printPlayerResources(2,p2);
		assertTrue(pm.canUpgradeSettlement(1));

		p2.setResources(new Resources(1,1,1,3,4));
		printPlayerResources(2,p2);
		assertTrue(pm.canUpgradeSettlement(1));

		System.out.println();
	}

	@Test
	public void testCanBuyDevCard() {
		System.out.println("Testing canBuyDevCard()");

		Player p1 = new Player();
		Player p2 = new Player();
		PlayerManager pm = new PlayerManager(new Player[]{p1,p2});

		p1.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuyDevCard(0));

		p1.setResources(new Resources(0,0,1,0,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuyDevCard(0));

		p1.setResources(new Resources(0,0,1,1,0));
		printPlayerResources(1,p1);
		assertFalse(pm.canBuyDevCard(0));

		p1.setResources(new Resources(0,0,1,1,1));
		printPlayerResources(1,p1);
		assertTrue(pm.canBuyDevCard(0));


		p2.setResources(new Resources(0,0,0,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuyDevCard(1));

		p2.setResources(new Resources(0,0,1,0,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuyDevCard(1));

		p2.setResources(new Resources(0,0,1,1,0));
		printPlayerResources(2,p2);
		assertFalse(pm.canBuyDevCard(1));

		p2.setResources(new Resources(0,0,1,1,1));
		printPlayerResources(2,p2);
		assertTrue(pm.canBuyDevCard(1));

		System.out.println();
	}

	@Test
	public void testCanPlayDevCard() {
		System.out.println("Testing canPlayDevCard()");
		Player p1 = new Player();
		Player p2 = new Player();

		p1.setCurrentDevCards(new DevCards(0,0,0,0,0));
		p2.setCurrentDevCards(new DevCards(0,0,0,0,0));

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

		System.out.println();
	}


}
