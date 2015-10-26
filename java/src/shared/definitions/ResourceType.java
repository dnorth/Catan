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
	
	public static String getResourceName(ResourceType type) {
		switch(type) {
			case WOOD: return "wood";
			case BRICK: return "brick";
			case SHEEP: return "sheep";
			case WHEAT: return "wheat";
			case ORE: return "ore";
			default: return null;
		}
	}
}

