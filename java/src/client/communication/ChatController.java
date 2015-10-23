package client.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import shared.definitions.CatanColor;
import client.base.*;
import client.models.communication.MessageLine;
import client.state.StateManager;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

	private StateManager stateManager;
	public ChatController(IChatView view, StateManager sm) {
		super(view);
		stateManager=sm;
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		stateManager.getFacade().sendMessage(message);
	}
	
	private void initFromModel() {	
		List<MessageLine> messages = Arrays.asList(stateManager.getClientModel().getChat().getLines());
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		for(MessageLine m : messages)
		{
			entries.add(new LogEntry(stateManager.getFacade().getLocalPlayer().getColor(),m.getMessage()));
		}
		
		if(entries.isEmpty())
		{entries.add(new LogEntry(CatanColor.BLACK, "No Messages"));}
		
		getView().setEntries(entries);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		initFromModel();
	}

}

