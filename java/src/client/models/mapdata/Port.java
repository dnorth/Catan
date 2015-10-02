package client.models.mapdata;

import client.models.Resources;

/**
 * Port object contains data of hex location, direction, trade resource, and
 * ratio
 *
 */

public class Port {
	private String resource;
	private HexLocation location;
	private String direction;
	private int ratio;
	
	
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	
	public EdgeLocation getEdgeLocation() {
		int x = location.getXcoord();
		int y = location.getYcoord();
		return new EdgeLocation(x, y, direction);
	}
}