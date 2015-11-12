 package server.model;

import java.util.ArrayList;
import java.util.List;

import client.models.ClientModel;
import client.models.Player;

/**
 * Contains Game info, including the ClientModel object for each game.
 */
public class ServerGame {
	
	private String title;
	private int id;
	private List<ServerPlayer> players;
	private ClientModel clientModel;
	
	/**
	 * Get title.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	public ServerGame(String title, int id) {
		this.title = title;
		this.id = id;
		this.players = new ArrayList<ServerPlayer>();
		this.clientModel = new ClientModel();
	}
	
	public boolean addUser(ServerUser user, String color) {
		if (players.size() > 3) return false;
		else {
			// don't add the same player in twice
			for (ServerPlayer p : players) {
				if (p.getId() == user.getPlayerID()) {
					return true;
				}
			}
			this.players.add(new ServerPlayer(color, user.getUsername(), user));
			return true;
		}
	}

	/**
	 * Set title.
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	
	/**
	 * Get players.
	 * @return players
	 */
	public List<ServerPlayer> getPlayers() {
		return players;
	}
	
	/**
	 * Set players.
	 * @param players
	 */
	public void setPlayers(List<ServerPlayer> players) {
		this.players = players;
	}
	
	/**
	 * Get clientModel.
	 * @return clientModel
	 */
	public ClientModel getClientModel() {
		return clientModel;
	}
	
	/**
	 * Set clientModel.
	 * @param clientModel
	 */
	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

}
