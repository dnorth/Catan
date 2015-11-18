package client.models.communication;
import java.util.ArrayList;
import java.util.List;

import client.models.communication.MessageLine;

/**
 * Contains list of MessageLines
 *
 */

public class MessageList
{

	private List<MessageLine> lines;

	public MessageList() {
		lines = new ArrayList<MessageLine>();
	}
	
	public List<MessageLine> getLines() {
		return lines;
	}

	public void setLines(List<MessageLine> lines) {
		this.lines = lines;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(MessageLine line : lines) {
			sb.append(line.toString() + "\n");
		}
		
		return sb.toString();
	}
}