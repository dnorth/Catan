package client.models;

import java.awt.List;
import java.util.ArrayList;

import shared.definitions.ResourceType;

public class Resources{
	
	private int woodCount;
	private int brickCount;
	private int sheepCount;
	private int wheatCount;
	private int oreCount;
	
	public Resources(int woodCount, int brickCount, int sheepCount, int wheatCount, int oreCount) {
		this.woodCount = woodCount;
		this.brickCount = brickCount;
		this.sheepCount = sheepCount;
		this.wheatCount = wheatCount;
		this.oreCount = oreCount;
	}

	public int getWoodCount() {
		return woodCount;
	}

	public int getBrickCount() {
		return brickCount;
	}

	public int getSheepCount() {
		return sheepCount;
	}

	public int getWheatCount() {
		return wheatCount;
	}

	public int getOreCount() {
		return oreCount;
	}
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		appendStringType(sb, "wood", woodCount);
		appendStringType(sb, "brick", brickCount);
		appendStringType(sb, "sheep", sheepCount);
		appendStringType(sb, "wheat", wheatCount);
		appendStringType(sb, "ore", oreCount);
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