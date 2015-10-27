package client.models;

import java.util.List;
import java.util.ArrayList;

import shared.definitions.ResourceType;

public class Resources{
	
	private int wood;
	private int brick;
	private int sheep;
	private int wheat;
	private int ore;
	
	public Resources(int wood, int brick, int sheep, int wheat, int ore) {
		this.wood = wood;
		this.brick = brick;
		this.sheep = sheep;
		this.wheat = wheat;
		this.ore = ore;
	}
	
	public Resources(){
		this.wood = 0;
		this.brick = 0;
		this.sheep = 0;
		this.wheat = 0;
		this.ore = 0;
	}
	
	public List<ResourceType> getAvailableResourceTypes()
	{
		List<ResourceType> r = new ArrayList<ResourceType>();
		if(wood>0){r.add(ResourceType.WOOD);}
		if(brick>0){r.add(ResourceType.BRICK);}
		if(sheep>0){r.add(ResourceType.SHEEP);}
		if(wheat>0){r.add(ResourceType.WHEAT);}
		if(ore>0){r.add(ResourceType.ORE);}
		return r;
	}
	public int getAmountOfSpecificResource(ResourceType type) {
		switch(type) {
		case BRICK: return this.brick;
		case ORE: return this.ore;
		case SHEEP: return this.sheep;
		case WHEAT: return this.wheat;
		case WOOD: return this.wood;
		default: return -1;
		}
	}
	
	public void addOne(ResourceType type) {
		switch(type) {
		case BRICK:
			this.brick++;
			break;
		case ORE:
			this.ore++;
			break;
		case SHEEP:
			this.sheep++;
			break;
		case WHEAT:
			this.wheat++;
			break;
		case WOOD:
			this.wood++;
			break;
		default:
			break;
		}
	}
	
	public void subtractOne(ResourceType type) {
		switch(type) {
		case BRICK:
			this.brick--;
			break;
		case ORE:
			this.ore--;
			break;
		case SHEEP:
			this.sheep--;
			break;
		case WHEAT:
			this.wheat--;
			break;
		case WOOD:
			this.wood--;
			break;
		default:
			break;	
		}
	}
	
	public void unsetResource(ResourceType type) {
		switch(type) {
		case BRICK:
			this.brick = 0;
			break;
		case ORE:
			this.ore = 0;
			break;
		case SHEEP:
			this.sheep = 0;
			break;
		case WHEAT:
			this.wheat = 0;
			break;
		case WOOD:
			this.wood = 0;
			break;
		default:
			break;	
		}
	}
	
	public int getResourceCount(ResourceType resource) {
		switch (resource) {
		case WOOD:
			return getWoodCount();
		case BRICK:
			return getBrickCount();
		case SHEEP:
			return getSheepCount();
		case WHEAT:
			return getWheatCount();
		case ORE:
			return getOreCount();
		default:
			return -1;
		}
	}

	public int getWoodCount() {
		return wood;
	}

	public int getBrickCount() {
		return brick;
	}

	public int getSheepCount() {
		return sheep;
	}

	public int getWheatCount() {
		return wheat;
	}

	public int getOreCount() {
		return ore;
	}


	public void setWood(int wood) {
		this.wood = wood;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public int getTotalCount()
	{
		return wood+brick+sheep+wheat+ore;
	}
	@Override
	public String toString() {
		return "Resources [wood=" + wood + ", brick=" + brick + ", sheep=" + sheep + ", wheat=" + wheat + ", ore=" + ore
				+ "]";
	}

	private void appendStringType(StringBuilder sb,String type, int count)
	{
		if(count>0)
		{sb.append(count + " "+ type +", ");}
	}
	
	public void invert() {
		this.wood *= -1;
		this.brick *= -1;
		this.sheep *= -1;
		this.wheat *= -1;
		this.ore *= -1;
	}
	
}