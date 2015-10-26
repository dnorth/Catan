package client.map;

import java.util.Observable;

import client.base.Controller;
import client.data.RobPlayerInfo;
import client.models.ClientModel;
import client.models.VertexObject;
import client.models.mapdata.Board;
import client.models.mapdata.Hex;
import client.models.mapdata.Port;
import client.models.mapdata.Road;
import client.state.ActivePlayerState;
import client.state.IStateBase;
import client.state.InactivePlayerState;
import client.state.RoadBuildingState;
import client.state.SetupOneActivePlayerState;
import client.state.SetupTwoActivePlayerState;
import client.state.StateManager;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private IRobView robView;
	private StateManager stateManager;
	private int roadBuildingCount;
	
	public MapController(IMapView view, IRobView robView, StateManager stateManager) {
		
		super(view);
		
		setRobView(robView);
		this.stateManager = stateManager;
		this.stateManager.addObserver(this);
		this.roadBuildingCount = 0;
		
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
		System.out.println("ROAD COUNT: " + String.valueOf(board.getRoads().length));
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
//		System.out.println("Trying to place road: " + stateManager.getState().canPlaceRoadAtLocation(edgeLoc));
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
		this.stateManager.setPlacing(false);
		if (stateManager.getState() instanceof RoadBuildingState) roadBuildingCount += 1;
	}

	public void placeSettlement(VertexLocation vertLoc) {	
		this.stateManager.getState().placeSettlement(vertLoc);
		this.stateManager.setPlacing(false);
	}

	public void placeCity(VertexLocation vertLoc) {	
		this.stateManager.getState().placeCity(vertLoc);
	}

	public void placeRobber(HexLocation hexLoc) {
		RobPlayerInfo[] candidateVictims = this.stateManager.getState().placeRobber(hexLoc);
		//System.out.println("ROBBER LOCATION SELECTED!");
		if (candidateVictims != null) {
			this.getRobView().setPlayers(candidateVictims);
		}
		this.robView.showModal();
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
		stateManager.setCurrentlyRobbing(false);
	}

	@Override
	public void update(Observable o, Object arg) {
		//System.out.println("MAP-CONTROLLER");
		//System.out.println("\tROB-VIEW: " + this.getRobView().isModalShowing());
		//System.out.println("MAP-CONTROLLER ENTRANCE-STATE: \t\t" + stateManager.getState().getClass().getSimpleName());

		if(stateManager.getState() instanceof SetupOneActivePlayerState) {
			if (stateManager.getClientModel().getBoard().numRoadsOwnedByPlayer(stateManager.getFacade().getPlayerIndex()) < 1) {
				System.out.println("PLACE1");
				if (!stateManager.isPlacing()) {
					startMove(PieceType.ROAD, true, true);
					stateManager.setPlacing(true);
				}
			}
			else if (stateManager.getClientModel().getBoard().numSettlementsOwnedByPlayer(stateManager.getFacade().getPlayerIndex()) < 1) {
				System.out.println("PLACE2");
				if (!stateManager.isPlacing()) {
					startMove(PieceType.SETTLEMENT, true, true);
					stateManager.setPlacing(true);
				}
			}
			else {
				if (this.stateManager.clientTurn()) {
					stateManager.getState().endTurn();
					stateManager.setState(new InactivePlayerState(stateManager.getFacade()));
				}
				this.cancelMove();
			}
		}
		else if(stateManager.getState() instanceof SetupTwoActivePlayerState) {
			if (stateManager.getClientModel().getBoard().numRoadsOwnedByPlayer(stateManager.getFacade().getPlayerIndex()) < 2) {
				System.out.println("PLACE3");
				if (!stateManager.isPlacing()) {
					startMove(PieceType.ROAD, true, true);
					stateManager.setPlacing(true);
				}
			}
			else if (stateManager.getClientModel().getBoard().numSettlementsOwnedByPlayer(stateManager.getFacade().getPlayerIndex()) < 2) {
				System.out.println("PLACE4");
				if (!stateManager.isPlacing()) {
					startMove(PieceType.SETTLEMENT, true, true);
					stateManager.setPlacing(true);
				}
			}
			else {
				if (this.stateManager.clientTurn()) {
					stateManager.getState().endTurn();
					stateManager.setState(new InactivePlayerState(stateManager.getFacade()));
				}
				this.cancelMove();
			}
		}
		else if (stateManager.getState() instanceof RoadBuildingState) {
			if (roadBuildingCount < 2) {
				System.out.println("TEST1");
				if (!stateManager.isPlacing()) {
					System.out.println("TEST2");
					startMove(PieceType.ROAD, true, false);
					stateManager.setPlacing(true);
				}
			}
			else {
				roadBuildingCount = 0;
				stateManager.setState(new ActivePlayerState(stateManager.getFacade()));
			}
		}
		
		else if(stateManager.getState() instanceof ActivePlayerState) {
			if (!stateManager.getCurrentlyRobbing() && stateManager.getClientModel().getTurnTracker().getStatus().equals("Robbing")) {
				stateManager.setCurrentlyRobbing(true);
				startMove(PieceType.ROBBER, true, true);
			}
		}
		
		if(!stateManager.getClientModel().newCli()) { //Don't want to do this if the client is new...
			this.initFromModel();
		}
		//System.out.println("MAP-CONTROLLER EXIT-STATE: \t\t\t" + stateManager.getState().getClass().getSimpleName());
	}	
}

