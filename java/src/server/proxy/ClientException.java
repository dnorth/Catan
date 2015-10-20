package server.proxy;

@SuppressWarnings("serial")
public class ClientException extends Exception {

	String response = "";
	
    public ClientException() {
        return;
    }

    public ClientException(String message) {
        super(message);
    }
    
    public ClientException(String message, String response) {
    	super(message);
    	this.response = response;
    }

    public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public ClientException(Throwable throwable) {
        super(throwable);
    }

    public ClientException(String message, Throwable throwable) {
        super(message, throwable);
    }

}