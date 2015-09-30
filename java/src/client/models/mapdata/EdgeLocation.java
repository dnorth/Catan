package client.models.mapdata;

/**
 * Holds x and y coordinates of edge, along with direction
 *
 */

public class EdgeLocation {
	int xcoord;
	int ycoord;
	String direction;
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
		if (xcoord != other.xcoord)
			return false;
		if (ycoord != other.ycoord)
			return false;
		return true;
	}
	public EdgeLocation(int xcoord, int ycoord, String direction) {
		this.xcoord = xcoord;
		this.ycoord = ycoord;
		this.direction = direction;
	}
	public int getXcoord() {
		return xcoord;
	}
	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}
	public int getYcoord() {
		return ycoord;
	}
	public void setYcoord(int ycoord) {
		this.ycoord = ycoord;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
}