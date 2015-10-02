package client.models.mapdata;


/**
 * Holds x and y coordinates of hex
 *
 */

public class HexLocation {
	private int xcoord;
	private int ycoord;
	
	
	public HexLocation(int xcoord, int ycoord) {
		super();
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}
	
	public int getXcoord() {
		return xcoord;
	}
	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}
	public int getYcoord() {
		return ycoord;
	}
	public void setYcoord(int ycoord) {
		this.ycoord = ycoord;
	}
	
    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof HexLocation))
            {return false;}
        HexLocation h =(HexLocation) obj;
            return xcoord==h.getXcoord() && ycoord==h.getYcoord();
    }
}