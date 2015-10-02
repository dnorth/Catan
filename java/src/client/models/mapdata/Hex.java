package client.models.mapdata;


import shared.locations.HexLocation;
import client.models.Resources;

/**
 * Hex holds location on map, resource, and number token
 *
 */

public class Hex {
	private HexLocation location;
	private String resource = "none";
	public Hex(HexLocation location, String resource, int number) {
		super();
		this.location = location;
		this.resource = resource;
		this.number = number;
	}
	private int number;

	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public int getNumberToken() {
		return number;
	}
	public void setNumberToken(int numberToken) {
		this.number = numberToken;
	}
}