package server.proxy;

import com.google.gson.JsonObject;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.Resources;


public interface IProxy {


	//GAME PROXY
	public JsonObject getGameModel (JsonObject optionalCookies);
	public JsonObject resetGame (JsonObject optionalCookies);
	public JsonObject executeCommandList (JsonObject commands, JsonObject optionalCookies);
	public JsonObject getExecutedCommands (JsonObject optionalCookies);
	public JsonObject addAI (String soldierType, JsonObject optionalCookies);
	public JsonObject listAI ();


	//GAMES PROXY
	public JsonObject getGamesList ();
	public JsonObject createGame (JsonObject inputGame);
	public JsonObject joinGame (JsonObject gameData);
	public JsonObject saveGame (JsonObject data);
	public JsonObject loadGame (JsonObject gameName);

	//MOVES PROXY
	public JsonObject sendChat (JsonObject data);
	public JsonObject rollNumber();
	public JsonObject robPlayer (JsonObject data);
	public JsonObject finishTurn (JsonObject data);
	public JsonObject buyDevCard (JsonObject data);
	public JsonObject playYearOfPlenty (JsonObject data);
	public JsonObject playRoadBuilding (JsonObject data);
	public JsonObject playSoldier (JsonObject data);
	public JsonObject playMonopoly (JsonObject data);
	public JsonObject playMonument (JsonObject data);
	public JsonObject buildRoad (JsonObject data);
	public JsonObject buildSettlement (JsonObject data);
	public JsonObject buildCity (JsonObject data);
	public JsonObject offerTrade (JsonObject data);
	public JsonObject acceptTrade (JsonObject data);
	public JsonObject maritimeTrade (JsonObject data);
	public JsonObject discardCards (JsonObject data);
	
	//USER PROXY
	public JsonObject userLogin (JsonObject user);
	public JsonObject userRegister (JsonObject user);
	
	//UTIL PROXY
	public void utilChangeLogLevel ();
	
}
