package server.model;

/**
 * Contains server-level player info.
 */
public class ServerPlayer {
	
	private String color;
	private String name;
	private ServerUser user;
	
	public ServerPlayer(String color, String name, ServerUser user) {
		this.color = color;
		this.name = name;
		this.user = user;
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
		if(user == null) {
			return -1;
		}
		return user.getPlayerID();
	}

	public ServerUser getUser() {
		return user;
	}

	public void setUser(ServerUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ServerPlayer [color=" + color + ", name=" + name + ", user=" + user + "]";
	}
	
	
}
