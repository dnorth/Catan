package client.maritime;

import java.util.List;
import java.util.Observable;

import shared.definitions.*;
import client.base.*;
import client.models.Resources;
import client.models.TradeOffer;
import client.models.mapdata.PortTrade;
import client.state.ActivePlayerState;
import client.state.StateManager;
import client.state.trading.TradeOfferedWaitingState;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	StateManager stateManager;
	ResourceType[] canGiveResourceTypes;
	ResourceType[] canGetResourceTypes;
	ResourceType giveResource;
	ResourceType getResource;
	int ratio;

	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay, StateManager stateManager) {

		super(tradeView);
		setTradeOverlay(tradeOverlay);
		this.stateManager= stateManager;
		this.stateManager.addObserver(this);
		System.out.println("Disabled maritime");
		this.getTradeView().enableMaritimeTrade(false);
	}

	public IMaritimeTradeView getTradeView() {
		return (IMaritimeTradeView)super.getView();
	}

	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {	
		getTradeOverlay().setTradeEnabled(false);	
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().hideGiveOptions();
	
		List<ResourceType> resourcesToTrade= stateManager.getState().startMaritimeTrade();

		this.canGiveResourceTypes = resourcesToTrade.toArray(new ResourceType[resourcesToTrade.size()]);
		getTradeOverlay().showGiveOptions(canGiveResourceTypes);
	
		List<ResourceType> banksAvailableResources =stateManager.getClientModel().getBank().getAvailableResourceTypes();
		this.canGetResourceTypes= banksAvailableResources.toArray(new ResourceType[banksAvailableResources.size()]);
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {
		stateManager.getState().makeMaritimeTrade(giveResource, getResource, ratio);
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		getResource=resource;
		getTradeOverlay().selectGetOption(resource, 1);
		getTradeOverlay().setTradeEnabled(true);
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		PortTrade p =stateManager.getClientModel().getPlayers()[stateManager.getFacade().getPlayerIndex()].getPortTrade();
		giveResource=resource;
		getTradeOverlay().selectGiveOption(resource, p.getCost(resource));
		ratio = p.getCost(resource);
		getTradeOverlay().showGetOptions(this.canGetResourceTypes);
	}

	@Override
	public void unsetGetValue() {
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().showGetOptions(this.canGetResourceTypes);
		getTradeOverlay().setTradeEnabled(false);

	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().hideGiveOptions();
		getTradeOverlay().showGiveOptions(canGiveResourceTypes);
		getTradeOverlay().setTradeEnabled(false);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(this.stateManager.getState() instanceof ActivePlayerState || stateManager.getState() instanceof TradeOfferedWaitingState) {
			this.getTradeView().enableMaritimeTrade(true);
		}
		else {
			this.getTradeView().enableMaritimeTrade(false);
		}
	}
}

