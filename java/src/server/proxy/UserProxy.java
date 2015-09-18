package server.proxy;

public class UserProxy {

	public void userLogin (){
        try {
            doPost("/user/login", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }

	public void userRegister (){
        try {
            doPost("/user/register", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }
}
