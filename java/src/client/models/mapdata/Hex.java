package client.models.mapdata;


import shared.locations.HexLocation;
import client.models.Resources;

/**
 * Hex holds location on map, resource, and number token
 *
 */

public class Hex {
	private HexLocation location;
	private Resources resource;
	private int numberToken;
	private boolean hasRobber;
	
	public boolean isHasRobber() {
		return hasRobber;
	}
	public void setHasRobber(boolean hasRobber) {
		this.hasRobber = hasRobber;
	}
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	public Resources getResource() {
		return resource;
	}
	public void setResource(Resources resource) {
		this.resource = resource;
	}
	public int getNumberToken() {
		return numberToken;
	}
	public void setNumberToken(int numberToken) {
		this.numberToken = numberToken;
	}
}