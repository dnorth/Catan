package tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class RunAllTests {

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"tests.CanDoTests",
				"tests.ServerPollerTests",
				"tests.ServerProxyTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}

}
