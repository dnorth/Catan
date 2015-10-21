package client.communication;

import java.util.*;

import client.base.*;
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
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		
		//<temp>
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		entries.add(new LogEntry(CatanColor.BLACK, "No Messages"));
		
		
		getView().setEntries(entries);
	
		//</temp>
	}

	@Override
	public void update(Observable o, Object arg) {
		initFromModel();
		
	}
	
}

