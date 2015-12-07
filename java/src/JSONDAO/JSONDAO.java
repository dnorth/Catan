package JSONDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonArray;

public abstract class JSONDAO {
	
	public void writeJsonArrayToFile(JsonArray jsonArray, String filename) throws IOException {
		try(FileWriter output = new FileWriter(filename))
		{
			output.write(jsonArray.toString());
		}
	}
	
	public BufferedReader getBufferedReaderFromFilename(String dir, String filename) throws IOException {
		File directory = new File(dir);
		directory.mkdir();
		File file = new File(dir + filename);
		file.createNewFile();
		return new BufferedReader(new FileReader(dir + filename)); 
	}
}
