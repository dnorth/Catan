package server.proxy;

import com.google.gson.JsonObject;

public class UserProxy extends ServerProxy{

	/**
	 * @return
	 */
	public JsonObject userLogin (){
        
        String username;
        String password;
        // doPost("/user/login", null);
        
        return null;
        
    }

	/**
	 * @return
	 */
	public JsonObject userRegister (JsonObject object){
      
        try {
			System.out.println(doPost("/user/register", object));
		} catch (ClientException e) {

			e.printStackTrace();
		}
       	
       	return null;
    }
}
