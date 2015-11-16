package client.models.mapdata;


import shared.definitions.HexType;
import shared.definitions.ResourceType;
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
	public HexType getHexType() {
		if (this.resource == null) return HexType.DESERT;
		switch(this.resource) {
		case "wheat":
			return HexType.WHEAT;
		case "wood":
			return HexType.WOOD;
		case "sheep":
			return HexType.SHEEP;
		case "brick":
			return HexType.BRICK;
		case "ore":
			return HexType.ORE;
		default:
			return null;
		}
	}
	
	public ResourceType getResourceType() {
		if (this.resource == null) return null;
		switch(this.resource) {
		case "wheat":
			return ResourceType.WHEAT;
		case "wood":
			return ResourceType.WOOD;
		case "sheep":
			return ResourceType.SHEEP;
		case "brick":
			return ResourceType.BRICK;
		case "ore":
			return ResourceType.ORE;
		default:
			return null;
		}
	}
	
}