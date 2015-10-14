package client.state;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.models.TradeOffer;

public class SetupOneActivePlayerState extends IStateBase {

	@Override
	public boolean canJoinGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStartCreateNewGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStartPlayerWaitingView() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canLogin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBuyRoad(int playerIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPlaceRoadAtLocation(int playerIndex, EdgeLocation edge) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBuySettlement(int playerIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPlaceSettlementAtLocation(int playerIndex,
			VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUpgradeSettlement(int playerIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUpgradeSettlementAtLocation(int playerIndex,
			VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canOfferTrade(int playerIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canAcceptTrade(int playerIndex, TradeOffer tradeOffer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMaritimeTrade(int playerIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBuyDevCard(int playerIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPlayDevCard(int playerIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
