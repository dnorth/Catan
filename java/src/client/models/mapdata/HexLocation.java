package client.models.mapdata;


/**
 * Holds x and y coordinates of hex
 *
 */

public class HexLocation {
	private int x;
	private int y;
	private shared.locations.HexLocation sharedHexLocation;
	
	public HexLocation(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public HexLocation(shared.locations.HexLocation hex) {
		this.x = hex.getX();
		this.y = hex.getY();
	}
	
	public boolean Equals(shared.locations.HexLocation hexLocation)
	{ return x==hexLocation.getX() && y==hexLocation.getY();}
	
	public boolean Equals(HexLocation hexLocation)
	{ return x==hexLocation.getX() && y==hexLocation.getY();}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof HexLocation))
            {return false;}
        HexLocation h =(HexLocation) obj;
            return x==h.getX() && y==h.getY();
    }

	@Override
	public String toString() {
		return "HexLocation [x=" + x + ", y=" + y + "]";
	}
    
	public shared.locations.HexLocation getSharedHexLocation() {
		if (this.sharedHexLocation == null) this.sharedHexLocation = new shared.locations.HexLocation(this.x, this.y);
		return this.sharedHexLocation;
	}
	
}