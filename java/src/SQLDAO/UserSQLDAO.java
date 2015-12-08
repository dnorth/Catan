package SQLDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import server.model.ServerUser;

public class UserSQLDAO {

	private Database db;
	
	public UserSQLDAO(Database db) {
		super();
		this.db = db;
	}
	
	public List<ServerUser> getAll() throws DatabaseException {//I need get 1 user instead of getAll
				
		ArrayList<ServerUser> result = new ArrayList<ServerUser>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "SELECT username, password, id FROM Users";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while(rs.next()){
				String username = rs.getString(1);
				String password = rs.getString(2);
				int playerID = rs.getInt(3);			
				result.add(new ServerUser(username, password, playerID));
			}
		}
		catch(SQLException e){
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		}
		finally{
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		return result;
	}
	
	public ServerUser getById(int userID) throws DatabaseException {//I need get 1 user instead of getAll
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "SELECT username, password FROM Users WHERE id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, userID);
			
			rs = stmt.executeQuery();
			if(rs.next()){
				String username = rs.getString(1);
				String password = rs.getString(2);
				ServerUser user = new ServerUser(username, password, userID);
				return user;
			}
			else {
				throw new DatabaseException("Could not find user");
			}
		}
		catch(SQLException e){
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		}
		finally{
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
	}
	
	/**
	 * Adds a User to the database
	 * @param User
	 * @throws DatabaseException
	 */
	public void add(ServerUser user) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "INSERT INTO Users (id, username, password) VALUES (?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, user.getPlayerID());
			stmt.setString(2, user.getUsername());
			stmt.setString(3, user.getPassword());

			
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setPlayerID(id);
			}
			else {
				throw new DatabaseException("Could not insert user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert user", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}

}
