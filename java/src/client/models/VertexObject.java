package client.models;

import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
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
	private EdgeLocation location;

	public VertexObject(int i, EdgeLocation edgeLocation) {
		this.owner = i;
		this.location = edgeLocation;
	}

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


	@Override
	public String toString() {
		return "VertexObject [owner=" + owner + ", location=" + location.toString() + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VertexObject other = (VertexObject) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (owner != other.owner)
			return false;
		return true;
	}

	
	public VertexLocation getVertexLocation() {
		return new VertexLocation(
				new HexLocation(this.location.getXcoord(), this.location.getYcoord()),
				VertexDirection.getVertexDirectionFromString(this.location.getDirection()));
	}
	
}