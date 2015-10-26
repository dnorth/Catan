package client.state;

import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import server.proxy.ClientException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Random;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

import client.data.GameInfo;
import client.data.RobPlayerInfo;
import client.facade.Facade;
import client.models.ClientModel;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.User;

public interface IStateBase {
	
	public abstract Facade getFacade();
	
	public abstract boolean canJoinGame();
	public abstract boolean canStartCreateNewGame();
	public abstract boolean canStartPlayerWaitingView();
	public abstract boolean canLogin();
	public abstract boolean register(String username, String password) throws ClientException;
	public abstract boolean login(String username, String password) throws ClientException;
	public abstract boolean canPlaceRoadAtLocation(EdgeLocation edge);
	public abstract boolean canPlaceSettlementAtLocation(VertexLocation vertLoc);
	public abstract boolean canUpgradeSettlementAtLocation(VertexLocation vertLoc);
	public abstract boolean canPlaceRobber(HexLocation hexLoc);
	public abstract boolean canOfferTrade();
	public abstract boolean canAcceptTrade(TradeOffer tradeOffer);
	public abstract boolean canBuyDevCard();
	public abstract boolean canPlayDevCard();
	public abstract void sendMessage (String message);
	public abstract void playMonopolyCard(ResourceType resource);
	public abstract void playMonumentCard();
	public abstract void playRoadBuildingCard();
	public abstract void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2);
	public abstract void increaseAmount(ResourceType resource);
	public abstract void decreaseAmount(ResourceType resource);
	public abstract void discard(Resources toDiscard);
	public abstract void decreaseDomesticTradeResourceAmount(ResourceType resource);
	public abstract void increaseDomesticTradeResourceAmount(ResourceType resource);
	public abstract void sendTradeOffer(TradeOffer offer);
	public abstract void setPlayerToTradeWith(int playerIndex);
	public abstract void setResourceToReceive(ResourceType resource);
	public abstract void setResourceToSend(ResourceType resource);
	public abstract void unsetResource(ResourceType resource);
	public abstract void acceptTrade(boolean willAccept);
	public abstract boolean createNewGame(String title, boolean useRandomHexes, boolean useRandomNumbers, boolean useRandomPorts);
	public abstract void startJoinGame(GameInfo game);
	public abstract void joinGame(CatanColor color);
	public abstract void addAI(String AIType);
	public abstract void placeRoad(EdgeLocation edgeLoc);
	public abstract void placeSettlement(VertexLocation vertLoc);
	public abstract void placeCity(VertexLocation vertLoc);
	public abstract RobPlayerInfo[] placeRobber(HexLocation hexLoc);
	public abstract void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected);
	public abstract void playSoldierCard();
	public abstract void robPlayer(RobPlayerInfo victim);
	public abstract void startMaritimeTrade();
	public abstract void makeMaritimeTrade();
	public abstract void setGetResource(ResourceType resource);
	public abstract void setGiveResource(ResourceType resource);
	public abstract void unsetGetValue();
	public abstract void unsetGiveValue();
	public abstract void buildRoad();
	public abstract void buildSettlement();
	public abstract void buildCity();
	public abstract void buyCard();
	public abstract void playCard();
	public abstract int rollDice();
	public abstract void endTurn();
}
