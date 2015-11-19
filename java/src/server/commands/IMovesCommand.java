package server.commands;

import server.exceptions.AlreadyPlayedDevCardException;
import server.exceptions.CantBuildThereException;
import server.exceptions.DontHaveDevCardException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidMaritimeTradeException;
import server.exceptions.InvalidPlayerException;
import server.exceptions.InvalidRollException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NoTradeOfferedException;
import server.exceptions.NotYourTurnException;
import server.exceptions.OutOfPiecesException;
import server.exceptions.RobberIsAlreadyThereException;

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
	 */
	public void execute() throws InvalidStatusException, InsufficientResourcesException, CantBuildThereException, NotYourTurnException, OutOfPiecesException, NoTradeOfferedException, InvalidPlayerException, InvalidMaritimeTradeException, RobberIsAlreadyThereException, InvalidRollException, DontHaveDevCardException, AlreadyPlayedDevCardException;
}