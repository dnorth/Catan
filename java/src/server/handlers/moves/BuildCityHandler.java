package server.handlers.moves;

import java.io.IOException;
import java.util.logging.Logger;

import server.facade.MovesFacade;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class BuildCityHandler.
 */
public class BuildCityHandler implements HttpHandler {
	Logger logger;
	MovesFacade movesFacade;

	public BuildCityHandler(MovesFacade movesFacade) {
		this.movesFacade = movesFacade;
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
