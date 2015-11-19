package client.models.mapdata;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import client.models.VertexObject;
import shared.definitions.HexType;

/**
 * Holds pointers to important map data: <br>
 * Hexes, ports, locations of cities, settlements, <br>
 * roads, and robber
 *
 */

public class Board {

	private Hex[] hexes;
	private Port[] ports;
	private List<VertexObject> settlements;
	private List<VertexObject> cities;
	private Road[] roads;
	private int radius;
	private HexLocation robber;
	
	public Board() {
		createDefaultBoard();
	}

	public static Map<String, String> getOppDir() {
		Map<String, String> oppDir = new HashMap<String, String>();
		oppDir.put("N", "S");
		oppDir.put("NE", "SW");
		oppDir.put("E", "W");
		oppDir.put("SE", "NW");
		oppDir.put("S", "N");
		oppDir.put("SW", "NE");
		oppDir.put("W", "E");
		oppDir.put("NW", "SE");
		oppDir = Collections.unmodifiableMap(oppDir);
		return oppDir;
	}

	public static Map<String, String[]> getAdjVertices() {
		Map<String, String[]> adjVertices = new HashMap<String, String[]>();
		adjVertices.put("N", new String[] { "NE", "NW" });
		adjVertices.put("NE", new String[] { "NE", "E" });
		adjVertices.put("SE", new String[] { "E", "SE" });
		adjVertices.put("S", new String[] { "SE", "SW" });
		adjVertices.put("SW", new String[] { "SW", "W" });
		adjVertices.put("NW", new String[] { "W", "NW" });
		adjVertices = Collections.unmodifiableMap(adjVertices);
		return adjVertices;
	}
	
	public ArrayList<Hex> getHexesFromNumber(int number) {
		ArrayList<Hex> hexesToReturn = new ArrayList<Hex>();
		for(Hex h : this.hexes) {
			if(h.getNumberToken() == number) {
				hexesToReturn.add(h);
			}
		}// TODO Auto-generated method stub
		return hexesToReturn;
	}

	public Hex getHexFromCoords(int x, int y) {
		for (Hex hex : this.hexes) { 
			if (hex.getLocation().getX() == x && hex.getLocation().getY() == y) return hex;
		}
		return null;
	}
	
	public int numRoadsOwnedByPlayer(int playerIndex) {
		int i = 0;
		for (Road r : this.roads) {
			if (r.getOwner() == playerIndex) i += 1;
		}
		return i;
	}
	
	public int numSettlementsOwnedByPlayer(int playerIndex) {
		int i = 0;
		for (VertexObject s : this.settlements) {
			if (s.getOwner() == playerIndex) i += 1;
		}
		return i;
	}
	
	public void removeLocalOnlyRoad() {
		Road[] newRoads = new Road[roads.length-1];
		int i = 0;
		while (i < roads.length-1) {
			newRoads[i] = roads[i];
			i += 1;
		}
		setRoads(newRoads);
	}
	
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

	public List<VertexObject> getSettlements() {
		return settlements;
	}

	public void setSettlements(List<VertexObject> settlements) {
		this.settlements = settlements;
	}

	public List<VertexObject> getCities() {
		return cities;
	}

	public void setCities(List<VertexObject> cities) {
		this.cities = cities;
	}

	public Road[] getRoads() {
		return roads;
	}

	public void setRoads(Road[] roads) {
		this.roads = roads;
	}
	
