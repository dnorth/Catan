package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import server.facade.GameFacade;
import server.facade.GamesFacade;
import server.facade.MovesFacade;
import server.facade.UserFacade;
import server.facade.iGameFacade;
import server.handlers.GamesHandler;
import server.handlers.GameHandler;
import server.handlers.MovesHandler;
import server.handlers.UserHandler;
import server.model.ServerData;

import com.sun.net.httpserver.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Server.
 */
public class Server {

	/** The server_port_number. */
	private static int server_port_number = 8081;
	
	/** The Constant MAX_WAITING_CONNECTIONS. */
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	/** The logger. */
	private static Logger logger;	
	
	/** The server. */
	private HttpServer server;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static ServerData serverData = new ServerData();
	private GamesFacade gamesFacade = new GamesFacade();
	private GameFacade gameFacade = new GameFacade();
	private UserFacade userFacade = new UserFacade();
	private MovesFacade movesFacade = new MovesFacade();
	
	/**
	 * Inits the log.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("Catan"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}
		
	/**
	 * Instantiates a new server.
	 */
	public Server(){
		return;
	}
	
	/**
	 * Run.
	 */
	public void run(){
		logger.info("Initializing Model");
//		serverData = new ServerData();
//		gameFacade = new GameFacade(serverData);
//		gamesFacade = new GamesFacade(serverData);
//		movesFacade = new MovesFacade(serverData);
//		userFacade = new UserFacade(serverData);
		/*try {
			ServerFacade.initialize();		
		}
		catch (ServerException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}*/		
		logger.info("Initializing HTTP Server on port " + server_port_number);
		
		try {
			server = HttpServer.create(new InetSocketAddress(server_port_number), 
											MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e){
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		server.setExecutor(null); // user the default executor
		
		server.createContext("/user", userHandler);
		server.createContext("/games", gamesHandler);
		server.createContext("/moves", movesHandler);
		server.createContext("/game", gameHandler);


		
		//The other contexts as well
		
		logger.info("Starting HTTP Server.");
		server.start();
		
	}
	
	/** The User handler */
	private HttpHandler userHandler = new UserHandler(userFacade);

	private HttpHandler gamesHandler = new GamesHandler(gamesFacade);
	
	/** The model handler. */
	private HttpHandler gameHandler = new GameHandler(gameFacade);
	
	/** The discard cards handler. */
	private HttpHandler movesHandler = new MovesHandler(movesFacade);

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws ServerException the server exception
	 */
	public static void main(String[] args) throws ServerException {
		try{
			if(args.length > 0)
			{
				server_port_number = Integer.parseInt(args[0]);
			}
			new Server().run();
		}
		catch (NumberFormatException e)
		{
			throw new ServerException("Not a valid port number.");
		}
	}

	public static ServerData getServerData() {
		return serverData;
	}

	public static void setServerData(ServerData serverData) {
		Server.serverData = serverData;
	}
	
	
	
}
