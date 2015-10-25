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
	public JsonObject addAI (JsonObject AIObject, JsonObject optionalCookies);
	public JsonObject listAI ();


	//GAMES PROXY
	public JsonObject getGamesList ();
	public JsonObject createGame (JsonObject inputGame, JsonObject optionalCookies);
	public JsonObject joinGame (JsonObject gameData, JsonObject optionalCookies);
	public JsonObject saveGame (JsonObject data, JsonObject optionalCookies);
	public JsonObject loadGame (JsonObject gameName, JsonObject optionalCookies);

	//MOVES PROXY
	public JsonObject sendChat (JsonObject data, JsonObject optionalCookies);
	public JsonObject rollNumber(JsonObject data, JsonObject optionalCookies);
	public JsonObject robPlayer (JsonObject data, JsonObject optionalCookies);
	public JsonObject finishTurn (JsonObject data, JsonObject optionalCookies);
	public JsonObject buyDevCard (JsonObject data, JsonObject optionalCookies);
	public JsonObject playYearOfPlenty (JsonObject data, JsonObject optionalCookies);
	public JsonObject playRoadBuilding (JsonObject data, JsonObject optionalCookies);
	public JsonObject playSoldier (JsonObject data, JsonObject optionalCookies);
	public JsonObject playMonopoly (JsonObject data, JsonObject optionalCookies);
	public JsonObject playMonument (JsonObject data, JsonObject optionalCookies);
	public JsonObject buildRoad (JsonObject data, JsonObject optionalCookies);
	public JsonObject buildSettlement (JsonObject data, JsonObject optionalCookies);
	public JsonObject buildCity (JsonObject data, JsonObject optionalCookies);
	public JsonObject offerTrade (JsonObject data, JsonObject optionalCookies);
	public JsonObject acceptTrade (JsonObject data, JsonObject optionalCookies);
	public JsonObject maritimeTrade (JsonObject data, JsonObject optionalCookies);
	public JsonObject discardCards (JsonObject data, JsonObject optionalCookies);
	
	//USER PROXY
	public JsonObject userLogin (JsonObject user, JsonObject optionalCookies) throws ClientException;
	public JsonObject userRegister (JsonObject user, JsonObject optionalCookies) throws ClientException;
	
	//UTIL PROXY
	public void utilChangeLogLevel ();
	
}
