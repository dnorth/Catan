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
	 * If the server call is successful, the JSON return object will
	 * look like: 
	 * {
	 * 	 "Set-cookie": {   
	 * 			"authentication":"2126518911",
	 * 			"name":"dnorth2",
	 * 			"password":"dnorth2",
	 * 			"playerID":12
	 * 		},
	 * 	  "User-cookie":"%7B%22authentication%22%3A%222126518911%22%2C%22name%22%3A%22dnorth2%22%2C%22password%22%3A%22dnorth2%22%2C%22playerID%22%3A12%7D",
	 * 	  "Response-body":"Success"}
	 * 
	 * @return
	 */
	public JsonObject userRegister (JsonObject user){
      
		JsonObject returnObject = new JsonObject();
        try {
			returnObject = doPost("/user/register", user);
		} catch (ClientException e) { //TODO have this return the failed response body

			returnObject.addProperty("Error-message", e.getMessage());
			//e.printStackTrace();
		}
        
        System.out.println(returnObject);
       	
       	return returnObject;
    }
}
