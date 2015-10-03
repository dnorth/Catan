package client.models.mapdata;


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
	
	public Port(String resource, HexLocation location, String direction, int ratio) {
		super();
		this.resource = resource;
		this.location = location;
		this.direction = direction;
		this.ratio = ratio;
	}
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
		int x = location.getX();
		int y = location.getY();
		return new EdgeLocation(x, y, direction);
	}
	
	@Override
	public String toString() {
		return "Port [resource=" + resource + ", location=" + location.toString() + ", direction=" + direction + ", ratio=" + ratio
				+ "]";
	}
	
	
}