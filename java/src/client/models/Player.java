package client.models;

import java.util.List;

import shared.locations.EdgeDirection;
import client.models.mapdata.Hex;



public class Player
{
	int citiesNum;
	String color;
	boolean discarded;
	int monuments;
	String name;
	List<DevCardList> newDevCards;
	List<DevCardList> oldDevCards;
	int playerIndex;
	boolean playedDevCard;
	int playerID;
	List<ResourceList> resources;
	int roads;
	int settlements;
	int soldiers;
	int victoryPoints;
	


}