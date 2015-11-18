package client.models;

import server.model.ServerPlayer;
import shared.definitions.ResourceType;
import client.models.mapdata.PortTrade;



public class Player
{
	private int cities;
	private String color;
	private boolean discarded;
	private int monuments;
	private String name;
	private DevCards newDevCards;
	private DevCards oldDevCards;
	private int playerIndex;
	private boolean playedDevCard;
	private int playerID;
	private Resources resources;
	private int roads;
	private int settlements;
	private int soldiers;
	private int victoryPoints;
	private PortTrade portTrade;

	public Player(){
		newDevCards = new DevCards();
		oldDevCards = new DevCards();
		name="";
		resources = new Resources();
		portTrade = new PortTrade();
		cities = 4;
		settlements = 5;
		roads = 15;
	}

	public Player(ServerPlayer p){
		newDevCards = new DevCards();
		oldDevCards = new DevCards();
		name= p.getName();
		color =p.getColor();
		resources = new Resources();
		portTrade = new PortTrade();
		cities = 4;
		settlements = 5;
		roads = 15;
	}

//	newDevCards = new DevCards();
//	oldDevCards = new DevCards();
//	name="";
//	resources = new Resources();
//	portTrade = new PortTrade();
//	cities = 4;
//	settlements = 5;
//	roads = 15;
//	}
	
	public Player(ServerPlayer p, int playerIndex){
//		System.out.println("COLOR COLOR COLOR: " + p.getColor());
		playerID = p.getId();
		newDevCards = new DevCards();
		oldDevCards = new DevCards();
		name= p.getName();
		color =p.getColor();
		this.playerIndex = playerIndex;
		resources = new Resources();
		portTrade = new PortTrade();
		cities = 4;
		settlements = 5;
		roads = 15;
	}
	
