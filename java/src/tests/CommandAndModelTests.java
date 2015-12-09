package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.models.DevCards;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.communication.MessageList;
import client.models.mapdata.Board;
import client.models.mapdata.Hex;
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
import server.exceptions.InvalidLoginException;
import server.exceptions.InvalidMaritimeTradeException;
import server.exceptions.InvalidPlayerException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidRollException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NoTradeOfferedException;
import server.exceptions.NotYourTurnException;
import server.exceptions.OutOfPiecesException;
import server.exceptions.RobberIsAlreadyThereException;
import server.exceptions.UsernameAlreadyTakenException;
import server.facade.MovesFacade;
import server.model.ServerData;
import server.model.ServerGame;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class CommandAndModelTests {

	@Test
	public void createGameTest1AllTrue() {
		System.out.println("Testing createGame command with all true values.");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
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
		System.out.println("Testing createGame command with all false values.");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
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
		System.out.println("Testing createGame command with empty name.");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
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
		System.out.println("Testing createGame command with mixed values");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
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
	public void registerTest() {
		System.out.println("Testing register command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		IUserCommand command = new RegisterCommand(serverData, "Tommy", "Williams");
		try {
			command.execute();
			assertEquals(serverData.getNextUserID(), 9);
			assertEquals(serverData.getPlayerID("Tommy", "Williams"), 8);
		} catch (UsernameAlreadyTakenException | InvalidLoginException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void loginTest() {
		System.out.println("Testing login command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		IUserCommand command = new RegisterCommand(serverData, "Tommy", "Williams");
		try {
			command.execute();
			command = new LoginCommand(serverData, "Tommy", "Williams");
			command.execute();
			assertEquals(serverData.getNextUserID(), 9);
			assertEquals(serverData.getPlayerID("Tommy", "Williams"), 8);
		} catch (UsernameAlreadyTakenException | InvalidLoginException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void joinTest() {
		System.out.println("Testing join command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		IUserCommand registerCommand = new RegisterCommand(serverData, "Tommy", "Williams");
		IGamesCommand createGameCommand = new CreateCommand(serverData, false, false, false, "CreateUnitTest2", 3);
		IGamesCommand joinCommand = new JoinCommand(serverData, 8, 3, "orange");
		try {
			registerCommand.execute();
			createGameCommand.execute();
			joinCommand.execute();
			assertEquals(serverData.getNextUserID(), 9);
			assertEquals(serverData.getPlayerID("Tommy", "Williams"), 8);
			assertEquals(serverData.getGameByID(3).getClientModel().getPlayers()[0].getColor(), "orange");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void getModelTest() {
		System.out.println("Testing model command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		IUserCommand registerCommand = new RegisterCommand(serverData, "Tommy", "Williams");
		IGamesCommand createGameCommand = new CreateCommand(serverData, false, false, false, "CreateUnitTest2", 3);
		IGamesCommand joinCommand = new JoinCommand(serverData, 8, 3, "orange");
		IGameCommand modelCommand = new ModelCommand();
		try {
			registerCommand.execute();
			createGameCommand.execute();
			joinCommand.execute();
			modelCommand.execute();
			assertEquals(serverData.getNextUserID(), 9);
			assertEquals(serverData.getPlayerID("Tommy", "Williams"), 8);
			assertEquals(serverData.getGameByID(3).getClientModel().getPlayers()[0].getColor(), "orange");
		} catch (Exception e) {
			System.out.println("Exception Message: \n" + e.getMessage());
//			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void listGamesTest() {
		System.out.println("Testing list command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		IUserCommand registerCommand = new RegisterCommand(serverData, "Tommy", "Williams");
		IGamesCommand listGamesCommand = new ListCommand();
		try {
			registerCommand.execute();
			listGamesCommand.execute();
			assertEquals(serverData.getNextUserID(), 9);
			assertEquals(serverData.getPlayerID("Tommy", "Williams"), 8);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void sendChatTestCheckMessages() {
		System.out.println("Testing sendChat command, checking messages");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		IMovesCommand command = new SendChatCommand(serverData.getGameByID(1), 0, "This is a chat from player 0", 1);
		
		try {
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 1, "This is a chat from player 1", 2);
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 2, "This is a chat from player 2", 3);
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 3, "This is a chat from player 3", 4);
			command.execute();
			MessageList messageList = serverData.getGameByID(1).getClientModel().getChat();
			assertTrue(messageList.getLines().size() == 4);
			assertEquals(messageList.getLines().get(0).getMessage(), "This is a chat from player 0");
			assertEquals(messageList.getLines().get(1).getMessage(), "This is a chat from player 1");
			assertEquals(messageList.getLines().get(2).getMessage(), "This is a chat from player 2");
			assertEquals(messageList.getLines().get(3).getMessage(), "This is a chat from player 3");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void sendChatTestCheckSources() {
		System.out.println("Testing sendChat command, checking sources");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		IMovesCommand command = new SendChatCommand(serverData.getGameByID(1), 0, "This is a chat from player 0", 1);
		try {
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 1, "This is a chat from player 1", 2);
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 2, "This is a chat from player 2", 3);
			command.execute();
			command = new SendChatCommand(serverData.getGameByID(1), 3, "This is a chat from player 3", 4);
			command.execute();
			MessageList messageList = serverData.getGameByID(1).getClientModel().getChat();
			assertTrue(messageList.getLines().size() == 4);
			assertEquals(messageList.getLines().get(0).getSource(), "Pete");
			assertEquals(messageList.getLines().get(1).getSource(), "Quinn");
			assertEquals(messageList.getLines().get(2).getSource(), "Scott");
			assertEquals(messageList.getLines().get(3).getSource(), "Hannah");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void preGameRoadAndSettlementPlacementTest() {
		System.out.println("Testing placeRoad and placeSettlement commands, first two rounds");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		ServerGame game = serverData.getGameByID(0);
		Board board = game.getClientModel().getBoard();
		
		try {
			IMovesCommand command = new BuildRoadCommand(game, 0, new EdgeLocation(1,-2, "NW"), true, 1);
			command.execute();
			command = new BuildSettlementCommand(game, 0, new VertexLocation(new HexLocation(1,-2), VertexDirection.NorthWest), true, 1);
			command.execute();
			command = new FinishTurnCommand(game, 0, -1);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 1);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "FirstRound");
			assertEquals(game.getClientModel().getPlayers()[0].getRoads(), 14);
			assertEquals(game.getClientModel().getPlayers()[0].getSettlements(), 4);
			assertEquals(board.getRoads()[0].getOwner(), 0);
			assertEquals(board.getSettlements().get(0).getOwner(), 0);
			
			
			command = new BuildRoadCommand(game, 1, new EdgeLocation(2,-2, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 1, new VertexLocation(new HexLocation(2,-2), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 1, -1);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 2);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "FirstRound");
			assertEquals(game.getClientModel().getPlayers()[1].getRoads(), 14);
			assertEquals(game.getClientModel().getPlayers()[1].getSettlements(), 4);
			assertEquals(board.getRoads()[1].getOwner(), 1);
			assertEquals(board.getSettlements().get(1).getOwner(), 1);
			
			command = new BuildRoadCommand(game, 2, new EdgeLocation(-1,-1, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 2, new VertexLocation(new HexLocation(-1,-1), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 2, -1);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 3);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "FirstRound");
			assertEquals(game.getClientModel().getPlayers()[2].getRoads(), 14);
			assertEquals(game.getClientModel().getPlayers()[2].getSettlements(), 4);
			assertEquals(board.getRoads()[2].getOwner(), 2);
			assertEquals(board.getSettlements().get(2).getOwner(), 2);
			
			command = new BuildRoadCommand(game, 3, new EdgeLocation(0,-1, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 3, new VertexLocation(new HexLocation(0,-1), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 3, -1);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 3);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "SecondRound");
			assertEquals(game.getClientModel().getPlayers()[3].getRoads(), 14);
			assertEquals(game.getClientModel().getPlayers()[3].getSettlements(), 4);
			assertEquals(board.getRoads()[3].getOwner(), 3);
			assertEquals(board.getSettlements().get(3).getOwner(), 3);
			
			command = new BuildRoadCommand(game, 3, new EdgeLocation(-1,0, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 3, new VertexLocation(new HexLocation(-1,0), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 3, -1);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 2);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "SecondRound");
			assertEquals(game.getClientModel().getPlayers()[3].getRoads(), 13);
			assertEquals(game.getClientModel().getPlayers()[3].getSettlements(), 3);
			assertEquals(board.getRoads()[4].getOwner(), 3);
			assertEquals(board.getSettlements().get(4).getOwner(), 3);
			
			command = new BuildRoadCommand(game, 2, new EdgeLocation(-2,0, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 2, new VertexLocation(new HexLocation(-2,0), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 2, -1);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 1);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "SecondRound");
			assertEquals(game.getClientModel().getPlayers()[2].getRoads(), 13);
			assertEquals(game.getClientModel().getPlayers()[2].getSettlements(), 3);
			assertEquals(board.getRoads()[5].getOwner(), 2);
			assertEquals(board.getSettlements().get(5).getOwner(), 2);

			command = new BuildRoadCommand(game, 1, new EdgeLocation(2,-1, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 1, new VertexLocation(new HexLocation(2,-1), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 1, -1);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 0);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "SecondRound");
			assertEquals(game.getClientModel().getPlayers()[1].getRoads(), 13);
			assertEquals(game.getClientModel().getPlayers()[1].getSettlements(), 3);
			assertEquals(board.getRoads()[6].getOwner(), 1);
			assertEquals(board.getSettlements().get(6).getOwner(), 1);
	
			command = new BuildRoadCommand(game, 0, new EdgeLocation(1,-1, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 0, new VertexLocation(new HexLocation(1,-1), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 0, -1);
			command.execute();
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 0);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Rolling");
			assertEquals(game.getClientModel().getPlayers()[0].getRoads(), 13);
			assertEquals(game.getClientModel().getPlayers()[0].getSettlements(), 3);
			assertEquals(board.getRoads()[7].getOwner(), 0);
			assertEquals(board.getSettlements().get(7).getOwner(), 0);
			
			

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void preGameRoadPlacement(ServerData serverData) {
		System.out.println("Testing first two rounds buildRoad command");
		ServerGame game = serverData.getGameByID(0);
		try {
			IMovesCommand command = new BuildRoadCommand(game, 0, new EdgeLocation(1,-2, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 0, new VertexLocation(new HexLocation(1,-2), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 0, -1);
			command.execute();
					
			command = new BuildRoadCommand(game, 1, new EdgeLocation(2,-2, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 1, new VertexLocation(new HexLocation(2,-2), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 1, -1);
			command.execute();
			
			command = new BuildRoadCommand(game, 2, new EdgeLocation(-1,-1, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 2, new VertexLocation(new HexLocation(-1,-1), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 2, -1);
			command.execute();
	
			command = new BuildRoadCommand(game, 3, new EdgeLocation(0,-1, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 3, new VertexLocation(new HexLocation(0,-1), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 3, -1);
			command.execute();
			
			command = new BuildRoadCommand(game, 3, new EdgeLocation(1,-1, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 3, new VertexLocation(new HexLocation(1,-1), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 3, -1);
			command.execute();
			
			command = new BuildRoadCommand(game, 2, new EdgeLocation(2,-1, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 2, new VertexLocation(new HexLocation(2,-1), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 2, -1);
			command.execute();

			command = new BuildRoadCommand(game, 1, new EdgeLocation(-2,0, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 1, new VertexLocation(new HexLocation(-2,0), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 1, -1);
			command.execute();
	
			command = new BuildRoadCommand(game, 0, new EdgeLocation(-1,0, "NW"), true, -1);
			command.execute();
			command = new BuildSettlementCommand(game, 0, new VertexLocation(new HexLocation(-1,0), VertexDirection.NorthWest), true, -1);
			command.execute();
			command = new FinishTurnCommand(game, 0, -1);
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
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	
	
	@Test
	public void rollNumberTurnTrackerTest() {
		System.out.println("Testing rollNumber command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		assertEquals(game.getClientModel().getTurnTracker().getCurrentTurn(), 0);
		assertEquals(game.getClientModel().getTurnTrackerStatus(), "Rolling");
		try {
			IMovesCommand command = new RollNumberCommand(game, 0, 4, -1);
			command.execute();
			assertEquals(game.getClientModel().getTurnTracker().getCurrentTurn(), 0);
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command = new FinishTurnCommand(game, 0, -1);
			command.execute();
			assertEquals(game.getClientModel().getBank().getBrickCount(), 19);
			assertEquals(game.getClientModel().getBank().getWoodCount(), 22);
			assertEquals(game.getClientModel().getBank().getOreCount(), 20);
			assertEquals(game.getClientModel().getBank().getWheatCount(), 24);
			assertEquals(game.getClientModel().getBank().getSheepCount(), 22);

			//Add checks for resources here based on initialized game
			assertTrue(game.getClientModel().getTurnTracker().getCurrentTurn() == 1);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void useMonumentTest() {
		System.out.println("Testing monument command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,3,0,0,0));
		IMovesCommand command = new MonumentCommand(game, 0, -1);
		IMovesCommand command2 = new RollNumberCommand(game, 0, 4, -1);
		try {
			command2.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 5);
			assertEquals(game.getClientModel().getPlayers()[0].getMonuments(), 3);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void useYearOfPlentyBothBrickTest() {
		System.out.println("Testing yearOfPlenty command, both brick");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,0,1));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new YearOfPlentyCommand(game, 0, ResourceType.BRICK, ResourceType.BRICK, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 2);
			Resources playersResources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(0,4,1,0,1);
			assertEquals(playersResources, toCompare); //wood, brick, sheep, wheat, ore
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void useYearOfPlentyBothWoodTest() {
		System.out.println("Testing yearOfPlenty, both wood");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,0,1));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new YearOfPlentyCommand(game, 0, ResourceType.WOOD, ResourceType.WOOD, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 2);
			Resources playersResources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(2,2,1,0,1);
			assertEquals(playersResources, toCompare); //wood, brick, sheep, wheat, ore
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void useYearOfPlentyBothOreTest() {
		System.out.println("Testing yearOfPlenty, both ore");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,0,1));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new YearOfPlentyCommand(game, 0, ResourceType.ORE, ResourceType.ORE, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 2);
			Resources playersResources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(0,2,1,0,3);
			assertEquals(playersResources, toCompare); //wood, brick, sheep, wheat, ore
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void useYearOfPlentyBothWheatTest() {
		System.out.println("Testing yearOfPlenty, both wheat");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,0,1));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new YearOfPlentyCommand(game, 0, ResourceType.WHEAT, ResourceType.WHEAT, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 2);
			Resources playersResources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(0,2,1,2,1);
			assertEquals(playersResources, toCompare); //wood, brick, sheep, wheat, ore
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void useYearOfPlentyBothSheepTest() {
		System.out.println("Testing yearOfPlenty, both sheep");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,0,1));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new YearOfPlentyCommand(game, 0, ResourceType.SHEEP, ResourceType.SHEEP, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 2);
			Resources playersResources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(0,2,3,0,1);
			assertEquals(playersResources, toCompare); //wood, brick, sheep, wheat, ore
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void useYearOfPlentyMixed1Test() {
		System.out.println("Testing yearOfPlenty, mixed 1");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,0,1));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new YearOfPlentyCommand(game, 0, ResourceType.BRICK, ResourceType.ORE, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 2);
			Resources playersResources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(0,3,1,0,2);
			assertEquals(playersResources, toCompare); //wood, brick, sheep, wheat, ore
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void useYearOfPlentyMixed2Test() {
		System.out.println("Testing yearOfPlenty, mixed 2");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,0,1));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new YearOfPlentyCommand(game, 0, ResourceType.WHEAT, ResourceType.SHEEP, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 2);
			Resources playersResources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(0,2,2,1,1);
			assertEquals(playersResources, toCompare); //wood, brick, sheep, wheat, ore
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void useSoldierTest() {
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,1,0));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 6, -1);
		IMovesCommand command = new SoldierCommand(game, 0, 1, new HexLocation(-2, 0), -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 2);
			Resources playersResources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(0,1,1,0,2);
			assertEquals(playersResources, toCompare); //wood, brick, sheep, wheat, ore
			assertEquals(game.getClientModel().getPlayers()[0].getSoldiers(), 1);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void biggestArmyTest() {
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,3,0));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 6, -1);
		IMovesCommand command = new SoldierCommand(game, 0, 1, new HexLocation(-2, 0), -1); //2
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			game.getClientModel().getPlayers()[0].incSoldiers();
			game.getClientModel().getPlayers()[0].incSoldiers();
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 4);
			Resources playersResources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(0,1,1,0,2);
			assertEquals(playersResources, toCompare); //wood, brick, sheep, wheat, ore
			assertEquals(game.getClientModel().getPlayers()[0].getSoldiers(), 3);
			assertEquals(game.getClientModel().getTurnTracker().getLargestArmy(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void robPlayerTest() {
		System.out.println("Testing robPlayer commad");
		ServerData serverData  = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 7, -1);
		IMovesCommand command = new RobPlayerCommand(game, 0, 1, new HexLocation(-2,0), -1);
		try {
			rollCommand.execute();
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[1].getResources(), new Resources(0,0,0,0,0));
			assertEquals(game.getClientModel().getPlayers()[0].getResources(), new Resources(0,1,1,0,2));
			client.models.mapdata.HexLocation hex = game.getClientModel().getBoard().getRobber();
			assertEquals(hex, new client.models.mapdata.HexLocation(-2,0));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void offerAndAcceptTradeTest() {
		System.out.println("Testing tradeOffer and tradeAccept commands, accept=true");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,1,0));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new OfferTradeCommand(game, 0, new Resources(0,2,0,0,-1), 1, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			command = new AcceptTradeCommand(game, 1, true, -1);
			command.execute();
			Resources player0Resources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(0,0,1,0,2);
			assertEquals(player0Resources, toCompare); //wood, brick, sheep, wheat, ore
			Resources player1Resources = game.getClientModel().getPlayers()[1].getResources();
			Resources toCompare2 = new Resources(0,3,0,0,0);
			assertEquals(player1Resources, toCompare2);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void offerAndDontAcceptTradeTest() {
		System.out.println("Testing tradeOffer and tradeAccept comands, accept=false");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,0,1,0));
		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new OfferTradeCommand(game, 0, new Resources(0,2,0,0,-1), 1, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			command = new AcceptTradeCommand(game, 1, false, -1);
			command.execute();
			Resources player0Resources = game.getClientModel().getPlayers()[0].getResources();
			Resources toCompare = new Resources(0,2,1,0,1);
			assertEquals(player0Resources, toCompare); //wood, brick, sheep, wheat, ore
			Resources player1Resources = game.getClientModel().getPlayers()[1].getResources();
			Resources toCompare2 = new Resources(0,1,0,0,1);
			assertEquals(player1Resources, toCompare2);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void buildCityWithNoResourcesTest() {
		System.out.println("Testing buildCity command with no resources");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);

		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new BuildCityCommand(game, 0, new VertexLocation(new HexLocation(1, -2), VertexDirection.NorthWest), -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getCities(), 4);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void buildCityWithNoSettlementTest() {
		System.out.println("Testing buildCity command with no settlement");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setResources(new Resources(5,5,5,5,5));

		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new BuildCityCommand(game, 0, new VertexLocation(new HexLocation(1, -2), VertexDirection.NorthEast), -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertTrue(false);
		} catch (CantBuildThereException e) {
			assertEquals(game.getClientModel().getPlayers()[0].getCities(), 4);		
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(true);
		}	
	}
	
	@Test
	public void validBuildCityTest() {
		System.out.println("Testing valid buildCity command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setResources(new Resources(5,5,5,5,5));

		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new BuildCityCommand(game, 0, new VertexLocation(new HexLocation(1, -2), VertexDirection.NorthWest), -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getCities(), 3);
			assertEquals(game.getClientModel().getPlayers()[0].getSettlements(), 5);
			assertEquals(game.getClientModel().getPlayers()[0].getVictoryPoints(), 3);
			assertEquals(game.getClientModel().getPlayers()[0].getResources(), new Resources(5, 6, 5, 3, 2));
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(true);
		}	
	}
	
	@Test
	public void buildRoadTest() {
		System.out.println("Testing buildRoad command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setResources(new Resources(5,5,5,5,5));

		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new BuildRoadCommand(game, 0, new EdgeLocation(new HexLocation(1, -2), EdgeDirection.North), false, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getBoard().getRoads().length, 9);
			assertEquals(game.getClientModel().getPlayers()[0].getRoads(), 12);
			assertEquals(game.getClientModel().getBoard().getRoads()[8].getOwner(), 0);
			assertEquals(game.getClientModel().getPlayers()[0].getResources(), new Resources(4,5,5,5,5));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void buildSettlementTest() {
		System.out.println("Testing buildSettlement command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setResources(new Resources(5,5,5,5,5));

		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new BuildRoadCommand(game, 0, new EdgeLocation(new HexLocation(-1, 0), EdgeDirection.SouthWest), false, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			command = new BuildSettlementCommand(game, 0, new VertexLocation(new HexLocation(-1,0), VertexDirection.SouthWest), false, -1);
			command.execute();

			assertEquals(game.getClientModel().getBoard().getSettlements().size(), 9);
			assertEquals(game.getClientModel().getBoard().getRoads().length, 9);
			assertEquals(game.getClientModel().getPlayers()[0].getRoads(), 12);
			assertEquals(game.getClientModel().getPlayers()[0].getSettlements(), 2);
			assertEquals(game.getClientModel().getBoard().getRoads()[8].getOwner(), 0);
			assertEquals(game.getClientModel().getBoard().getSettlements().get(8).getOwner(), 0);
			assertEquals(game.getClientModel().getPlayers()[0].getResources(), new Resources(3,4,4,4,5));
			} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void buyDevCardTest() {
		System.out.println("Testing buyDevCardTest command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setResources(new Resources(5,5,5,5,5));

		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new BuyDevCardCommand(game, 0, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getDeck().getTotalCount(), 23);
			Player p = game.getClientModel().getPlayers()[0];
			assertEquals(p.getOldDevCards().getTotalCount() + p.getNewDevCards().getTotalCount(), 1); 

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void discardCardsTest() {
		System.out.println("Testing discard command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setResources(new Resources(4,4,4,4,4));
		game.getClientModel().getPlayers()[1].setResources(new Resources(10,8,6,4,2));
		game.getClientModel().getPlayers()[2].setResources(new Resources(0,0,0,0,0));


		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 7, -1);
		IMovesCommand command = new DiscardCardsCommand(game, 0, new Resources(2,2,2,2,2), -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Discarding");
			command.execute();
			command = new DiscardCardsCommand(game, 1, new Resources(5,4,3,2,1), -1);
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getResources(), new Resources(2,2,2,2,2));
			assertEquals(game.getClientModel().getPlayers()[1].getResources(), new Resources(5,4,3,2,1));
			assertEquals(game.getClientModel().getPlayers()[2].getResources(), new Resources(0,0,0,0,0));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void finishTurnTest() {
		System.out.println("Testing finishTurn command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);

		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new FinishTurnCommand(game, 0, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Rolling");

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void maritimeTradeTest() {
		System.out.println("Testing maritimeTrade command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setResources(new Resources(4,4,4,4,4));

		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		IMovesCommand command = new MaritimeTradeCommand(game, 0, 4, "wood", "brick", -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getResources(), new Resources(0,6,4,4,4));  //wood brick sheep wheat ore
			command = new MaritimeTradeCommand(game, 0, 3, "brick", "sheep", -1);
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getResources(), new Resources(0,3,5,4,4));
			command = new MaritimeTradeCommand(game, 0, 2, "sheep", "wheat", -1);
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getResources(), new Resources(0,3,3,5,4));
			command = new MaritimeTradeCommand(game, 0, 2, "wheat", "ore", -1);
			command.execute();
			assertEquals(game.getClientModel().getPlayers()[0].getResources(), new Resources(0,3,3,3,5));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	@Test
	public void roadBuildingTest() {
		System.out.println("Testing roadBuilding command");
		ServerData serverData = new ServerData("Null");
		serverData.initServerData();
		Server.setServerData(serverData);
		this.preGameRoadPlacement(serverData);
		ServerGame game = serverData.getGameByID(0);
		game.getClientModel().getPlayers()[0].setOldDevCards(new DevCards(0,0,1,0,0));

		IMovesCommand rollCommand = new RollNumberCommand(game, 0, 4, -1);
		EdgeLocation e1 = new EdgeLocation(new HexLocation(-1,0), EdgeDirection.SouthWest);
		EdgeLocation e2 = new EdgeLocation(new HexLocation(-1,0), EdgeDirection.South);
		IMovesCommand command = new RoadBuildingCommand(game, 0, e1, e2, -1);
		try {
			rollCommand.execute();
			assertEquals(game.getClientModel().getTurnTrackerStatus(), "Playing");
			command.execute();
			assertEquals(game.getClientModel().getBoard().getRoads().length, 10);
			assertEquals(game.getClientModel().getPlayers()[0].getRoads(), 11);
			assertEquals(game.getClientModel().getPlayers()[0].getOldDevCards().getRoadBuildingCount(), 0);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}	
	}
	
	private void SetPlayerResources(ServerGame game){
		
		Resources resources = new Resources(10,10,10,10,10);
		for(Player p: game.getClientModel().getPlayers()){
			p.setResources(resources);
		}
	}
}
