package server.commands.moves;

import client.models.ClientModel;
import client.models.Player;
import client.models.VertexObject;
import client.models.mapdata.Hex;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import shared.definitions.HexType;

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
			game.getClientModel().getTurnTracker().setStatus("Robbing");
		}
		
		ClientModel model = game.getClientModel();
		for(Hex h : model.getBoard().getHexes())
		{
			if(h.getNumberToken()==number){
				addResourcesToPlayers(h, model);
			}
		}
	}

	private void addResourcesToPlayers(Hex h, ClientModel model)
	{
		if(h.getHexType()== HexType.DESERT || h.getLocation().Equals(model.getBoard().getRobber())){
			return;
		}
		for(VertexObject v  : model.getBoard().getSettlements())
		{
			if(h.getLocation().Equals(v.getVertexLocation().getHexLoc()))
			{
				Player p = model.getPlayers()[v.getOwner()];
				p.getResources().addOne(h.getResourceType());
			}
		}

		for(VertexObject v  : model.getBoard().getCities())
		{
			if(h.getLocation().Equals(v.getVertexLocation().getHexLoc()))
			{
				Player p = model.getPlayers()[v.getOwner()];
				p.getResources().addOne(h.getResourceType());
				p.getResources().addOne(h.getResourceType());
			}
		}

	}

}
