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
import server.commands.IMovesCommand;
import server.model.ServerGame;

public class CommandJSONDAO extends JSONDAO {

	ModelToJSON modelToJson;
	String dir;
	String filename;
	BufferedReader br;
	
	
	public CommandJSONDAO() throws FileNotFoundException {
		modelToJson = new ModelToJSON();
		dir = "jsonFiles";
		filename = "/commands.json";
	}
	
	public List<IMovesCommand> getCommandsByGameAfterIndex(ServerGame game, int index) throws FileNotFoundException{

		List<IMovesCommand> commands = new ArrayList<>();
		List<IMovesCommand> commandsInGame = game.getCommands();
			
		for(int i=index+1; i<commandsInGame.size(); i++)	
		{
			commands.add(commandsInGame.get(i));
		}
		
		return commandsInGame;
	}

	public List<IMovesCommand> getAll() throws IOException{
		br = super.getBufferedReaderFromFilename(dir, filename);
		IMovesCommand[] commands = new Gson().fromJson(br, IMovesCommand[].class);
		List<IMovesCommand> asList = new ArrayList<>();
		if(commands != null) {
			asList = Arrays.asList(commands);
		}
		return asList;
	}
	
	public void add(IMovesCommand command) throws IOException {
	
		List<IMovesCommand> commands = new ArrayList<>(getAll());
		commands.add(command);
		JsonArray jsonCommands = modelToJson.generateCommands(commands);
		
		super.writeJsonArrayToFile(jsonCommands, dir + filename);
	}

}
