
/**
 * @author Daniel Rosendal
 * A simulation event. Holds information about when it occured, its id and where it is located.
 */
public class Event {
	
	private int m_eventId;
	private int m_time;
	private int m_xPosition;
	private int m_yPosition;
	
	public Event(int eventId, int time, int xPosition, int yPosition)
	{
		m_eventId = eventId;
		m_time = time;
		m_xPosition = xPosition;
		m_yPosition = yPosition;
	}
}
