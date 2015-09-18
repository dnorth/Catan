package client.models;

import java.util.ArrayList;

public class Game {

	private String name;
	private int gameID;
	private ArrayList<Player> players;
	
	private static int nextGameId = 1;

	public Game(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) {
		super();
		this.name = name;
		this.gameID = nextGameId++;
		this.players = new ArrayList<Player>();
		
		//TODO: Implement stuff with random tiles, numbers and ports.
	}
	
}
