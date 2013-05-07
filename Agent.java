import java.util.Hashtable;
import java.util.List;

/**
 * @author Daniel Rosendal
 * An agent message that traverses the network to spread event information
 * to nodes it visits.
 * 
 */
public class Agent extends MessageBase {
	

	private int m_numberOfJumps;
	
	private Hashtable<Integer, RoutingTableEntry> m_routingTable;
	
	public Agent(int numberOfJumps)
	{
		
		m_numberOfJumps = numberOfJumps;
	}

	public Hashtable<Integer, RoutingTableEntry> get_routingTable() {
		return m_routingTable;
	}

	public void set_routingTable(Hashtable<Integer, RoutingTableEntry> m_routingTable) {
		this.m_routingTable = m_routingTable;
	}

	public int get_numberOfJumps() {
		return m_numberOfJumps;
	}
	
	/**
	 * Subtracts a jump from the life span of the agent.
	 */
	public void subtractJump()
	{
		m_numberOfJumps = m_numberOfJumps--;
	}
}
