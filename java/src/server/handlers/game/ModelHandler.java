package server.handlers.game;

import java.io.IOException;
import java.util.logging.Logger;

import server.facade.GameFacade;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelHandler.
 */
public class ModelHandler implements HttpHandler{
	Logger logger;
	GameFacade gameFacade;

	public ModelHandler(GameFacade gameFacade) {
		this.gameFacade = gameFacade;
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		logger = Logger.getLogger("Catan");

	}

}
