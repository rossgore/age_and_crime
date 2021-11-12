
public class Landscape {
	
	Position[][] grid;
	
	public Landscape(){
		int col=100;
		int row=100;
		grid = new Position[row][col];
		for(int i =0; i<row; i++)
		{
			for(int j=0; j<col; j++)
			{
				grid[i][j] = new Position(i, j);
			}
		}
	}
	
	public Landscape(int row, int col)
	{
		grid = new Position[row][col];
		for(int i =0; i<row; i++)
		{
			for(int j=0; j<col; j++)
			{
				grid[i][j] = new Position(i, j);
			}
		}
	}
	
	public Landscape(int row, int col, int numberOfAgents, int distributionChoice)
	{
		grid = new Position[row][col];
		double density = (1.0*numberOfAgents)/(row*col);
		for(int i =0; i<row; i++)
		{
			for(int j=0; j<col; j++)
			{
				grid[i][j] = new Position(i, j);
				if (Math.random() <= density)
				{
					if (distributionChoice == 0)
					{
						if (i< row*.15)
						{
							grid[i][j].setAgent(new Agent(0, 0));
						}
						else if (i < row*.30)
						{
							grid[i][j].setAgent(new Agent(1, 1));
						}
						else if (i < row*.45)
						{
							grid[i][j].setAgent(new Agent(2, 2));
						}
						else if (i < row*.60)
						{
							grid[i][j].setAgent(new Agent(3, 3));
						}
						else if (i < row*.75)
						{
							grid[i][j].setAgent(new Agent(4, 4));
						}
						else if (i <row*.90)
						{
							grid[i][j].setAgent(new Agent(5, 5));
						}
						else if (i <row*.100)
						{
							grid[i][j].setAgent(new Agent(6, 6));
						}
					}
					else
					{
						if (i<5)
						{
							grid[i][j].setAgent(new Agent(0, 0));
						}
						else if (i <row*.15)
						{
							grid[i][j].setAgent(new Agent(1, 1));
						}
						else if (i <row*.40)
						{
							grid[i][j].setAgent(new Agent(2, 2));
						}
						else if (i <row*.60)
						{
							grid[i][j].setAgent(new Agent(3, 3));
						}
						else if (i <row*.80)
						{
							grid[i][j].setAgent(new Agent(4, 4));
						}
						else if (i <row*.95)
						{
							grid[i][j].setAgent(new Agent(5, 5));
						}
						else if (i <row*.100)
						{
							grid[i][j].setAgent(new Agent(6, 6));
						}
					}
					
				}
			}
		}
	}
	
	public Position getPosition(int row, int col)
	{
		return grid[row][col];
	}
	
	public Position getEmptyRandom()
	{
		Position toReturn = null;
		int rows = grid.length;
		int cols = grid[0].length;
		while (toReturn == null)
		{
			int x = ((int) (rows*Math.random()));
			int y = ((int) (cols*Math.random()));
			if (grid[x][y].getAgent() == null)
			{
				toReturn = grid[x][y];
			}
		}
		return toReturn;
	}
	
	public Position getEmptyRandom(int stage)
	{
		int[] minXForStage = new int[] {0, 15, 30, 45, 60, 75, 90, 100};
		Position toReturn = null;
		int rows = grid.length;
		int cols = grid[0].length;
		while (toReturn == null)
		{
			int x = ((int) (rows*Math.random()));
			int y = ((int) (cols*Math.random()));
			if (grid[x][y].getAgent() == null && x > minXForStage[stage] && x < minXForStage[stage+1])
			{
				toReturn = grid[x][y];
			}
		}
		return toReturn;
	}

}
