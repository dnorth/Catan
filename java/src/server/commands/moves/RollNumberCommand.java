package server.commands.moves;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.TurnTracker;
import client.models.VertexObject;
import client.models.mapdata.Hex;
import server.commands.IMovesCommand;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidRollException;
import server.exceptions.InvalidStatusException;
import server.model.ServerGame;
import shared.definitions.HexType;
import shared.definitions.ResourceType;

// TODO: Auto-generated Javadoc
/**
 * The Class RollNumberCommand.
 */
public class RollNumberCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	int number;



	public RollNumberCommand(ServerGame game, int playerIndex, int number) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.number = number;
	}



	/**
	 *  Rolls the "dice". 
	 * @throws InvalidRollException 
	 * @throws InvalidStatusException 
	 * @throws InsufficientResourcesException 
	 */
	@Override
	public void execute() throws InvalidRollException, InvalidStatusException, InsufficientResourcesException {
		
		if(number<2 || number > 12) // throw exception
		{throw new InvalidRollException();}
		

		TurnTracker turnTracker = game.getClientModel().getTurnTracker();

		if(number==7){
			//check if players need to discard
			if(game.getClientModel().needToDiscard()){
				turnTracker.setStatus("Discarding");
				return;
			}
			else{
				turnTracker.setStatus("Robbing");
				return;
			}
		}
<<<<<<< Updated upstream
		
		turnTracker.setStatus("Rolling");
=======
		else {
			game.getClientModel().setTurnTrackerStatus("Playing");
		}
>>>>>>> Stashed changes

		ClientModel model = game.getClientModel();
		for(Hex h : model.getBoard().getHexes())
		{
			if(h.getNumberToken()==number){
				addResourcesToPlayers(h, model);
			}
		}
		game.getClientModel().increaseVersion();
	}

	private void addResourcesToPlayers(Hex h, ClientModel model) throws InvalidStatusException, InsufficientResourcesException
	{
		if(h.getHexType()== HexType.DESERT || h.getLocation().Equals(model.getBoard().getRobber())){ // throw exception
			return;
		}

		model.checkStatus("Rolling");

		Resources bank = model.getBank();
		ResourceType type = h.getResourceType();

		for(VertexObject v  : model.getBoard().getSettlements())
		{
			if(h.getLocation().Equals(v.getVertexLocation().getHexLoc()))
			{
				Player p = model.getPlayers()[v.getOwner()];
					p.getResources().addResource(h.getResourceType(),1,bank);
			}
		}

		for(VertexObject v  : model.getBoard().getCities())
		{
			if(h.getLocation().Equals(v.getVertexLocation().getHexLoc()))
			{
				Player p = model.getPlayers()[v.getOwner()];

				if(bank.hasResource(type, 2)){
					p.getResources().addResource(h.getResourceType(),2,bank);
				}
				else {                  // if bank only has 1 resource of that type, give it to player
					p.getResources().addResource(h.getResourceType(),1,bank);
				}
			}
		}

	}

}
