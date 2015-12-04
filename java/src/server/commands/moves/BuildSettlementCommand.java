package server.commands.moves;

import java.util.ArrayList;
import java.util.List;

import jsonTranslator.ModelToJSON;

import com.google.gson.JsonObject;

import client.models.ClientModel;
import client.models.Player;
import client.models.Resources;
import client.models.VertexObject;
import client.models.communication.MessageLine;
import client.models.mapdata.Board;
import client.models.mapdata.EdgeLocation;
import client.models.mapdata.Hex;
import client.models.mapdata.PortTrade;
import server.commands.IMovesCommand;
import server.exceptions.CantBuildThereException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NotYourTurnException;
import server.exceptions.OutOfPiecesException;
import server.model.ServerGame;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class BuildSettlementCommand.
 */
public class BuildSettlementCommand implements IMovesCommand {

	ServerGame game;
	int playerIndex;
	VertexLocation location;
	boolean free;
	int commandNumber;
	
	public BuildSettlementCommand(ServerGame game, int playerIndex,
			VertexLocation location, boolean free, int commandNumber) {
		super();
		this.game = game;
		this.playerIndex = playerIndex;
		this.location = location;
		this.free = free;
		this.commandNumber = commandNumber;
	}


	/**
	 *  Builds a settlement.
	 * @throws InsufficientResourcesException 
	 * @throws CantBuildThereException 
	 * @throws InvalidStatusException 
	 * @throws NotYourTurnException 
	 * @throws OutOfPiecesException 
	 * @throws InvalidPlayerIndexException 
	 */
	@Override
	public void execute() throws InsufficientResourcesException, CantBuildThereException, InvalidStatusException, NotYourTurnException, OutOfPiecesException, InvalidPlayerIndexException {
		Board board = game.getClientModel().getBoard();
		List<VertexObject> settlements = board.getSettlements();
		
		ClientModel model = game.getClientModel();
		if(!free) {
			model.checkStatus("Playing");
		}
		model.checkTurn(playerIndex);
		model.checkPlayerIndex(playerIndex);
		
		
		VertexObject settlement = new VertexObject(playerIndex, location);
		game.getClientModel().checkSettlement(playerIndex, new EdgeLocation(location));
		
		Player p = game.getClientModel().getPlayers()[playerIndex];
		Resources bank = game.getClientModel().getBank();
		
		if(!free) {
			p.payForSettlement(bank);
		}
		p.decSettlements();
		settlements.add(settlement);
		p.incrementVictoryPoints();
		game.getClientModel().getLog().getLines().add(new MessageLine(p.getName() + " built a settlement", p.getName()));
		if(game.getClientModel().getTurnTrackerStatus().equals("SecondRound")) {
			HexLocation[] hexLocations = settlement.getHexes();
			ArrayList<Hex> hexes = new ArrayList<Hex>();
			hexes.add(game.getClientModel().getBoard().getHexFromCoords(hexLocations[0].getX(), hexLocations[0].getY()));
			hexes.add(game.getClientModel().getBoard().getHexFromCoords(hexLocations[1].getX(), hexLocations[1].getY()));
			hexes.add(game.getClientModel().getBoard().getHexFromCoords(hexLocations[2].getX(), hexLocations[2].getY()));
			for(Hex h : hexes) {
				if(h != null) {
					if (h.getResource() != null) {						
						p.getResources().addResource(h.getResourceType(),1,bank);
					}
				}
			}

		}
		board.updatePlayerMaritimeTradeCosts(p);
		game.getClientModel().increaseVersion();
	}


	@Override
	public JsonObject toJSON() {
		ModelToJSON toJSON = new ModelToJSON();
		return toJSON.getBuildSettlementCommand(playerIndex, location, free);
	}


	@Override
	public int getCommandNumber() {
		return commandNumber;
	}


	@Override
	public int getGameID() {
		return game.getId();
	}

}