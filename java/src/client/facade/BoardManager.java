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
