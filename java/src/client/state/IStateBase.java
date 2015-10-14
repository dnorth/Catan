package client.state;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import client.models.TradeOffer;

interface IStateBase {

	public boolean canJoinGame();
	public boolean canStartCreateNewGame();
	public boolean canStartPlayerWaitingView();
	public boolean canLogin();
	public boolean canBuyRoad(int playerIndex);
	public boolean canPlaceRoadAtLocation(int playerIndex, EdgeLocation edge);
	public boolean canBuySettlement(int playerIndex);
	public boolean canPlaceSettlementAtLocation(int playerIndex, VertexLocation vertLoc);
	public boolean canUpgradeSettlement(int playerIndex);
	public boolean canUpgradeSettlementAtLocation(int playerIndex, VertexLocation vertLoc);
	public boolean canPlaceRobber(HexLocation hexLoc);
	
	public boolean canOfferTrade(int playerIndex);
	public boolean canAcceptTrade(int playerIndex, TradeOffer tradeOffer);
	public boolean canMaritimeTrade(int playerIndex);
	public boolean canBuyDevCard(int playerIndex);
	public boolean canPlayDevCard(int playerIndex);
}
