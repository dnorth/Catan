package tests;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import server.proxy.ClientCommunicator;
import server.proxy.IProxy;
import server.proxy.MockServerProxy;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import client.models.Resources;

import com.google.gson.JsonObject;


public class ServerProxyTests {
	
	static Random randGenerator;
	static int rand;
	static IProxy proxy1;
	static IProxy proxy2;
	static String userCookie;
	static int gameId = 2;

	
	@BeforeClass
	public static void initRandom() {
		randGenerator = new Random();
		rand = randGenerator.nextInt(); //We're using a random number generator because we can't register the same user without restarting the server
		proxy1 = new ClientCommunicator();
		proxy2 = new MockServerProxy();
		
		JsonObject newUser = new JsonObject();
		newUser.addProperty("username", "Tommy" + rand);
		newUser.addProperty("password", "Williams");

		JsonObject responseObject = proxy1.userRegister(newUser);
		//System.out.println(responseObject.toString());
		
		userCookie = responseObject.get("User-cookie").getAsString();
		System.out.println("Got Here");
	}
	
	@Test
	public void registerUserSuccessTest() {
				
		JsonObject newUser = new JsonObject();
		newUser.addProperty("username", "Tommy" + (rand + 1));
		newUser.addProperty("password", "Williams");
		
		
		JsonObject responseObject = proxy1.userRegister(newUser);
		System.out.println(responseObject.toString());
		String shouldMatchUsername = "\"Tommy" + Integer.toString(rand + 1) + '"';
		String shouldMatchPassword = "\"Williams\"";
		
		String userCookie = responseObject.get("User-cookie").toString();
		JsonObject cookie = responseObject.getAsJsonObject("Set-cookie");
		String responseBody = responseObject.get("Response-body").getAsString();
		System.out.println(cookie.get("name").toString());
		
		assertEquals(cookie.get("name").toString(), shouldMatchUsername);
		assertEquals(cookie.get("password").toString(), shouldMatchPassword);
		assertTrue(cookie.get("authentication").toString() != null);
		assertTrue(cookie.get("playerID").getAsInt() > 11);
		assertEquals(responseBody, "Success");
	}
	
	@Test
	public void registerDuplicateUserFailTest() {
				
		JsonObject newUser = new JsonObject();
		newUser.addProperty("username", "Tommy" + rand);
		newUser.addProperty("password", "Williams");
		
		
		JsonObject responseObject = proxy1.userRegister(newUser);
		System.out.println(responseObject.toString());
		
		JsonObject cookie = responseObject.getAsJsonObject("Set-cookie");
		String responseBody = responseObject.get("Error-message").getAsString();
		System.out.println(responseBody);
		
		assertTrue(responseBody.contains("http code 400"));
		assertTrue(responseBody.contains("Bad Request"));
	}
	
	@Test
	public void loginUserSuccessTest() {
		JsonObject user = new JsonObject();
		user.addProperty("username", "Tommy" + rand);
		user.addProperty("password", "Williams");		
		
		JsonObject responseObject = proxy1.userLogin(user);
		System.out.println(responseObject.toString());
		String shouldMatchUsername = "\"Tommy" + Integer.toString(rand) + '"';
		String shouldMatchPassword = "\"Williams\"";
		
		JsonObject cookie = responseObject.getAsJsonObject("Set-cookie");
		String responseBody = responseObject.get("Response-body").toString();
		
		assertEquals(cookie.get("name").toString(), shouldMatchUsername);
		assertEquals(cookie.get("password").toString(), shouldMatchPassword);
		assertTrue(cookie.get("authentication").toString() != null);
		assertTrue(cookie.get("playerID").getAsInt() > 11);
		assertEquals(responseBody, "\"Success\"");
	}
	
	@Test
	public void getGamesListTest() {
		
		JsonObject responseBody = proxy1.getGamesList();
		System.out.println(responseBody.toString());
		assertNotNull(responseBody);	
	}
	
	@Test
	public void getGameModelTest() {
		
		String gameCookie = "1";
		
		JsonObject cookies = new JsonObject();
		cookies.addProperty("User-cookie", userCookie);
		cookies.addProperty("Game-cookie", gameCookie);

		JsonObject responseBody = proxy1.getGameModel(cookies);
		System.out.println(responseBody.toString());
		assertNotNull(responseBody);
	}
	
