package client.models;

import shared.definitions.ResourceType;

/**
 * TradeOffer object contains info for trading
 *
 */

public class TradeOffer {

	/**
	 *  Attribute sender refers to player number making offer
	 */
	private int sender;
	
	/**
	 * Attribute receiver refers to player number receiving offer
	 */
	private int receiver;
	
	/**
	 *  Attribute senderNeeds refers to resources the sender needs
	 */
	private Resources offer;
	
	public TradeOffer(int sender, int receiver, Resources offer) {
		
		this.sender = sender;
		this.receiver = receiver;
		this.offer = offer;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public Resources getOffer() {
		return offer;
	}

	public void setOffer(Resources offer) {
		this.offer = offer;
	}
	
	public int getOreCount() {
		return offer.getOreCount();
	}
	
	public int getWoodCount() {
		return offer.getWoodCount();
	}
	
	public int getWheatCount() {
		return offer.getWheatCount();
	}
	
	public int getBrickCount() {
		return offer.getBrickCount();
	}
	
	public int getSheepCount() {
		return offer.getSheepCount();
	}
	
}