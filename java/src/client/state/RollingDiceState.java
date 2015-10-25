package client.state;

import server.proxy.ClientException;
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

public class RollingDiceState implements IStateBase {

	private Facade facade;
	
	@Override
	public Facade getFacade() {
		return this.facade;
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
	public boolean register(String username, String password) throws ClientException {
		return false;
	}

	@Override
	public boolean login(String username, String password) throws ClientException {
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
	public void sendMessage(String message) {}

	@Override
	public void playMonopolyCard(ResourceType resource) {}

	@Override
	public void playMonumentCard() {}

	@Override
	public void playRoadBuildingCard() {}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {}

	@Override
	public void increaseAmount(ResourceType resource) {}

	@Override
	public void decreaseAmount(ResourceType resource) {}

	@Override
	public void discard() {}

	@Override
	public void decreaseDomesticTradeResourceAmount(ResourceType resource) {}

	@Override
	public void increaseDomesticTradeResourceAmount(ResourceType resource) {}

	@Override
	public void sendTradeOffer() {}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {}

	@Override
	public void setResourceToReceive(ResourceType resource) {}

	@Override
	public void setResourceToSend(ResourceType resource) {}

	@Override
	public void unsetResource(ResourceType resource) {}

	@Override
	public void acceptTrade(boolean willAccept) {}

	@Override
	public boolean createNewGame(String title, boolean useRandomHexes, boolean useRandomNumbers, boolean useRandomPorts) {
		return false;
	}

	@Override
	public void startJoinGame(GameInfo game) {}

	@Override
	public void joinGame(CatanColor color) {}

	@Override
	public void addAI(String AIType) {}

	@Override
	public void placeRoad(EdgeLocation edgeLoc) {}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {}

	@Override
	public void placeCity(VertexLocation vertLoc) {}

	@Override
	public void placeRobber(HexLocation hexLoc) {}

	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {}

	@Override
	public void playSoldierCard() {}

	@Override
	public void robPlayer(RobPlayerInfo victim) {}

	@Override
	public void startMaritimeTrade() {}

	@Override
	public void makeMaritimeTrade() {}

	@Override
	public void setGetResource(ResourceType resource) {}

	@Override
	public void setGiveResource(ResourceType resource) {}

	@Override
	public void unsetGetValue() {}

	@Override
	public void unsetGiveValue() {}

	@Override
	public void buildRoad() {}

	@Override
	public void buildSettlement() {}

	@Override
	public void buildCity() {}

	@Override
	public void buyCard() {}

	@Override
	public void playCard() {}

	@Override
	public int rollDice() {
		return facade.rollDice();
	}

	@Override
	public void endTurn() {}

}
