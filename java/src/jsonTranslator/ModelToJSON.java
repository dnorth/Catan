package jsonTranslator;

import shared.definitions.ResourceType;
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
}
