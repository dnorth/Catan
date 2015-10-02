package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import client.models.ClientModel;
import server.ServerPoller.ServerPoller;
import server.proxy.ClientCommunicator;
import server.proxy.IProxy;
import server.proxy.MockServerProxy;

public class ServerPollerTests {

	@Test
	public void UpdateModelTest() throws IOException {
		
		
		
		IProxy proxy = new MockServerProxy();
		ClientModel clientModel = new ClientModel();

		ServerPoller poller = new ServerPoller(proxy, clientModel);
		
		//assertEquals(cookie.get(\"name\").toString(), shouldMatchUsername);
		//assertEquals(cookie.get(\"password\").toString(), shouldMatchPassword);
		//assertTrue(cookie.get(\"authentication\").toString() != null);
		//assertTrue(cookie.get(\"playerID\").getAsInt() > 11);
		//assertEquals(responseBody, \"Success\");
		assertTrue(true);
	}

}
