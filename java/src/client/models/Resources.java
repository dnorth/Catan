package client.models;

import java.awt.List;
import java.util.ArrayList;

import shared.definitions.ResourceType;

public class Resources extends ArrayList<ResourceType> {
	
	private int woodCount;
	private int brickCount;
	private int sheepCount;
	private int wheatCount;
	private int oreCount;
	
	Resources(int woodCount, int brickCount, int sheepCount, int wheatCount, int oreCount) {
		this.woodCount = woodCount;
		this.brickCount = brickCount;
		this.sheepCount = sheepCount;
		this.wheatCount = wheatCount;
		this.oreCount = oreCount;
		for(int i = 0; i < woodCount; i++) {
			this.add(ResourceType.WOOD);
		}
		for(int i = 0; i < brickCount; i++) {
			this.add(ResourceType.BRICK);
		}
		for(int i = 0; i < sheepCount; i++) {
			this.add(ResourceType.SHEEP);
		}
		for(int i = 0; i < wheatCount; i++) {
			this.add(ResourceType.WHEAT);
		}
		for(int i = 0; i < oreCount; i++) {
			this.add(ResourceType.ORE);
		}	
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