package server.handlers.user;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterHandler.
 */
public class RegisterHandler implements HttpHandler {
	Logger logger;

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		logger = Logger.getLogger("Catan");
		logger.info("Handling Register.");
		exchange.getResponseHeaders().set("Content-Type", "application/json");
		String response = "Success";
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
		exchange.getResponseBody().write(response.getBytes());
        exchange.close();
	}

}
