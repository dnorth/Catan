package client.models.mapdata;


import client.models.mapdata.HexLocation;

/**
 * Hex holds location on map, resource, and number token
 *
 */

public class Hex {
	private HexLocation location;
	private String resource = "none";
	
	public Hex(HexLocation location, String resource, int number) {
		this.location = location;
		this.resource = resource;
		this.number = number;
	}
	public Hex(HexLocation location, int number) {
		this.location = location;
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
	@Override
	public String toString() {
		return "Hex [location=" + location.toString() + ", resource=" + resource + ", number=" + number + "]";
	}
	
	
}