package SQLDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.model.ServerPlayer;

public class GameUserMapSQLDAO {

	private Database db;
	
	public GameUserMapSQLDAO(Database db) {
		super();
		this.db = db;
	}
	
	public void addPlayerToGame(int userID, int gameID, String color) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			ArrayList<Integer> usersInGame = getUserIDsForGame(gameID);
			if(!usersInGame.contains(userID)) {
				String query = "INSERT INTO UserGameMap (userID, gameID, color) VALUES (?, ?, ?)";
				stmt = db.getConnection().prepareStatement(query);
				stmt.setInt(1, userID);
				stmt.setInt(2, gameID);
				stmt.setString(3, color);
				if(stmt.executeUpdate() != 1) throw new DatabaseException("Could not insert user, game and color");
			}
			else {
				String query = "UPDATE UserGameMap set color = ? WHERE userID = ? AND gameID = ?";
				stmt = db.getConnection().prepareStatement(query);
				stmt.setString(1, color);
				stmt.setInt(2, userID);
				stmt.setInt(3, gameID);
				if(stmt.executeUpdate() != 1) throw new DatabaseException("Could not update user's color");

			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert or update user, game and color", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	public ArrayList<Integer> getUserIDsForGame(int gameID) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		ArrayList<Integer> userIDs = new ArrayList<Integer>();
		try {
			String query = "SELECT userID FROM UserGameMap where gameID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameID);

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				userIDs.add(rs.getInt(1));
			}
			return userIDs;
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not find players", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	public String getColorForGameAndUser(int gameID, int userID) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "SELECT color FROM UserGameMap WHERE gameID = ? AND  userID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameID);
			stmt.setInt(2, userID);

			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				return rs.getString(1);
			}
			return null;
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not find users", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	public ArrayList<Integer> getGameIDsForUser (int userID) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		ArrayList<Integer> gameIDs = new ArrayList<Integer>();
		try {
			String query = "SELECT gameID FROM UserGameMap where userID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, userID);

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				gameIDs.add(rs.getInt(1));
			}
			return gameIDs;
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not find games", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}

}
