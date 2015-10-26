package client.state;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.data.GameInfo;
import client.data.RobPlayerInfo;
import client.facade.Facade;
import client.models.Resources;
import client.models.TradeOffer;

public class SetupOneActivePlayerState implements IStateBase {
	
	private Facade facade;
	private boolean roadPlaced;
	
	public SetupOneActivePlayerState(Facade facade) {
		this.facade = facade;
		this.roadPlaced = false;
	}

	@Override
	public boolean canJoinGame() {
		return false;
	}

	@Override
	public boolean canStartCreateNewGame() {
		return false;
	}

	@Override
	public boolean canStartPlayerWaitingView() {
		return false;
	}

	@Override
	public boolean canLogin() {
		return false;
	}
	
	@Override
	public boolean canPlaceRoadAtLocation(EdgeLocation edge) {
		return facade.canPlaceInitialRoad(edge);
	}

	@Override
	public boolean canPlaceSettlementAtLocation(VertexLocation vertLoc) {
		return facade.canPlaceSettlement(vertLoc);
	}

	@Override
	public boolean canUpgradeSettlementAtLocation(VertexLocation vertLoc) {
		return false;
	}


	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return false;
	}

	@Override
	public boolean canOfferTrade() {
		return false;
	}

	@Override
	public boolean canAcceptTrade(TradeOffer tradeOffer) {
		return false;
	}

	@Override
	public boolean canBuyDevCard() {
		return false;
	}

	@Override
	public boolean canPlayDevCard() {
		return false;
	}


	@Override
	public boolean register(String username, String password) {
		return false;
	}

	@Override
	public boolean login(String username, String password) {
		return false;
	}

	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playMonumentCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void discard(Resources toDiscard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decreaseDomesticTradeResourceAmount(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void increaseDomesticTradeResourceAmount(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendTradeOffer(TradeOffer offer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsetResource(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean createNewGame(String title, boolean useRandomHexes, boolean useRandomNumbers, boolean useRandomPorts) {
		return false;
	}

	@Override
	public void startJoinGame(GameInfo game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void joinGame(CatanColor color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAI(String AIType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeRoad(EdgeLocation edgeLoc) {
		facade.placeFreeRoad(edgeLoc);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {
		facade.placeFreeSettlement(vertLoc);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeCity(VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RobPlayerInfo[] placeRobber(HexLocation hexLoc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playSoldierCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playRoadBuildingCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ResourceType> startMaritimeTrade() {
		return new ArrayList<ResourceType>();
		
	}

	@Override
	public void makeMaritimeTrade(ResourceType giveResource, ResourceType getResource, int ratio) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGetResource(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsetGetValue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsetGiveValue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildRoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildSettlement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildCity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int rollDice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void endTurn() {
		facade.endTurn();
	}
	
	@Override
	public Facade getFacade() {
		return facade;
	}

	public boolean isRoadPlaced() {
		return roadPlaced;
	}

	public void setRoadPlaced(boolean roadPlaced) {
		this.roadPlaced = roadPlaced;
	}

}
