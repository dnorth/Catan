package server.proxy;

public class UtilProxy extends ServerProxy{

	public UtilProxy(String host, int port) {
		super(host, port);
	}

	public void utilChangeLogLevel (){
		
        try {
            doPost("/util/changeLogLevel", null, null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }
}
