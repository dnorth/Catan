package client.data;

import java.util.*;

import shared.definitions.CatanColor;
import client.models.Player;

/**
 * Used to pass game information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique game ID</li>
 * <li>Title: Game title (non-empty string)</li>
 * <li>Players: List of players who have joined the game (can be empty)</li>
 * </ul>
 * 
 */
public class GameInfo
{
	private int id;
	private String title;
	private ArrayList<PlayerInfo> players;
	
	public GameInfo()
	{
		setId(-1);
		setTitle("");
		players = new ArrayList<PlayerInfo>();
	}
	
	public void setPlayers(Player[] p) {
		ArrayList<PlayerInfo> pis = new ArrayList<PlayerInfo>();
		for(int i = 0; i < p.length; i++) {
			if(p[i] != null) {
				String name = p[i].getName();
				int id = p[i].getPlayerID();
				int playerIndex = p[i].getPlayerIndex();
				CatanColor cc = null;
				try {
					cc = CatanColor.getCatanColor(p[i].getColor());
				} catch (Exception e) {
					e.printStackTrace();
				}
				String cookie = "FAKE";
				PlayerInfo newPlayerInfo = new PlayerInfo(id, playerIndex, name, cc, cookie);
				pis.add(newPlayerInfo);
			}
		}
		this.players = pis;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void addPlayer(PlayerInfo newPlayer)
	{
		players.add(newPlayer);
	}
	
	public List<PlayerInfo> getPlayers()
	{
		return Collections.unmodifiableList(players);
	}
	
	public ArrayList<PlayerInfo> getFreakingNotUnmodifiablePlayerList() {
		return players;
	}
	
	public void setPlayers(ArrayList<PlayerInfo> list) {
		this.players = list;
	}
	
	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder("\n\nGame Info:" + 
				"\n\tID = " + id +
				"\n\tTitle = " + title);
		StringBuilder playersSB = new StringBuilder();
		for(int i = 0; i < players.size(); i++) {
			playersSB.append("\n" + players.get(i).toString());
		}
		toReturn.append(playersSB.toString());
		return toReturn.toString();
				
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameInfo other = (GameInfo) obj;
		if (id != other.id)
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}

