package JSONDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.commands.IMovesCommand;
import server.model.ServerGame;

public class GameJSONDAO extends JSONDAO {

	
	ModelToJSON modelToJson;
	String dir;
	String filename;
	BufferedReader br;
	
	public GameJSONDAO() throws FileNotFoundException {
		modelToJson = new ModelToJSON();
		dir = "../jsonFiles";
		filename = "/games.json";
	}
	
	
	public void add(ServerGame game) throws IOException
	{
		List<ServerGame> serverGames = new ArrayList<ServerGame>(getAll());
		serverGames.add(game);
		JsonArray jsonServerUsers = modelToJson.translateServerGamesList(serverGames);
		super.writeJsonArrayToFile(jsonServerUsers, dir + filename);
	}
	
	
	public List<ServerGame> getAll() throws IOException{
		br = super.getBufferedReaderFromFilename(dir, filename);
		ServerGame[] serverGame = new Gson().fromJson(br, ServerGame[].class);
		List<ServerGame> asList = new ArrayList<>();
		if(serverGame != null) {
			asList = new ArrayList<>(Arrays.asList(serverGame));
		}
		File jsonFile = new File(dir + filename);
		if (!jsonFile.exists()){
			return asList;
		}
		JsonParser parser = new JsonParser();
		
		JsonArray jArray = null;
		try {
			jArray = (JsonArray) parser.parse(new FileReader(dir + filename));
		} catch (ClassCastException e) {} //this means the file hasn't been made yet, or isn't valid JSON
		
		for (int i = 0; i < asList.size(); i++) {
			ServerGame g = asList.get(i);
			if (jArray != null) {
				JsonObject obj = (JsonObject)jArray.get(i);
				g.setClientModel(JSONToModel.translateClientModel((JsonObject)obj.get("clientModel")));
			}
			g.setCommands(new ArrayList<IMovesCommand>());
		}
		return asList;
	}

	public ServerGame getGameByID (int gameID) throws IOException {
		List<ServerGame> serverGames = this.getAll();
		for (ServerGame sg : serverGames) {
			if (sg.getId() == gameID) {
				return sg;
			}
		}
		return null;
	}

	public void update(ServerGame game) throws IOException { // if game is in database update values, else do nothing.
		List<ServerGame> games = getAll();
		
		for(int i=0; i<games.size(); i++){
			
			if(games.get(i).getId() == game.getId() ){
				games.set(i, game);
				JsonArray jsonServerGames = modelToJson.translateServerGamesList(games);
				super.writeJsonArrayToFile(jsonServerGames, dir + filename);
				return;
			}
		}
		add(game);
	}

}