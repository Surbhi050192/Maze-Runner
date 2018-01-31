
import java.awt.Color;

//Name: Surbhi Goel
//USC loginid: surbhigo@usc.edu
//CS 455 PA3
//Fall 2016

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.JComponent;

/**
MazeComponent class

A component that displays the maze and path through it if one has been found.
 */
public class MazeComponent extends JComponent
{
	private Maze maze;

	private static final int START_X = 10; // where to start drawing maze in frame
	private static final int START_Y = 10;
	private static final int BOX_WIDTH = 20;  // width and height of one maze unit
	private static final int BOX_HEIGHT = 20;
	private static final int INSET = 2;  
	// how much smaller on each side to make entry/exit inner box
	private Rectangle mazeWallBox;
	private Rectangle mazeFreeBox;
	private int rows;
	private int cols;
	private MazeCoord loc;
	private MazeCoord entryLoc;
	private MazeCoord exitLoc;
	private Maze myMaze;
	private Line2D.Double from;


	/**
   Constructs the component.
   @param maze   the maze to display
	 */
	public MazeComponent(Maze maze) 
	{  

		myMaze = maze;
		rows = maze.numRows();
		cols = maze.numCols();
		entryLoc = maze.getEntryLoc();
		exitLoc = maze.getExitLoc();


	}

	/**
 
  Draws the current state of maze including the path through it if one has
  been found.
  @param g the graphics context
	 */
	public void paintComponent(Graphics g)
	{	
		Graphics2D g2 = (Graphics2D) g;

		
		//Draws the maze by checking whether there is wall or not.
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				loc = new MazeCoord(i,j);
				
				if(myMaze.hasWallAt(loc))    
				{
					mazeWallBox = new Rectangle(START_X+BOX_WIDTH*j,START_Y+BOX_HEIGHT*i, BOX_WIDTH, BOX_HEIGHT);
					g2.setColor(Color.BLACK);
					g2.draw(mazeWallBox);
					g2.fill(mazeWallBox);

				}
				else
				{
					mazeFreeBox = new Rectangle(START_X + BOX_WIDTH*j,START_Y+BOX_HEIGHT*i, BOX_WIDTH, BOX_HEIGHT);
					g2.setColor(Color.WHITE);
					g2.draw(mazeFreeBox);
					g2.fill(mazeFreeBox);		

				}				
			}//end of for j
		}//end of for i

		//Draws the rectangle for entry position
		Rectangle entry = new Rectangle(INSET + START_X + BOX_WIDTH * entryLoc.getCol(),INSET + START_Y+BOX_HEIGHT*entryLoc.getRow(), BOX_WIDTH-2*INSET, BOX_HEIGHT-2*INSET);
		g2.setColor(Color.YELLOW);
		g2.draw(entry);
		g2.fill(entry);

		//Draws the rectangle for exit position
		Rectangle exit = new Rectangle(INSET + START_X + BOX_WIDTH * exitLoc.getCol(),INSET + START_Y+BOX_HEIGHT*exitLoc.getRow(), BOX_WIDTH-2*INSET, BOX_HEIGHT-2*INSET);
		g2.setColor(Color.GREEN);
		g2.draw(exit);
		g2.fill(exit);

		//Calling the getPath method in order to get the path from entry to exit location
		LinkedList<MazeCoord> myPath = myMaze.getPath();
		ListIterator<MazeCoord> iter = myPath.listIterator();
		if(myPath.size()>0)
		{
			MazeCoord from = iter.next();
			while(iter.hasNext())
			{
				MazeCoord to = iter.next();
				g2.setColor(Color.BLUE);
				g2.drawLine(START_X + from.getCol() * BOX_WIDTH  + BOX_WIDTH/2, START_Y + from.getRow() * BOX_HEIGHT  + BOX_HEIGHT/2, START_X + to.getCol() * BOX_WIDTH  + BOX_WIDTH/2,START_Y + to.getRow() * BOX_HEIGHT  + BOX_HEIGHT/2);
				from = to;

			}
		}

		//Draws the maze boundary
		Rectangle mazeBox = new Rectangle(START_X, START_Y, BOX_WIDTH * cols, BOX_HEIGHT * rows);
		g2.setColor(Color.BLACK);
		g2.draw(mazeBox);

	}//end of paint Component

}


