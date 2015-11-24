package client.models;

import java.util.List;
import java.util.ArrayList;

import server.exceptions.InsufficientResourcesException;
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

	public Resources(Resources offer) {
		wood = offer.wood;
		brick = offer.brick;
		sheep = offer.sheep;
		wheat = offer.wheat;
		ore = offer.ore;
	}

	public List<ResourceType> getResourceTypes()
	{
		List<ResourceType> types = new ArrayList<>();

		for(int i=0; i< wood; i++)
		{types.add(ResourceType.WOOD);}

		for(int i=0; i< brick; i++)
		{types.add(ResourceType.BRICK);}

		for(int i=0; i< sheep; i++)
		{types.add(ResourceType.SHEEP);}

		for(int i=0; i< wheat; i++)
		{types.add(ResourceType.WHEAT);}

		for(int i=0; i< ore; i++)
		{types.add(ResourceType.ORE);}

		return types;
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

	public void addOne(ResourceType type){
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

	public void subtractOne(ResourceType type){
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

	public void addResource(ResourceType type, int amount, Resources takeFrom) throws InsufficientResourcesException{
		if(amount>takeFrom.getAmountOfSpecificResource(type))
		{throw new InsufficientResourcesException();}
		
		switch(type) {
		case BRICK:
			this.brick+=amount;
			takeFrom.brick-=amount;
			break;
		case ORE:
			this.ore+=amount;
			takeFrom.ore-=amount;
			break;
		case SHEEP:
			this.sheep+=amount;
			takeFrom.sheep-=amount;
			break;
		case WHEAT:
			this.wheat+=amount;
			takeFrom.wheat-=amount;
			break;
		case WOOD:
			this.wood+=amount;
			takeFrom.wood-=amount;
			break;
		default:
			break;
		}
	}

	public void subtractResource(ResourceType type, int amount, Resources giveTo) throws InsufficientResourcesException{
		if(amount>this.getAmountOfSpecificResource(type))
		{throw new InsufficientResourcesException();}
		
		switch(type) {
		case BRICK:
			this.brick-=amount;
			giveTo.brick+=amount;
			break;
		case ORE:
			this.ore-=amount;
			giveTo.ore+=amount;
			break;
		case SHEEP:
			this.sheep-=amount;
			giveTo.sheep+=amount;
			break;
		case WHEAT:
			this.wheat-=amount;
			giveTo.wheat+=amount;
			break;
		case WOOD:
			this.wood-=amount;
			giveTo.wood+=amount;
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

	public boolean hasResource(ResourceType resource, int count){
		switch (resource) {
		case WOOD:
			return getWoodCount()>=count;
		case BRICK:
			return getBrickCount()>=count;
		case SHEEP:
			return getSheepCount()>=count;
		case WHEAT:
			return getWheatCount()>=count;
		case ORE:
			return getOreCount()>=count;
		default:
			return false;
		}
	}
	
	public boolean hasSpecifiedResources(Resources resources){	
		return hasBrick(resources.getBrickCount()) && 
				hasOre(resources.getOreCount())     && 
				hasSheep(resources.getSheepCount()) && 
				hasWheat(resources.getWheatCount()) &&
				hasWood(resources.getWoodCount());
	}
	
	public boolean hasBrick(int count){return brick>=count;}
	public boolean hasOre(int count){return ore>=count;}
	public boolean hasSheep(int count){return sheep>=count;}
	public boolean hasWheat(int count){return wheat>=count;}
	public boolean hasWood(int count){return wood>=count;}
	
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
	
	@Override
	public boolean equals(Object o) {
		Resources res = (Resources)o;
		return res.getBrickCount() == this.brick && res.getWheatCount() == this.wheat
				&& res.getWoodCount() == this.wood && res.getOreCount() == this.ore
				&& res.getSheepCount() == this.sheep ? true : false;
	}
	

}