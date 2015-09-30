package tests;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import server.proxy.ClientCommunicator;
import server.proxy.IProxy;

import com.google.gson.JsonObject;


public class ServerProxyTests {
	
	static Random randGenerator;
	static int rand;
	static IProxy proxy;
	static String userCookie;

	
	@BeforeClass
	public static void initRandom() {
		randGenerator = new Random();
		rand = randGenerator.nextInt(); //We're using a random number generator because we can't register the same user without restarting the server
		proxy = new ClientCommunicator();
	}
	
	@Test
	public void registerUserSuccessTest() {
				
		JsonObject newUser = new JsonObject();
		newUser.addProperty("username", "Tommy" + rand);
		newUser.addProperty("password", "Williams");
		
		
		JsonObject responseObject = proxy.userRegister(newUser);
		System.out.println(responseObject.toString());
		String shouldMatchUsername = "\"Tommy" + Integer.toString(rand) + '"';
		String shouldMatchPassword = "\"Williams\"";
		
		userCookie = responseObject.get("Cookie").toString();
		JsonObject cookie = responseObject.getAsJsonObject("Set-cookie");
		String responseBody = responseObject.get("Response-body").toString();
		
		assertEquals(cookie.get("name").toString(), shouldMatchUsername);
		assertEquals(cookie.get("password").toString(), shouldMatchPassword);
		assertTrue(cookie.get("authentication").toString() != null);
		assertTrue(cookie.get("playerID").getAsInt() > 11);
		assertEquals(responseBody, "\"Success\"");
	}
	
	@Test //TODO
	public void registerDuplicateUserFailTest() {
				
		/*JsonObject newUser = new JsonObject();
		newUser.addProperty("username", "Tommy" + rand);
		newUser.addProperty("password", "Williams");
		
		
		JsonObject responseObject = proxy.userRegister(newUser);
		System.out.println(responseObject.toString());
		String shouldMatchUsername = "\"Tommy" + Integer.toString(rand) + '"';
		String shouldMatchPassword = "\"Williams\"";
		
		JsonObject cookie = responseObject.getAsJsonObject("Set-cookie");
		String responseBody = responseObject.get("Response-body").toString();
		
		assertEquals(cookie.get("name").toString(), shouldMatchUsername);
		assertEquals(cookie.get("password").toString(), shouldMatchPassword);
		assertTrue(cookie.get("authentication").toString() != null);
		assertTrue(cookie.get("playerID").getAsInt() > 11);
		assertEquals(responseBody, "\"Success\"");*/
		assertFalse(true);
	}
	
	@Test
	public void loginUserSuccessTest() {
		JsonObject user = new JsonObject();
		user.addProperty("username", "Tommy" + rand);
		user.addProperty("password", "Williams");		
		
		JsonObject responseObject = proxy.userLogin(user);
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
		
		JsonObject responseBody = proxy.getGamesList();
		System.out.println(responseBody.toString());
		assertTrue(true);	
	}
	
	@Test
	public void getGameModelTest() {
		
		JsonObject responseBody = proxy.getGameModel(userCookie);
	}

	//@Test
	public void createGameAllRandomTest (boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) {
		JsonObject inputGame = new JsonObject();
		inputGame.addProperty("randomTiles", true);
		inputGame.addProperty("randomNumbers", true);
		inputGame.addProperty("randomPorts", true);
		inputGame.addProperty("name", "New Game!");
		
		JsonObject newGame = proxy.createGame(inputGame); 
	}
	
	//@Test
	public void joinGame (int gameId, String color) {
		
	}
	
	//@Test
	public void saveGame (int gameId, String fileName) {
		
	}
	
	//@Test
	public void loadGame (JsonObject gameName) {
		
	}

}
