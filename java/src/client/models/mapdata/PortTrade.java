package client.models.mapdata;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.PortType;
import shared.definitions.ResourceType;
import client.models.Resources;
import client.models.TradeOffer;

public class PortTrade 
{
private int wheatCost;
private int woodCost;
private int sheepCost;
private int brickCost;
private int oreCost;

PortType type;

public PortTrade()
{
	wheatCost=4;
	woodCost =4;
	sheepCost=4;
	brickCost=4;
	oreCost  =4;
}
public void setTradeCosts(Port p)
{
		switch(p.getPortType())
		{
		case WHEAT: wheatCost= getMin(wheatCost,p.getRatio()); break;
		case WOOD:  woodCost=  getMin(woodCost,p.getRatio()); break;
		case SHEEP: sheepCost= getMin(sheepCost,p.getRatio()); break;
		case BRICK: brickCost= getMin(brickCost,p.getRatio()); break;
		case ORE:   oreCost=   getMin(oreCost,p.getRatio()); break;
		case THREE: 
			wheatCost= getMin(wheatCost, p.getRatio());
			woodCost= getMin(woodCost, p.getRatio());
			sheepCost= getMin(sheepCost, p.getRatio());
			brickCost= getMin(brickCost, p.getRatio());
			oreCost= getMin(oreCost, p.getRatio());
			break;
		}
}

public int getMin(int a, int b)
{
if(a<b){return a;}
else {return b;}

}
public int getWheatCost() {
	return wheatCost;
}
public int getWoodCost() {
	return woodCost;
}
public int getSheepCost() {
	return sheepCost;
}
public int getBrickCost() {
	return brickCost;
}
public int getOreCost() {
	return oreCost;
}
public PortType getType() {
	return type;
}


	
}
