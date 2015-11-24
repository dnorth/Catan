package server.exceptions;

public class NoTradeOfferedException extends Exception{

	public NoTradeOfferedException() {
		super("No trade offered.");
	}
}
