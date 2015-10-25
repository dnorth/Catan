package client.domestic;

import java.util.Observable;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import client.state.IStateBase;
import client.state.InactivePlayerState;
import client.state.StateManager;
import client.state.trading.OfferingTradeState;
import client.state.trading.TradeOfferedWaitingState;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private StateManager stateManager;

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay, StateManager stateManager) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
		this.stateManager = stateManager;
		this.stateManager.addObserver(this);
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {

		this.stateManager.setState(new OfferingTradeState(this.stateManager.getFacade()));
		getTradeOverlay().showModal();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		IStateBase state = this.stateManager.getState();
		state.decreaseAmount(resource);
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		IStateBase state = this.stateManager.getState();
		state.increaseAmount(resource);
	}

	@Override
	public void sendTradeOffer() {
		IStateBase state = this.stateManager.getState();
		getTradeOverlay().closeModal();
		state.sendTradeOffer();
		this.stateManager.setState(new TradeOfferedWaitingState(this.stateManager.getFacade()));
		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		IStateBase state = this.stateManager.getState();
		state.setPlayerToTradeWith(playerIndex);
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		IStateBase state = this.stateManager.getState();
		state.setResourceToReceive(resource);
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		IStateBase state = this.stateManager.getState();
		state.setResourceToSend(resource);
	}

	@Override
	public void unsetResource(ResourceType resource) {
		IStateBase state = this.stateManager.getState();
		state.unsetResource(resource);
	}

	@Override
	public void cancelTrade() {
		this.stateManager.getFacade().cancelTradeOffer();		
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		IStateBase state = this.stateManager.getState();
		state.acceptTrade(willAccept);
		this.stateManager.setState(new InactivePlayerState(this.stateManager.getFacade()));
		getAcceptOverlay().closeModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(this.stateManager.getState() instanceof TradeOfferedWaitingState) {
			if(this.stateManager.getClientModel().getTradeOffer() == null) {
				this.stateManager.setState(new InactivePlayerState(this.stateManager.getFacade())); //This may be completely unnecessary and maybe we should set them to ActivePlayerState here??
			}
		}
	}

}

