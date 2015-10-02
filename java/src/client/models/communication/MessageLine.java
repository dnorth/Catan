package client.models.communication;

/**
 * Message Line contains message and source
 *
 */

public class MessageLine
{
	String message;
	String source;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}