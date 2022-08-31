package homework.hw1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Back end for the game of Frogs and Toads on a rectangular grid.
 *
 * @author Alyxander Claiborne
 * @since 8-24-2022
 * @version 2.1
 */

public class FrogsAndToads {
	
	//====================================== VARIABLES
	private final int DEFAULT_ROWS = 3;
	private final int DEFAULT_COLUMNS = 3;
	private int rows, columns;
	private String[][] gameGrid;
	private String[][] winningGameGrid;
	
	String ansiBoldRed = "\u001B[1;31m";
	String ansiBoldYellow = "\u001B[1;33m";
	String ansiReset = "\u001B[0m";
	String frog = ansiBoldRed + "F" + ansiReset;
	String toad = ansiBoldYellow + "T" + ansiReset;
	String emptySpace = "-";
	ArrayList<int[]> allCells = new ArrayList<>();
	ArrayList<int[]> legalMoves = new ArrayList<>();
	
	
	//====================================== CONSTRUCTORS
	public FrogsAndToads() {
		// Creates a new game of default size
		rows = DEFAULT_ROWS;
		columns = DEFAULT_COLUMNS;
		setUpGrid();
	}
	
	public FrogsAndToads(int size) {
		// Creates a new game on a square grid.
		if (size%2 != 0) {
			System.out.println("There must be an odd number of rows & columns!");
			this.rows = DEFAULT_ROWS;
		} else {
			this.rows = size;
			this.columns = size;
		}
		setUpGrid();
	}
	