	@Test 
	public void joinGameSuccessTest() {
		
		JsonObject inputNewGameData = new JsonObject();
		inputNewGameData.addProperty("randomTiles", true);
		inputNewGameData.addProperty("randomNumbers", true);
		inputNewGameData.addProperty("randomPorts", true);
		inputNewGameData.addProperty("name", "Test Game2");
		
		JsonObject responseObject1 = proxy1.createGame(inputNewGameData);
		JsonObject responseBody1 = (JsonObject) responseObject1.get("Response-body");

		int gameId = responseBody1.get("id").getAsInt();
		System.out.println(gameId);
		assertTrue(gameId > 2);
		
		JsonObject gameData = new JsonObject();
		gameData.addProperty("id", gameId);
		gameData.addProperty("color", "puce");
		gameData.addProperty("User-cookie", userCookie);
			
		JsonObject responseObject2 = proxy1.joinGame(gameData);
		
		String responseBody2 = responseObject2.get("Response-body").toString();
		System.out.println(responseObject2.toString());
		assertEquals(responseBody2, "\"Success\"");
	}

	@Test
	public void createGameAllRandomTest () {
		JsonObject inputGame = new JsonObject();
		inputGame.addProperty("randomTiles", true);
		inputGame.addProperty("randomNumbers", true);
		inputGame.addProperty("randomPorts", true);
		inputGame.addProperty("name", "New Game!");
		
		JsonObject newGame = proxy2.createGame(inputGame);
		assertTrue(newGame.get("title").getAsString().equals("game"));
	}
	
	@Test
	public void addApiTest() {
		JsonObject inputCookies = new JsonObject();
		inputCookies.addProperty("User-cookie", userCookie);
		inputCookies.addProperty("Game-cookie", gameId);
		JsonObject resultObject = proxy2.addAI("LARGEST_ARMY", inputCookies);
		assertNotNull(resultObject);
		assertEquals(resultObject.get("Response-body").getAsString(), "Success");
	}
	
	//@Test
	public void listApiTest() {
		JsonObject resultObject = proxy2.listAI(null);
		String returned = resultObject.get("Response-body").getAsString();
		assertTrue(returned.contains("LARGEST_ARMY"));
	}
	
	@Test 
	public void sendChatTest() {
		JsonObject resultObject = proxy2.sendChat(0, "This is my message");
		assertNotNull(resultObject);
	}
	
	@Test
	public void rollNumberTest() {
		JsonObject resultObject = proxy2.rollNumber();
		assertNotNull(resultObject);		
	}
	
	@Test
	public void robPlayerTest() {
		JsonObject resultObject = proxy2.robPlayer(0, 1, new HexLocation(2, 2));
		assertNotNull(resultObject);		
	}
	
	@Test
	public void finishTurnTest() {
		JsonObject resultObject = proxy2.finishTurn(0);
		assertNotNull(resultObject);		
	}
	
	@Test
	public void buyDevCardTest() {
		JsonObject resultObject = proxy2.buyDevCard(0);
		assertNotNull(resultObject);
	}
	
	@Test
	public void playYearOfPlentyCardTest() {
		JsonObject resultObject = proxy2.playYearOfPlenty(0, 2, 2);
		assertNotNull(resultObject);	
	}
	
	@Test
	public void playSoldierCardTest() {
		JsonObject resultObject = proxy2.playSoldier(0, 1, new HexLocation(1,1));
		assertNotNull(resultObject);		
	}
	
	@Test
	public void playMonopolyCardTest() {
		JsonObject resultObject = proxy2.playMonopoly(0, "wood");
		assertNotNull(resultObject);		
	}
	
	@Test
	public void playMonumentCardTest() {
		JsonObject resultObject = proxy2.playMonument(0);
		assertNotNull(resultObject);		
	}
	
	@Test
	public void buildRoadTest() {
		JsonObject resultObject = proxy2.buildRoad(0, new EdgeLocation(new HexLocation(1,1), EdgeDirection.North), false);
		assertNotNull(resultObject);		
	}
	
	@Test
	public void buildSettlementTest() {
		JsonObject resultObject = proxy2.buildSettlement(0, new VertexLocation(new HexLocation(1,1), VertexDirection.East), false);
		assertNotNull(resultObject);		
	}
	
	@Test
	public void buildCityTest() {
		JsonObject resultObject = proxy2.buildCity(0, new VertexLocation(new HexLocation(1,1), VertexDirection.East));
		assertNotNull(resultObject);		
	}
	
	@Test
	public void offerTradeTest() {
		JsonObject resultObject = proxy2.offerTrade(0, new Resources(1, 1, 1, 1, 1), 1);
		assertNotNull(resultObject);		
	}
	
	@Test
	public void acceptTradeTest() {
		JsonObject resultObject = proxy2.acceptTrade(1, true);
		assertNotNull(resultObject);		
	}
	
	@Test
	public void maritimeTradeTest() {
		JsonObject resultObject = proxy2.maritimeTrade(0, 3, "brick", "ore");
		assertNotNull(resultObject);		
	}
	
	@Test
	public void discardCardsTest() {
		JsonObject resultObject = proxy2.discardCards(0, new Resources(0,0,1,1,0));
		assertNotNull(resultObject);		
	}
	
	
	
	
	
}
