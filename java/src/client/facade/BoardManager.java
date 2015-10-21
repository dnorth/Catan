package client.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import client.models.Player;
import client.models.VertexObject;
import client.models.mapdata.Board;
import client.models.mapdata.EdgeLocation;
import client.models.mapdata.HexLocation;
import client.models.mapdata.Port;

public class BoardManager {
	private Board board;
	
	public BoardManager(Board board) {
		this.board = board;
	}

	/**
	 * Determines if the player has a settlement in a port.
	 * @param playerIndex Number of player to determine ability to take action
	 * @return if the player has a settlement in a port.
	 */
	
	public int getEdgeOwner(EdgeLocation edge) {
		return board.getEdgeOwner(edge);
	}
	
	public boolean canPlaceRobber(HexLocation hexLoc) {
		if (hexLoc.equals(this.board.getRobber())) return false;
		return true;
	}
	
	public Integer[] getAdjacentVertexOwners(EdgeLocation edge) {
		//get three adjacent vertices
		EdgeLocation adj1 = null;
		EdgeLocation adj2 = null;
		EdgeLocation adj3 = null;
		int x = edge.getXcoord();
		int y = edge.getYcoord();
		String dir = edge.getDirection();
		switch(dir) {
		case "NW":
			adj1 = new EdgeLocation(x, y, "W");
			adj2 = new EdgeLocation(x, y, "NE");
			adj3 = new EdgeLocation(x-1, y, "NE");
		case "NE":
			adj1 = new EdgeLocation(x, y, "NW");
			adj2 = new EdgeLocation(x, y, "E");
			adj3 = new EdgeLocation(x, y-1, "E");
		case "E":
			adj1 = new EdgeLocation(x, y, "NE");
			adj2 = new EdgeLocation(x, y, "SE");
			adj3 = new EdgeLocation(x+1, y-1, "SE");
		case "SE":
			adj1 = new EdgeLocation(x, y, "E");
			adj2 = new EdgeLocation(x, y, "SW");
			adj3 = new EdgeLocation(x+1, y, "SW");
		case "SW":
			adj1 = new EdgeLocation(x, y, "SE");
			adj2 = new EdgeLocation(x, y, "W");
			adj3 = new EdgeLocation(x, y+1, "W");
		case "W":
			adj1 = new EdgeLocation(x, y, "SW");
			adj2 = new EdgeLocation(x, y, "NW");
			adj3 = new EdgeLocation(x-1, y+1, "NW");
		}
		Integer[] owners = {board.getVertexOwner(adj1), board.getVertexOwner(adj2), board.getVertexOwner(adj3)};
		return owners;
	}
	
	public Integer[] getSurroundingEdgeOwners(EdgeLocation edge) {
		EdgeLocation adj1 = null;
		EdgeLocation adj2 = null;
		EdgeLocation adj3 = null;
		int x = edge.getXcoord();
		int y = edge.getYcoord();
		String dir = edge.getDirection();
		switch(dir) {
		case "NW":
			adj1 = new EdgeLocation(x, y, "NW");
			adj2 = new EdgeLocation(x, y, "N");
			adj3 = new EdgeLocation(x-1, y, "NE");
		case "NE":
			adj1 = new EdgeLocation(x, y, "N");
			adj2 = new EdgeLocation(x, y, "NE");
			adj3 = new EdgeLocation(x, y-1, "SE");
		case "E":
			adj1 = new EdgeLocation(x, y, "NE");
			adj2 = new EdgeLocation(x, y, "SE");
			adj3 = new EdgeLocation(x+1, y-1, "S");
		case "SE":
			adj1 = new EdgeLocation(x, y, "SE");
			adj2 = new EdgeLocation(x, y, "S");
			adj3 = new EdgeLocation(x+1, y, "SW");
		case "SW":
			adj1 = new EdgeLocation(x, y, "S");
			adj2 = new EdgeLocation(x, y, "SW");
			adj3 = new EdgeLocation(x, y+1, "NW");
		case "W":
			adj1 = new EdgeLocation(x, y, "SW");
			adj2 = new EdgeLocation(x, y, "NW");
			adj3 = new EdgeLocation(x-1, y+1, "N");
		}
		Integer[] owners = {board.getEdgeOwner(adj1), board.getEdgeOwner(adj2), board.getEdgeOwner(adj3)};
		return owners;
	}
	
