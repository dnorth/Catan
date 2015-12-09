package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.Server;

public class RunAllTests {

	public static void main(String[] args) {

		Server server = new Server();
		server.testServer();
		
		String[] testClasses = new String[] {
				"tests.CanDoTests",
				"tests.ServerPollerTests",
				"tests.ServerProxyTests",
				"tests.CommandAndModelTests",
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}

}
