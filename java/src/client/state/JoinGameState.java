package client.state;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.facade.Facade;
import client.models.TradeOffer;

public class JoinGameState implements IStateBase {

	Facade facade;
	
	public JoinGameState(Facade facade) {
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