	public int getCitiesNum() {
		return cities;
	}
	public void setCitiesNum(int citiesNum) {
		this.cities = citiesNum;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public boolean isDiscarded() {
		return discarded;
	}
	public void setDiscarded(boolean discarded) {
		this.discarded = discarded;
	}
	public int getMonuments() {
		return monuments;
	}
	public void setMonuments(int monuments) {
		this.monuments = monuments;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DevCards getNewDevCards() {
		return newDevCards;
	}
	public void setNewDevCards(DevCards newDevCards) {
		this.newDevCards = newDevCards;
	}
	public DevCards getCurrentDevCards() {
		return oldDevCards;
	}
	public void setCurrentDevCards(DevCards oldDevCards) {
		this.oldDevCards = oldDevCards;
	}
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public boolean isPlayedDevCard() {
		return playedDevCard;
	}
	public void setPlayedDevCard(boolean playedDevCard) {
		this.playedDevCard = playedDevCard;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public Resources getResources() {
		return resources;
	}
	public void setResources(Resources resources) {
		this.resources = resources;
	}
	public int getRoads() {
		return roads;
	}
	public void setRoads(int roads) {
		this.roads = roads;
	}
	public int getSettlements() {
		return settlements;
	}
	public void setSettlements(int settlements) {
		this.settlements = settlements;
	}
	public int getSoldiers() {
		return soldiers;
	}
	public void setSoldiers(int soldiers) {
		this.soldiers = soldiers;
	}
	public int getVictoryPoints() {
		return victoryPoints;
	}
	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}
	public int getCities() {
		return cities;
	}

	public void setCities(int cities) {
		this.cities = cities;
	}

	public DevCards getOldDevCards() {
		return oldDevCards;
	}

	public void setOldDevCards(DevCards oldDevCards) {
		this.oldDevCards = oldDevCards;
	}

	public PortTrade getPortTrade() {
		return portTrade;
	}

	public void setPortTrade(PortTrade portTrade) {
		this.portTrade = portTrade;
	}

	public int numResources() {
		return this.resources.getTotalCount();
	}



	public boolean hasBrick(){return resources.getBrickCount()>0;}
	public boolean hasOre(){return resources.getOreCount()>0;}
	public boolean hasSheep(){return resources.getSheepCount()>0;}
	public boolean hasWheat(){return resources.getWheatCount()>0;}
	public boolean hasWood(){return resources.getWoodCount()>0;}

	public boolean canBuyRoad(){
		return hasBrick() && hasWood() && roads > 0;
	}

	public boolean canBuySettlement(){
		return hasBrick() && hasWheat() && hasSheep() && hasWood() && settlements > 0;
	}

	public boolean canBuyDevCard(){
		return hasSheep() && hasWheat() && hasOre();
	}

	public boolean canBuildCity(){
		return hasWheat(2) && hasOre(3) && cities > 0;
	}

	public boolean canAcceptTrade(TradeOffer tradeOffer){	
		return hasSpecifiedResources(tradeOffer);
	}

	public boolean hasSpecifiedResources(TradeOffer tradeOffer){	

		Resources offer = tradeOffer.getOffer();

		return hasBrick(offer.getBrickCount()*-1) && 
				hasOre(offer.getOreCount()*-1)     && 
				hasSheep(offer.getSheepCount()*-1) && 
				hasWheat(offer.getWheatCount()*-1) &&
				hasWood(offer.getWoodCount()*-1);
	}

	public boolean needsToDiscard(){
		if(resources.getTotalCount()>7){
			return true;
		}
		else{
			return false;
		}

	}
	public void payForRoad(Resources bank){
		resources.subtractResource(ResourceType.BRICK, 1, bank);
		resources.subtractResource(ResourceType.WOOD, 1, bank);
	}

	public void payForSettlement(Resources bank){
		resources.subtractResource(ResourceType.BRICK, 1, bank);
		resources.subtractResource(ResourceType.WHEAT, 1, bank);
		resources.subtractResource(ResourceType.SHEEP, 1, bank);
		resources.subtractResource(ResourceType.WOOD, 1, bank);
	}

	public void payForDevCard(Resources bank){
		resources.subtractResource(ResourceType.SHEEP, 1, bank);
		resources.subtractResource(ResourceType.WHEAT, 1, bank);
		resources.subtractResource(ResourceType.ORE, 1, bank);
	}

	public void payForCity(Resources bank){
		resources.subtractResource(ResourceType.WHEAT, 2,bank);
		resources.subtractResource(ResourceType.ORE, 3,bank);
	}

	public boolean hasResource(){return hasBrick() || hasOre() || hasSheep() || hasWheat() || hasWood();}

	public boolean hasBrick(int count){return resources.getBrickCount()>=count;}
	public boolean hasOre(int count){return resources.getOreCount()>=count;}
	public boolean hasSheep(int count){return resources.getSheepCount()>=count;}
	public boolean hasWheat(int count){return resources.getWheatCount()>=count;}
	public boolean hasWood(int count){return resources.getWoodCount()>=count;}

	public boolean hasMonopolyCard(DevCards d){return d.getMonopolyCount()>0;}
	public boolean hasMonumentCard(DevCards d){return d.getMonumentCount()>0;}
	public boolean hasRoadBuildingCard(DevCards d){return d.getRoadBuildingCount()>0;}
	public boolean hasSoldierCard(DevCards d){return d.getSoldierCount()>0;}
	public boolean hasYearOfPlentyCard(DevCards d){return d.getYearOfPlentyCount()>0;}

	public void incMonuments(){monuments++;}
	public void decSettlements(){settlements--;}
	public void decCities(){cities--;}
	public void decRoads(){roads--;}

	@Override
	public String toString() {
		return "Player [cities=" + cities + ", color=" + color + ", discarded=" + discarded + ", monuments=" + monuments
				+ ", name=" + name + ", newDevCards=" + newDevCards + ", oldDevCards=" + oldDevCards + ", playerIndex="
				+ playerIndex + ", playedDevCard=" + playedDevCard + ", playerID=" + playerID + ", resources="
				+ resources + ", roads=" + roads + ", settlements=" + settlements + ", soldiers=" + soldiers
				+ ", victoryPoints=" + victoryPoints + "]";
	}

	public void transferDevCards() {
		this.oldDevCards.addDevCards(newDevCards);
		this.newDevCards = new DevCards();
	}



}