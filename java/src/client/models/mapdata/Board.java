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
	private Road[] roads;
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
	
	public Road[] getRoads() {
		return roads;
	}
	
	public void setRoads(Road[] roads) {
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
	
	public EdgeLocation getAltEdge(EdgeLocation edgeLocation)
	{
		int x = edgeLocation.getXcoord();
		int y = edgeLocation.getYcoord();
		String direction = "";
		switch (edgeLocation.getDirection())
		{
			case "N":
				y--;
				direction = "S";
				break;
			case "NE":
				y--;
				x++;
				direction = "SW";
				break;
			case "SE":
				x++;
				direction = "NW";
				break;
			case "S":
				y++;
				direction = "N";
				break;
			case "SW":
				x--;
				y++;
				direction = "NE";
				break;
			case "NW":
				x--;
				direction = "SE";
				break;
		}
		return new EdgeLocation(x, y, direction);
	}
	
	public int getEdgeOwner(EdgeLocation edgeLocation)
	{
		EdgeLocation altLocation = this.getAltEdge(edgeLocation);
		for (Road r : this.roads)
		{
			if (r.getLocation().equals(edgeLocation) ||
					r.getLocation().equals(altLocation)) return r.getOwner();
		}
		return -1;
	}
	
	public EdgeLocation[] getAlternateEdgeLocations(EdgeLocation edgeLocation)
	{
		int x1 = edgeLocation.getXcoord();
		int x2 = x1;
		int y1 = edgeLocation.getYcoord();
		int y2 = y1;
		String direction1 = "";
		String direction2 = "";
		String direction = edgeLocation.getDirection();
		switch (direction)
		{
			case "NW":
				x1--;
				y2--;
				direction1 = "E";
				direction2 = "SW";
				break;
			case "NE":
				y1--;
				x2++;
				y2--;
				direction1 = "SE";
				direction2 = "W";
				break;
			case "E":
				x1++;
				y1--;
				x2++;
				direction1 = "SW";
				direction2 = "NW";
				break;
			case "SE":
				x1++;
				y2++;
				direction1 = "W";
				direction2 = "NE";
				break;
			case "SW":
				y1++;
				x2--;
				y2++;
				direction1 = "NW";
				direction2 = "E";
				break;
			case "W":
				x1--;
				y1++;
				x2--;
				direction1 = "NE";
				direction2 = "SE";
				break;
		}
		return new EdgeLocation[] { new EdgeLocation(x1, y1, direction1), new EdgeLocation(x2, y2, direction2) };
	}
	
	public int getVertexOwner(EdgeLocation edgeLocation)
	{
		EdgeLocation[] alternateEdgeLocations = this.getAlternateEdgeLocations(edgeLocation);
		EdgeLocation altLoc1 = alternateEdgeLocations[0];
		EdgeLocation altLoc2 = alternateEdgeLocations[1];
		for (VertexObject s : this.settlements)
		{
			EdgeLocation checkLoc = s.getLocation();
			if (checkLoc.equals(edgeLocation) ||
					checkLoc.equals(altLoc1) ||
					checkLoc.equals(altLoc2))
			{
				return s.getOwner();
			}
		}
		for (VertexObject c : this.cities)
		{
			EdgeLocation checkLoc = c.getLocation();
			if (checkLoc.equals(edgeLocation) ||
					checkLoc.equals(altLoc1) ||
					checkLoc.equals(altLoc2))
			{
				return c.getOwner();
			}
		}
		return -1;
	}
}
