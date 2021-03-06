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
	String dir;
	String filename;
	BufferedReader br;
	
	public UserJSONDAO() throws FileNotFoundException {
		modelToJson = new ModelToJSON();
		dir = "../jsonFiles";
		filename = "/user.json";
	}
	
	public List<ServerUser> getAll() throws IOException {
		br = super.getBufferedReaderFromFilename(dir, filename);
		ServerUser[] serverUsers = new Gson().fromJson(br, ServerUser[].class);
		List<ServerUser> asList = new ArrayList<ServerUser>();
		if(serverUsers != null) {
			asList = new ArrayList<>(Arrays.asList(serverUsers));
		}
		return asList;
	}
	
	public ServerUser getUserByID(int userID) throws IOException{
		List<ServerUser> users = this.getAll();
		for (ServerUser u : users) {
			if (u.getPlayerID() == userID){
				return u;
			}
		}
		return null;
	}
	
	public void add(ServerUser user) throws IOException {		
		List<ServerUser> serverUsers = new ArrayList<ServerUser>(getAll());
		serverUsers.add(user);
		JsonArray jsonServerUsers = modelToJson.translateServerUsers(serverUsers);
		super.writeJsonArrayToFile(jsonServerUsers, dir + filename);
	}

	
}
