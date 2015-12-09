package PluginFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResetPersistence {

	public static void main(String[] args) {
		// delete JSON stuff
		File directory = new File("../jsonFiles");
		for (File f : directory.listFiles()) {
			f.delete();
		}
		System.out.println("JSON files deleted.");
		// reset table
		File sqlFile = new File("../database/catan.sqlite");
		File emptyFile = new File("../database/catan_empty.sqlite");
		if (sqlFile.exists()) sqlFile.delete();
		try {
			Files.copy(emptyFile.toPath(), sqlFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("SQLite file reset.");
	}
	
}
