package client.facade;

import java.util.ArrayList;
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
		return null;
	}

	public boolean canMaritimeTrade(int playerIndex)
	{
		Port[] ports = board.getPorts();
		List<VertexObject> settlementVertexes = new ArrayList<VertexObject>();

		for(VertexObject v : board.getCities())
		{
			if(v.getOwner()==playerIndex)
			{settlementVertexes.add(v);}
		}

		for(VertexObject v : board.getSettlements())
		{
			if(v.getOwner()==playerIndex)
			{settlementVertexes.add(v);}
		}
	
		for(Port port : ports)
		{
			for(VertexObject v : settlementVertexes)
			{
				EdgeLocation[] locations = v.getLocation();
				for(EdgeLocation edgeLocation : locations)
				{
					if(edgeLocation.isInHexLocation(port.getLocation(), port.getDirection()))
					{return true;}
				}
			}
		}


		return false;
	}
}
