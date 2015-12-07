package JSONDAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.google.gson.JsonArray;

public abstract class JSONDAO {
	
	public void writeJsonArrayToFile(JsonArray jsonArray, String filename) throws IOException {
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename));)
		{
			output.writeObject(jsonArray);
		}
	}
	
	public BufferedReader getBufferedReaderFromFilename(String filename) throws FileNotFoundException {
		return new BufferedReader(new FileReader(filename)); 
	}
}
