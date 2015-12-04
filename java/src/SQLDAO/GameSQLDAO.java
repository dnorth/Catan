package SQLDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.model.ServerGame;
import server.model.ServerUser;

public class GameSQLDAO {

	private Database db;
	
	public GameSQLDAO(Database db) {
		super();
		this.db = db;
	}
	
	public List<ServerGame> getAll() throws DatabaseException {//I need get 1 user instead of getAll
		
		ArrayList<ServerGame> result = new ArrayList<ServerGame>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "SELECT username, password, id from Users";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			JSONToModel toModel = new JSONToModel();
			while(rs.next()){
				String serverGameJSON = rs.getString(1);
				ServerGame game = toModel.TranslateServerGame(serverGameJSON);
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
			String query = "insert into Users (modelJSON, lastCommandNumSaved) values (?, ?)";
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
	//update()
}
