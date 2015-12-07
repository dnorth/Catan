package JSONDAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.google.gson.JsonObject;

import jsonTranslator.ModelToJSON;
import server.model.ServerUser;

public class UserJSONDAO {
	
	ModelToJSON modelToJson;
	
	public UserJSONDAO() {
		modelToJson = new ModelToJSON();
	}
	
	public List<ServerUser> getAll() throws FileNotFoundException {
		
		BufferedReader br = new BufferedReader(new FileReader("jsonFiles/users.json")); 
		
		return null;
	}
	
	public void add(ServerUser user) {		
		JsonObject jsonUser = modelToJson.translateServerUser(user);
	}

}
