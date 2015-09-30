package server.proxy;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.ClientModel;
import client.models.Game;
import client.models.Resources;


public interface IProxy {


	//GAME PROXY
	public JsonObject getGameModel (String userCookie);
	public JsonObject resetGame ();
	public JsonObject executeCommandList (JsonObject commands);
	public JsonObject getExecutedCommands ();
	public void addAI (String soldierType);
	public JsonObject listAI ();


	//GAMES PROXY
	public JsonObject getGamesList ();
	public JsonObject createGame (JsonObject inputGame);
	public JsonObject joinGame (JsonObject gameData);
	public JsonObject saveGame (int gameId, String fileName);
	public JsonObject loadGame (JsonObject gameName);

	//MOVES PROXY
	public JsonObject sendChat (int playerIndex, String content);
	public JsonObject rollNumber();
	public JsonObject robPlayer (int playerIndex, int victimIndex, HexLocation hexLocation);
	public JsonObject finishTurn (int playerIndex);
	public JsonObject buyDevCard (int playerIndex);
	public JsonObject playYearOfPlenty (int playerIndex, int resource1, int resource2);
	public JsonObject playRoadBuilding (int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2);
	public JsonObject playSoldier (int playerIndex, int victimIndex, HexLocation newRobberHexLocation);
	public JsonObject playMonopoly (int playerIndex, String resource);
	public JsonObject playMonument (int playerIndex);
	public JsonObject buildRoad (int playerIndex, EdgeLocation roadLocation, boolean free);
	public JsonObject buildSettlement (int playerIndex, VertexLocation settlementLocation, boolean free);
	public JsonObject buildCity (int playerIndex, VertexLocation cityLocation);
	public JsonObject offerTrade (int playerIndex, Resources resourceList, int receivingPlayerIndex);
	public JsonObject acceptTrade (int playerIndex, boolean willAccept);
	public JsonObject maritimeTrade (int playerIndex, int ratio, String inputResource, String outputResource);
	public JsonObject discardCards (int playerIndex, Resources discardedCards);
	
	//USER PROXY
	public JsonObject userLogin (JsonObject user);
	public JsonObject userRegister (JsonObject user);
	
	//UTIL PROXY
	public void utilChangeLogLevel ();
	
}
