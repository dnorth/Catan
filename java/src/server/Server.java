package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import server.facade.ServerFacade;
import server.handlers.moves.*;
import server.handlers.user.*;
import server.handlers.games.*;
import server.handlers.game.*;


import com.sun.net.httpserver.*;

public class Server {

	private static int server_port_number = 8081;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private static Logger logger;	
	private HttpServer server;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
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
		
	public Server(){
		return;
	}
	
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
		
		/*
		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/GetProjects", getProjectsHandler);
		server.createContext("/GetSampleImage", getSampleImageHandler);
		server.createContext("/DownloadBatch", downloadBatchHandler);
		server.createContext("/GetFields", getFieldsHandler);
		server.createContext("/SubmitBatch", submitBatchHandler);
		server.createContext("/Search", searchHandler);
		server.createContext("/", downloadHandler);
		*/
		//The other contexts as well
		
		logger.info("Starting HTTP Server.");
		server.start();
		
	}
	
	
	private HttpHandler loginHandler = new LoginHandler();
	private HttpHandler registerHandler = new RegisterHandler();
	private HttpHandler listHandler = new ListHandler();
	private HttpHandler createHandler = new CreateHandler();
	private HttpHandler joinHandler = new JoinHandler();
	private HttpHandler modelHandler = new ModelHandler();
	private HttpHandler sendChatHandler = new SendChatHandler();
	private HttpHandler rollNumberHandler = new RollNumberHandler();
	private HttpHandler robPlayerHandler = new RobPlayerHandler();
	private HttpHandler finishTurnHandler = new FinishTurnHandler();
	private HttpHandler buyDevCardHandler = new BuyDevCardHandler();
	private HttpHandler yearOfPlentyHandler = new YearOfPlentyHandler();
	private HttpHandler roadBuildingHandler = new RoadBuildingHandler();
	private HttpHandler soldierHandler = new SoldierHandler();
	private HttpHandler monumentHandler = new MonumentHandler();
	private HttpHandler buildRoadHandler = new BuildRoadHandler();
	private HttpHandler buildCityHandler = new BuildCityHandler();
	private HttpHandler buildSettlementHandler = new BuildSettlementHandler();
	private HttpHandler offerTradeHandler = new OfferTradeHandler();
	private HttpHandler acceptTradeHandler = new AcceptTradeHandler();
	private HttpHandler maritimeTradeHandler = new MaritimeTradeHandler();
	private HttpHandler discardCardsHandler = new DiscardCardsHandler();

	
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
