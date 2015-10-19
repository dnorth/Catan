package client.state;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.facade.Facade;
import client.models.TradeOffer;

public class SetupOneActivePlayerState implements IStateBase {
	
	Facade facade;
	
	public SetupOneActivePlayerState(Facade facade) {
		this.facade = facade;
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
