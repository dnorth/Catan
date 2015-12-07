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
	
	public void addPlayerToGame(int playerID, int gameID) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "INSERT INTO UserGameMap (playerID, gameID) VALUES (?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, playerID);
			stmt.setInt(2, gameID);

			
			if (!(stmt.executeUpdate() == 1)) {
				throw new DatabaseException("Could not add player to game");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not add player to game", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	public ArrayList<Integer> getPlayerIDsForGame(int gameID) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		ArrayList<Integer> playerIDs = new ArrayList<Integer>();
		try {
			String query = "SELECT playerID FROM UserGameMap where gameID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameID);

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				playerIDs.add(rs.getInt(1));
			}
			return playerIDs;
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not find players", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	public ArrayList<Integer> getGameIDsForPlayer (int playerID) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		ArrayList<Integer> gameIDs = new ArrayList<Integer>();
		try {
			String query = "SELECT gameID FROM UserGameMap where playerID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, playerID);

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
