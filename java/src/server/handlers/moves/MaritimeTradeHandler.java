package server.handlers.moves;

import java.io.IOException;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class MaritimeTradeHandler.
 */
public class MaritimeTradeHandler implements HttpHandler {
	Logger logger;

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		logger = Logger.getLogger("Catan");

	}

}
