package server.proxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class MovesProxy {


    private static String SERVER_HOST = "localhost";
    private static int SERVER_PORT = 8081;
    private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT; //the path that we're given in the xml is relative. Must add this to it.
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";

    private XStream xmlStream;

    public MovesProxy(String host, int port) {
        xmlStream = new XStream(new DomDriver());
        SERVER_HOST = host;
        SERVER_PORT = port;
        URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    }    
    
    public void sendChat (){
        try {
            doPost("/moves/sendChat", null);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
    }
    
    public void rollNumber(){
        try {
            doPost("/moves/rollNumber", null);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    
    public void robPlayer (){
    	try {
    		doPost("/moves/robPlayer", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void finishTurn (){
    	try {
    		doPost("/moves/finishTurn", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void buyDevCard (){
    	try {
    		doPost("/moves/buyDevCard", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void playYearOfPlenty (){
    	try {
    		doPost("/moves/Year_of_Plenty", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void playRoadBuilding (){
    	try {
    		doPost("/moves/Road_Building", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void playSoldier (){
    	try {
    		doPost("/moves/Soldier", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void playMonopoly (){
    	try {
    		doPost("/moves/Monopoly", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void playMonument (){
    	try {
    		doPost("/moves/Monument", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void buildRoad (){
    	try {
    		doPost("/moves/buildRoad", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void buildSettlement (){
    	try {
    		doPost("/moves/buildSettlement", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void buildCity (){
    	try {
    		doPost("/moves/buildCity", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void offerTrade (){
    	try {
    		doPost("/moves/offerTrade", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void acceptTrade (){
    	try {
    		doPost("/moves/acceptTrade", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void maritimeTrade (){
    	try {
    		doPost("/moves/maritimeTrade", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }
    
    public void discardCards (){
    	try {
    		doPost("/moves/discardCards", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
    }

    
    private Object doGet(String urlPath) throws ClientException {
        try {
            URL url = new URL(URL_PREFIX + urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HTTP_GET);
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Object result = xmlStream.fromXML(connection.getInputStream());
                return result;
            }
            else {
                throw new ClientException(String.format("doGet failed: %s (http code %d)",
                                            urlPath, connection.getResponseCode()));
            }
        }
        catch (IOException e) {
            throw new ClientException(String.format("doGet failed: %s", e.getMessage()), e);
        }
    }
    
    private Object doPost(String urlPath, Object postData) throws ClientException {
        try {
            URL url = new URL(URL_PREFIX + urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HTTP_POST);
            connection.setDoOutput(true);
            connection.connect();
            xmlStream.toXML(postData, connection.getOutputStream());
            connection.getOutputStream().close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Object result = xmlStream.fromXML(connection.getInputStream());
                return result;
            }
            else {
                throw new ClientException(String.format("doPost failed: %s (http code %d)",
                        urlPath, connection.getResponseCode()));
            }
        }
        catch (IOException e) {
            throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
        }
    }
	    
}
