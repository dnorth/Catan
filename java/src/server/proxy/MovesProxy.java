package server.proxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
    public String sendChat (int playerIndex, String content){
    	String clientModelJSON = null;
        try {
            clientModelJSON = (String)doPost("/moves/sendChat", null);
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
    public String rollNumber(){
    	String clientModelJSON = null;
        try {
            clientModelJSON = (String)doPost("/moves/rollNumber", null);
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
    public String robPlayer (int playerIndex, int victimIndex, HexLocation hexLocation){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/robPlayer", null);
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
    public String finishTurn (int playerIndex){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/finishTurn", null);
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
    public String buyDevCard (int playerIndex){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/buyDevCard", null);
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
    public String playYearOfPlenty (int playerIndex, int resource1, int resource2){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/Year_of_Plenty", null);
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
    public String playRoadBuilding (int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/Road_Building", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @pre playerIndex and victimIndex are between 0 and 3
     * @pre newRobberHexLocation and victimIndex are correctly connected
     * @post one card at random was trasferred from the hand of the victim to the hand of the player
     * @post robber now appears on the new hex location
     * @post indicated player soldier count is incremented and if they have the most, the LargestArmy card is transferred to their hand
     * @param playerIndex index of player who moves the robber
     * @param victimIndex
     * @return clientModel JSON (Same as "/game/model")
     */
    public String playSoldier (int playerIndex, int victimIndex, HexLocation newRobberHexLocation){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/Soldier", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public String playMonopoly (){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/Monopoly", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public String playMonument (){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/Monument", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public String buildRoad (){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/buildRoad", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public String buildSettlement (){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/buildSettlement", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public String buildCity (){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/buildCity", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public String offerTrade (){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/offerTrade", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public String acceptTrade (){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/acceptTrade", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public String maritimeTrade (){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/maritimeTrade", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
    
    /**
     * @return clientModel JSON (Same as "/game/model")
     */
    public String discardCards (){
    	String clientModelJSON = null;
    	try {
    		clientModelJSON = (String)doPost("/moves/discardCards", null);
    	} catch (ClientException e) {
    		e.printStackTrace();
    	}
        return clientModelJSON;
    }
	    
}
