package client.models;

import shared.locations.EdgeLocation;

/**
 * Each vertex on map, stores who owns vertex (has a settlement, city, or roads on corresponding edges)
 */
public class VertexObject{
	
	/**
	 * Refers to number of player who owns vertex, or -1 if not owned
	 */
	int owner;
	
	/**
	 * Locations of three edges touching vertex
	 */
	EdgeLocation[] location;
}