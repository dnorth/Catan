package client.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import shared.definitions.CatanColor;
import client.base.*;
import client.data.PlayerInfo;
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
		this.stateManager.addObserver(this);
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
		List<MessageLine> messages = stateManager.getClientModel().getChat().getLines();
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		for(MessageLine m : messages)
		{
			entries.add(new LogEntry(getColorFromSource(m.getSource()),m.getMessage()));
		}
		
		if(entries.isEmpty())
		{entries.add(new LogEntry(CatanColor.WHITE, "No Messages"));}
		
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

