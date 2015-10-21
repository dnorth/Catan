package shared.definitions;

import java.awt.Color;

public enum CatanColor
{
	RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN, BLACK;
	
	private Color color;
	
	static
	{
		RED.color = new Color(227, 66, 52);
		ORANGE.color = new Color(255, 165, 0);
		YELLOW.color = new Color(253, 224, 105);
		BLUE.color = new Color(111, 183, 246);
		GREEN.color = new Color(109, 192, 102);
		PURPLE.color = new Color(157, 140, 212);
		PUCE.color = new Color(204, 136, 153);
		WHITE.color = new Color(223, 223, 223);
		BROWN.color = new Color(161, 143, 112);
		BLACK.color = new Color(0,0,0);
	}
	
	public static CatanColor getCatanColor(String findMe) throws Exception {
		switch(findMe) {
			case "red": return RED;
			case "orange": return ORANGE;
			case "yellow": return YELLOW;
			case "blue": return BLUE;
			case "green": return GREEN;
			case "purple": return PURPLE;
			case "puce": return PUCE;
			case "white": return WHITE;
			case "brown": return BROWN;
			default: throw new Exception("BAD COLOR TYPE");
		}
	}
	
	public Color getJavaColor()
	{
		return color;
	}
}

