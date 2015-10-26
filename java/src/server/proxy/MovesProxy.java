package server.proxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.Resources;

import com.google.gson.JsonObject;

public class MovesProxy extends ServerProxy{
    
   /**
     * @pre The player is logged in.
     * @pre Player index is positive
     * @pre content message is non-null
     * @post NA
     * @param playerIndex
     * @param content
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject sendChat (JsonObject data, JsonObject optionalCookies){ //int playerIndex, String content
    	JsonObject clientModelJSON = null;
        try {
            clientModelJSON = doPost("/moves/sendChat", data, optionalCookies);
        } catch (ClientException e) {
        	e.printStackTrace();
        }
        return clientModelJSON;
    }
    
    
    /**
     * @pre NA
     * @post NA
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject rollNumber(JsonObject data, JsonObject optionalCookies){
    	JsonObject clientModelJSON = null;
        try {
            clientModelJSON = doPost("/moves/rollNumber", data, optionalCookies);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return clientModelJSON;
    }
    

    /**
     * @pre Hex position and victim index are both valid and connected.
     * @post Robber position is on new hex
     * @param playerIndex
     * @param victimIndex
     * @param new hexLocation of robber
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject robPlayer (JsonObject data, JsonObject optionalCookies){ //int playerIndex, int victimIndex, HexLocation hexLocation
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/robPlayer", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre playerIndex is between 0 and 3
     * @post Indicated players turn is concluded and the game continues on
     * @param playerIndex 
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject finishTurn (JsonObject data, JsonObject optionalCookies){ //int playerIndex
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/finishTurn", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre player index is between 0 and 3
     * @post the indicated player's hand has one more dev card that was chosen randomly from the bank's dev cards.
     * @param playerIndex index of player buying a dev card
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject buyDevCard (JsonObject data, JsonObject optionalCookies){ //int playerIndex
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/buyDevCard", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre indicated player has a year of plenty card he can play
     * @pre playerIndex is between 0 and 3
     * @post indicated player has one less year of plenty card in his hand
     * @post indicated player has gained 2 resource cards from the bank of his choosing
     * @param playerIndex the index of the player playing the year of plenty card
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject playYearOfPlenty (JsonObject data, JsonObject optionalCookies){ //int playerIndex, int resource1, int resource2
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/Year_of_Plenty", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre playerIndex is between 0 and 3
     * @pre edgeLocation1 and edgeLocation2 are vaild locations for the player to play roads
     * @post roads are successfully added to the model
     * @post if the indicated player now has the most roads, the MaxRoads card will be transferred to his hand
     * @param playerIndex index of player who played the Road Building card
     * @param edgeLocations location of edges where roads will be placed 
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject playRoadBuilding (JsonObject data, JsonObject optionalCookies){ //int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/Road_Building", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre playerIndex and victimIndex are between 0 and 3
     * @pre indicated player has a soldier card in his hand
     * @pre newRobberHexLocation and victimIndex are correctly connected
     * @post one resource card at random was transferred from the hand of the victim to the hand of the player
     * @post the indicated player has 1 fewer soldier cards
     * @post robber now appears on the new hex location
     * @post indicated player soldier count is incremented and if they have the most, the LargestArmy card is transferred to their hand
     * @param playerIndex index of player who moves the robber
     * @param victimIndex index of victim player
     * @param newRobberHexLocation new position of the robber
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject playSoldier (JsonObject data, JsonObject optionalCookies){ //int playerIndex, int victimIndex, HexLocation newRobberHexLocation
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/Soldier", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre indicated player has a Monopoly card
     * @post indicated player gets all benefits of monopoly card
     * @post player has one less monopoly card in his hand
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject playMonopoly (JsonObject data, JsonObject optionalCookies){ //int playerIndex, String resource
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/Monopoly", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre indicated player must have a monument card
     * @post player has one less monument card
     * @post player has one more victory point
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject playMonument (JsonObject data, JsonObject optionalCookies){ //int playerIndex
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/Monument", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre roadLocation Road location is a valid road
     * @param free this is set to true during the initialize phase
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject buildRoad (JsonObject data, JsonObject optionalCookies){ //int playerIndex, EdgeLocation roadLocation, boolean free
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/buildRoad", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre vertexLocation is a valid vertex that doesn't already have a settlement on it
     * @param free is set to true during initialize phase
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject buildSettlement (JsonObject data, JsonObject optionalCookies){ //int playerIndex, VertexLocation settlementLocation, boolean free
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/buildSettlement", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre the indicated player already owns a settlement at that location
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject buildCity (JsonObject data, JsonObject optionalCookies){ //int playerIndex, VertexLocation cityLocation
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/buildCity", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre the indicated player has the resources that he/she is offering.
     * @pre both player indicies are valid
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject offerTrade (JsonObject data, JsonObject optionalCookies){ //int playerIndex, Resources resourceList, int receivingPlayerIndex
    	JsonObject clientModelJSON = null;
    	try {
    		System.out.println("\n\n\n TRADE SENT \n\n\n");
    		clientModelJSON = doPost("/moves/offerTrade", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject acceptTrade (JsonObject data, JsonObject optionalCookies){ //int playerIndex, boolean willAccept
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/acceptTrade", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject maritimeTrade (JsonObject data, JsonObject optionalCookies){ //int playerIndex, int ratio, String inputResource, String outputResource
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/maritimeTrade", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public JsonObject discardCards (JsonObject data, JsonObject optionalCookies){ //int playerIndex, Resources discardedCards
    	JsonObject clientModelJSON = null;
    	try {
    		clientModelJSON = doPost("/moves/discardCards", data, optionalCookies);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
	    
}
