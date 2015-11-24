package server.facade.mock;

import java.util.ArrayList;
import java.util.List;

import client.data.GameInfo;
import client.data.PlayerInfo;
import server.facade.iGamesFacade;
import server.model.ServerData;
import server.model.ServerGame;
import shared.definitions.CatanColor;

public class MockGamesFacade implements iGamesFacade{

	ServerData serverData;
	
	public MockGamesFacade (ServerData data){
		this.serverData=data;
	}
	@Override
	public List<ServerGame> listGames() {

		List<ServerGame> games = new ArrayList<ServerGame>();
		games.add(new ServerGame("empty gamer",12345));
		return games;
	}

	@Override
	public GameInfo createGame(boolean randomTiles, boolean randomNumbers,
			boolean randomPorts, String name) {
		
		GameInfo gameInfo = new GameInfo();
		gameInfo.setId(1874641);
		gameInfo.setTitle("some title");

		PlayerInfo p1 = new PlayerInfo();
		PlayerInfo p2 = new PlayerInfo();
		PlayerInfo p3 = new PlayerInfo();
		PlayerInfo p4 = new PlayerInfo();
		
		p1.setId(10);
		p2.setId(11);
		p3.setId(12);
		p4.setId(13);
		
		p1.setPlayerIndex(0);
		p2.setPlayerIndex(1);
		p3.setPlayerIndex(2);
		p4.setPlayerIndex(3);
		
		p1.setName("Jeff");
		p2.setName("Tim");
		p3.setName("Bob");
		p4.setName("Jeffery");
		
		p1.setColor(CatanColor.BLACK);
		p2.setColor(CatanColor.BLUE);
		p3.setColor(CatanColor.GREEN);
		p4.setColor(CatanColor.PUCE);
		
		
		gameInfo.addPlayer(p1);
		gameInfo.addPlayer(p2);
		gameInfo.addPlayer(p3);
		gameInfo.addPlayer(p4);
		
		return gameInfo;
	}

	@Override
	public String joinGame(int pID, int gID, String color) {
		
		return "Success!!!@#$!@$@#$@!$@#$$!@";
	}

	@Override
	public ServerData getServerData() {
		return serverData;
	}

}
