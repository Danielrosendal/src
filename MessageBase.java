import java.util.List;

/**
 * @author Daniel Rosendal
 * Base class for messages. Holds information about the nodes it has visited.
 *
 */
public abstract class MessageBase {
	private List<Integer> m_visitedNodes;

	public List<Integer> get_visitedNodes() {
		return m_visitedNodes;
	}

	public void set_visitedNodes(List<Integer> m_visitedNodes) {
		this.m_visitedNodes = m_visitedNodes;
	}
}
