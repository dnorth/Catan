package client.models.mapdata;

/**
 * Holds x and y coordinates of edge, along with direction
 *
 */

public class EdgeLocation {
	int xcoord;
	int ycoord;
	String direction;
	
	public boolean isInHexLocation(HexLocation hl, String hlDirection)
	{
		return xcoord==hl.getXcoord() && ycoord==hl.getYcoord() && hlDirection.equals(direction);
	}
}