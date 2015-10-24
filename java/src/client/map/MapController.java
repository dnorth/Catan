package client.map;

import java.util.*;

import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;
import client.models.ClientModel;
import client.models.Player;
import client.models.VertexObject;
import client.models.mapdata.Board;
import client.models.mapdata.Hex;
import client.models.mapdata.Port;
import client.models.mapdata.Road;
import client.state.IStateBase;
import client.state.SetupOneActivePlayerState;
import client.state.SetupTwoActivePlayerState;
import client.state.StateManager;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private IRobView robView;
	private StateManager stateManager;
	
	public MapController(IMapView view, IRobView robView, StateManager stateManager) {
		
		super(view);
		
		setRobView(robView);
		this.stateManager = stateManager;
		this.stateManager.addObserver(this);
		
//		initFromModel();
	}
	
	public IMapView getView() {
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	private HexLocation[] getWaterLocations() {
		return new HexLocation[] {
				new HexLocation(0,-3), new HexLocation(0,3),
				new HexLocation(1,-3), new HexLocation(-1,3),
				new HexLocation(2,-3), new HexLocation(-2,3),
				new HexLocation(3,-3), new HexLocation(-3,3),
				new HexLocation(3,-2), new HexLocation(-3,2),
				new HexLocation(3,-1), new HexLocation(-3,1),
				new HexLocation(3,0), new HexLocation(-3,0),
				new HexLocation(2,1), new HexLocation(-2,-1),
				new HexLocation(1,2), new HexLocation(-1,-2)
		};
	}
	
	protected void initFromModel() {
		ClientModel clientModel;
		try {
			clientModel = this.stateManager.getClientModel();
		} catch (NullPointerException e) {
			return;
		}
		Board board = clientModel.getBoard();
		
		// Place Hexes, Add Numbers
		for (Hex h : board.getHexes()) {
			getView().addHex(h.getLocation().getSharedHexLocation(), h.getHexType());
			if (h.getNumberToken() != 0) getView().addNumber(h.getLocation().getSharedHexLocation(), h.getNumberToken());
		}
		
		// Place Water
		for (HexLocation hl : this.getWaterLocations())  {
			getView().addHex(hl, HexType.WATER);
		}
		
		// Place Ports
		for (Port p : board.getPorts()) {
			getView().addPort(p.getSharedEdgeLocation(), p.getPortType());
		}
		
		// Place Settlements
		for (VertexObject s : board.getSettlements()) {
			CatanColor color = null;
			try {
				color = CatanColor.getCatanColor(clientModel.getPlayers()[s.getOwner()].getColor());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			VertexLocation vertexLocation = s.getVertexLocation();
			getView().placeSettlement(vertexLocation, color);
		}
		
		// Place Cities
		for (VertexObject s : board.getCities()) {
			CatanColor color = null;
			try {
				color = CatanColor.getCatanColor(clientModel.getPlayers()[s.getOwner()].getColor());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			VertexLocation vertexLocation = s.getVertexLocation();
			getView().placeCity(vertexLocation, color);
		}
		
		// Place Roads
		for (Road r : board.getRoads()) {
			CatanColor color = null;
			try {
				color = CatanColor.getCatanColor(clientModel.getPlayers()[r.getOwner()].getColor());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			getView().placeRoad(r.getLocation().getSharedEdgeLocation(), color);
		}
		
		// Place Robber
		getView().placeRobber(board.getRobber().getSharedHexLocation());
		
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		System.out.println("CANPLACEROAD?!");
		return stateManager.getState().canPlaceRoadAtLocation(edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return this.stateManager.getFacade().canPlaceSettlement(vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return this.stateManager.getFacade().canPlaceCity(vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return this.stateManager.getFacade().canPlaceRobber(hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		
		this.stateManager.getState().placeRoad(edgeLoc);
		/*CatanColor color = null;
		try {
			color = CatanColor.getCatanColor(
					this.stateManager.getClientModel().getPlayers()[this.stateManager.getFacade().getPlayerIndex()].getColor());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		getView().placeRoad(edgeLoc, color);*/
	}

	public void placeSettlement(VertexLocation vertLoc) {
		
		this.stateManager.getState().placeSettlement(vertLoc);
		/*CatanColor color = null;
		try {
			color = CatanColor.getCatanColor(
					this.stateManager.getClientModel().getPlayers()[this.stateManager.getFacade().getPlayerIndex()].getColor());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		getView().placeSettlement(vertLoc, color);*/
	}

	public void placeCity(VertexLocation vertLoc) {
		
		this.stateManager.getState().placeCity(vertLoc);
		/*CatanColor color = null;
		try {
			color = CatanColor.getCatanColor(
					this.stateManager.getClientModel().getPlayers()[this.stateManager.getFacade().getPlayerIndex()].getColor());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		getView().placeCity(vertLoc, color);*/
	}

	public void placeRobber(HexLocation hexLoc) {
		
		this.stateManager.getState().placeRobber(hexLoc);
		//getView().placeRobber(hexLoc);
		
		//getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		IStateBase state = stateManager.getState();
		if(state instanceof SetupOneActivePlayerState || state instanceof SetupTwoActivePlayerState) {
			if(pieceType.equals(PieceType.ROAD) || pieceType.equals(PieceType.SETTLEMENT)) {
				getView().startDrop(pieceType, stateManager.getFacade().getLocalPlayer().getColor(), false);
			}
			else {
				try {
					throw new Exception("CANT PLACE A " + pieceType.toString() + " RIGHT NOW!");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("These aren't the exceptions you're looking for");
					System.out.println(e.getMessage());
				}
			}
		}
		else {
			getView().startDrop(pieceType, stateManager.getFacade().getLocalPlayer().getColor(), true);
		}
	}
	
	public void cancelMove() {
	}
	
	public void playSoldierCard() {	
		stateManager.getState().playSoldierCard();
	}
	
	public void playRoadBuildingCard() {	
		stateManager.getState().playRoadBuildingCard();
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		stateManager.getState().robPlayer(victim);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(stateManager.getState() instanceof SetupOneActivePlayerState) {
			startMove(PieceType.ROAD, true, true);
		}		
		
		if(!stateManager.getClientModel().newCli()) { //Don't want to do this if the client is new...
			this.initFromModel();
		}
	}
	
}

