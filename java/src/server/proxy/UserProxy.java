package server.proxy;

import com.google.gson.JsonObject;

public class UserProxy extends ServerProxy{

	/**
	 * @return
	 */
	public boolean userLogin (){
        
        String username;
        String password;
        // doPost("/user/login", null);
        
        return false;
        
    }

	/**
	 * @return
	 */
	public boolean userRegister (JsonObject object){
      
        try {
			System.out.println(doPost("/user/register", object));
		} catch (ClientException e) {

			e.printStackTrace();
		}
       	
       	return false;
    }
}
