package server.proxy;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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


    public ServerProxy(String host, int port) { 
        SERVER_HOST = host;
        SERVER_PORT = port;
        URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    }    
    
    protected JsonObject doGet(String urlPath, JsonObject optionalCookies) throws ClientException { // Do we want to return a String, or a JSON object? Because some of the methods have multiple model types that get returned in one single call.

    	try {
    		JsonObject jsonReturnObject = new JsonObject();
    		JsonParser jsonParser = new JsonParser();
            URL url = new URL(URL_PREFIX + urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            if(optionalCookies != null && optionalCookies.has("User-cookie") && optionalCookies.has("Game-cookie")) {
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
                sb.setLength(sb.length() - 1);
                String responseBody = sb.toString();
                
                
                try {
                	try {
                   	 JsonArray j = (JsonArray)jsonParser.parse(responseBody);
                   	 jsonReturnObject.add("Response-body", j);
                	} catch (ClassCastException e) {
                   	 JsonObject j = (JsonObject)jsonParser.parse(responseBody);
                   	 jsonReturnObject.add("Response-body", j);
                	}
                } catch (ClassCastException e) {
                    jsonReturnObject.addProperty("Response-body", responseBody);
                }
                                
                return jsonReturnObject;
            }
            else {
            	InputStream es = connection.getErrorStream();
            	int ret = 0;
            	String errorMessage = "";
            	while((ret=es.read())!=-1)
                {
                   char c=(char)ret;
                   errorMessage += c;
                }
            	es.close();
            	System.out.println("ClientException error message: " + errorMessage);
                throw new ClientException(String.format("doGet failed: %s (http code %d)",
                                            urlPath, connection.getResponseCode()));
            }
        }
        catch (IOException e) {
            throw new ClientException(String.format("doGet failed: %s", e.getMessage()), e);
        }
    }
    
    protected JsonObject doPost(String urlPath, JsonObject postData, JsonObject optionalCookies) throws ClientException {
    	try {
            URL url = new URL(URL_PREFIX + urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            if(optionalCookies != null) {
            	String userCookie = null;
            	String gameCookie = null;
            	userCookie = optionalCookies.get("User-cookie").getAsString();
            	if(optionalCookies.has("Game-cookie")) {
            		gameCookie = optionalCookies.get("Game-cookie").getAsString();
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
                    	try {
                          	 JsonArray j = (JsonArray)jsonParser.parse(responseBody);
                          	 jsonReturnObject.add("Response-body", j);
                       	} catch (ClassCastException e) {
                          	 JsonObject j = (JsonObject)jsonParser.parse(responseBody);
                          	 jsonReturnObject.add("Response-body", j);
                       	}
                    } catch (ClassCastException e) {
                        jsonReturnObject.addProperty("Response-body", responseBody);
                    }
                    return jsonReturnObject;
            	}
            	catch (ClassCastException e) {
            		System.out.println("ServerProxy JSON translator ClassCast Exception");
            		return null;
            	}
            }
            else {
            	InputStream es = connection.getErrorStream();
            	int ret = 0;
            	String errorMessage = "";
            	while((ret=es.read())!=-1)
                {
                   char c=(char)ret;
                   errorMessage += c;
                }
            	es.close();
                throw new ClientException(String.format("doPost failed: %s (http code %d : %s)",
                        urlPath, connection.getResponseCode(), connection.getResponseMessage()), errorMessage);
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