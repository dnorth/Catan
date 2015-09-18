package server.proxy;


import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

abstract class ServerProxy {

    private static String SERVER_HOST = "localhost";
    private static int SERVER_PORT = 8081;
    private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT; //the path that we're given in the xml is relative. Must add this to it.
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";

    private XStream xmlStream;

    public ServerProxy() {
        xmlStream = new XStream(new DomDriver());
        SERVER_HOST = "localhost";
        SERVER_PORT = 8081;
        URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    }    
    
    protected Object doGet(String urlPath) throws ClientException {
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
    
    protected Object doPost(String urlPath, Object postData) throws ClientException {
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