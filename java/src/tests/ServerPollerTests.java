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
		
		
		
//		IProxy proxy = new MockServerProxy();
//		ClientModel clientModel = new ClientModel();
//
//	//	ServerPoller poller = new ServerPoller(proxy, clientModel);
//		
//		//Fake Cookies?
//		JsonObject j = null;
//		
//		poller.updateCurrentModel(j);
//		
//		ClientModel cm = poller.getClient();
//		
//		System.out.println("Testing bank: ");
//		System.out.println(cm.getBank().toString() + "\n");
//		
//		System.out.println("Testing chat: ");
//		System.out.println(cm.getChat().toString());
//		
//		System.out.println("\nTesting log: ");
//		System.out.println(cm.getLog().toString());
//		
//		System.out.println("\nTesting Board: ");
//		System.out.println(cm.getBoard().toString());
//		
//		System.out.println("\nTesting Turn Tracker: ");
//		System.out.println(cm.getTurnTracker().toString());
//		
//		System.out.println("\nTesting Players: ");
//		System.out.println(cm.playersToString());
//		
//		System.out.println("\nTesting Version: ");
//		System.out.println(cm.getVersion());
//		
//		System.out.println("\nTesting Winner: ");
//		System.out.println(cm.getWinner());
//		
//		//Initializes model from sample_model2.json. These tests print out data to prove it's true, rather than by assertion.
//		
//		assertTrue(true);
	}

}
