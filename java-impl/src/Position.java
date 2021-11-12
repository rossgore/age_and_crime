
public class Position {
	
	int xCord;
	int yCord;
	Agent agent;
	
	public Position()
	{
		xCord = 0;
		yCord = 0;
		agent=null;
	}
	
	public Position(int x, int y)
	{
		xCord = x;
		yCord = y;
		agent=null;
	}
	
	public int getX()
	{
		return xCord;
	}
	
	public int getY()
	{
		return yCord;
	}
	
	public void setAgent(Agent pAgent)
	{
		agent = pAgent;
	}
	
	public Agent getAgent()
	{
		return agent;
	}

}
