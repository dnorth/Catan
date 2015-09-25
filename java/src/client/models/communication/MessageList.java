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
}