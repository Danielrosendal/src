import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Daniel Rosendal
 * Represents a sensor node in the network. It has logic that handles 
 * message routing as well as a routing table and the nodes routing table and 
 * position. 
 *
 */
public class SensorNode {

	private int m_xCoordinate;
	private int m_yCoordinate;
	private int m_Id;

	private List<Integer> m_neighbours;
	private boolean m_isAvailable;
	private Queue<MessageBase> m_messageQueue;
	private Hashtable<Integer, RoutingTableEntry> m_routingTable;
	private Environment m_environment;

	public SensorNode(Environment environment) {
		m_environment = environment;
		m_routingTable = new Hashtable<Integer, RoutingTableEntry>();
		m_messageQueue = new ConcurrentLinkedQueue<MessageBase>();
	}

	public int get_Id() {
		return m_Id;
	}

	public void set_Id(int m_Id) {
		this.m_Id = m_Id;
	}
	
	public int get_xCoordinate() {
		return m_xCoordinate;
	}
	
	public int get_yCoordinate() {
		return m_yCoordinate;
	}
	
	/**
	 * Represents that the node has detected an event. 
	 * @param eventId - The id of the event.
	 */
	public void eventDetected(int eventId) {
		int currentTime = 0;
		m_routingTable.put(eventId, new RoutingTableEntry(get_Id(), 0, currentTime,
				m_xCoordinate, m_yCoordinate));
		generateAgent();
	}

	/**
	 * Generates an agent on x% of the times it is called.
	 * x is fetched from the sensor environment 
	 */
	private void generateAgent() {
		if (Math.random() < m_environment.getAgentGenerationProbability()) {
			Agent agent = new Agent(m_environment.get_agentLifespan());
			m_messageQueue.add(agent);
		}
	}

	public boolean getIsAvailable() {
		return m_isAvailable;
	}

	public void performTimeStep() {
		if (!m_messageQueue.isEmpty()) {
			if(forwardMessage(m_messageQueue.peek()))
			{
				m_messageQueue.remove();
			}
		}
	}

	private boolean forwardMessage(MessageBase message) {
		if (message == null) {
			throw new IllegalArgumentException(
					"Message and Node can't be null.");
		}
		m_isAvailable = false;
		if (m_neighbours == null) {
			fetchNeighbors();
		}
		
		int neighborId = -1;

		if (message instanceof QueryMessage) {
			QueryMessage qMessage = (QueryMessage) message;
			int eventId = qMessage.getEventId();
			RoutingTableEntry routingEntry = m_routingTable.get(eventId);
			if (routingEntry != null) {
				neighborId = m_neighbours.get(routingEntry.getForwardPathId());
			} else {
				neighborId = getRandomNeighborId();
			}
		} else if (message instanceof Agent) {
			Agent aMessage = (Agent) message;
			if(aMessage.get_numberOfJumps() > 0)
			{
				List<Integer> visitedNodes = aMessage.get_visitedNodes();
				neighborId = getRandomNeighborId(visitedNodes);
			}
		} else if (message instanceof ReturnMessage) {
			ReturnMessage rMessage = (ReturnMessage) message;
			rMessage.get_visitedNodes();
		}

		// -1 Equals no neighbors
		if (neighborId != -1) {
			return m_environment.sendMessage(neighborId, message);
		}
		return false;
	}
	
	public void RecieveMessage(MessageBase message) {
		m_isAvailable = false;
		if (message instanceof Agent) {
			Agent agent = (Agent) message;
			syncRoutingTable(agent.get_routingTable());
			if(agent.get_numberOfJumps() == 0)
			{
				return;
			}
		}
		m_messageQueue.add(message);
	}

	private int getRandomNeighborId() {
		return getRandomNeighborId(null);
	}

	private int getRandomNeighborId(List<Integer> exclusionList) {
		if (m_neighbours == null) {
			return -1;
		}
		List<Integer> neighborSelectionList;

		List<Integer> filteredList = m_neighbours;
		filteredList.removeAll(exclusionList);
		// Is there any neighbors that are not part of the exclusionlist? If
		// there isn't
		// send it to a random neighbor ignoring the list.
		neighborSelectionList = filteredList.size() > 0 ? filteredList
				: m_neighbours;
		int neighborCount = neighborSelectionList.size();

		// No neighbors to send the message to.
		if (neighborCount == 0) {
			return -1;
		}
		// Find a random neighbor in the list
		int neighborNumber = (int) (Math.random() * ((neighborCount) + 1));
		return m_neighbours.get(neighborNumber);
	}
	
	private void syncRoutingTable(Hashtable<Integer, RoutingTableEntry> mergeTable)
	{
		Enumeration<Integer> routingKeys = m_routingTable.keys();
		boolean isDone = false;
		while(routingKeys.hasMoreElements())
		{
			int key = routingKeys.nextElement();
			RoutingTableEntry entry = m_routingTable.get(key);
			RoutingTableEntry mergeEntry = mergeTable.containsKey(key) ? 
					mergeTable.get(key) : null;
			if(mergeEntry != null)
			{
				//TODO Gör klart!
				mergeEntry.getStepsToGoal();
			}
			
		}
	}

	private void printQuestionResult() {

	}

	private void fetchNeighbors() {
		m_neighbours = m_environment.getNeighborIds(this);
	}
}
