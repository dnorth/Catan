package server.facade;

import client.data.GameInfo;

public interface iGamesFacade {
	public GameInfo[] listGames();
	public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);
	public String joinGame(int id, String color);
}
