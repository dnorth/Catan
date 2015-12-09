package JSONDAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.commands.IMovesCommand;
import server.model.ServerGame;

public class CommandJSONDAO extends JSONDAO {

	ModelToJSON modelToJson;
	String dir;
	String filename;
	BufferedReader br;
	
	
	public CommandJSONDAO() throws FileNotFoundException {
		modelToJson = new ModelToJSON();
		dir = "../jsonFiles";
		filename = "/commands.json";

	}
	
	public List<IMovesCommand> getCommandsByGameAfterIndex(ServerGame game) throws IOException{
		
		int lastCommand = game.getLastCommandSaved();		
		JSONToModel jsonToModel = new JSONToModel();
		
		List<JsonObject> all = getAll();
		List<IMovesCommand> commands = new ArrayList<IMovesCommand>();
		for (JsonObject c : all) {
			int num = c.get("commandNum").getAsInt();
			if (num > lastCommand && c.get("gameID").getAsInt() == game.getId()) {
				commands.add(jsonToModel.translateCommand(c.get("commandJSON").getAsJsonObject(), game, c.get("commandNum").getAsInt()));
			}
		}
		
		return commands;
	}

	public List<JsonObject> getAll() throws IOException{
		br = super.getBufferedReaderFromFilename(dir, filename);
		JsonObject[] commands = new Gson().fromJson(br, JsonObject[].class);
		if (commands != null) {			
			return Arrays.asList(commands);
		}
		return new ArrayList<JsonObject>();
	}
	
	public void add(IMovesCommand command) throws IOException {
		
		JsonObject obj = new JsonObject();
		obj.addProperty("gameID", command.getGameID());
		obj.addProperty("commandNum", command.getCommandNumber());
		obj.add("commandJSON", command.toJSON());
		
		List<JsonObject> commands = new ArrayList<JsonObject>(getAll());
		for (JsonObject c : commands) {
			if (c.get("gameID").getAsInt() == command.getGameID() &&
					c.get("commandNum").getAsInt() == command.getCommandNumber()) {
				return;
			}
		}
		commands.add(obj);
		JsonArray arr = new JsonArray();
		for (JsonObject c : commands) {
			arr.add(c);
		}
		
		super.writeJsonArrayToFile(arr, dir + filename);
	}

}
