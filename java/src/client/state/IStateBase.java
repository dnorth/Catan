package client.state;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.facade.Facade;
import client.models.TradeOffer;

abstract class IStateBase {
	
	static protected Facade facade = new Facade(null);
	
	public abstract boolean canJoinGame();
	public abstract boolean canStartCreateNewGame();
	public abstract boolean canStartPlayerWaitingView();
	public abstract boolean canLogin();
	public abstract boolean canPlaceRoadAtLocation(EdgeLocation edge);
	public abstract boolean canPlaceSettlementAtLocation(VertexLocation vertLoc);
	public abstract boolean canUpgradeSettlementAtLocation(VertexLocation vertLoc);
	public abstract boolean canPlaceRobber(HexLocation hexLoc);
	
	public abstract boolean canOfferTrade(int playerIndex);
	public abstract boolean canAcceptTrade(int playerIndex, TradeOffer tradeOffer);
	public abstract boolean canMaritimeTrade(int playerIndex);
	public abstract boolean canBuyDevCard(int playerIndex);
	public abstract boolean canPlayDevCard(int playerIndex);
}
