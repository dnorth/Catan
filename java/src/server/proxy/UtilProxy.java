package server.proxy;

public class UtilProxy extends ServerProxy{

	public void utilChangeLogLevel (){
		
        try {
            doPost("/util/changeLogLevel", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }
}
