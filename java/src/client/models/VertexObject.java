package client.models;

import shared.locations.EdgeLocation;

/**
 * Each vertex on map, stores who owns vertex (has a settlement, city, or roads on corresponding edges)
 */
public class VertexObject{
	
	/**
	 * Refers to number of player who owns vertex, or 0 if not owned
	 */
	int owner;
	
	/**
	 * Location of edge
	 */
	EdgeLocation location;
}