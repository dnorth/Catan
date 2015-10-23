package client.state;

import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.data.GameInfo;
import client.data.RobPlayerInfo;
import client.facade.Facade;
import client.models.TradeOffer;

public class PlayerWaitingState implements IStateBase {

	private Facade facade;
	
	public PlayerWaitingState(Facade facade) {
		this.facade = facade;
	}
	
	@Override
	public boolean canJoinGame() {
		return facade.canJoinGame();
	}

	@Override
	public boolean canStartCreateNewGame() {
		return facade.canStartCreateNewGame();
	}

	@Override
	public boolean canStartPlayerWaitingView() {
		return facade.canStartPlayerWaitingView();
	}

	@Override
	public boolean canLogin() {
		return false;
	}

	@Override
	public boolean canPlaceRoadAtLocation(EdgeLocation edge) {
		return false;
	}

	@Override
	public boolean canPlaceSettlementAtLocation(VertexLocation vertLoc) {
		return false;
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
	public void playRoadBuildCard() {
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
	public void discard() {
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
	public void sendTradeOffer() {
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
		System.out.println("IN JOIN GAME STATE, CREATE NEW GAME FUNCTION");
		return facade.createNewGame(title, useRandomHexes, useRandomNumbers, useRandomPorts);
	}

	@Override
	public void startJoinGame(GameInfo game) {
		facade.startJoinGame(game);
	}

	@Override
	public void joinGame(CatanColor color) {
		facade.joinGame(color);
	}

	@Override
	public void addAI(String AIType) {
		facade.addAI(AIType);
	}

	@Override
	public void placeRoad(EdgeLocation edgeLoc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeCity(VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeRobber(HexLocation hexLoc) {
		// TODO Auto-generated method stub
		
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
	public void startMaritimeTrade() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeMaritimeTrade() {
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Facade getFacade() {
		return facade;
	}

}
