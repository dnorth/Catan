package client.models;

/**
 * TradeOffer object contains info for trading
 *
 */

public class TradeOffer {
	/**
	 *  Attribute sender refers to player number making offer
	 */
	int sender;
	
	/**
	 * Attribute receiver refers to player number receiving offer
	 */
	int receiver;
	
	/**
	 *  Attribute senderNeeds refers to resources the sender needs
	 */
	ResourceList senderNeeds;
	
	/**
	 * Attribute senderOffers refers to resources the sender is willing to give
	 */
	ResourceList senderOffers;
}