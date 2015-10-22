package client.communication;

import java.util.*;

import client.base.*;
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
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		if(stateManager.getClientModel().getLog() != null) {
			List<MessageLine> messages = Arrays.asList(stateManager.getClientModel().getLog().getLines());
			
			for(MessageLine m : messages)
			{
				entries.add(new LogEntry(stateManager.getFacade().getUser().getColor(),m.getMessage()));
			}
		}
		else {
			entries.add(new LogEntry(CatanColor.WHITE, "No Messages"));
		}
		getView().setEntries(entries);
	}

	@Override
	public void update(Observable o, Object arg) {
		initFromModel();
		
	}
	
}

