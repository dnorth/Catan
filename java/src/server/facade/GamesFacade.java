package server.facade;

import java.util.List;

import server.Server;
import server.commands.games.CreateCommand;
import server.commands.games.JoinCommand;
import server.model.ServerData;
import server.model.ServerGame;
import client.data.GameInfo;


/**
 * The Class GamesFacade.
 */
public class GamesFacade implements iGamesFacade {

	
	ServerData serverData;
	
	
	
	public GamesFacade() {
		super();
		this.serverData = Server.getServerData();
	}

	/**
	 * Facade for the command games/list
	 */
	@Override
	public List<ServerGame> listGames() {
		
		return serverData.getGames();
	}

	/**
	 * Facade for the command games/create
	 */
	@Override
	public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) {
		
		int gameID =serverData.getNextGameID();
		CreateCommand command = new CreateCommand(serverData, randomTiles, randomNumbers, randomPorts, name, gameID); 
		
		try{
			// check cookie here
			command.execute();
			
			GameInfo gameInfo = new GameInfo();
			
			gameInfo.setTitle(name);
			gameInfo.setId(gameID);
			
			serverData.incNextGameID();
		    return gameInfo;
		}
		
		
		catch(Exception e){}
		return null;
	}

	/**
	 * Facade for the command games/join
	 */
	@Override
	public String joinGame(int id, String color) {
		
		JoinCommand command = new JoinCommand(serverData, id, color);
		try{
			// check for cookies here
			// check for valid color
		command.execute();
		return "success";
		}
		catch(Exception e)
		{
			return "failure";
		}
		
	}

}
