/**
 * 
 * @author Daniel Rosendal
 * An entry in a routing table. 
 */
public class RoutingTableEntry {
	
	private int m_forwardPathId;
	private int m_stepsToGoal;
	private int m_timeStamp;
	private int m_xPosition;
	private int m_yPosition;
	
	public RoutingTableEntry(int neighborId, int stepsToGoal, int timeStamp, 
			int  xPosition, int yPosition)
	{
		m_forwardPathId = neighborId;
		m_stepsToGoal = stepsToGoal;
		m_timeStamp = timeStamp;
		m_xPosition = xPosition;
		m_yPosition = yPosition;
	}
	
	public RoutingTableEntry(int neighborId, int stepsToGoal)
	{
		m_forwardPathId = neighborId;
		m_stepsToGoal = stepsToGoal;
	}
	
	public int getForwardPathId()
	{
		return m_forwardPathId;
	}
	
	public int getStepsToGoal()
	{
		return m_stepsToGoal;
	}
	
	public int getTimeStamp()
	{
		return m_timeStamp;
	}
	
	public int getXPosition()
	{
		return m_xPosition;
	}
	
	public int getYPosition()
	{
		return m_yPosition;
	}
}
