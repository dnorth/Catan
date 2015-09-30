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
	
}