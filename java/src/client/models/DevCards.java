package client.models;

import shared.definitions.DevCardType;



public class DevCards{
	
	
private int monopoly;
private int monument;
private int roadBuilding;
private int soldier;
private int yearOfPlenty;

public DevCards(){}
public DevCards(int monopolyCount, int monumentCount, int roadBuildingCount, int soldierCount, int yearOfPlentyCount) {
	this.monopoly = monopolyCount;
	this.monument = monumentCount;
	this.roadBuilding = roadBuildingCount;
	this.soldier = soldierCount;
	this.yearOfPlenty = yearOfPlentyCount;
}

public int getSpecifiedDevCardCount(DevCardType type) {
	switch (type) {
	case SOLDIER:
		return getSoldierCount();
	case YEAR_OF_PLENTY:
		return getYearOfPlentyCount();
	case MONOPOLY:
		return getMonopolyCount();
	case ROAD_BUILD:
		return getRoadBuildingCount();
	case MONUMENT:
		return getMonumentCount();
	}
	return -99;
}

public int getMonopolyCount() {
	return monopoly;
}

public void setMonopolyCount(int monopolyCount) {
	this.monopoly = monopolyCount;
}

public int getMonumentCount() {
	return monument;
}

public void setMonumentCount(int monumentCount) {
	this.monument = monumentCount;
}

public int getRoadBuildingCount() {
	return roadBuilding;
}

public void setRoadBuildingCount(int roadBuildingCount) {
	this.roadBuilding = roadBuildingCount;
}

public int getSoldierCount() {
	return soldier;
}

public void setSoldierCount(int soldierCount) {
	this.soldier = soldierCount;
}

public int getYearOfPlentyCount() {
	return yearOfPlenty;
}

public void setYearOfPlentyCount(int yearOfPlentyCount) {
	this.yearOfPlenty = yearOfPlentyCount;
}

public int getTotalCount()
{
	return monopoly + monument + roadBuilding + soldier + yearOfPlenty;	
}


@Override
public String toString() {
	return "DevCards [monopoly=" + monopoly + ", monument=" + monument + ", roadBuilding=" + roadBuilding + ", soldier="
			+ soldier + ", yearOfPlenty=" + yearOfPlenty + "]";
}

private void appendStringType(StringBuilder sb,String type, int count)
{
	if(count>0)
	{sb.append(count + " "+ type +", ");}
}

}