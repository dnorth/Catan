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
				+ "CREATE TABLE Games(id integer not null,title text not null,blobJSON text not null,lastCommandNumSaved integer);"
				+ "CREATE TABLE UserGameMap(id integer not null primary key autoincrement,gameID integer not null,userID integer not null,color text not null,foreign key (gameID) references Games(id),foreign key (userID) references Users(id));"
				+ "CREATE TABLE Users(id integer not null,username text not null,password text not null);";
		
	}
	
	public ArrayList<ServerGame> getAll() throws DatabaseException {//I need get 1 user instead of getAll
		
		ArrayList<ServerGame> result = new ArrayList<ServerGame>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "SELECT id, title, blobJSON, lastCommandNumSaved FROM Games";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while(rs.next()){
				int gameID = rs.getInt(1);
				String title = rs.getString(2);
				String modelJSONString = rs.getString(3);
				int lastCommandNumSaved = rs.getInt(4);
				JsonObject blobJSON = new JsonParser().parse(modelJSONString).getAsJsonObject();
//				System.out.println("HERE'S THE BLOB: " + blobJSON.toString());
				ServerGame game = new ServerGame(title, gameID);
				game.setClientModel(new JSONToModel().translateClientModel(blobJSON));
//				System.out.println("GAME: " + String.valueOf(gameID) + " THIS TIME?: " + String.valueOf(lastCommandNumSaved));
				game.setLastCommandSaved(lastCommandNumSaved);
				game.setNumberOfCommands(lastCommandNumSaved);
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
			JsonObject blob = toJson.translateModel(game.getClientModel());
			String query = "insert into Games (blobJSON, lastCommandNumSaved, id, title) values (?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, blob.toString());
			stmt.setInt(2, game.getLastCommandSaved());
			stmt.setInt(3, game.getId());
			stmt.setString(4, game.getTitle());

			
			if (stmt.executeUpdate() != 1) {
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
			JsonObject blob = toJson.translateModel(game.getClientModel());
			String query = "update Games set blobJSON = ?, lastCommandNumSaved = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, blob.toString());
			for (ServerGame g : getAll()) {
				if (g.getId() == game.getId()) {
					if (g.getLastCommandSaved() > game.getLastCommandSaved()) {
						game.setLastCommandSaved(g.getLastCommandSaved());
					}
				}
			}
			stmt.setInt(2, game.getLastCommandSaved());
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
