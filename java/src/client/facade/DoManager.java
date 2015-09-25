package client.facade;

import java.util.ArrayList;
import java.util.List;

import client.models.ClientModel;

public class DoManager {
	private ClientModel clientModel;
	private PlayerManager playerManager;
	private TurnManager turnManager;
	private BoardManager boardManager;

	public DoManager(ClientModel clientModel) {
		this.clientModel = clientModel;
		this.playerManager = new PlayerManager(this.clientModel.getPlayers());
		this.boardManager = new BoardManager(this.clientModel.getBoard());
	}

	/**
	 * Determines if there are any players to rob after the robber is placed.
	 * @return if there is a player that can be robbed.
	 */
	public List<Integer> robablePlayerIndices(){
		return new ArrayList<Integer>();
	}
}
