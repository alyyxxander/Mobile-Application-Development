package homework.hw1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Back end for the game of Frogs and Toads on a rectangular grid.
 *
 * @author Alyxander Claiborne
 * @since 8-24-2022
 * @version 3.1
 */

public class FrogsAndToads {
	
	//====================================== VARIABLES
	private static final int DEFAULT_ROWS = 5;
	private static final int DEFAULT_COLUMNS = 5;
	private int rows, columns;
	private char[][] gameGrid;
	private char[][] winningGameGrid;
	private Stack<char[][]> gameHistory;
	
	private static final String ansiBoldRed = "\u001B[1;31m";
	private static final String ansiBoldYellow = "\u001B[1;33m";
	private static final String ansiReset = "\u001B[0m";
	private static final String frog = ansiBoldRed + "F" + ansiReset;
	private static final String toad = ansiBoldYellow + "T" + ansiReset;
	private static final String emptySpace = "-";
	private ArrayList<int[]> allCells = new ArrayList<>();
	private ArrayList<int[]> legalMoves = new ArrayList<>();
	
	
	//====================================== CONSTRUCTORS
	/**
	 * Creates a new game of the default size: 5x5
	 */
	public FrogsAndToads() {
		this(DEFAULT_ROWS, DEFAULT_COLUMNS);
	}

	/**
	 * Creates a new game on a square grid
	 *
	 * @param size The number of rows and columns of the grid, must be an odd number
	 */
	public FrogsAndToads(int size) {
		this(size, size);
	}

	/**
	 * Creates a new game on a rectangular grid
	 *
	 * @param rows The number of rows to be in the grid, must be an odd number
	 * @param columns The number of columns to be in the grid, must be an odd number
	 */
	public FrogsAndToads(int rows, int columns) {

		if (rows%2 == 0) {
			//There must be an odd number of rows. Since the user entered an even
			// number, we'll use the default value instead
			this.rows = DEFAULT_ROWS;
		} else {
			this.rows = rows;
		}
		
		if (columns%2 == 0) {
			//There must be an odd number of columns. Since the user entered an even
			// number, we'll use the default value instead
			this.columns = DEFAULT_COLUMNS;
		} else {
			this.columns = columns;
		}
		setUpGrid();
	}
	
	
	//====================================== METHODS
	/**
	 * @return Returns true if there is at least one legal move.
	 */
	public boolean canMove() {
		return getLegalMoves().size() != 0;
	}

	/**
	 * Returns false if there is a frog or toad at the provided coordinates, or
	 * if said coordinates are out of bounds
	 *
	 * @param row Row of the grid
	 * @param col Column of the grid
	 * @return Returns true if the empty space is at (i, j)
	 */
	public boolean emptyAt(int row, int col) {

		//checking if the coordinates are out of bounds
		if ((row < 0) || (row >= gameGrid.length))
			return false;
		if ((col < 0) || (col >= gameGrid[row].length))
			return false;
		
		return gameGrid[row][col] == 'E';
	}

	/**
	 * @param row Row of the grid
	 * @param col Column of the grid
	 * @return Returns true if there is a frog at the specified coordinate
	 */
	public boolean frogAt(int row, int col) {
		return gameGrid[row][col] == 'F';
	}

	/**
	 * Loops through each cell of the grid and checks if that coordinate is a valid move
	 *
	 * @return Returns a List of legal moves from the current configuration
	 */
	public List<int[]> getLegalMoves() {
		legalMoves.clear();
		for (int[] cell : allCells) {
		    if (canMove(cell[0], cell[1])) {
				legalMoves.add(cell);
			}
		}
		return legalMoves;
	}

	/**
	 * Makes a move at cell (row, col).
	 * NOTE: toads can ONLY jump up or to the left, and frogs can ONLY jump down or to
	 * the right, this is still true when one creature is jumping over another
	 * @param row Row of the game grid
	 * @param col Column of the game grid
	 */
	public void move(int row, int col) {

		if (emptyAt(row, col)) {
			//There is no creature at this coordinate
			return;
		} else if (!canMove(row, col)){
			//User did not select a valid move
			return;
		} else if (toadAt(row, col)) {
			//NOTE: toads can ONLY jump up or to the left
			
			//see if the toad can jump up
			if (emptyAt(row-1, col)) {
				gameGrid[row-1][col] = 'T';
				gameGrid[row][col] = 'E';
			}
			//see if the toad can jump to the left
			else if (emptyAt(row, col-1)) {
				gameGrid[row][col-1] = 'T';
				gameGrid[row][col] = 'E';
			}
			//see if the toad can jump up and over a frog into the empty space
			else if (emptyAt(row-2, col)) {
				if (frogAt(row-1, col)) {
					gameGrid[row-2][col] = 'T';
					gameGrid[row][col] = 'E';
				}
			}
			//see if the toad can jump left and over a frog into the empty space
			else if (emptyAt(row, col-2)) {
				if (frogAt(row, col-1)) {
					gameGrid[row][col-2] = 'T';
					gameGrid[row][col] = 'E';
				}
			}
			
		} else if (frogAt(row, col)) {
			//NOTE: frogs can ONLY jump down or to the right
			
			//see if the frog can jump down
			if (emptyAt(row+1, col)) {
				gameGrid[row+1][col] = 'F';
				gameGrid[row][col] = 'E';
			}
			//see if the frog can jump to the right
			else if (emptyAt(row, col+1)) {
				gameGrid[row][col+1] = 'F';
				gameGrid[row][col] = 'E';
			}
			//see if the frog can down up and over a toad into the empty space
			else if (emptyAt(row+2, col)) {
				if (toadAt(row+1, col)) {
					gameGrid[row+2][col] = 'F';
					gameGrid[row][col] = 'E';
				}
			}
			//see if the frog can jump right and over a toad into the empty space
			else if (emptyAt(row, col+2)) {
				if (toadAt(row, col+1)) {
					gameGrid[row][col+2] = 'F';
					gameGrid[row][col] = 'E';
				}
			}
		}
		gameHistory.push(copyGrid(gameGrid));
	}

