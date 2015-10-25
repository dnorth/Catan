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
	private Resources senderNeeds;
	
	/**
	 * Attribute senderOffers refers to resources the sender is willing to give
	 */
	private Resources senderOffers;
	
	/**
	 * These next functions set whether the given resource is being sent, or received. -1 == sent; 0 = neither; 1 = received
	 */
	private int toSendBrick;
	private int toSendWood;
	private int toSendSheep;
	private int toSendWheat;
	private int toSendOre;
	
	public TradeOffer(int sender, int receiver, Resources senderNeeds, Resources senderOffers) {
		
		this.sender = sender;
		this.receiver = receiver;
		this.senderNeeds = senderNeeds;
		this.senderOffers = senderOffers;
		this.toSendBrick = 0;
		this.toSendWood = 0;
		this.toSendSheep = 0;
		this.toSendWheat = 0;
		this.toSendOre = 0;
	}
	
	/**
	 * 
	 * @param type
	 * @param whoReceives will be an int. -1 = sender offers this resource, 0 = neither will receive, 1 = sender will receive
	 */
	public void setResourceReceiver(ResourceType type, int whoReceives) {
		switch(type) {
		case BRICK:
			if(whoReceives == -1) {
				senderNeeds.setBrick(0);
			}
			else if (whoReceives == 1) {
				senderOffers.setBrick(0);
			}
			else {
				senderNeeds.setBrick(0);
				senderOffers.setBrick(0);
			}
			toSendBrick = whoReceives;
			break;
		case ORE:
			if(whoReceives == -1) {
				senderNeeds.setOre(0);
			}
			else if (whoReceives == 1) {
				senderOffers.setOre(0);
			}
			else {
				senderNeeds.setOre(0);
				senderOffers.setOre(0);
			}
			toSendOre = whoReceives;
			break;
		case SHEEP:
			if(whoReceives == -1) {
				senderNeeds.setSheep(0);
			}
			else if (whoReceives == 1) {
				senderOffers.setSheep(0);
			}
			else {
				senderNeeds.setSheep(0);
				senderOffers.setSheep(0);
			}
			toSendSheep = whoReceives;
			break;
		case WHEAT:
			if(whoReceives == -1) {
				senderNeeds.setWheat(0);
			}
			else if (whoReceives == 1) {
				senderOffers.setWheat(0);
			}
			else {
				senderNeeds.setWheat(0);
				senderOffers.setWheat(0);
			}
			toSendWheat = whoReceives;
			break;
		case WOOD:
			if(whoReceives == -1) {
				senderNeeds.setWood(0);
			}
			else if (whoReceives == 1) {
				senderOffers.setWood(0);
			}
			else {
				senderNeeds.setWood(0);
				senderOffers.setWood(0);
			}
			toSendWood = whoReceives;
			break;
		default:
			break;
		
		}
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
	
	public void unsetResource(ResourceType type) {
		switch(type) {
		case BRICK:
			if(toSendBrick == 1) {
				senderNeeds.setBrick(0);
			}
			else if(toSendBrick == -1) {
				senderOffers.setBrick(0);
			}
			this.toSendBrick = 0;
			break;
		case ORE:
			if(toSendOre == 1) {
				senderNeeds.setOre(0);
			}
			else if(toSendOre == -1) {
				senderOffers.setOre(0);
			}
			this.toSendOre = 0;
			break;
		case SHEEP:
			if(toSendOre == 1) {
				senderNeeds.setOre(0);
			}
			else if(toSendOre == -1) {
				senderOffers.setOre(0);
			}
			this.toSendSheep = 0;
			break;
		case WHEAT:
			if(toSendWheat == 1) {
				senderNeeds.setWheat(0);
			}
			else if(toSendWheat == -1) {
				senderOffers.setWheat(0);
			}
			this.toSendWheat = 0;
			break;
		case WOOD:
			if(toSendWood == 1) {
				senderNeeds.setWood(0);
			}
			else if(toSendWood == -1) {
				senderOffers.setWood(0);
			}
			this.toSendWood = 0;
			break;
		default:
			break;
		}
	}
	
	public void addOne(ResourceType type) {
		switch(type) {
		case BRICK:
			if(toSendBrick == 1) {
				senderNeeds.addOne(type);
			}
			else if (toSendBrick == -1) {
				senderOffers.addOne(type);
			}
			break;
		case ORE:
			if(toSendOre == 1) {
				senderNeeds.addOne(type);
			}
			else if(toSendOre == -1) {
				senderOffers.addOne(type);
			}
			break;
		case SHEEP:
			if(toSendSheep == 1) {
				senderNeeds.addOne(type);
			}
			else if(toSendSheep == -1) {
				senderOffers.addOne(type);
			}
			break;
		case WHEAT:
			if(toSendWheat == 1) {
				senderNeeds.addOne(type);
			}
			else if(toSendWheat == -1) {
				senderOffers.addOne(type);
			}
			break;
		case WOOD:
			if(toSendWood == 1) {
				senderNeeds.addOne(type);
			}
			else if(toSendWood == -1) {
				senderOffers.addOne(type);
			}
			break;
		default:
			break;
		}
	}
	
	public void subtractOne(ResourceType type) {
		switch(type) {
		case BRICK:
			if(toSendBrick == 1) {
				senderNeeds.subtractOne(type);
			}
			else if (toSendBrick == -1) {
				senderOffers.subtractOne(type);
			}
			break;
		case ORE:
			if(toSendOre == 1) {
				senderNeeds.subtractOne(type);
			}
			else if(toSendOre == -1) {
				senderOffers.subtractOne(type);
			}
			break;
		case SHEEP:
			if(toSendSheep == 1) {
				senderNeeds.subtractOne(type);
			}
			else if(toSendSheep == -1) {
				senderOffers.subtractOne(type);
			}
			break;
		case WHEAT:
			if(toSendWheat == 1) {
				senderNeeds.subtractOne(type);
			}
			else if(toSendWheat == -1) {
				senderOffers.subtractOne(type);
			}
			break;
		case WOOD:
			if(toSendWood == 1) {
				senderNeeds.subtractOne(type);
			}
			else if(toSendWood == -1) {
				senderOffers.subtractOne(type);
			}
			break;
		default:
			break;
		}
	}
	
	public int getBrickCount() {
		if(toSendBrick == -1){
			return senderOffers.getBrickCount();
		}
		else if (toSendBrick == 1) {
			return senderNeeds.getBrickCount();
		}
		else {
			return 0;
		}
	}
	
	public int getWoodCount() {
		if(toSendWood == -1){
			return senderOffers.getWoodCount();
		}
		else if (toSendWood == 1) {
			return senderNeeds.getWoodCount();
		}
		else {
			return 0;
		}
	}
	
	public int getWheatCount() {
		if(toSendWheat == -1){
			return senderOffers.getWheatCount();
		}
		else if (toSendWheat == 1) {
			return senderNeeds.getWheatCount();
		}
		else {
			return 0;
		}
	}
	
	public int getOreCount() {
		if(toSendOre == -1){
			return senderOffers.getOreCount();
		}
		else if (toSendOre == 1) {
			return senderNeeds.getOreCount();
		}
		else {
			return 0;
		}
	}
	
	public int getSheepCount() {
		if(toSendSheep == -1){
			return senderOffers.getSheepCount();
		}
		else if (toSendSheep == 1) {
			return senderNeeds.getSheepCount();
		}
		else {
			return 0;
		}
	}
}