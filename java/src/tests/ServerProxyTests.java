package tests;
import static org.junit.Assert.*;

import org.junit.Test;

import server.proxy.ClientCommunicator;

import com.google.gson.JsonObject;


public class ServerProxyTests {

	@Test
	public void loginText() {
		JsonObject newUser = new JsonObject();
		newUser.addProperty("username", "tetetet");
		newUser.addProperty("password", "tetetetet");
		
		
		ClientCommunicator cc = new ClientCommunicator();
		cc.userRegister(newUser);
		assertTrue(true);
	}

}
