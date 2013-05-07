import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Rosendal
 * The node networks environment. Holds information about the
 * simulation parameters and handles forwarding node transmissions.
 */
public class Environment {

	private double m_agentGenerationProbability;
	private int m_totalTimeSteps;
	private double m_eventOccuranceProbability;
	private List<SensorNode> m_sensorNodes;
	private int m_sensorRange;
	private int m_agentLifespan;

	/**
	 * Environment Constructor. Takes a number of simulation parameters as input.
	 * @param agentGenerationProbability - Takes a number between 0 and 1 which represents
	 *  The chance to generate and agent message.
	 * @param totalTimeSteps - A positive number that dictates the total timesteps of the simulation.
	 * @param eventOccuranceProbability - Takes a number between 0 and 1 which represents
	 *  The probability that an event is generated in a node in a time step.
	 */
	public Environment(double agentGenerationProbability, int totalTimeSteps,
			double eventOccuranceProbability) {
		if(agentGenerationProbability < 0 || agentGenerationProbability > 1)
		{
			throw new IllegalArgumentException("agentGenerationProbability must be a value between 0 and 1.");
		}
		if(eventOccuranceProbability < 0 || eventOccuranceProbability > 1)
		{
			throw new IllegalArgumentException("eventOccuranceProbability must be a value between 0 and 1.");
		}
		if(totalTimeSteps <= 0)
		{
			throw new IllegalArgumentException("totalTimeSteps needs to be a possitive value.");
		}
		m_agentGenerationProbability = agentGenerationProbability;
		m_totalTimeSteps = totalTimeSteps;
		m_eventOccuranceProbability = eventOccuranceProbability;
		m_sensorNodes = new ArrayList<SensorNode>();
		m_sensorRange = 15;
	}

	public List<SensorNode> get_sensorNodes() {
		return m_sensorNodes;
	}

	public int get_agentLifespan() {
		return m_agentLifespan;
	}

	public void set_agentLifespan(int m_agentLifespan) {
		this.m_agentLifespan = m_agentLifespan;
	}

	public void set_sensorNodes(List<SensorNode> m_sensorNodes) {
		this.m_sensorNodes = m_sensorNodes;
	}

	public List<Integer> getNeighborIds(SensorNode currentNode) {
		if (m_sensorNodes == null) {
			return null;
		}
		int nodexCoordinate = currentNode.get_xCoordinate();
		int nodeyCoordinate = currentNode.get_yCoordinate();

		List<Integer> neighborIdList = new ArrayList<>();

		for (SensorNode node : m_sensorNodes) {
			int xCoordinateDifference = Math.abs(node.get_xCoordinate()
					- nodexCoordinate);
			int yCoordinateDifference = Math.abs(node.get_yCoordinate()
					- nodeyCoordinate);
			if (xCoordinateDifference <= m_sensorRange
					&& yCoordinateDifference <= m_sensorRange) {
				neighborIdList.add(node.get_Id());
			}
		}
		return neighborIdList;
	}

	public double getAgentGenerationProbability() {
		return m_agentGenerationProbability;
	}

	public void performTimeStep() {
		for (SensorNode node : m_sensorNodes) {
			node.performTimeStep();
		}
	}

	public boolean sendMessage(int nodeId, MessageBase message) {
		// Check that the neighbor is within hearing range of the node.
		// Forward the message.
		for (SensorNode node : m_sensorNodes) {
			if (node.get_Id() == nodeId) {
				if (node.getIsAvailable()) {
					node.RecieveMessage(message);
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

}
