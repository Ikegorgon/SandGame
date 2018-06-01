package sandgame.controller;
import java.awt.*;
import java.util.*;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  private int temp;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[4];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    
    //1. Add code to initialize the data member grid with same dimensions
    
    grid = new int[numRows][numCols];
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    temp = 1;
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
	grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
   //Hint - use a nested for loop
	  ArrayList<Color> colors = new ArrayList<Color>();
	  colors.add(Color.BLACK);
	  colors.add(Color.GRAY);
	  colors.add(Color.YELLOW);
	  colors.add(Color.BLUE);
	  Color color = colors.get(display.getTool());
	  
    for (int row = 0; row < grid.length; row++) {
    		for (int col = 0; col < grid[0].length; col++) {
    			if (grid[row][col] == EMPTY) {
    				display.setColor(row, col, colors.get(0));
    			} else if (grid[row][col] == METAL) {
    				display.setColor(row, col, colors.get(1));
    			} else if (grid[row][col] == SAND) {
    				display.setColor(row, col, colors.get(2));
    			} else if (grid[row][col] == WATER) {
    				display.setColor(row, col, colors.get(3));
    			}
    		}
    }
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
    int randRow = (int) (Math.random() * (grid.length));
    int randCol = (int) (Math.random() * (grid[0].length));
    int randMove = (int) (Math.random() * 4);
    if ((randRow + 1 < grid.length) && grid[randRow][randCol] == SAND
    		&& grid[randRow + 1][randCol] != METAL && grid[randRow + 1][randCol] != SAND) {
    		if ((randRow + 1 < grid.length) && grid[randRow + 1][randCol] == WATER) {
    			grid[randRow - 1][randCol] = WATER;
    			grid[randRow][randCol] = EMPTY;
    			grid[randRow + 1][randCol] = SAND;
    		} else {
    			grid[randRow][randCol] = EMPTY;
    			grid[randRow + 1][randCol] = SAND;
    		}
    		
    }
    if (grid[randRow][randCol] == WATER) {
    	if (temp % 2 == 0) {
    		if (randMove == 1) {
    			if (randRow + 1 < grid.length && randCol + 1 < grid[0].length) {
	    			if ((randRow + 1 < grid.length) && grid[randRow + 1][randCol] != METAL
	    					&& grid[randRow + 1][randCol] != SAND && grid[randRow + 1][randCol] != WATER) {
	    				grid[randRow][randCol] = EMPTY;
	    				grid[randRow + 1][randCol] = WATER;
	    			}
    			}
    		}
    		if (randMove == 2) {
    			if (randRow + 1 < grid.length && randCol + 1 < grid[0].length) {
	    			if ((randCol + 1 < grid[0].length) && grid[randRow][randCol + 1] != METAL
	    					&& grid[randRow + 1][randCol] != SAND && grid[randRow + 1][randCol] != WATER) {
	    				grid[randRow][randCol] = EMPTY;
	    				grid[randRow][randCol + 1] = WATER;
	    			}
    			}
    		}
			if (randMove == 3) {
				if (randRow - 1 > grid.length && randCol + 1 < grid[0].length) {
					if ((randCol - 1 > grid[0].length) && grid[randRow][randCol - 1] != METAL
						&& grid[randRow + 1][randCol] != SAND && grid[randRow + 1][randCol] != WATER) {
					grid[randRow][randCol] = EMPTY;
					grid[randRow][randCol - 1] = WATER;
					}
				}
			}
    	} else {
    		if (randRow + 1 < grid.length && randCol + 1 < grid[0].length) {
			if ((randRow + 1 < grid.length) && grid[randRow + 1][randCol] != METAL
					&& grid[randRow + 1][randCol] != SAND && grid[randRow + 1][randCol] != WATER) {
				grid[randRow][randCol] = EMPTY;
				grid[randRow + 1][randCol] = WATER;
			}
    		}
    	}
    }
  }
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
        temp = temp + 1;
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
