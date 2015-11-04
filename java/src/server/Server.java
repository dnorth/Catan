package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import server.facade.iGameFacade;
import server.handlers.moves.*;
import server.handlers.user.*;
import server.handlers.games.*;
import server.handlers.game.*;


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
		
		
		server.createContext("/user/login", loginHandler);
		server.createContext("/user/register", registerHandler);
		server.createContext("/games/list", listHandler);
		server.createContext("/games/create", createHandler);
		server.createContext("/games/join", joinHandler);
		server.createContext("/moves/SendChat", sendChatHandler);
		server.createContext("/moves/rollNumber", rollNumberHandler);
		server.createContext("/moves/robPlayer", robPlayerHandler);
		server.createContext("/moves/finishTurn", finishTurnHandler);
		server.createContext("/moves/buyDevCard", buyDevCardHandler);
		server.createContext("/moves/Year_or_Plenty", yearOfPlentyHandler);
		server.createContext("/moves/Road_Building", roadBuildingHandler);
		server.createContext("/moves/Soldier", soldierHandler);
		server.createContext("/moves/Monopoly", monopolyHandler);
		server.createContext("/moves/Monument", monumentHandler);
		server.createContext("/moves/buildRoad", buildRoadHandler);
		server.createContext("/moves/buildSettlement", buildSettlementHandler);
		server.createContext("/moves/buildCity", buildCityHandler);
		server.createContext("/moves/offerTrade", offerTradeHandler);
		server.createContext("/moves/acceptTrade", acceptTradeHandler);
		server.createContext("/moves/maritimeTrade", maritimeTradeHandler);
		server.createContext("/moves/discardCards", discardCardsHandler);
		server.createContext("/game/model", modelHandler);


		
		//The other contexts as well
		
		logger.info("Starting HTTP Server.");
		server.start();
		
	}
	
	
	/** The login handler. */
	private HttpHandler loginHandler = new LoginHandler();
	
	/** The register handler. */
	private HttpHandler registerHandler = new RegisterHandler();
	
	/** The list handler. */
	private HttpHandler listHandler = new ListHandler();
	
	/** The create handler. */
	private HttpHandler createHandler = new CreateHandler();
	
	/** The join handler. */
	private HttpHandler joinHandler = new JoinHandler();
	
	/** The model handler. */
	private HttpHandler modelHandler = new ModelHandler();
	
	/** The send chat handler. */
	private HttpHandler sendChatHandler = new SendChatHandler();
	
	/** The roll number handler. */
	private HttpHandler rollNumberHandler = new RollNumberHandler();
	
	/** The rob player handler. */
	private HttpHandler robPlayerHandler = new RobPlayerHandler();
	
	/** The finish turn handler. */
	private HttpHandler finishTurnHandler = new FinishTurnHandler();
	
	/** The buy dev card handler. */
	private HttpHandler buyDevCardHandler = new BuyDevCardHandler();
	
	/** The year of plenty handler. */
	private HttpHandler yearOfPlentyHandler = new YearOfPlentyHandler();
	
	/** The road building handler. */
	private HttpHandler roadBuildingHandler = new RoadBuildingHandler();
	
	/** The soldier handler. */
	private HttpHandler soldierHandler = new SoldierHandler();
	
	/** The monopoly handler. */
	private HttpHandler monopolyHandler = new MonopolyHandler();
	
	/** The monument handler. */
	private HttpHandler monumentHandler = new MonumentHandler();
	
	/** The build road handler. */
	private HttpHandler buildRoadHandler = new BuildRoadHandler();
	
	/** The build city handler. */
	private HttpHandler buildCityHandler = new BuildCityHandler();
	
	/** The build settlement handler. */
	private HttpHandler buildSettlementHandler = new BuildSettlementHandler();
	
	/** The offer trade handler. */
	private HttpHandler offerTradeHandler = new OfferTradeHandler();
	
	/** The accept trade handler. */
	private HttpHandler acceptTradeHandler = new AcceptTradeHandler();
	
	/** The maritime trade handler. */
	private HttpHandler maritimeTradeHandler = new MaritimeTradeHandler();
	
	/** The discard cards handler. */
	private HttpHandler discardCardsHandler = new DiscardCardsHandler();

	
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
	
}
