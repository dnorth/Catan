package SQLDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.commands.IMovesCommand;
import server.model.ServerGame;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

	public ArrayList<IMovesCommand> getCommandsByGameAfterIndex(ArrayList<ServerGame> games, ArrayList<Integer> indeces) {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		ArrayList<IMovesCommand> listOCommands = new ArrayList<IMovesCommand>();
		try {
			for(int i = 0; i < games.size(); i++) {
				String query1 = "SELECT commandJSON, commandNumber FROM Commands WHERE gameID = ? AND commandNumber > ?";
				stmt = db.getConnection().prepareStatement(query1);
				stmt.setInt(1, games.get(i).getId());
				stmt.setInt(2, indeces.get(i));
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					String commandJSONString = rs.getString(1);
					int commandNumber = rs.getInt(2);
					JsonObject commandJSONObject = new JsonParser().parse(commandJSONString).getAsJsonObject();
					IMovesCommand command = JSONToModel.translateCommand(commandJSONObject, games.get(i), commandNumber);
					listOCommands.add(command);
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		return listOCommands;
	}
}
