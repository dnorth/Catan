package JSONDAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import jsonTranslator.ModelToJSON;
import server.model.ServerUser;

public class UserJSONDAO extends JSONDAO{
	
	ModelToJSON modelToJson;
	String filename;
	BufferedReader br;
	
	public UserJSONDAO() throws FileNotFoundException {
		modelToJson = new ModelToJSON();
		filename = "jsonFiles/user.json";
	}
	
	public List<ServerUser> getAll() throws FileNotFoundException {
		br = super.getBufferedReaderFromFilename(filename);
		ServerUser[] serverUsers = new Gson().fromJson(br, ServerUser[].class);
		List<ServerUser> asList = Arrays.asList(serverUsers);
		return asList;
	}
	
	public void add(ServerUser user) throws IOException {		
		List<ServerUser> serverUsers = new ArrayList<ServerUser>(getAll());
		serverUsers.add(user);
		JsonArray jsonServerUsers = modelToJson.translateServerUsers(serverUsers);
		super.writeJsonArrayToFile(jsonServerUsers, filename);
	}

	
}
