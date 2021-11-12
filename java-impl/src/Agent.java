
public class Agent {
	
	int stage;
	int initialStage;
	
	public Agent()
	{
		stage = (int) (Math.random()*7);
	}
	
	public Agent (int pStage)
	{
		stage = pStage;
	}
	
	public Agent (int pStage, int pInitialStage)
	{
		stage = pStage;
		initialStage = pInitialStage;
	}

}
