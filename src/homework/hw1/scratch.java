//FILE CREATED ON: 8/30/22
package homework.hw1;

import java.util.ArrayList;
import java.util.Arrays;

public class scratch {
	
	//THIS IS JUST FOR TESTING BITS OF CODE
	// .xml, .iml .gitignore
	
	
	public static String[][] gameGrid;
	public static ArrayList<int[]> allCells = new ArrayList<>();
	public static int rows = 3, columns = 3;
	
	//====================================== MAIN METHOD
	public static void main(String[] args) {
		
		setUpGrid();
		System.out.println("allCells:   ");
		
		for (int[] a : allCells) {
		    System.out.print(Arrays.toString(a) + " - ");
		}
		
		
	}
	
	public static void setUpGrid() {
		gameGrid = new String[rows][columns];
		int middleRow = (int) Math.floor((double) rows/2);
		int middleColumn = (int) Math.floor((double) columns/2);
		
		//i feel like there's a better way to do this
		for (int i = 0; i < gameGrid.length; i++) {
			for (int j = 0; j < gameGrid[i].length; j++) {
				
				//make a list of all coordinates
				allCells.add(new int[] {i, j});
				
				if (i < middleRow)  {
					gameGrid[i][j] = "F";
				} else if (i == middleRow) {
					if (j < middleColumn)
						gameGrid[i][j] = "F";
					else
						gameGrid[i][j] = "T";
				} else {
					gameGrid[i][j] = "T";
				}
			}
		}
		
		//we want to keep the box in the center of the grid empty
		gameGrid[middleRow][middleColumn] = "-";
		
	}
	
	
}
