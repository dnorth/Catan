package client.models;

import java.awt.List;
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
	
	public Resources(){}

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

	public int getBrick() {
		return brick;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public int getSheep() {
		return sheep;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public int getWheat() {
		return wheat;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public int getOre() {
		return ore;
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
	
}