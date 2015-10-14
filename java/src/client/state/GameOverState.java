package client.state;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.TradeOffer;

public class GameOverState implements IStateBase {

	@Override
	public boolean canJoinGame() {
		// TODO Auto-generated method stub
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

	@Override
	public boolean register(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
