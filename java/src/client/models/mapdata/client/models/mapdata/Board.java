package client.models.mapdata;

import client.models.VertexObject;

/**
 * Holds pointers to important map data: <br>
 * Hexes, ports, locations of cities, settlements, <br>
 * roads, and robber
 *
 */

public class Board {
	
	Hex[] hexes;
	Port[] ports;
	VertexObject[] settlements;
	VertexObject[] cities;
	EdgeValue[] roads;
	int radius;
	HexLocation robber;

}
