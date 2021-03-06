package server.authentication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import server.Server;
import server.exceptions.MissingCookieException;
import server.model.ServerData;

public final class Authenticate {
	private Authenticate() {
		
	}
	
	public static boolean isValidCookie(JsonObject cookie, boolean requiresGameCookie) throws MissingCookieException {
		if(requiresGameCookie && !cookie.has("game")) {
			throw new MissingCookieException("The catan.game HTTP cookie is missing.  You must join a game before calling this method.");
		}
		
		ServerData serverData = Server.getServerData();
				
		int pID = serverData.getPlayerID(cookie.get("name").getAsString(), cookie.get("password").getAsString());
		
		if (pID == cookie.get("playerID").getAsInt()) {
			if(!requiresGameCookie) {
				return true;
			} else if (serverData.getGameByID(cookie.get("game").getAsInt()) != null){
				return true;
			}
		}
		
		return false;
	}
	
	public static String dressCookie(JsonObject fullCookie) throws UnsupportedEncodingException {
		return "catan.user=" + URLEncoder.encode(fullCookie.toString(), "utf-8") + ";Path=/;";
	}
	
	public static String getSpecificContextFromExchange(HttpExchange exchange) {
		String uri = exchange.getRequestURI().toString();
		return uri.substring(uri.lastIndexOf("/") + 1);
	}
}
