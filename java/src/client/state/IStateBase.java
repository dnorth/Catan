package client.state;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.facade.Facade;
import client.models.TradeOffer;

public interface IStateBase {
	
	public abstract boolean canJoinGame();
	public abstract boolean canStartCreateNewGame();
	public abstract boolean canStartPlayerWaitingView();
	public abstract boolean canLogin();
	public abstract boolean register(String username, String password);
	public abstract boolean login(String username, String password);
	public abstract boolean canPlaceRoadAtLocation(EdgeLocation edge);
	public abstract boolean canPlaceSettlementAtLocation(VertexLocation vertLoc);
	public abstract boolean canUpgradeSettlementAtLocation(VertexLocation vertLoc);
	public abstract boolean canPlaceRobber(HexLocation hexLoc);
	public abstract boolean canOfferTrade();
	public abstract boolean canAcceptTrade(TradeOffer tradeOffer);
	public abstract boolean canBuyDevCard();
	public abstract boolean canPlayDevCard();
}
