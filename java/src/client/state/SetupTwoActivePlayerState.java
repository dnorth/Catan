package client.state;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.TradeOffer;

public class SetupTwoActivePlayerState extends IStateBase {

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
		return facade.canPlaceRoad(edge);
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
	public boolean canOfferTrade(int playerIndex) {
		return false;
	}

	@Override
	public boolean canAcceptTrade(int playerIndex, TradeOffer tradeOffer) {
		return false;
	}

	@Override
	public boolean canBuyDevCard(int playerIndex) {
		return false;
	}

	@Override
	public boolean canPlayDevCard(int playerIndex) {
		return false;
	}

}
