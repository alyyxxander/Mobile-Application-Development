//FILE CREATED ON: 8/24/22
package homework.hw1;

import java.util.Arrays;

public class FrogsAndToads {
	
	/* Advice: The game can be solved on a 3 x 3 grid in 12 moves. Work out a
   solution by hand, then write a program that creates an instance of the game
   and makes those 12 moves. If the grid is in the winning configuration at that
   point, you can be fairly certain that there are no hidden bugs in your code. */
	
	//====================================== VARIABLES
	/* Include a class constant initialized to 5 to  use for the default size of the game. */
	private final int DEFAULT_ROWS = 5;
	private final int DEFAULT_COLUMNS = 5;
	private int rows, columns;
	private String[][] gameGrid;
	
	/* You can write colored text to an output terminal using ANSI
	   (American National Standards Institute) escape sequences. */
	String ansiBoldRed = "\u001B[1;31m";
	String ansiBoldYellow = "\u001B[1;33m";
	String ansiReset = "\u001B[0m";
	String frog = ansiBoldRed + "F" + ansiReset;
	String toad = ansiBoldYellow + "T" + ansiReset;
	
	
	//====================================== CONSTRUCTORS
	public FrogsAndToads(int rows, int columns) {
		/* There must be an odd number of rows and columns for the starting
		   configuration to be well-defined, so if constructor parameters are
		   even then use the default size instead. */
		
		if (rows%2 != 0) {
			System.out.println("There must be an odd number of rows!");
			this.rows = DEFAULT_ROWS;
		} else {
			this.rows = rows;
		}
		
		if (columns%2 != 0) {
			System.out.println("There must be an odd number of columns!");
			this.columns = DEFAULT_COLUMNS;
		} else {
			this.columns = columns;
		}
		setUpGrid();
	}
	public FrogsAndToads() {
		rows = DEFAULT_ROWS;
		columns = DEFAULT_COLUMNS;
		setUpGrid();
	}
	
	//====================================== METHODS
	public void move(int row, int col) {
	
	}
	
	public void jumpOver() {
	
	}
	
	public boolean over() {
		return false;
	}
	
	public boolean canMove() {
		return false;
	}
	
	public void undo() {
	
	}
	
	@Override
	public String toString() {
		return Arrays.deepToString(gameGrid).replace("], ", "]\n").
											 replace("[[", "[").
											 replace("]]", "]");
	}
	
	
	public void setUpGrid() {
		gameGrid = new String[rows][columns];
		
		int middleRow = (int) Math.floor((double) rows/2);
		int middleColumn = (int) Math.floor((double) columns/2);
		
		//i feel like there's a better way to do this
		for (int i = 0; i < gameGrid.length; i++) {
			for (int j = 0; j < gameGrid[i].length; j++) {
				
				if (i < middleRow)  {
					gameGrid[i][j] = "X";
				} else if (i == middleRow) {
					if (j < middleColumn)
						gameGrid[i][j] = "X";
					else
						gameGrid[i][j] = "O";
				} else {
					gameGrid[i][j] = "O";
				}
			}
		}
		
		//we want to keep the box in the center of the grid empty
		gameGrid[middleRow][middleColumn] = "-";
	}
	
	
	
}


