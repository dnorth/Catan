package jsonTranslator;

import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import client.data.GameInfo;
import client.models.mapdata.EdgeLocation;

import com.google.gson.JsonObject;

/**
 * Translates Model class structure to JSON format
 *
 */
public class ModelToJSON {

	/**
	 * Takes the existing model and translates it into a JSON object to send to the server
	 *
	 */
	public JsonObject translateModel() {
		return null;
	}
	
	public JsonObject createJoinGameObject(GameInfo game, CatanColor color, String userCookie) {
		JsonObject object = new JsonObject();
		object.addProperty("id", game.getId());
		object.addProperty("color", color.toString().toLowerCase());
		object.addProperty("User-cookie", userCookie);
		return object;
	}
	
	public JsonObject createGameObject(String title, boolean useRandomHexes, boolean useRandomNumbers, boolean useRandomPorts) {
		JsonObject object = new JsonObject();
		object.addProperty("randomTiles", useRandomHexes);
		object.addProperty("randomNumbers", useRandomNumbers);
		object.addProperty("randomPorts", useRandomPorts);
		object.addProperty("name", title);
		return object;
	}
	
	public JsonObject createUserObject(String username, String password) {
		JsonObject user = new JsonObject();
		user.addProperty("username", username);
		user.addProperty("password", password);
		return user;
	}
	
	public JsonObject createMessageObject(String message)
	{
		JsonObject object = new JsonObject();
		object.addProperty("message", message);
		return object;
	}
	
	public JsonObject createPlayerIndex(int playerIndex)
	{
		JsonObject object = new JsonObject();
		object.addProperty("Player Index", playerIndex);
		return object;
	}
	
	public JsonObject createPlayMonopolyObject(int playerIndex, ResourceType resource)
	{
		JsonObject object = new JsonObject();
		object.addProperty("Player Index", playerIndex);
		object.addProperty("ResourceType", resource.name());
		return object;
	}
	
	public JsonObject createPlayRoadBuildingObject(int playerIndex, EdgeLocation roadLocation, EdgeLocation roadLocation2, boolean free)
	{
//		JsonObject object = new JsonObject();
//		object.addProperty("Player Index", playerIndex);
//		object.addProperty("Road Location 1", roadLocation);
		return null;
	}
	
	public JsonObject createYearOfPlentyObject(ResourceType resource1, ResourceType resource2){
		JsonObject object = new JsonObject();
		object.addProperty("ResourceType", resource1.name());
		object.addProperty("ResourceType", resource2.name());
		return  object;
	}
	
	public JsonObject getBuildRoadCommand(int playerIndex, EdgeLocation edge, boolean free) {
		JsonObject object = new JsonObject();
		object.addProperty("type", "buildRoad");
		object.addProperty("playerIndex", playerIndex);
		
		JsonObject edgeLoc = new JsonObject();
		edgeLoc.addProperty("x", edge.getXcoord());
		edgeLoc.addProperty("y", edge.getYcoord());
		edgeLoc.addProperty("direction", edge.getDirection());
		object.add("roadLocation", edgeLoc);
		
		if (free) object.addProperty("free", false);
		else object.addProperty("free", false);
		return object;
	}
	
	public JsonObject getBuildSettlementCommand(int playerIndex, EdgeLocation edge, boolean free) {
		JsonObject object = new JsonObject();
		object.addProperty("type", "buildSettlement");
		object.addProperty("playerIndex", playerIndex);

		JsonObject vertex = new JsonObject();
		vertex.addProperty("x", edge.getXcoord());
		vertex.addProperty("y", edge.getYcoord());
		vertex.addProperty("direction", edge.getDirection());
		object.add("vertexLocation", vertex);
		
		if (free) object.addProperty("free", true);
		else object.addProperty("free", false);
		return object;
	}
	
	public JsonObject createUserAndGameCookie(String userCookie, int gameCookie)
	{
		JsonObject object = new JsonObject();
		object.addProperty("User-cookie", userCookie);
		object.addProperty("Game-cookie", gameCookie);
		return object;
	}
	
	
	
	
	
	
	
	
	
	
}