	public Integer[] getAdjacentEdgeOwners(EdgeLocation edge) {
		String dir = edge.getDirection();
		EdgeLocation altEdge1 = board.getAltEdge(edge);
		EdgeLocation altEdge2 = board.getAltEdge(edge);
		EdgeLocation vert1 = null;
		EdgeLocation vert2 = null;
		int x = edge.getXcoord();
		int y = edge.getYcoord();
		String dir1 = "";
		String dir2 = "";
		switch(dir) {
		case "N":
			altEdge1.setDirection("SW");
			altEdge2.setDirection("SE");
			dir1 = "NW";
			dir2 = "NE";
			vert1 = new EdgeLocation(x, y, "NE");
			vert2 = new EdgeLocation(x, y, "NW");
			break;
			
		case "NE":
			altEdge1.setDirection("NW");
			altEdge2.setDirection("S");
			dir1 = "N";
			dir2 = "SE";
			vert1 = new EdgeLocation(x, y, "NE");
			vert2 = new EdgeLocation(x, y, "E");
			break;
			
		case "SE":
			altEdge1.setDirection("N");
			altEdge2.setDirection("SW");
			dir1 = "NE";
			dir2 = "S";
			vert1 = new EdgeLocation(x, y, "E");
			vert2 = new EdgeLocation(x, y, "SE");
			break;
			
		case "S":
			altEdge1.setDirection("NW");
			altEdge2.setDirection("NE");
			dir1 = "SE";
			dir2 = "SW";
			vert1 = new EdgeLocation(x, y, "SE");
			vert2 = new EdgeLocation(x, y, "SW");
			break;
			
		case "SW":
			altEdge1.setDirection("N");
			altEdge2.setDirection("SE");
			dir1 = "S";
			dir2 = "NW";
			vert1 = new EdgeLocation(x, y, "SW");
			vert2 = new EdgeLocation(x, y, "W");
			break;
			
		case "NW":
			altEdge1.setDirection("NE");
			altEdge2.setDirection("S");
			dir1 = "SW";
			dir2 = "N";
			vert1 = new EdgeLocation(x, y, "W");
			vert2 = new EdgeLocation(x, y, "NW");
			break;
		}
		Integer[] owners = {-1, -1, -1, -1, -1, -1};
		EdgeLocation adj1 = new EdgeLocation(x,y,dir1);
		EdgeLocation adj2 = new EdgeLocation(x,y,dir2);
		EdgeLocation[] edgeList = {adj1, adj2, altEdge1, altEdge2};
		for(int i = 0; i < edgeList.length; i++) {
			EdgeLocation e = edgeList[i];
			owners[i] = board.getEdgeOwner(e);
		}
		if (vert1 != null) owners[4] = this.board.getVertexOwner(vert1);
		if (vert2 != null) owners[5] = this.board.getVertexOwner(vert2);
		return owners;
	}
	
	public boolean canPlaceSettlementAtLocation(int playerIndex, EdgeLocation edge) {
		//check if someone owns vertex
		if(board.getVertexOwner(edge) != -1) {
			return false;
		}
		//make sure neighboring vertices are empty
		Integer[] adjVertexOwners = this.getAdjacentVertexOwners(edge);
		for (Integer i : adjVertexOwners) {
			if (i != -1) return false;
		}
		//make sure player owns road next to vertex
		Integer[] adjEdgeOwners = this.getAdjacentEdgeOwners(edge);
		for (Integer i : adjEdgeOwners) {
			if (i == playerIndex) return true;
		}
		return false;
	}

	/**
	 * Determines if a player can place a road on a specific hex edge.
	 * @param playerIndex Number of player to determine ability to take action
	 * @param hex Hex where road will be built
	 * @param edge Edge on hex where road will be built
	 * @return if the player can place a road at the HexLocation.
	 */
	public boolean canPlaceRoadAtLocation(int playerIndex, EdgeLocation edge){
		if (this.getEdgeOwner(edge) != -1) return false;
		Integer[] surrOwners = this.getAdjacentEdgeOwners(edge);
		if (surrOwners == null) {
			return false;
		}
		if (Arrays.asList(surrOwners).contains(playerIndex)) return true;
		else return false;
	}
	
	public boolean canPlaceInitialRoad(int playerIndex, EdgeLocation edge) {
		if (this.getEdgeOwner(edge) != -1) return false;
		EdgeLocation[] vertices = this.getEdgeVertices(edge);
		for (EdgeLocation vertex : vertices) {
			if (board.getVertexOwner(vertex) != -1) return false;
			Integer[] adjVertexOwners = this.getAdjacentVertexOwners(edge);
			for (Integer i : adjVertexOwners) {
				if (i != -1) return false;
			}
		}
		return true;
	}
	
	EdgeLocation[] getEdgeVertices(EdgeLocation edge) {
		String vert1 = "";
		String vert2 = "";
		switch(edge.getDirection()) {
		case "N":
			vert1 = "NW";
			vert2 = "NE";
		case "NE":
			vert1 = "NE";
			vert2 = "E";
		case "SE":
			vert1 = "E";
			vert2 = "SE";
		case "S":
			vert1 = "SE";
			vert2 = "SW";
		case "SW":
			vert1 = "SW";
			vert2 = "W";
		case "NW":
			vert1 = "W";
			vert2 = "NW";
		}
		EdgeLocation vertex1 = new EdgeLocation(edge.getXcoord(), edge.getYcoord(), vert1);
		EdgeLocation vertex2 = new EdgeLocation(edge.getXcoord(), edge.getYcoord(), vert2);
		EdgeLocation[] adjVertices = {vertex1, vertex2};
		return adjVertices;
	}
	
	public boolean canUpgradeSettlementAtLocation(int playerIndex, EdgeLocation edge) {
		VertexObject[] e = board.getSettlements();
		for (VertexObject v : e) {
			if(edge.equals(v.getLocation())) {
				if (v.getOwner() == playerIndex) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canMaritimeTrade(int playerIndex)
	{
		Port[] ports = board.getPorts();
	
		for(Port port : ports)
		{
			EdgeLocation portLocation = port.getEdgeLocation();
			//if (portLocation.getDirection() == null) System.out.println("WHY DOES THIS KEEP HAPPENNING TO ME");
			String[] adjVertexDirs = this.board.getAdjVertices().get(portLocation.getDirection());
			EdgeLocation adj1 = new EdgeLocation(portLocation.getXcoord(), portLocation.getYcoord(), adjVertexDirs[0]);
			EdgeLocation adj2 = new EdgeLocation(portLocation.getXcoord(), portLocation.getYcoord(), adjVertexDirs[1]);
			
			if (this.board.getVertexOwner(adj1) == playerIndex) return true;
			if (this.board.getVertexOwner(adj2) == playerIndex) return true;
		}

		return false;
	}
}
