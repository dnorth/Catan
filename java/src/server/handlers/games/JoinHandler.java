package server.handlers.games;

import java.io.IOException;
import java.util.logging.Logger;

import server.facade.GamesFacade;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class JoinHandler.
 */
public class JoinHandler implements HttpHandler {
	Logger logger;
	GamesFacade gamesFacade;

	public JoinHandler(GamesFacade gamesFacade) {
		this.gamesFacade = gamesFacade;
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
