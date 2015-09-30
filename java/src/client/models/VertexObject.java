package client.models;

import client.models.mapdata.EdgeLocation;

/**
 * Each vertex on map, stores who owns vertex (has a settlement, city, or roads on corresponding edges)
 */
public class VertexObject{
	
	/**
	 * Refers to number of player who owns vertex, or -1 if not owned
	 */
	private int owner;
	
	/**
	 * Locations of two or three edges touching vertex
	 */
	private EdgeLocation[] location;

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public EdgeLocation[] getLocation() {
		return location;
	}

	public void setLocation(EdgeLocation[] location) {
		this.location = location;
	}
}