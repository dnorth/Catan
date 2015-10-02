package client.models.communication;
import client.models.communication.MessageLine;

/**
 * Contains list of MessageLines
 *
 */

public class MessageList
{
	private MessageLine[] lines;

	public MessageLine[] getLines() {
		return lines;
	}

	public void setLines(MessageLine[] lines) {
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