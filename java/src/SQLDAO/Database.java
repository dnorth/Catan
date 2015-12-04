package SQLDAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Database {

	private static final String DATABASE_DIRECTORY = "./";
	//private static final String DATABASE_DIRECTORY = "C:\\Users\\James\\workspace\\RecordIndexer";
	private static final String DATABASE_FILE = "RecordIndexer.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY+
			File.separator + DATABASE_FILE;
	
	private static Logger logger;
	
	static{
		logger = Logger.getLogger("recordindexer");
	}
	
	public static void initialize() throws DatabaseException { 
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e){
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			logger.throwing("server.database.Database", "initialize", serverEx);
			
			throw serverEx;
		}
	}
	private CommandSQLDAO commandSQLDAO;
	private GameUserMapSQLDAO gameUserMapSQLDAO;
	private GameSQLDAO gameSQLDAO;
	private UserSQLDAO userDQLDAO;
	private Connection connection;
	
	public Database(){
		commandSQLDAO = new CommandSQLDAO(this);
		gameUserMapSQLDAO = new GameUserMapSQLDAO(this);
		gameSQLDAO = new GameSQLDAO(this);
		userDQLDAO = new UserSQLDAO(this);
		connection = null;
	}

	public Connection getConnection() {
		return connection;
	}
	
	public void startTransaction() throws DatabaseException {
		try{
			assert (connection == null);
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}
		catch(SQLException e) {
			throw new DatabaseException("Could not connect to database. Make sure" + DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}
	
	public void endTransaction(boolean commit) {
		if(connection != null) {
			try {
				if(commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (SQLException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally {
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn){
		if(conn != null) {
			try{
				conn.close();
			}
			catch(SQLException e) {
				System.out.println("Could not close connection");
				e.printStackTrace();
			}
		}
	}
	
	public static void safeClose(Statement stmt){
		if(stmt != null) {
			try{
				stmt.close();
			}
			catch(SQLException e) {
				System.out.println("Could not close statement");
				e.printStackTrace();
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt){
		if(stmt != null) {
			try{
				stmt.close();
			}
			catch(SQLException e) {
				System.out.println("Could not close PreparedStatement");
				e.printStackTrace();
			}
		}
	}
	
	public static void safeClose(ResultSet rs){
		if(rs != null) {
			try{
				rs.close();
			}
			catch(SQLException e) {
				System.out.println("Could not close ResultSet");
				e.printStackTrace();
			}
		}
	}
	
}