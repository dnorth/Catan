package client.models;

import client.models.mapdata.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
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
	
	public VertexObject(VertexObject v) {
		this.owner = v.getOwner();
		this.location = v.getLocation();
	}

	public VertexObject(int i, VertexLocation vertexLocation) {
		this.owner = i;
		
		HexLocation hexLoc = vertexLocation.getHexLoc();
		EdgeLocation edge = new EdgeLocation(hexLoc.getX(),hexLoc.getY(), vertexLocation.getDir().toString());
		this.location = edge;
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
	
	public HexLocation[] getHexes() {
		int x = location.getXcoord();
		int y = location.getYcoord();
		HexLocation hex1 = new HexLocation(x, y);
		HexLocation hex2 = new HexLocation(x, y);
		HexLocation hex3 = new HexLocation(x, y);
		switch(location.getDirection()){
		case "NW":
			hex1 = new HexLocation(x-1, y);
			hex2 = new HexLocation(x, y-1);
		case "NE":
			hex1 = new HexLocation(x, y-1);
			hex2 = new HexLocation(x+1, y-1);
		case "E":
			hex1 = new HexLocation(x+1, y-1);
			hex2 = new HexLocation(x+1, y);
		case "SE":
			hex1 = new HexLocation(x+1, y);
			hex2 = new HexLocation(x, y+1);
		case "SW":
			hex1 = new HexLocation(x, y+1);
			hex2 = new HexLocation(x-1, y+1);
		case "W":
			hex1 = new HexLocation(x-1, y+1);
			hex2 = new HexLocation(x-1, y);
		}
		HexLocation[] hexes = new HexLocation[3];
		hexes[0] = hex1;
		hexes[1] = hex2;
		hexes[2] = hex3;
		return hexes;
	}

	
	public VertexLocation getVertexLocation() {
		return new VertexLocation(
				new HexLocation(this.location.getXcoord(), this.location.getYcoord()),
				VertexDirection.getVertexDirectionFromString(this.location.getDirection()));
	}
	
}