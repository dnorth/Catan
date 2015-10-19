package client.state;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.TradeOffer;

public class GameOverState implements IStateBase {

	@Override
	public boolean canJoinGame() {
		return false;
	}

	@Override //Is this right??
	public boolean canStartCreateNewGame() {
		return false;
	}

	@Override //Is this right??
	public boolean canStartPlayerWaitingView() {
		return false;
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

}
