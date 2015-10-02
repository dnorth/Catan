package client.models;



public class DevCards{
	
	
private int monopoly;
private int monument;
private int roadBuilding;
private int soldier;
private int yearOfPlenty;

public DevCards(int monopolyCount, int monumentCount, int roadBuildingCount, int soldierCount, int yearOfPlentyCount) {
	this.monopoly = monopolyCount;
	this.monument = monumentCount;
	this.roadBuilding = roadBuildingCount;
	this.soldier = soldierCount;
	this.yearOfPlenty = yearOfPlentyCount;
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

@Override
public String toString()
{
	StringBuilder sb = new StringBuilder();
	appendStringType(sb, "monopoly", monopoly);
	appendStringType(sb, "monument", monument);
	appendStringType(sb, "roadBuilding", roadBuilding);
	appendStringType(sb, "soldier", soldier);
	appendStringType(sb, "yearOfPlenty", yearOfPlenty);
	if(sb.length()==0)
	{sb.append("no DevCards");}
	return sb.toString();
}

private void appendStringType(StringBuilder sb,String type, int count)
{
	if(count>0)
	{sb.append(count + " "+ type +", ");}
}

}