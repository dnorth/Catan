package client.models;



public class DevCards{
	
	
private int monopolyCount;
private int monumentCount;
private int roadBuildingCount;
private int soldierCount;
private int yearOfPlentyCount;

public DevCards(int monopolyCount, int monumentCount, int roadBuildingCount, int soldierCount, int yearOfPlentyCount) {
	this.monopolyCount = monopolyCount;
	this.monumentCount = monumentCount;
	this.roadBuildingCount = roadBuildingCount;
	this.soldierCount = soldierCount;
	this.yearOfPlentyCount = yearOfPlentyCount;
}

public int getMonopolyCount() {
	return monopolyCount;
}

public void setMonopolyCount(int monopolyCount) {
	this.monopolyCount = monopolyCount;
}

public int getMonumentCount() {
	return monumentCount;
}

public void setMonumentCount(int monumentCount) {
	this.monumentCount = monumentCount;
}

public int getRoadBuildingCount() {
	return roadBuildingCount;
}

public void setRoadBuildingCount(int roadBuildingCount) {
	this.roadBuildingCount = roadBuildingCount;
}

public int getSoldierCount() {
	return soldierCount;
}

public void setSoldierCount(int soldierCount) {
	this.soldierCount = soldierCount;
}

public int getYearOfPlentyCount() {
	return yearOfPlentyCount;
}

public void setYearOfPlentyCount(int yearOfPlentyCount) {
	this.yearOfPlentyCount = yearOfPlentyCount;
}

@Override
public String toString()
{
	StringBuilder sb = new StringBuilder();
	appendStringType(sb, "monopoly", monopolyCount);
	appendStringType(sb, "monument", monumentCount);
	appendStringType(sb, "roadBuilding", roadBuildingCount);
	appendStringType(sb, "soldier", soldierCount);
	appendStringType(sb, "yearOfPlenty", yearOfPlentyCount);
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