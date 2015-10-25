package shared.definitions;

public enum ResourceType
{
	WOOD, BRICK, SHEEP, WHEAT, ORE;
	
	public static ResourceType getResourceType(String type) {
		switch(type) {
			case "wood": return WOOD;
			case "brick": return BRICK;
			case "sheep": return SHEEP;
			case "wheat":return WHEAT;
			case "ore": return ORE;
			default: return null;
		}
	}
}

