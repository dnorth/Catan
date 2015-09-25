package client.models.mapdata;

import client.models.VertexObject;

/**
 * Holds pointers to important map data: <br>
 * Hexes, ports, locations of cities, settlements, <br>
 * roads, and robber
 *
 */

public class Board {

	private Hex[] hexes;
	private Port[] ports;
	private VertexObject[] settlements;
	private VertexObject[] cities;
	private EdgeValue[] roads;
	private int radius;
	private HexLocation robber;
	
	public Hex[] getHexes() {
		return hexes;
	}
	public void setHexes(Hex[] hexes) {
		this.hexes = hexes;
	}
	public Port[] getPorts() {
		return ports;
	}
	public void setPorts(Port[] ports) {
		this.ports = ports;
	}
	public VertexObject[] getSettlements() {
		return settlements;
	}
	public void setSettlements(VertexObject[] settlements) {
		this.settlements = settlements;
	}
	public VertexObject[] getCities() {
		return cities;
	}
	public void setCities(VertexObject[] cities) {
		this.cities = cities;
	}
	public EdgeValue[] getRoads() {
		return roads;
	}
	public void setRoads(EdgeValue[] roads) {
		this.roads = roads;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public HexLocation getRobber() {
		return robber;
	}
	public void setRobber(HexLocation robber) {
		this.robber = robber;
	}

}