	/**
	 * @return Returns true if the positions of the frogs and toads in the starting
	 * configuration have been interchanged (game over)
	 */
	public boolean over() {
		return equalGrids(gameGrid, winningGameGrid);
	}

	/**
	 * @param row Row of the game grid
	 * @param col Column of the game grid
	 * @return Returns true if there is a toad at (row, col)
	 */
	public boolean toadAt(int row, int col) {
		return gameGrid[row][col] == 'T';
	}

	/**
	 * @return Returns the current game state with row and column headers
	 */
	public String toString() {

		String[][] stringGameGrid = new String[rows][columns];
		for (int i = 0; i < gameGrid.length; i++) {
			for (int j = 0; j < gameGrid[i].length; j++) {

				// place frogs
				if (gameGrid[i][j] == 'F') {
					stringGameGrid[i][j] = frog;
				} // place toads
				else if (gameGrid[i][j] == 'T') {
					stringGameGrid[i][j] = toad;
				} // place empty space
				else if (gameGrid[i][j] == 'E') {
					stringGameGrid[i][j] = emptySpace;
				}
			}
		}

		//adding the column indices
		StringBuilder sb = new StringBuilder("  ");
		for (int col = 0; col < stringGameGrid[0].length; col++) {
			sb.append(col + " ");
		}
		sb.append("\n");

		for (int row = 0; row < stringGameGrid.length; row++) {
			sb.append(row + "|"); //this line adds the row indices

			for (int col = 0; col < gameGrid[row].length; col++) {
				sb.append(stringGameGrid[row][col] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Undoes the most recent move by setting the game grid equal to the previous
	 * version of itself
	 */
	public void undo() {
		if (gameHistory.size() == 1) {
			// Cannot undo any further
			return;
		}
		gameHistory.pop();
		gameGrid = gameHistory.peek();
	}
	
	
	//====================================== OTHER ADDED METHODS
	/**
	 * Creates a 2D array using the size specified in the FrogsAndToads constructor
	 */
	private void setUpGrid() {
		gameGrid = new char[rows][columns];
		
		int middleRow = rows/2;
		int middleColumn = columns/2;
		
		//Place frogs and toads on the board
		for (int i = 0; i < gameGrid.length; i++) {
			for (int j = 0; j < gameGrid[i].length; j++) {
				
				//make a list of all coordinates
				allCells.add(new int[] {i, j});
				
				if (i < middleRow)  {
					gameGrid[i][j] = 'F';
				} else if (i == middleRow) {
					if (j < middleColumn)
						gameGrid[i][j] = 'F';
					else
						gameGrid[i][j] = 'T';
				} else {
					gameGrid[i][j] = 'T';
				}
			}
		}
		
		//we want to keep the box in the center of the grid empty
		gameGrid[middleRow][middleColumn] = 'E';

		//begin keeping a history of the game grid
		gameHistory = new Stack<>();
		gameHistory.add(copyGrid(gameGrid));
		
		setWinningGameGrid(gameGrid);
	}

	/**
	 * Creates a copy of the starting game grid, but with all toads and frogs
	 * in opposite positions
	 *
	 * @param gameGrid The original game grid before any moves have been made by the
	 *                    player
	 */
	private void setWinningGameGrid(char[][] gameGrid) {

		winningGameGrid = copyGrid(gameGrid);
		
		// replace all values in the grid with temporary ones
		for (int i = 0; i < winningGameGrid.length; i++) {
			for (int j = 0; j < winningGameGrid[i].length; j++) {
				if (winningGameGrid[i][j] == 'F') {
					winningGameGrid[i][j] = 'f';
				} else if (winningGameGrid[i][j] == 'T') {
					winningGameGrid[i][j] = 't';
				}
			}
		}
		
		for (int i = 0; i < winningGameGrid.length; i++) {
			for (int j = 0; j < winningGameGrid[i].length; j++) {
				
				// place toads where the frogs were
				if (winningGameGrid[i][j] == 'f') {
					winningGameGrid[i][j] = 'T';
				} // place frogs where the toads were
				else if (winningGameGrid[i][j] == 't') {
					winningGameGrid[i][j] = 'F';
				}
			}
		}
	}

	/**
	 * Uses a for-loop to iterate over each row of the original array and then calls
	 * the clone() method to copy each row
	 *
	 * @param grid The 2D array to be copied
	 * @return Returns a copy of the provided grid
	 */
	private static char[][] copyGrid(char[][] grid) {
		// this method is based off of the first block of code from https://bit.ly/3dZZ9Px
		
		if (grid == null)
			return null;
		
		char[][] copy = new char[grid.length][];

		for (int i = 0; i < grid.length; i++) {
			copy[i] = grid[i].clone();
		}
		
		return copy;
	}

	/**
	 * Compares each index of the 2D arrays that are passed to the method
	 *
	 * @param arr1 The first 2D array being compared
	 * @param arr2 The second 2D array being compared
	 * @return Returns true if the two provided grids are equal
	 */
	private static boolean equalGrids(char[][] arr1, char[][] arr2) {
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

	/**
	 * Checks if the creature at the specified coordinate is able to make a valid move
	 *
	 * @param row row of the grid
	 * @param col column of the grid
	 * @return Returns true if the creature at the specified coordinate is able to make
	 * a valid move
	 */
	private boolean canMove(int row, int col) {

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