	public void addRoad(Road road) {
		Road[] newRoads = new Road[roads.length+1];
		int i = 0;
		while (i < roads.length) {
			newRoads[i] = roads[i];
			i += 1;
		}
		newRoads[i] = road;
		setRoads(newRoads);
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

	public EdgeLocation getAltEdge(EdgeLocation edgeLocation) {
		int x = edgeLocation.getXcoord();
		int y = edgeLocation.getYcoord();
		String direction = "";
		switch (edgeLocation.getDirection()) {
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

	public int getEdgeOwner(EdgeLocation edgeLocation) {
		EdgeLocation altLocation = this.getAltEdge(edgeLocation);
		if (this.roads != null)
			for (Road r : this.roads) {
				if (r.getLocation().equals(edgeLocation)
						|| r.getLocation().equals(altLocation))
					return r.getOwner();
			}
		return -1;
	}

	public EdgeLocation[] getAlternateEdgeLocations(EdgeLocation edgeLocation) {
		int x1 = edgeLocation.getXcoord();
		int x2 = x1;
		int y1 = edgeLocation.getYcoord();
		int y2 = y1;
		String direction1 = "";
		String direction2 = "";
		String direction = edgeLocation.getDirection();
		switch (direction) {
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
		return new EdgeLocation[] { new EdgeLocation(x1, y1, direction1),
				new EdgeLocation(x2, y2, direction2) };
	}

	public int getVertexOwner(EdgeLocation edgeLocation) {
		EdgeLocation[] alternateEdgeLocations = this.getAlternateEdgeLocations(edgeLocation);
		EdgeLocation altLoc1 = alternateEdgeLocations[0];
		EdgeLocation altLoc2 = alternateEdgeLocations[1];
		if (this.settlements != null) {
			for (VertexObject s : this.settlements) {
				EdgeLocation checkLoc = s.getLocation();
				//System.out.println(s.toString());
				//if (checkLoc == null)
					///System.out.println("OH MY EFFING GOSH");
				if (checkLoc.equals(edgeLocation) || checkLoc.equals(altLoc1)
						|| checkLoc.equals(altLoc2)) {
					return s.getOwner();
				}
			}
		}
		if (this.cities != null) {
			for (VertexObject c : this.cities) {
				EdgeLocation checkLoc = c.getLocation();
				if (checkLoc.equals(edgeLocation) || checkLoc.equals(altLoc1)
						|| checkLoc.equals(altLoc2)) {
					return c.getOwner();
				}
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Hexes: \n");
		for (Hex hex : hexes) {
			sb.append("\t" + hex.toString() + "\n");
		}

		sb.append("Ports: \n");
		for (Port port : ports) {
			sb.append("\t" + port.toString() + "\n");
		}

		sb.append("Settlements: \n");
		for (VertexObject settlement : settlements) {
			sb.append("\t" + settlement.toString() + "\n");
		}

		sb.append("Cities: \n");
		for (VertexObject city : cities) {
			sb.append("\t" + city.toString() + "\n");
		}

		sb.append("Roads: \n");
		for (Road road : roads) {
			sb.append("\t" + road.toString() + "\n");
		}

		sb.append("Radius: \n");
		sb.append("\t" + radius + "\n");

		sb.append("Robber: \n");
		sb.append("\t" + robber + "\n");

		return sb.toString();
	}
	
	
	private void createDefaultBoard()
	{
		this.roads = new Road[0];
		this.settlements = new ArrayList<VertexObject>();
		this.cities = new ArrayList<VertexObject>();
		this.setHexes(new Hex[]
				{
						new Hex(new HexLocation(0,-2)),
						new Hex(new HexLocation(1,-2),"brick",4 ),
						new Hex(new HexLocation(2,-2),"wood",11 ),

						new Hex(new HexLocation(-1,-1),"brick",8 ),
						new Hex(new HexLocation(0,-1),"wood",3 ),
						new Hex(new HexLocation(1,-1),"ore",9 ),
						new Hex(new HexLocation(2,-1),"sheep",12 ),

						new Hex(new HexLocation(-2,0),"ore",5 ),
						new Hex(new HexLocation(-1,0),"sheep",10 ),
						new Hex(new HexLocation(0,0),"wheat",11 ),
						new Hex(new HexLocation(1,0),"brick",5 ),
						new Hex(new HexLocation(2,0),"wheat",6 ),

						new Hex(new HexLocation(-2,1),"wheat",2 ),
						new Hex(new HexLocation(-1,1),"sheep",9 ),
						new Hex(new HexLocation(0,1),"wood",4 ),
						new Hex(new HexLocation(1,1),"sheep",10 ),

						new Hex(new HexLocation(-2,2),"wood",6 ),
						new Hex(new HexLocation(-1,2),"ore",3 ),
						new Hex(new HexLocation(0,2),"wheat",8 )
				});


		this.setPorts(new Port[]
				{
						new Port("brick",new HexLocation(-2,3), "NE", 2),
						new Port(new HexLocation(-3,0), "SE", 3),
						new Port("wood",new HexLocation(-3,2), "NE", 2),
						new Port("sheep",new HexLocation(3,-1), "NW", 2),
						new Port(new HexLocation(2,1), "NW", 3),
						new Port(new HexLocation(3,-3), "SW", 3),
						new Port("ore",new HexLocation(1,-3), "S", 2),
						new Port("wheat",new HexLocation(-1,-2), "S", 2),
						new Port(new HexLocation(0,3), "N", 3)
				});
		this.setRobber(this.getHexes()[0].getLocation());
	}
	
	public void CreateRandomTiles(){
		List<String> tileTypes = new ArrayList<String>();
		
		for(int i=0; i<4; i++)
		{
			tileTypes.add("wood");
			tileTypes.add("sheep");
			tileTypes.add("wheat");
		}
		for(int i=0; i<3; i++)
		{
			tileTypes.add("brick");
			tileTypes.add("ore");
		}
		tileTypes.add(null);
		
		Random rand = new Random();
		
		for(Hex h : hexes)
		{
			
			int index = rand.nextInt(tileTypes.size());
			String tileType = tileTypes.remove(index);
			h.setResource(tileType);
			if(h.getHexType()==HexType.DESERT){
				this.robber=h.getLocation();
			}
		}
		
	}
	
	public void CreateRandomNumbers(){
		List<Integer> numbers = new ArrayList<>();
		numbers.add(11);
		numbers.add(12);
		numbers.add(9);
		
		numbers.add(4);
		numbers.add(6);
		numbers.add(5);
		numbers.add(10);
		
		numbers.add(3);
		numbers.add(11);
		numbers.add(4);
		numbers.add(8);
		
		numbers.add(8);
		numbers.add(10);
		numbers.add(9);
		numbers.add(3);
		
		numbers.add(5);
		numbers.add(2);
		numbers.add(6);
		
		Random rand = new Random();
		
		for(Hex h : hexes)
		{
			if(h.getHexType()==HexType.DESERT){
				continue;
			}
			
			int index = rand.nextInt(numbers.size());

			h.setNumberToken( numbers.remove(index));
		}
	}
	
	public void CreateRandomPorts()
	{
		class PortInfo
		{
			public int ratio;
			public String resource;
			
			public PortInfo(int ratio, String resource)
			{
				this.ratio = ratio;
				this.resource=resource;
			}
		}
		
		List<PortInfo> portInfos = new ArrayList<>();
		
		for(int i=0; i<4; i++)
		{
			portInfos.add(new PortInfo(3,"all"));
		}
		
		portInfos.add(new PortInfo(2,"Ore"));
		portInfos.add(new PortInfo(2,"Wheat"));
		portInfos.add(new PortInfo(2,"Sheep"));
		portInfos.add(new PortInfo(2,"Wood"));
		portInfos.add(new PortInfo(2,"Brick"));

		Random rand = new Random();
		for(Port p : ports)
		{
			int index= rand.nextInt(portInfos.size());
			PortInfo info = portInfos.remove(index);
			p.setRatio(info.ratio);
			p.setResource(info.resource);
		}
	}
}
