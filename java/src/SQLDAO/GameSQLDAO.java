package SQLDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.model.ServerGame;

public class GameSQLDAO {

	private Database db;
	
	public GameSQLDAO(Database db) {
		super();
		this.db = db;
	}
	
	public void resetDatabase() {
		
		String statement = "DROP TABLE IF EXISTS Commands;"
				+ "DROP TABLE IF EXISTS Games;"
				+ "DROP TABLE IF EXISTS UserGameMap;"
				+ "DROP TABLE IF EXISTS Users;"
				+ "CREATE TABLE Commands(id integer not null primary key autoincrement,commandJSON text not null,commandNumber integer not null,gameID integer not null,foreign key (gameID) references Games(id));"
				+ "CREATE TABLE Games(id integer not null primary key,blobJSON text not null,lastCommandNumSaved integer);"
				+ "CREATE TABLE UserGameMap(id integer not null primary key autoincrement,gameID integer not null,userID integer not null,color text not null,foreign key (gameID) references Games(id),foreign key (userID) references Users(id));"
				+ "CREATE TABLE Users(id integer not null primary key,username text not null,password text not null);";
		
	}
	
	public ArrayList<ServerGame> getAll() throws DatabaseException {//I need get 1 user instead of getAll
		
		ArrayList<ServerGame> result = new ArrayList<ServerGame>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "SELECT blobJSON, lastCommandNumSaved From Games";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while(rs.next()){
				String modelJSONString = rs.getString(1);
				JsonObject blobJSON = new JsonParser().parse(modelJSONString).getAsJsonObject();
				ServerGame game = JSONToModel.translateServerGame(blobJSON);
				result.add(game);
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
	
	public void add(ServerGame game) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			ModelToJSON toJson = new ModelToJSON();
			JsonObject blobToAdd = toJson.generateServerGameObject(game);
			String query = "insert into Games (modelJSON, lastCommandNumSaved) values (?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, blobToAdd.getAsString());
			stmt.setString(2, "IMPLEMENT ME");

			
			if (!(stmt.executeUpdate() == 1)) {
				throw new DatabaseException("Could not insert game");
			}
			else {
				throw new DatabaseException("Could not insert game");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert game", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	public void update(ServerGame game) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			ModelToJSON toJson = new ModelToJSON();
			JsonObject blobToAdd = toJson.generateServerGameObject(game);
			String query = "update Games set modelJSON = ?, lastCommandNumberSaved = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, blobToAdd.getAsString());
			stmt.setInt(2, game.getNumberOfCommands()-1);
			stmt.setInt(3, game.getId());
			
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update game");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update game", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
}
