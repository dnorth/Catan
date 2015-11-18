package client.models.mapdata;

import shared.locations.EdgeDirection;
import shared.locations.VertexDirection;

/**
 * Holds x and y coordinates of edge, along with direction
 *
 */

public class EdgeLocation {
	int x;
	int y;
	String direction;

	public EdgeLocation(int xcoord, int ycoord, String direction) {
		this.x = xcoord;
		this.y = ycoord;
		this.direction = direction;
	}
	public EdgeLocation(shared.locations.EdgeLocation edge) {
		this.x = edge.getHexLoc().getX();
		this.y = edge.getHexLoc().getY();
		this.direction = edge.getDir().toString();
	}
	public EdgeLocation(shared.locations.VertexLocation vertexLoc) {
		this.x = vertexLoc.getHexLoc().getX();
		this.y = vertexLoc.getHexLoc().getY();
		this.direction = vertexLoc.getDir().toString();
	}
	public int getXcoord() {
		return x;
	}
	public void setXcoord(int xcoord) {
		this.x = xcoord;
	}
	public int getYcoord() {
		return y;
	}
	public void setYcoord(int ycoord) {
		this.y = ycoord;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	@Override
	public String toString() {
		return "EdgeLocation [x=" + x + ", y=" + y + ", direction=" + direction + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdgeLocation other = (EdgeLocation) obj;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public shared.locations.VertexLocation getSharedVertexLocation() {
		return new shared.locations.VertexLocation(new shared.locations.HexLocation(this.x, this.y), VertexDirection.getVertexDirectionFromString(this.direction));
	}
	public shared.locations.EdgeLocation getSharedEdgeLocation() {
		return new shared.locations.EdgeLocation(new shared.locations.HexLocation(this.x, this.y), EdgeDirection.getEdgeDirectionFromString(this.direction));
	}
}