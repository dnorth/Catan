package client.models;

import java.util.List;

import shared.locations.EdgeDirection;
import client.models.mapdata.Hex;



public class Player
{
	private int citiesNum;
	private String color;
	private boolean discarded;
	private int monuments;
	private String name;
	private DevCards newDevCards;
	private DevCards currentDevCards;
	private int playerIndex;
	private boolean playedDevCard;
	private int playerID;
	private Resources resources;
	private int roads;
	private int settlements;
	private int soldiers;
	private int victoryPoints;
	
	public int getCitiesNum() {
		return citiesNum;
	}
	public void setCitiesNum(int citiesNum) {
		this.citiesNum = citiesNum;
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
		return currentDevCards;
	}
	public void setOldDevCards(DevCards oldDevCards) {
		this.currentDevCards = oldDevCards;
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
	
	public boolean hasBrick(){return resources.getBrickCount()>0;}
	public boolean hasOre(){return resources.getOreCount()>0;}
	public boolean hasSheep(){return resources.getSheepCount()>0;}
	public boolean hasWheat(){return resources.getWheatCount()>0;}
	public boolean hasWood(){return resources.getWoodCount()>0;}
	
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

}