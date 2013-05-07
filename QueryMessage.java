/**
 * @author Daniel Rosendal
 * A message that requests information about an event. Holds information 
 * about the id of the event it is querying about.
 *
 */
public class QueryMessage extends MessageBase {

	private int m_eventId;

	public QueryMessage(int eventId)
	{
		m_eventId = eventId; 
	}
	
	public int getEventId() {
		return m_eventId;
	}

	public void setEventId(int eventId) {
		this.m_eventId = eventId;
	}
}
