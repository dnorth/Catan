package server.proxy;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

abstract class ServerProxy {
	
	//Create this with a constructor for the dependency injection stuff.
	//Getters and setters for the server type stuffs.

    private static String SERVER_HOST = "localhost";
    private static int SERVER_PORT = 8081;
    private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT; //the path that we're given in the xml is relative. Must add this to it.
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";


    public ServerProxy() { 
        SERVER_HOST = "localhost";
        SERVER_PORT = 8081;
        URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    }    
    
    protected JsonObject doGet(String urlPath) throws ClientException { // Do we want to return a String, or a JSON object? Because some of the methods have multiple model types that get returned in one single call.
        try {
            URL url = new URL(URL_PREFIX + urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HTTP_GET);
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	
            	BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        		StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                System.out.print("What is returned: ");
                System.out.println(sb.toString());
                String jsonToParse = sb.toString();

                JsonParser jsonParser = new JsonParser();
                Gson gson = new Gson();
               // JsonObject o = gson.fromJson(json, classOfT);
        		//JsonObject jsonReturnObject = (JsonObject)jsonParser.parse(jsonToParse);
            	
            	
                return null;
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
    
    protected JsonObject doPost(String urlPath, JsonObject postData) throws ClientException { //Return a JSON Object
        try {
            URL url = new URL(URL_PREFIX + urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            if(postData.has("Cookie")) { //TODO add duplicity for catan.game cookie https://students.cs.byu.edu/~cs340ta/fall2015/group_project/Cookies.pdf
            	String inputCookie = null;
            	inputCookie = postData.get("Cookie").toString();
            	inputCookie = DressCookie(inputCookie);
            	connection.setRequestProperty("Cookie", inputCookie);
            }
            connection.setRequestMethod(HTTP_POST);
            connection.setDoOutput(true);
            connection.connect();
          //  xmlStream.toXML(postData, connection.getOutputStream());
            OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
            os.write(postData.toString());
            os.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	try {
            		JsonObject jsonReturnObject = new JsonObject();
            		JsonParser jsonParser = new JsonParser();
            		//Test to read in input
            		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            		if(connection.getHeaderField("Set-cookie") != null) {
            			String cookieHeader = connection.getHeaderField("Set-cookie");
            			String cookie = StripCookie(cookieHeader);
            			//System.out.println(cookie);
            			String cookieJsonString = URLDecoder.decode(cookie);
            			JsonObject jsonCookieElement = (JsonObject)jsonParser.parse(cookieJsonString);
            			jsonReturnObject.addProperty("Cookie", cookie);
            			jsonReturnObject.add("Set-cookie", jsonCookieElement);
            		}	
            			
            		
            		
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                    String responseBody = sb.toString();
                    jsonReturnObject.addProperty("Response-body", responseBody);
                    //System.out.print("What is returned: ");
                    //System.out.println(jsonReturnObject.toString());
                    return jsonReturnObject;
            	}
            	catch (ClassCastException e) {
            		System.out.println("ServerProxy JSON translator ClassCast Exception");
            		return null;
            	}
            }
            else {
                throw new ClientException(String.format("doPost failed: %s (http code %d : %s)",
                        urlPath, connection.getResponseCode(), connection.getResponseMessage()));
            }
        }
        catch (IOException e) {
            throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
        }
    }
    
    private String StripCookie(String cookieHeader) {
    	
    	int startingIndex = cookieHeader.indexOf('=') + 1;
    	int endingIndex = cookieHeader.indexOf(";Path=/;");
    	String cookie = cookieHeader.substring(startingIndex, endingIndex);
    	return cookie;
    }
    
    private String DressCookie(String inputCookie) {
    	return "catan.user=" + inputCookie;
    }
    
}