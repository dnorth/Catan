package server.proxy;

import com.google.gson.JsonObject;

public class UserProxy extends ServerProxy{

	/**
	 * @return
	 */
	public JsonObject userLogin (JsonObject user){
        
        try {
        	return doPost("/user/login", user);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
        
        return null;
        
    }

	/**
	 * @return
	 */
	public JsonObject userRegister (JsonObject user){
      
		JsonObject returnObject = new JsonObject();;
        try {
			returnObject = doPost("/user/register", user);
		} catch (ClientException e) { //TODO have this return the failed response body

			returnObject.addProperty("Error-message", e.getMessage());;
			//e.printStackTrace();
		}
       	
       	return returnObject;
    }
}
