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
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		appendStringType(sb, "wood", wood);
		appendStringType(sb, "brick", brick);
		appendStringType(sb, "sheep", sheep);
		appendStringType(sb, "wheat", wheat);
		appendStringType(sb, "ore", ore);
		if(sb.length()==0)
		{sb.append("no resources");}
		return sb.toString();
	}
	
	private void appendStringType(StringBuilder sb,String type, int count)
	{
		if(count>0)
		{sb.append(count + " "+ type +", ");}
	}
	
}