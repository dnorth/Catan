package server.model;

import client.models.ClientModel;
import client.models.Player;

public class Game {
	
	private String title;
	private int id;
	private Player[] players;
	private ClientModel clientModel;
	
	/**
	 * Get title.
	 * @return title
	 */
	public String getTitle() {
		return title;
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
	public Player[] getPlayers() {
		return players;
	}
	
	/**
	 * Set players.
	 * @param players
	 */
	public void setPlayers(Player[] players) {
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
