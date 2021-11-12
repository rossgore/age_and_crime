import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Simulation {

	Landscape land;
	int stopTime;
	int numberOfRows;
	int numberOfCols;
	int numberOfAgents;
	int curTime =0;
	String outputFilename = "";
	StringBuffer modelState;
	
	public Simulation(int pRows, int pCols, int pNumOfAgents, int pStopTime, int pDistChoice)
	{
		stopTime = pStopTime;
		numberOfRows = pRows;
		numberOfCols = pCols;
		numberOfAgents = pNumOfAgents;
		land = new Landscape(numberOfRows, numberOfCols, numberOfAgents, pDistChoice);
		outputFilename = "output.csv";
		modelState = new StringBuffer("Time,CurrentStage,InitialStage,Y,X\n");
	}

	public Simulation()
	{
		stopTime = 100;
		numberOfRows = 100;
		numberOfCols = 100;
		numberOfAgents = 400;
		land = new Landscape(numberOfRows, numberOfCols, numberOfAgents, 0);
		outputFilename = "output.csv";
		modelState = new StringBuffer("Time,CurrentStage,InitialStage,Y,X\n");
	}

	public double getDistance(Position p1, Position p2)
	{
		int x1 = p1.xCord;
		int x2 = p2.xCord;

		int y1 = p1.yCord;
		int y2 = p2.yCord;

		double  xDiff = x1-x2;
		double  xSqr  = Math.pow(xDiff, 2);

		double yDiff = y1-y2;
		double ySqr = Math.pow(yDiff, 2);

		return Math.sqrt(xSqr + ySqr);
	}

	public int updateStage(int row, int col, int numberOfNeighbors)
	{
		double[][] distanceGrid = new double[numberOfRows][numberOfCols];
		for (int i=0; i<distanceGrid.length; i++)
		{
			for (int j=0; j<distanceGrid[0].length; j++)
			{
				distanceGrid[i][j] = getDistance(land.getPosition(row, col), 
						land.getPosition(i, j));
			}
		}

		distanceGrid[row][col] = Double.POSITIVE_INFINITY;

		Position neighbors [] = new Position[numberOfNeighbors];
		int numOfNeighborsAbove = 0;
		int numOfNeighborsZero = 0;
		for (int i=0; i<neighbors.length; i++)
		{
			Position closestNeighbor = getMin(distanceGrid);
			neighbors[i] = land.getPosition(closestNeighbor.xCord, closestNeighbor.yCord);
			if (neighbors[i].getAgent().stage > land.getPosition(row, col).getAgent().stage)
			{
				numOfNeighborsAbove++;
			}
			if (neighbors[i].getAgent().stage==0)
			//if (neighbors[i].getAgent().stage==0 || neighbors[i].getAgent().initialStage==0)
			{
				numOfNeighborsZero++;
			}
			distanceGrid[closestNeighbor.xCord][closestNeighbor.yCord] = Double.POSITIVE_INFINITY;
		}

		// check if we need to move the stage up or down based on majority
		double ratioAbove = (1.0*numOfNeighborsAbove)/(numberOfNeighbors*1.0);
		double ratioZero = (1.0*numOfNeighborsZero)/(numberOfNeighbors*1.0);
		double ratioNonZero = (1.0*numOfNeighborsZero)/(numberOfNeighbors*1.0);
/**
		if (land.getPosition(row, col).getAgent().stage == 0)
		{
			if (ratioNonZero == 1.0)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		else
			**/
		if (ratioAbove == 1.0)
		{
			return ( land.getPosition(row, col).getAgent().stage+1);
		}
		else if (ratioZero == 1.0)
		{
			return (0);
		}
		else
		{
			return ( land.getPosition(row, col).getAgent().stage);
		}

	}

	public Position getMin(double [][] grid)
	{
		double minValue = Double.POSITIVE_INFINITY;
		Position minPosition = land.getPosition(0,0);
		for (int i=0; i<grid.length; i++)
		{
			for (int j=0; j<grid[0].length;j++)
			{
				if (grid[i][j]<minValue && land.getPosition(i, j).getAgent() != null)
				{
					minPosition = land.getPosition(i, j);
					minValue = grid[i][j];
				}
			}
		}
		return minPosition;
	}

	public int getStageAvg(int row, int col)
	{
		double sumOfStages = land.grid[row][col].getAgent().stage;
		double numOfSamples = 1;

		int north = col -1;
		int south = col + 1;
		int west = row -1;
		int east = row + 1;

		if (north >= 0 && north < numberOfCols)
		{
			if (land.grid[row][north].getAgent()!=null)
			{
				sumOfStages += land.grid[row][north].getAgent().stage;
				numOfSamples++;
			}		
		}

		if (south >= 0 && south < numberOfCols)
		{
			if (land.grid[row][south].getAgent()!=null)
			{
				sumOfStages += land.grid[row][south].getAgent().stage;
				numOfSamples++;
			}		
		}

		if (east >= 0 && east < numberOfRows)
		{
			if (land.grid[east][col].getAgent()!=null)
			{
				sumOfStages += land.grid[east][col].getAgent().stage;
				numOfSamples++;
			}		
		}

		if (west >= 0 && west < numberOfRows)
		{
			if (land.grid[west][col].getAgent()!=null)
			{
				sumOfStages += land.grid[west][col].getAgent().stage;
				numOfSamples++;
			}		
		}

		return ((int) (sumOfStages/numOfSamples));
	}

	public void next()
	{
		for(int i=0; i<land.grid.length; i++)
		{
			for (int j=0; j<land.grid[0].length; j++)
			{
				if (land.grid[i][j].getAgent() != null)
				{
					modelState.append(curTime+",Stage_"+land.grid[i][j].getAgent().stage+","+
							          "Stage_"+land.grid[i][j].getAgent().initialStage+","+i+","+j+"\n");
					land.grid[i][j].getAgent().stage = updateStage(i,j, 4);
				}
			}
		}

		for(int i=0; i<land.grid.length; i++)
		{
			for (int j=0; j<land.grid[0].length; j++)
			{
				if (land.grid[i][j].getAgent() != null)
				{
					Position newPos = land.getEmptyRandom(land.grid[i][j].getAgent().stage);
					Agent agentToMove = land.grid[i][j].getAgent();
					land.grid[i][j].setAgent(null);
					land.grid[newPos.getX()][newPos.getY()].setAgent(agentToMove);
				}
			}
		}
		System.out.println(curTime);
		curTime++;
	}

	public static void main(String [] args)
	{
		Simulation sim = new Simulation();
		for (int i=0; i<sim.stopTime; i++)
		{
			sim.next();
		}
		try {
			PrintWriter out = new PrintWriter(sim.outputFilename);
			out.println(sim.modelState);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
