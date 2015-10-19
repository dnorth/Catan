package shared.locations;

public enum VertexDirection
{
	West, NorthWest, NorthEast, East, SouthEast, SouthWest;
	
	private VertexDirection opposite;
	
	static
	{
		West.opposite = East;
		NorthWest.opposite = SouthEast;
		NorthEast.opposite = SouthWest;
		East.opposite = West;
		SouthEast.opposite = NorthWest;
		SouthWest.opposite = NorthEast;
	}
	
	public VertexDirection getOppositeDirection()
	{
		return opposite;
	}
	
	@Override
	public String toString(){
		switch(this){
		case NorthWest:
			return "NW";
		case NorthEast:
			return "NE";
		case East:
			return "E";
		case SouthEast:
			return "SE";
		case SouthWest:
			return "SW";
		case West:
			return "W";
			
		default:
				return "";
		}
	}
}

