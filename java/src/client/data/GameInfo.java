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
			if(p[i] == null) System.out.println("PLAYER AT INDEX " + i + " IS NULL");
			else {
				System.out.println("PLAYER AT INDEX " + i + " = " + p[i].toString());
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
				System.out.println("PLAYERINFO = " + newPlayerInfo.toString());
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
}

