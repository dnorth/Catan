package client.maritime;

import java.util.List;
import java.util.Observable;

import shared.definitions.*;
import client.base.*;
import client.models.Resources;
import client.models.TradeOffer;
import client.state.StateManager;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	StateManager stateManager;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay, StateManager stateManager) {
		
		super(tradeView);
		this.stateManager= stateManager;
		setTradeOverlay(tradeOverlay);
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
		getTradeOverlay().showModal();
		getTradeOverlay().setTradeEnabled(false);
		List<ResourceType> resourcesToTrade= stateManager.getFacade().startMaritimeTrade();
		getTradeOverlay().showGetOptions((ResourceType[]) resourcesToTrade.toArray());
	}

	@Override
	public void makeTrade() {
		stateManager.getState().makeMaritimeTrade();
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
	}

	@Override
	public void setGiveResource(ResourceType resource) {

	}

	@Override
	public void unsetGetValue() {

	}

	@Override
	public void unsetGiveValue() {

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

