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
	
	public Integer[] getAdjacentEdgeOwners(EdgeLocation edge) {
		String dir = edge.getDirection();
		EdgeLocation altEdge1 = board.getAltEdge(edge);
		EdgeLocation altEdge2 = board.getAltEdge(edge);
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
			break;
			
		case "NE":
			altEdge1.setDirection("NW");
			altEdge2.setDirection("S");
			dir1 = "N";
			dir2 = "SE";
			break;
			
		case "SE":
			altEdge1.setDirection("N");
			altEdge2.setDirection("SW");
			dir1 = "NE";
			dir2 = "S";
			break;
			
		case "S":
			altEdge1.setDirection("NW");
			altEdge2.setDirection("NE");
			dir1 = "SE";
			dir2 = "SW";
			break;
			
		case "SW":
			altEdge1.setDirection("N");
			altEdge2.setDirection("SE");
			dir1 = "S";
			dir2 = "NW";
			break;
			
		case "NW":
			altEdge1.setDirection("NE");
			altEdge2.setDirection("S");
			dir1 = "SW";
			dir2 = "N";
			break;
		}
		int[] owners = {-1, -1, -1, -1};
		EdgeLocation adj1 = new EdgeLocation(x,y,dir1);
		EdgeLocation adj2 = new EdgeLocation(x,y,dir2);
		EdgeLocation[] edgeList = {adj1, adj2, altEdge1, altEdge2};
		for(int i = 0; i < edgeList.length; i++) {
			EdgeLocation e = edgeList[i];
			owners[i] = board.getEdgeOwner(e);
		}
		return null;
	}

	public boolean canMaritimeTrade(int playerIndex)
	{
		Port[] ports = board.getPorts();
	
		for(Port port : ports)
		{
			EdgeLocation portLocation = port.getEdgeLocation();
			String[] adjVertexDirs = this.board.getAdjVertices().get(portLocation.getDirection());
			EdgeLocation adj1 = new EdgeLocation(portLocation.getXcoord(), portLocation.getYcoord(), adjVertexDirs[0]);
			EdgeLocation adj2 = new EdgeLocation(portLocation.getXcoord(), portLocation.getYcoord(), adjVertexDirs[1]);
			
			if (this.board.getVertexOwner(adj1) == playerIndex) return true;
			if (this.board.getVertexOwner(adj2) == playerIndex) return true;
		}

		return false;
	}
}
