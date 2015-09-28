package client.models.mapdata;

public class Road {
	private int owner;
	private EdgeLocation location;
	public Road() {
	}
	public Road(int owner, EdgeLocation location) {
		super();
		this.owner = owner;
		this.location = location;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public EdgeLocation getLocation() {
		return location;
	}
	public void setLocation(EdgeLocation location) {
		this.location = location;
	}
}
