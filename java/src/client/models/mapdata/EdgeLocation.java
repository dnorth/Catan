package client.models.mapdata;

/**
 * Holds x and y coordinates of edge, along with direction
 *
 */

public class EdgeLocation {
	int xcoord;
	int ycoord;
	String direction;
<<<<<<< HEAD
	
	public boolean isInHexLocation(HexLocation hl, String hlDirection)
	{
		return xcoord==hl.getXcoord() && ycoord==hl.getYcoord() && hlDirection.equals(direction);
=======
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
>>>>>>> 198a56021587e2a34edc252bc9e979f9344032f0
	}
}