package client.communication;

import java.util.*;

import client.base.*;
import client.data.PlayerInfo;
import client.models.communication.MessageLine;
import client.state.StateManager;
import shared.definitions.*;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

	private StateManager stateManager;
	public GameHistoryController(IGameHistoryView view, StateManager sm) {
		
		super(view);
		stateManager=sm;
		this.stateManager.addObserver(this);
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		getView().setEntries(new ArrayList<LogEntry>());
		List<LogEntry> entries = new ArrayList<LogEntry>();
		if(stateManager.getClientModel().getLog() != null) {
			List<MessageLine> messages = stateManager.getClientModel().getLog().getLines();
			
			for(MessageLine m : messages)
			{
				entries.add(new LogEntry(getColorFromSource(m.getSource()),m.getMessage()));
			}
		}
		else {
			entries.add(new LogEntry(CatanColor.WHITE, "No Messages"));
		}
		getView().setEntries(entries);
	}
	
	private CatanColor getColorFromSource(String messageSource) {
		List<PlayerInfo> players = stateManager.getFacade().getGame().getPlayers();
		for(PlayerInfo player : players) {
			if(messageSource.equals(player.getName())) {
				return player.getColor();
			}
		}
		//Could not find that player. This should never happen....
		return null;
	}

	@Override
	public void update(Observable o, Object arg) {
		initFromModel();
	}
	
}

