package server.proxy;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Optional;

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
    
    protected JsonObject doGet(String urlPath, JsonObject optionalCookies) throws ClientException { // Do we want to return a String, or a JSON object? Because some of the methods have multiple model types that get returned in one single call.
        try {
            URL url = new URL(URL_PREFIX + urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            if(optionalCookies != null) {
            	String cookie = DressCookie(optionalCookies.get("User-cookie").getAsString(), optionalCookies.get("Game-cookie").getAsString());
            	connection.setRequestProperty("Cookie", cookie);
            }
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
			    JsonObject returnObject = new JsonObject();
			    returnObject.addProperty("Response-body", jsonToParse);
                return returnObject;
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
            if(postData.has("User-cookie")) { //TODO add duplicity for catan.game cookie https://students.cs.byu.edu/~cs340ta/fall2015/group_project/Cookies.pdf
            	String userCookie = null;
            	String gameCookie = null;
            	userCookie = postData.get("User-cookie").getAsString();
            	
            	if(postData.has("Game-cookie")) {
            		gameCookie = postData.get("Game-cookie").getAsString();
            	}
            	String cookies = DressCookie(userCookie, gameCookie);
            	connection.setRequestProperty("Cookie", cookies);
            }
            connection.setRequestMethod(HTTP_POST);
            connection.setDoOutput(true);
            connection.connect();
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
            			
            			//If the ClassCastException gets caught, that means we are recieving a game cookie
            			try {
            			    JsonObject jsonCookieElement = (JsonObject)jsonParser.parse(cookieJsonString);
                			jsonReturnObject.add("Set-cookie", jsonCookieElement);
               			    jsonReturnObject.addProperty("User-cookie", cookie);
            			} catch (ClassCastException e) {
               			    jsonReturnObject.addProperty("Game-cookie", cookie);
            			}
            		}	
            			
            		
            		
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                    String responseBody = sb.toString();
                    
                    try {
                    	 JsonObject j = (JsonObject)jsonParser.parse(responseBody);
                    	 jsonReturnObject.add("Response-body", j);
                    } catch (ClassCastException e) {
                        jsonReturnObject.addProperty("Response-body", responseBody);
                    }
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
                        urlPath, connection.getResponseCode(), connection.getResponseMessage()), connection.getResponseMessage());
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
    
    private String DressCookie(String inputCookie, String gameCookie) {
    	StringBuilder finalString = new StringBuilder();
    	
		finalString.append("catan.user=" + inputCookie);
		if (gameCookie != null) {
			finalString.append("; catan.game=" + gameCookie);
		}

    	return finalString.toString();    			
    }
    
}