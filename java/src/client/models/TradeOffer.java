package client.models;

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
	private Resources senderNeeds;
	
	/**
	 * Attribute senderOffers refers to resources the sender is willing to give
	 */
	private Resources senderOffers;

	
	public TradeOffer(int sender, int receiver, Resources senderNeeds,
			Resources senderOffers) {
		
		this.sender = sender;
		this.receiver = receiver;
		this.senderNeeds = senderNeeds;
		this.senderOffers = senderOffers;
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

	public Resources getSenderNeeds() {
		return senderNeeds;
	}

	public void setSenderNeeds(Resources senderNeeds) {
		this.senderNeeds = senderNeeds;
	}

	public Resources getSenderOffers() {
		return senderOffers;
	}

	public void setSenderOffers(Resources senderOffers) {
		this.senderOffers = senderOffers;
	}
}