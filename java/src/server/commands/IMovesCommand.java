package server.commands;

import com.google.gson.JsonObject;

import server.exceptions.*;
import jsonTranslator.ModelToJSON;


// TODO: Auto-generated Javadoc
/**
 * The Interface IMovesCommand.
 */
public interface IMovesCommand {
	
	/**
	 * Execute.
	 * @throws InvalidStatusException 
	 * @throws OutOfPiecesException 
	 * @throws NotYourTurnException 
	 * @throws CantBuildThereException 
	 * @throws InsufficientResourcesException 
	 * @throws InvalidPlayerException 
	 * @throws NoTradeOfferedException 
	 * @throws InvalidMaritimeTradeException 
	 * @throws RobberIsAlreadyThereException 
	 * @throws InvalidRollException 
	 * @throws AlreadyPlayedDevCardException 
	 * @throws DontHaveDevCardException 
	 * @throws InvalidPlayerIndexException 
	 */
	public void execute() throws InvalidStatusException, InsufficientResourcesException, CantBuildThereException, NotYourTurnException, OutOfPiecesException, NoTradeOfferedException, InvalidPlayerException, InvalidMaritimeTradeException, RobberIsAlreadyThereException, InvalidRollException, DontHaveDevCardException, AlreadyPlayedDevCardException, InvalidPlayerIndexException;
	public JsonObject toJSON();
	public int getCommandNumber();
	public int getGameID();
}