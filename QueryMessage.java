/**
 * @author Daniel Rosendal
 * A message that requests information about an event. Holds information 
 * about the id of the event it is querying about.
 *
 */
public class QueryMessage extends MessageBase {

	private int eventId;

	public QueryMessage()
	{
		
	}
	
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
}
