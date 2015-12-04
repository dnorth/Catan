package SQLDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jsonTranslator.ModelToJSON;
import server.commands.IMovesCommand;
import server.model.ServerGame;

import com.google.gson.JsonObject;

public class CommandSQLDAO {
	
	private Database db;
	
	public CommandSQLDAO(Database db) {
		super();
		this.db = db;
	}
	
	public void add(IMovesCommand command) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			JsonObject blobToAdd = command.toJSON();
			String query = "insert into Users (commandJSON, commandNumber, gameID) values (?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, blobToAdd.getAsString());
			stmt.setInt(2, command.getCommandNumber());
			stmt.setInt(3, command.getGameID());
		
			if (!(stmt.executeUpdate() == 1)) {
				throw new DatabaseException("Could not insert command");
			}
			else {
				throw new DatabaseException("Could not insert command");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert command", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	//add()
	//getcommandsafterindex
}
