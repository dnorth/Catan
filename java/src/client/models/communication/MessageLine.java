package client.models.communication;

/**
 * Message Line contains message and source
 *
 */

public class MessageLine
{
	String message;
	String source;
	
	
	public MessageLine(String message, String source) {
		super();
		this.message = message;
		this.source = source;
	}
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
	
	
	@Override
	public String toString() {
		return "MessageLine [message=" + message + ", source=" + source + "]";
	}
	
	
	
}