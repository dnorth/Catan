package server.commands.moves;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.TurnTracker;
import client.models.VertexObject;
import client.models.mapdata.Hex;
import server.commands.IMovesCommand;
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
	 */
	@Override
	public void execute() {
		if(number==7){
			//check if players need to discard
			if(game.getClientModel().needToDiscard()){
				game.getClientModel().setTurnTrackerStatus("Discarding");
			}
			else{
				game.getClientModel().setTurnTrackerStatus("Robbing");
			}
		}

		ClientModel model = game.getClientModel();
		for(Hex h : model.getBoard().getHexes())
		{
			if(h.getNumberToken()==number){
				addResourcesToPlayers(h, model);
			}
		}
		game.getClientModel().increaseVersion();
	}

	private void addResourcesToPlayers(Hex h, ClientModel model)
	{
		if(h.getHexType()== HexType.DESERT || h.getLocation().Equals(model.getBoard().getRobber())){ // throw exception
			return;
		}

		TurnTracker turnTracker = game.getClientModel().getTurnTracker();
		if(number<2 || number > 12) // throw exception
		{return;}

		if(turnTracker.getStatus().equals("Rolling")==false){ // throw exception
			return;
		}

		if(number==7){
			for(Player p : game.getClientModel().getPlayers()){
				if(p.needsToDiscard()){
					turnTracker.setStatus("Discard");
				}
			}
			// continue work here
			
		}

		Resources bank = model.getBank();
		ResourceType type = h.getResourceType();

		for(VertexObject v  : model.getBoard().getSettlements())
		{
			if(h.getLocation().Equals(v.getVertexLocation().getHexLoc()))
			{
				Player p = model.getPlayers()[v.getOwner()];

				if(bank.hasResource(type, 1)){
					p.getResources().addResource(h.getResourceType(),1,bank);
				}
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
				else if(bank.hasResource(type, 1)){                  // if bank only has 1 resource of that type, give it to player
					p.getResources().addResource(h.getResourceType(),1,bank);
				}



			}
		}

	}

}