	public FrogsAndToads(int rows, int columns) {
		// Creates a new game on a rectangular grid.
		
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
	
	
	//====================================== METHODS
	public boolean canMove() {
		// Returns true if there is at least one legal move.
		return getLegalMoves().size() != 0;
	}
	
	public boolean emptyAt(int i, int j) {
		// Returns true if the empty space is at (i, j).
		
		//check if the coordinates are out of bounds
		if ((i < 0) || (i >= gameGrid.length))
			return false;
		if ((j < 0) || (j >= gameGrid[i].length))
			return false;
		
		return gameGrid[i][j].equals(emptySpace);
	}
	
	public boolean frogAt(int i, int j) {
		// Returns true if there is a frog at(i, j).
		return gameGrid[i][j].equals(frog);
	}
	
	public List<int[]> getLegalMoves() {
		// Returns a list of legal moves from the current configuration.
		
		legalMoves.clear();
		for (int[] cell : allCells) {
		    if (canMove(cell[0], cell[1])) {
				legalMoves.add(cell);
			}
		}
		
		return legalMoves;
	}
	
	public void	move(int row, int col) {
		// Makes a move at cell (row, col).
		
		if (emptyAt(row, col)) {
			System.out.println("No creature at this coordinate!");
		} else if (toadAt(row, col)) {
			//NOTE: toads can ONLY jump up or to the left
			
			//see if the toad can jump up
			if (emptyAt(row-1, col)) {
				System.out.println("TOAD MOVED UP");
				gameGrid[row-1][col] = toad;
				gameGrid[row][col] = emptySpace;
			}
			//see if the toad can jump to the left
			else if (emptyAt(row, col-1)) {
				System.out.println("TOAD MOVED LEFT");
				gameGrid[row][col-1] = toad;
				gameGrid[row][col] = emptySpace;
			}
			//see if the toad can jump up and over a frog into the empty space
			else if (emptyAt(row-2, col)) {
				if (frogAt(row-1, col)) {
					System.out.println("TOAD JUMPED UP AND OVER FROG");
					gameGrid[row-2][col] = toad;
					gameGrid[row][col] = emptySpace;
				}
			}
			//see if the toad can jump left and over a frog into the empty space
			else if (emptyAt(row, col-2)) {
				if (frogAt(row, col-1)) {
					System.out.println("TOAD JUMPED LEFT AND OVER FROG");
					gameGrid[row][col-2] = toad;
					gameGrid[row][col] = emptySpace;
				}
			}
			
		} else if (frogAt(row, col)) {
			//NOTE: frogs can ONLY jump down or to the right
			
			//see if the frog can jump down
			if (emptyAt(row+1, col)) {
				System.out.println("FROG MOVED DOWN");
				gameGrid[row+1][col] = frog;
				gameGrid[row][col] = emptySpace;
			}
			//see if the frog can jump to the right
			else if (emptyAt(row, col+1)) {
				System.out.println("FROG MOVED RIGHT");
				gameGrid[row][col+1] = frog;
				gameGrid[row][col] = emptySpace;
			}
			//see if the frog can down up and over a toad into the empty space
			else if (emptyAt(row+2, col)) {
				if (toadAt(row+1, col)) {
					System.out.println("FROG JUMPED DOWN AND OVER TOAD");
					gameGrid[row+2][col] = frog;
					gameGrid[row][col] = emptySpace;
				}
			}
			//see if the frog can jump right and over a toad into the empty space
			else if (emptyAt(row, col+2)) {
				if (toadAt(row, col+1)) {
					System.out.println("FROG JUMPED RIGHT AND OVER TOAD");
					gameGrid[row][col+2] = frog;
					gameGrid[row][col] = emptySpace;
				}
			}
		}
	}
	
	public boolean over() {
		// Returns true if the positions of the frogs and toads in
		// the starting configuration have been interchanged (gameover).
		
		return equalGrids(gameGrid, winningGameGrid);
	}
	
	public boolean toadAt(int i, int j) {
		// Returns true if there is a toad at(i, j).
		return gameGrid[i][j].equals(toad);
	}
	
	public String toString() {
		// Returns the current game state with row and column headers.
		return Arrays.deepToString(gameGrid)
				.replace("], ", "]\n")
				.replace("[[", "[")
				.replace("]]", "]")
				.replace(",", "");
	}
	
	//TODO: write undo() method
	public void undo() {
		// Undoes the most recent move.
	}
	
	
	//====================================== OTHER ADDED METHODS
	public void setUpGrid() {
		gameGrid = new String[rows][columns];
		int middleRow = (int) Math.floor((double) rows/2);
		int middleColumn = (int) Math.floor((double) columns/2);
		
		//i feel like there's a better way to do this
		for (int i = 0; i < gameGrid.length; i++) {
			for (int j = 0; j < gameGrid[i].length; j++) {
				
				//make a list of all coordinates
				allCells.add(new int[] {i, j});
				
				if (i < middleRow)  {
					gameGrid[i][j] = frog;
				} else if (i == middleRow) {
					if (j < middleColumn)
						gameGrid[i][j] = frog;
					else
						gameGrid[i][j] = toad;
				} else {
					gameGrid[i][j] = toad;
				}
			}
		}
		
		//we want to keep the box in the center of the grid empty
		gameGrid[middleRow][middleColumn] = emptySpace;
		
		setWinningGameGrid(gameGrid);
	}
	
	public void setWinningGameGrid(String[][] gameGrid) {
		winningGameGrid = copyGrid(gameGrid);
		
		// replace all values in the grid with temporary ones
		for (int i = 0; i < winningGameGrid.length; i++) {
			for (int j = 0; j < winningGameGrid[i].length; j++) {
				
				if (winningGameGrid[i][j].equals(frog)) {
					winningGameGrid[i][j] = "tempFrog";
				} else if (winningGameGrid[i][j].equals(toad)) {
					winningGameGrid[i][j] = "tempToad";
				}
				
			}
		}
		
		for (int i = 0; i < winningGameGrid.length; i++) {
			for (int j = 0; j < winningGameGrid[i].length; j++) {
				
				// place toads where the frogs were
				if (winningGameGrid[i][j].equals("tempFrog")) {
					winningGameGrid[i][j] = toad;
				} // place frogs where the toads were
				else if (winningGameGrid[i][j].equals("tempToad")) {
					winningGameGrid[i][j] = frog;
				}
			}
		}
		
	}
	
	public static String[][] copyGrid(String[][] grid) {
		// this method is based off of the first block of code from https://bit.ly/3dZZ9Px
		
		if (grid == null)
			return null;
		
		String[][] copy = new String[grid.length][];
		for (int i = 0; i < grid.length; i++) {
			copy[i] = grid[i].clone();
		}
		
		return copy;
	}
	
	public boolean equalGrids(String[][] arr1, String[][] arr2) {
		// this method is based off: https://bit.ly/3dZZ9Px
		if (arr1 == null)
			return (arr2 == null);
		
		if (arr2 == null)
			return false;
		
		if (arr1.length != arr2.length)
			return false;
		
		for (int i = 0; i < arr1.length; i++) {
			if (!Arrays.equals(arr1[i], arr2[i]))
				return false;
		}
		
		return true;
	}
	
	public boolean canMove(int row, int col) {
		if (emptyAt(row, col)) {
			return false;
		} else if (toadAt(row, col)) {
			//NOTE: toads can ONLY jump up or to the left
			
			//see if the toad can jump up
			if (emptyAt(row-1, col)) {
				return true;
			}
			//see if the toad can jump to the left
			else if (emptyAt(row, col-1)) {
				return true;
			}
			//see if the toad can jump up and over a frog into the empty space
			else if (emptyAt(row-2, col)) {
				return frogAt(row - 1, col);
			}
			//see if the toad can jump left and over a frog into the empty space
			else if (emptyAt(row, col-2)) {
				return frogAt(row, col - 1);
			}
			
		} else if (frogAt(row, col)) {
			//NOTE: frogs can ONLY jump down or to the right
			
			//see if the frog can jump down
			if (emptyAt(row+1, col)) {
				return true;
			}
			//see if the frog can jump to the right
			else if (emptyAt(row, col+1)) {
				return true;
			}
			//see if the frog can down up and over a toad into the empty space
			else if (emptyAt(row+2, col)) {
				return toadAt(row + 1, col);
			}
			//see if the frog can jump right and over a toad into the empty space
			else if (emptyAt(row, col+2)) {
				return toadAt(row, col + 1);
			}
		}
		
		return false;
	}
	
	
}
