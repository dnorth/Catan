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
import server.model.ServerGame;
import server.model.ServerUser;

public class GameJSONDAO extends JSONDAO {

	
	ModelToJSON modelToJson;
	String dir;
	String filename;
	BufferedReader br;
	
	public GameJSONDAO() throws FileNotFoundException {
		modelToJson = new ModelToJSON();
		dir = "jsonFiles";
		filename = "/game.json";
	}
	
	
	public void add(ServerGame game) throws IOException
	{
		List<ServerGame> serverGames = new ArrayList<ServerGame>(getAll());
		serverGames.add(game);
		JsonArray jsonServerUsers = modelToJson.generateServerGamesObject(serverGames);
		super.writeJsonArrayToFile(jsonServerUsers, dir + filename);
	}
	
	
	public List<ServerGame> getAll() throws IOException{
		br = super.getBufferedReaderFromFilename(dir, filename);
		ServerGame[] serverGame = new Gson().fromJson(br, ServerGame[].class);
		List<ServerGame> asList = new ArrayList<>();
		if(serverGame != null) {
			asList = Arrays.asList(serverGame);
		}
		return asList;
	}


	public void update(ServerGame game) throws IOException { // if game is in database update values, else do nothing.
		
		List<ServerGame> games = getAll();
		
		for(int i=0; i<games.size(); i++){
			
			if(games.get(i).getId() == game.getId() ){
				games.set(i, game);
				JsonArray jsonServerGames = modelToJson.generateServerGamesObject(games);
				super.writeJsonArrayToFile(jsonServerGames, dir + filename);
				break;
			}
		}
	}
	
	

}
