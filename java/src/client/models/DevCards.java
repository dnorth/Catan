package client.models;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.DevCard;
import shared.definitions.DevCardType;



public class DevCards{
	
	
private int monopoly;
private int monument;
private int roadBuilding;
private int soldier;
private int yearOfPlenty;

public DevCards() {
	this.monopoly = 0;
	this.monument = 0;
	this.roadBuilding = 0;
	this.soldier = 0;
	this.yearOfPlenty = 0;
}
public DevCards(int monopolyCount, int monumentCount, int roadBuildingCount, int soldierCount, int yearOfPlentyCount) {
	this.monopoly = monopolyCount;
	this.monument = monumentCount;
	this.roadBuilding = roadBuildingCount;
	this.soldier = soldierCount;
	this.yearOfPlenty = yearOfPlentyCount;
}

public List<DevCard> getDevCardTypes(){
	List<DevCard> devCardTypes = new ArrayList<>();
	
	for(int i=0; i<monopoly; i++)
	{devCardTypes.add(DevCard.MONOPOLY);}
	
	for(int i=0; i<monument; i++)
	{devCardTypes.add(DevCard.MONUMENT);}
	
	for(int i=0; i<roadBuilding; i++)
	{devCardTypes.add(DevCard.ROADBUILDING);}
	
	for(int i=0; i<soldier; i++)
	{devCardTypes.add(DevCard.SOLDIER);}
	
	for(int i=0; i<yearOfPlenty; i++)
	{devCardTypes.add(DevCard.YEAROFPLENTY);}
	return devCardTypes;
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

public void addDevCards(DevCards devCards) {
	this.monopoly += devCards.monopoly;
	this.monument += devCards.monument;
	this.roadBuilding += devCards.roadBuilding;
	this.soldier += devCards.soldier;
	this.yearOfPlenty += devCards.yearOfPlenty;
}

public void addSpecifiedDevCard(DevCard type, int amount) {
	switch (type) {
	case SOLDIER:
		soldier+=amount; break;
	case YEAROFPLENTY:
		yearOfPlenty+=amount; break;
	case MONOPOLY:
		monopoly+=amount; break;
	case ROADBUILDING:
		roadBuilding+=amount; break;
	case MONUMENT:
		monument+=amount; break;
	}
}

public void decSpecifiedDevCard(DevCard type) {
	switch (type) {
	case SOLDIER:
		soldier--; break;
	case YEAROFPLENTY:
		yearOfPlenty--; break;
	case MONOPOLY:
		monopoly--; break;
	case ROADBUILDING:
		roadBuilding--; break;
	case MONUMENT:
		monument--; break;
	}
}

public boolean hasMonopoly(){return monopoly>0;}
public boolean hasMonument(){return monument>0;}
public boolean hasRoadBuilding(){return roadBuilding>0;}
public boolean hasSoldier(){return soldier>0;}
public boolean hasYearOfPlenty(){return yearOfPlenty>0;}

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