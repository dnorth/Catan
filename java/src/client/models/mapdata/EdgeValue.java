package client.models.mapdata;

import shared.locations.EdgeLocation;

/**
 * Holds number of player that owns road on edge. If no road, owner = -1
 *
 */

public class EdgeValue {
	private int owner;
	private EdgeLocation location;
	
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public EdgeLocation getLocation() {
		return location;
	}
	public void setLocation(EdgeLocation location) {
		this.location = location;
	}

}