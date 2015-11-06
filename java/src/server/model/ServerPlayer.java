package server.model;

/**
 * Contains server-level player info.
 */
public class ServerPlayer {
	
	private String color;
	private String name;
	private int id;
	
	public ServerPlayer(String color, String name, int id) {
		this.color = color;
		this.name = name;
		this.id = id;
	}
	
	/**
	 * Get color.
	 * @return color
	 */
	public String getColor() {
		return color;
	}
	
	/**
	 * Set color.
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * Get name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get id.
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set id.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
}
