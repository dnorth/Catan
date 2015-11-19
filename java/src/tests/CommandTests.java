package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.communication.MessageList;
import client.models.mapdata.Board;
import server.Server;
import server.commands.*;
import server.commands.games.*;
import server.commands.game.*;
import server.commands.moves.*;
import server.commands.user.*;
import server.exceptions.AlreadyPlayedDevCardException;
import server.exceptions.CantBuildThereException;
import server.exceptions.DontHaveDevCardException;
import server.exceptions.GameFullException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidMaritimeTradeException;
import server.exceptions.InvalidPlayerException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidRollException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NoTradeOfferedException;
import server.exceptions.NotYourTurnException;
import server.exceptions.OutOfPiecesException;
import server.exceptions.RobberIsAlreadyThereException;
import server.facade.MovesFacade;
import server.model.ServerData;
import server.model.ServerGame;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class CommandTests {

	@Test
	public void createGameTest1AllTrue() {
		ServerData serverData = new ServerData();
		Server.setServerData(serverData);
		ServerGame game = serverData.getGameByID(0);
		assertEquals(game.getClientModel().getBank().getBrickCount(), 24);
		assertEquals(game.getClientModel().getBank().getOreCount(), 24);
		assertEquals(game.getClientModel().getBank().getWheatCount(), 24);
		assertEquals(game.getClientModel().getBank().getWoodCount(), 24);
		assertEquals(game.getClientModel().getBank().getSheepCount(), 24);
		IGamesCommand command = new CreateCommand(serverData, true, true, true, "CreateUnitTest1", 3);
		try {
			command.execute();
			assertTrue(serverData.getGames().size() == 4);
			assertEquals(serverData.getGameByID(3).getTitle(), "CreateUnitTest1");
		} catch (GameFullException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createGameTest2AllFalse() {
		ServerData serverData = new ServerData();
		Server.setServerData(serverData);
		IGamesCommand command = new CreateCommand(serverData, false, false, false, "CreateUnitTest2", 3);
		try {
			command.execute();
			assertTrue(serverData.getGames().size() == 4);
			assertEquals(serverData.getGameByID(3).getTitle(), "CreateUnitTest2");
		} catch (GameFullException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createGameTest3EmptyName() {
		ServerData serverData = new ServerData();
		Server.setServerData(serverData);
		IGamesCommand command = new CreateCommand(serverData, true, false, true, "", 3);
		try {
			command.execute();
			assertTrue(serverData.getGames().size() == 4);
			assertEquals(serverData.getGameByID(3).getTitle(),"");
		} catch (GameFullException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createGameTest4Mix() {
		ServerData serverData = new ServerData();
		Server.setServerData(serverData);
		IGamesCommand command = new CreateCommand(serverData, false, true, false, "CreateUnitTest4", 6);
		try {
			command.execute();
			assertTrue(serverData.getGames().size() == 4);
			assertEquals(serverData.getGameByID(6).getTitle(), "CreateUnitTest4");
		} catch (GameFullException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void sendChatTestCheckMessages() {
		ServerData serverData = new ServerData();
		Server.setServerData(serverData);
		IMovesCommand command = new SendChatCommand(serverData.getGameByID(1), 0, "This is a chat from player 0");
		
		try {
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 1, "This is a chat from player 1");
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 2, "This is a chat from player 2");
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 3, "This is a chat from player 3");
			command.execute();
			MessageList messageList = serverData.getGameByID(1).getClientModel().getChat();
			assertTrue(messageList.getLines().size() == 4);
			assertEquals(messageList.getLines().get(0).getMessage(), "This is a chat from player 0");
			assertEquals(messageList.getLines().get(1).getMessage(), "This is a chat from player 1");
			assertEquals(messageList.getLines().get(2).getMessage(), "This is a chat from player 2");
			assertEquals(messageList.getLines().get(3).getMessage(), "This is a chat from player 3");
		} catch (InsufficientResourcesException | CantBuildThereException
				| NotYourTurnException | OutOfPiecesException
				| NoTradeOfferedException | InvalidPlayerException
				| InvalidMaritimeTradeException
				| RobberIsAlreadyThereException | InvalidRollException
				| DontHaveDevCardException | AlreadyPlayedDevCardException
				| InvalidPlayerIndexException  e) {
			e.printStackTrace();
			assertTrue(false);
		}
		catch (InvalidStatusException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void sendChatTestCheckSources() {
		ServerData serverData = new ServerData();
		Server.setServerData(serverData);
		IMovesCommand command = new SendChatCommand(serverData.getGameByID(1), 0, "This is a chat from player 0");
		try {
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 1, "This is a chat from player 1");
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 2, "This is a chat from player 2");
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 3, "This is a chat from player 3");
			command.execute();
			MessageList messageList = serverData.getGameByID(1).getClientModel().getChat();
			assertTrue(messageList.getLines().size() == 4);
			assertEquals(messageList.getLines().get(0).getSource(), "Pete");
			assertEquals(messageList.getLines().get(1).getSource(), "Quinn");
			assertEquals(messageList.getLines().get(2).getSource(), "Scott");
			assertEquals(messageList.getLines().get(3).getSource(), "Hannah");
		} catch (InsufficientResourcesException | CantBuildThereException
				| NotYourTurnException | OutOfPiecesException
				| NoTradeOfferedException | InvalidPlayerException
				| InvalidMaritimeTradeException
				| RobberIsAlreadyThereException | InvalidRollException
				| DontHaveDevCardException | AlreadyPlayedDevCardException
				| InvalidPlayerIndexException  e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (InvalidStatusException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void preGameRoadAndSettlementPlacementTest() {
		ServerData serverData = new ServerData();
		Server.setServerData(serverData);
		ServerGame game = serverData.getGameByID(0);
		Board board = game.getClientModel().getBoard();
		
		try {
			IMovesCommand command = new BuildRoadCommand(game, 0, new EdgeLocation(1,-2, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 0, new VertexLocation(new HexLocation(1,-2), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 0);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 1);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "FirstRound");
			assertEquals(game.getClientModel().getPlayers()[0].getRoads(), 14);
			assertEquals(game.getClientModel().getPlayers()[0].getSettlements(), 4);
			assertEquals(board.getRoads()[0].getOwner(), 0);
			assertEquals(board.getSettlements().get(0).getOwner(), 0);
			
			
			command = new BuildRoadCommand(game, 1, new EdgeLocation(2,-2, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 1, new VertexLocation(new HexLocation(2,-2), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 1);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 2);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "FirstRound");
			assertEquals(game.getClientModel().getPlayers()[1].getRoads(), 14);
			assertEquals(game.getClientModel().getPlayers()[1].getSettlements(), 4);
			assertEquals(board.getRoads()[1].getOwner(), 1);
			assertEquals(board.getSettlements().get(1).getOwner(), 1);
			
			command = new BuildRoadCommand(game, 2, new EdgeLocation(-1,-1, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 2, new VertexLocation(new HexLocation(-1,-1), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 2);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 3);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "FirstRound");
			assertEquals(game.getClientModel().getPlayers()[2].getRoads(), 14);
			assertEquals(game.getClientModel().getPlayers()[2].getSettlements(), 4);
			assertEquals(board.getRoads()[2].getOwner(), 2);
			assertEquals(board.getSettlements().get(2).getOwner(), 2);
			
			command = new BuildRoadCommand(game, 3, new EdgeLocation(0,-1, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 3, new VertexLocation(new HexLocation(0,-1), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 3);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 3);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "SecondRound");
			assertEquals(game.getClientModel().getPlayers()[3].getRoads(), 14);
			assertEquals(game.getClientModel().getPlayers()[3].getSettlements(), 4);
			assertEquals(board.getRoads()[3].getOwner(), 3);
			assertEquals(board.getSettlements().get(3).getOwner(), 3);
			
			command = new BuildRoadCommand(game, 3, new EdgeLocation(-1,0, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 3, new VertexLocation(new HexLocation(-1,0), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 3);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 2);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "SecondRound");
			assertEquals(game.getClientModel().getPlayers()[3].getRoads(), 13);
			assertEquals(game.getClientModel().getPlayers()[3].getSettlements(), 3);
			assertEquals(board.getRoads()[4].getOwner(), 3);
			assertEquals(board.getSettlements().get(4).getOwner(), 3);
			
			command = new BuildRoadCommand(game, 2, new EdgeLocation(-2,0, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 2, new VertexLocation(new HexLocation(-2,0), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 2);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 1);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "SecondRound");
			assertEquals(game.getClientModel().getPlayers()[2].getRoads(), 13);
			assertEquals(game.getClientModel().getPlayers()[2].getSettlements(), 3);
			assertEquals(board.getRoads()[5].getOwner(), 2);
			assertEquals(board.getSettlements().get(5).getOwner(), 2);

			command = new BuildRoadCommand(game, 1, new EdgeLocation(2,-1, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 1, new VertexLocation(new HexLocation(2,-1), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 1);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 0);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "SecondRound");
			assertEquals(game.getClientModel().getPlayers()[1].getRoads(), 13);
			assertEquals(game.getClientModel().getPlayers()[1].getSettlements(), 3);
			assertEquals(board.getRoads()[6].getOwner(), 1);
			assertEquals(board.getSettlements().get(6).getOwner(), 1);
	
			command = new BuildRoadCommand(game, 0, new EdgeLocation(1,-1, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 0, new VertexLocation(new HexLocation(1,-1), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 0);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 0);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Rolling");
			assertEquals(game.getClientModel().getPlayers()[0].getRoads(), 13);
			assertEquals(game.getClientModel().getPlayers()[0].getSettlements(), 3);
			assertEquals(board.getRoads()[7].getOwner(), 0);
			assertEquals(board.getSettlements().get(7).getOwner(), 0);
			
			

		} catch (InsufficientResourcesException | CantBuildThereException
				| NotYourTurnException | OutOfPiecesException
				| NoTradeOfferedException | InvalidPlayerException
				| InvalidMaritimeTradeException
				| RobberIsAlreadyThereException | InvalidRollException
				| DontHaveDevCardException | AlreadyPlayedDevCardException
				| InvalidPlayerIndexException  e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (InvalidStatusException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void preGameRoadPlacement(ServerData serverData) {
		ServerGame game = serverData.getGameByID(0);
		Board board = game.getClientModel().getBoard();
		try {
			IMovesCommand command = new BuildRoadCommand(game, 0, new EdgeLocation(1,-2, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 0, new VertexLocation(new HexLocation(1,-2), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 0);
			command.execute();
					
			command = new BuildRoadCommand(game, 1, new EdgeLocation(2,-2, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 1, new VertexLocation(new HexLocation(2,-2), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 1);
			command.execute();
			
			command = new BuildRoadCommand(game, 2, new EdgeLocation(-1,-1, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 2, new VertexLocation(new HexLocation(-1,-1), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 2);
			command.execute();
	
			command = new BuildRoadCommand(game, 3, new EdgeLocation(0,-1, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 3, new VertexLocation(new HexLocation(0,-1), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 3);
			command.execute();
			
			command = new BuildRoadCommand(game, 3, new EdgeLocation(1,-1, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 3, new VertexLocation(new HexLocation(1,-1), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 3);
			command.execute();
			
			command = new BuildRoadCommand(game, 2, new EdgeLocation(2,-1, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 2, new VertexLocation(new HexLocation(2,-1), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 2);
			command.execute();

			command = new BuildRoadCommand(game, 1, new EdgeLocation(-2,0, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 1, new VertexLocation(new HexLocation(-2,0), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 1);
			command.execute();
	
			command = new BuildRoadCommand(game, 0, new EdgeLocation(-1,0, "NW"), true);
			command.execute();
			command = new BuildSettlementCommand(game, 0, new VertexLocation(new HexLocation(-1,0), VertexDirection.NorthWest), true);
			command.execute();
			command = new FinishTurnCommand(game, 0);
			command.execute();
			
			assertEquals(game.getClientModel().getBank().getBrickCount(), 22);
			assertEquals(game.getClientModel().getBank().getWoodCount(), 22);
			assertEquals(game.getClientModel().getBank().getOreCount(), 20);
			assertEquals(game.getClientModel().getBank().getWheatCount(), 24);
			assertEquals(game.getClientModel().getBank().getSheepCount(), 22);
			
			assertEquals(game.getClientModel().getPlayers()[0].getResources().getBrickCount(), 1);
			assertEquals(game.getClientModel().getPlayers()[0].getResources().getWoodCount(), 0);
			assertEquals(game.getClientModel().getPlayers()[0].getResources().getOreCount(), 1);
			assertEquals(game.getClientModel().getPlayers()[0].getResources().getWheatCount(), 0);
			assertEquals(game.getClientModel().getPlayers()[0].getResources().getSheepCount(), 1);
			
			assertEquals(game.getClientModel().getPlayers()[1].getResources().getBrickCount(), 0);
			assertEquals(game.getClientModel().getPlayers()[1].getResources().getWoodCount(), 0);
			assertEquals(game.getClientModel().getPlayers()[1].getResources().getOreCount(), 1);
			assertEquals(game.getClientModel().getPlayers()[1].getResources().getWheatCount(), 0);
			assertEquals(game.getClientModel().getPlayers()[1].getResources().getSheepCount(), 0);
			
			assertEquals(game.getClientModel().getPlayers()[2].getResources().getBrickCount(), 0);
			assertEquals(game.getClientModel().getPlayers()[2].getResources().getWoodCount(), 1);
			assertEquals(game.getClientModel().getPlayers()[2].getResources().getOreCount(), 1);
			assertEquals(game.getClientModel().getPlayers()[2].getResources().getWheatCount(), 0);
			assertEquals(game.getClientModel().getPlayers()[2].getResources().getSheepCount(), 1);
			
			assertEquals(game.getClientModel().getPlayers()[3].getResources().getBrickCount(), 1);
			assertEquals(game.getClientModel().getPlayers()[3].getResources().getWoodCount(), 1);
			assertEquals(game.getClientModel().getPlayers()[3].getResources().getOreCount(), 1);
			assertEquals(game.getClientModel().getPlayers()[3].getResources().getWheatCount(), 0);
			assertEquals(game.getClientModel().getPlayers()[3].getResources().getSheepCount(), 0);
		} catch (InsufficientResourcesException | CantBuildThereException
				| NotYourTurnException | OutOfPiecesException
				| NoTradeOfferedException | InvalidPlayerException
				| InvalidMaritimeTradeException
				| RobberIsAlreadyThereException | InvalidRollException
				| DontHaveDevCardException | AlreadyPlayedDevCardException
				| InvalidPlayerIndexException  e) {
			e.printStackTrace();
			assertTrue(false);
		} catch(InvalidStatusException e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	
	
	@Test
	public void rollNumberTurnTrackerTest() {
		ServerData serverData = new ServerData();
		Server.setServerData(serverData);
		preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		assertEquals(game.getClientModel().getTurnTracker().getCurrentTurn(), 0);
		assertEquals(game.getClientModel().getTurnTrackerStatus(), "Rolling");
		try {
			IMovesCommand command = new RollNumberCommand(game, 0, 4);
			command.execute();
			assertEquals(game.getClientModel().getTurnTracker().getCurrentTurn(), 0);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command = new FinishTurnCommand(game, 0);
			command.execute();
			assertNotEquals(game.getClientModel().getBank().getBrickCount(), 21);
			assertNotEquals(game.getClientModel().getBank().getWoodCount(), 22);
			assertNotEquals(game.getClientModel().getBank().getOreCount(), 20);
			assertNotEquals(game.getClientModel().getBank().getWheatCount(), 24);
			assertNotEquals(game.getClientModel().getBank().getSheepCount(), 22);

			//Add checks for resources here based on initialized game
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 1);
		} catch (InsufficientResourcesException | CantBuildThereException
				| NotYourTurnException | OutOfPiecesException
				| NoTradeOfferedException | InvalidPlayerException
				| InvalidMaritimeTradeException
				| RobberIsAlreadyThereException | InvalidRollException
				| DontHaveDevCardException | AlreadyPlayedDevCardException
				| InvalidPlayerIndexException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (InvalidStatusException e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	public void SetPlayerResources(ServerGame game){
		
		Resources resources = new Resources(10,10,10,10,10);
		for(Player p: game.getClientModel().getPlayers()){
			p.setResources(resources);
		}
	}
	@Test
	public void testDomesticTrade(){
		ServerData data = new ServerData();
		
		this.preGameRoadPlacement(data);
		ServerGame game = data.getGameByID(0);
		this.SetPlayerResources(game);
		MovesFacade facade = new MovesFacade();
		Player p1 = game.getClientModel().getPlayers()[0];
		Player p2 = game.getClientModel().getPlayers()[1];
		
		try{
			TradeOffer offer = new TradeOffer(0, 1, new Resources(50,1,2,3,4));
			game.getClientModel().getTurnTracker().setStatus("Playing");
		facade.offerTrade(0, 0, offer.getOffer(),1);
		assertTrue(false);
		}
		catch ( InvalidStatusException
				| NotYourTurnException 
				| InvalidPlayerIndexException e) {}
		
//		
//		try{
//			TradeOffer offer = new TradeOffer(0, 1, new Resources(0,1,2,3,4));
//			game.getClientModel().getTurnTracker().setStatus("Playing");
//		facade.offerTrade(0, 0, offer.getOffer(),1);
//		facade.acceptTrade(0, 1, true);
//		
//		System.out.println(x);
//		}
//		catch ( InvalidStatusException
//				| NotYourTurnException 
//				| InvalidPlayerIndexException e) {} catch (NoTradeOfferedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidPlayerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InsufficientResourcesException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		
		
		
	}

}
