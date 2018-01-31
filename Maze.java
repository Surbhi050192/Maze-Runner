
import java.util.Arrays;

//Name: Surbhi Goel
//USC loginid: surbhigo@usc.edu
//CS 455 PA3
//Fall 2016

import java.util.LinkedList;
import java.util.ListIterator;


/**
Maze class

Stores information about a maze and can find a path through the maze
(if there is one).

Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
(parameters to constructor), and the path:
  -- no outer walls given in mazeData -- search assumes there is a virtual 
     border around the maze (i.e., the maze path can't go outside of the maze
     boundaries)
  -- start location for a path is maze coordinate startLoc
  -- exit location is maze coordinate exitLoc
  -- mazeData input is a 2D array of booleans, where true means there is a wall
     at that location, and false means there isn't (see public FREE / WALL 
     constants below) 
  -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
  -- only travel in 4 compass directions (no diagonal paths)
  -- can't travel through walls
 */

public class Maze {

	public static final boolean FREE = false;
	public static final boolean WALL = true;
	private static int rows;
	private static int cols;
	private static boolean[][] mazeRun = null;
	private static MazeCoord startPos;
	private static MazeCoord endPos;
	private static LinkedList<MazeCoord> Path;
	private static boolean Visited[][];

	/**
   Constructs a maze.
   @param mazeData the maze to search.  See general Maze comments for what
   goes in this array.
   @param startLoc the location in maze to start the search (not necessarily on an edge)
   @param endLoc the "exit" location of the maze (not necessarily on an edge)
   PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
      and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

	 */
	public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc)
	{	

		rows = mazeData.length;
		cols = mazeData[0].length;
		mazeRun = new boolean[rows][cols];
		Visited=new boolean[rows][cols];
		startPos = startLoc;
		endPos = endLoc;
		Path = new LinkedList<MazeCoord>(); 
		for(int i =0;i<rows;i++)
		{
			for(int j = 0;j<cols;j++)
			{
				mazeRun[i][j] = mazeData[i][j];
			}
		}
	}


	/**
Returns the number of rows in the maze
@return number of rows
	 */
	public int numRows()
	{

		return rows;   
	}


	/**
Returns the number of columns in the maze
@return number of columns
	 */   
	public int numCols()
	{
		return cols;  
	} 


	/**
   Returns true iff there is a wall at this location
   @param loc the location in maze coordinates
   @return whether there is a wall here
   PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
	 */
	public boolean hasWallAt(MazeCoord loc) 
	{

		if(mazeRun[loc.getRow()][loc.getCol()] == false)

			return false;
		else
			return true;  
	}


	/**
   Returns the entry location of this maze.
	 */
	public MazeCoord getEntryLoc()
	{
		return new MazeCoord(startPos.getRow(),startPos.getCol());
	}


	/**
Returns the exit location of this maze.
	 */
	public MazeCoord getExitLoc()
	{
		return new MazeCoord(endPos.getRow(),endPos.getCol()); 
	}


	/**
   Returns the path through the maze. First element is starting location, and
   last element is exit location.  If there was not path, or if this is called
   before search, returns empty list.

   @return the maze path
	 */
	public LinkedList<MazeCoord> getPath() 
	{
		return Path; 
	}


	/**
   Find a path through the maze if there is one.  Client can access the
   path found via getPath method.
   @return whether path was found.
	 */
	public boolean search()  
	{ 
		Path = new LinkedList<MazeCoord>();
		Visited = new boolean[rows][cols]; 

		if(findpath(mazeRun, startPos.getRow(),startPos.getCol()))
		{
			return true;
		}
		else
			return false; 

	}
	/**
	   Helper method used to find a path through the maze if there is one.
	   @return whether path was found or not to Search method.
		 */
	public boolean findpath(boolean mazeRun[][], int startX, int startY)
	{
		int moveX = startX;
		int moveY = startY;

		Visited[moveX][moveY] = true;
		MazeCoord loc = new MazeCoord(moveX, moveY);


		if(moveX>-1 && moveX<rows && moveY>-1 && moveY<cols)
		{
			//If location has wall then will return no path
			if(hasWallAt(loc))
			{
				return false;
			}

			//If location is equal to end position then will return path
			if(loc.equals(endPos))
			{
				Path.add(loc);
				return true;
			}		
			
			boolean down=true;

			/* The condition is considered so that it does not follow ArrayBoundException 
			for all if cases and also computing the path for coordinated if it is not visited 
			previously */
			
			if(moveX<rows-1)
			{
				down= mazeRun[moveX+1][moveY];
			}

			boolean up=true;

			if(moveX>0)
			{ 
				up = mazeRun[moveX-1][moveY];
			}

			boolean right=true;

			if(moveY<cols-1)
			{
				right = mazeRun[moveX][moveY+1];

			}
			boolean left=true;

			if(moveY > 0)
			{
				left = mazeRun[moveX][moveY-1];
			}

			if(down == false && Visited[moveX+1][moveY]!=true)
			{	
				if(findpath(mazeRun,moveX+1,moveY))
				{
					Path.add(loc);
					return true;
				}				
			}

			if(up == false  && Visited[moveX-1][moveY]!=true)
			{	
				if(findpath(mazeRun,moveX-1,moveY))
				{
					Path.add(loc);
					return true;
				}
			}

			if(right == false && Visited[moveX][moveY+1]!=true)
			{
				if(findpath(mazeRun,moveX,moveY+1))
				{
					Path.add(loc);
					return true;
				}
			}

			if(left == false && Visited[moveX][moveY-1]!=true)
			{
				if(findpath(mazeRun,moveX,moveY-1))
				{
					Path.add(loc);
					return true;
				}
			}

		}//end of if 

		return false;
	}
}


