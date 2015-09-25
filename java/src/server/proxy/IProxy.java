package server.proxy;

import java.util.ArrayList;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.ClientModel;
import client.models.Game;
import client.models.Resources;


public interface IProxy {


	//GAME PROXY
	public ClientModel getGameModel ();
	public String resetGame ();
	public String executeCommandList (ArrayList<String> commands);
	public ArrayList<String> getExecutedCommands ();
	public void addAI (String soldierType);
	public ArrayList<String> listAI ();


	//GAMES PROXY
	public ArrayList<Game> getGamesList ();
	public Game createGame (boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);
	public boolean joinGame (int gameId, String color);
	public boolean saveGame (int gameId, String fileName);
	public Game loadGame (String gameName);

	//MOVES PROXY
	public String sendChat (int playerIndex, String content);
	public String rollNumber();
	public String robPlayer (int playerIndex, int victimIndex, HexLocation hexLocation);
	public String finishTurn (int playerIndex);
	public String buyDevCard (int playerIndex);
	public String playYearOfPlenty (int playerIndex, int resource1, int resource2);
	public String playRoadBuilding (int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2);
	public String playSoldier (int playerIndex, int victimIndex, HexLocation newRobberHexLocation);
	public String playMonopoly (int playerIndex, String resource);
	public String playMonument (int playerIndex);
	public String buildRoad (int playerIndex, EdgeLocation roadLocation, boolean free);
	public String buildSettlement (int playerIndex, VertexLocation settlementLocation, boolean free);
	public String buildCity (int playerIndex, VertexLocation cityLocation);
	public String offerTrade (int playerIndex, Resources resourceList, int receivingPlayerIndex);
	public String acceptTrade (int playerIndex, boolean willAccept);
	public String maritimeTrade (int playerIndex, int ratio, String inputResource, String outputResource);
	public String discardCards (int playerIndex, Resources discardedCards);
	
	//USER PROXY
	public boolean userLogin ();
	public boolean userRegister ();
	
	//UTIL PROXY
	public void utilChangeLogLevel ();
	
}
