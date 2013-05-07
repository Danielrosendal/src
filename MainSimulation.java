
public class MainSimulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Environment environment = new Environment(0.5, 100, 0.5);
		SensorNode s1 = new SensorNode(environment);
		s1.RecieveMessage(new Agent(2));
		s1.performTimeStep();
		
		for(int y = 0; y < 10; y++)
		{
			for(int x = 0; x < 10; x++)
			{
				SensorNode node = new SensorNode(environment);
			}
		}
	}
}
