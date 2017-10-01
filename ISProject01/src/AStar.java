/* To implement the A* algorithm and apply it to the problem
 * of 8-puzzle
 * 
 * Author	:	Vaijyant Tomar
 * Niner ID	:	800990636
 * 
 * */

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar {

	static int GOAL_GRID[][];
	static int INPUT_GRID[][];
	static PriorityQueue<Board> pq;
	
	static int GOAL_GRID_COORDINATE[][];
	
	static int nodesGenerated = 0;
	static int nodesExpanded = 0;
	public static void main(String[] args) throws IOException {
		
		System.out.println("\n============= 8-puzzle problem =============\n");
		getInput();
		GOAL_GRID_COORDINATE = getGridCoordinates(GOAL_GRID);
		
		Board board = new Board();
		board.setGrid(INPUT_GRID);
		board.setG(0);
		board.setH(calculatetHeuristic(INPUT_GRID));
		board.setLevel(0);
		Comparator<Board> comparator = new BoardComparator();
		pq = new PriorityQueue<Board>(comparator);
		pq.add(board);
		int level = solveEightPuzzle(pq);
		System.out.println("\n================== Result ==================");
		System.out.println("Number of moves required  : " + level);
		System.out.println("Number of nodes generated : " + nodesGenerated);
		System.out.println("Number of nodes expanded  : " + nodesExpanded);
	}

	// Method to solve 8 puzzle using A* algorithm
	public static int solveEightPuzzle(PriorityQueue<Board> pq) {
		ArrayList<int[][]> successorGrids = new ArrayList<int[][]>();
		Board board = null;
		while (!pq.isEmpty()) {
			board = pq.remove();
			
			if(Arrays.deepEquals(GOAL_GRID, board.getGrid())){
				return board.getLevel();
			}
			else{
				nodesExpanded++; //add the count of the expanded nodes
				successorGrids = getSuccessors(board.getGrid());
				nodesGenerated += successorGrids.size(); //add the count of the generated nodes
				for (int index = 0; index < successorGrids.size(); index++) {
					Board candidateBoard = new Board();
					int[][] candidateGrid = successorGrids.get(index);
					candidateBoard.setGrid(candidateGrid);
					candidateBoard.setG(board.getG() + 1);
					candidateBoard.setH(calculatetHeuristic(candidateGrid));
					candidateBoard.setLevel(board.getLevel() + 1);
					pq.add(candidateBoard);
				}
			}
		}
		return 0;
	}

	// Heuristic function for 8-Puzzle
	public static int calculatetHeuristic(int[][] grid) {
		int manhattanDistance = 0;
		int[][] gridCoordinates = new int [9][2]; //1st column number, 2nd row, 3rd column
		gridCoordinates = getGridCoordinates(grid);
		int drow =0;
		int dcol = 0;
		for(int i=1; i<9; i++) { //skipping 0
			drow = Math.abs(gridCoordinates[i][0] - GOAL_GRID_COORDINATE[i][0]);
			dcol = Math.abs(gridCoordinates[i][1] - GOAL_GRID_COORDINATE[i][1]);
			manhattanDistance += drow + dcol;
		}
		return manhattanDistance;
	}

	/*
	 * Utility functions
	 * 
	 */
	// getting children of a parent board
	public static ArrayList<int[][]> getSuccessors(int[][] grid) {
		ArrayList<int[][]> children = new ArrayList<int[][]>();

		// finding blank tile
		int i = 0, j = 0;
		boolean found = false;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				if (grid[i][j] == 0) {
					found = true;
					break;
				}
			}
			if (found)
				break;
		}

		// finding children
		int left_j = j - 1;
		int top_i = i - 1;
		int right_j = j + 1;
		int down_i = i + 1;

		if (left_j >= 0) {
			int leftBoard[][] = cloneGrid(grid);
			leftBoard[i][j] = leftBoard[i][left_j];
			leftBoard[i][left_j] = 0;
			children.add(leftBoard);
		}
		if (top_i >= 0) {
			int topBoard[][] = cloneGrid(grid);
			topBoard[i][j] = topBoard[top_i][j];
			topBoard[top_i][j] = 0;
			children.add(topBoard);
		}
		if (right_j <= 2) {
			int rightBoard[][] = cloneGrid(grid);
			rightBoard[i][j] = rightBoard[i][right_j];
			rightBoard[i][right_j] = 0;
			children.add(rightBoard);
		}
		if (down_i <= 2) {
			int downBoard[][] = cloneGrid(grid);
			downBoard[i][j] = downBoard[down_i][j];
			downBoard[down_i][j] = 0;
			children.add(downBoard);
		}
		return children;
	}

	// Method to take input from the user
	public static void getInput() throws NumberFormatException, IOException {
		
		System.out.println("Enter the values for Goal State: ");
		GOAL_GRID = matrixInput(); // for Goal State
		
		System.out.println("\nEnter the values for Initial State: ");
		INPUT_GRID = matrixInput(); // for Goal State
		
		return;
	}
	
	//Take a matrix as input
	public static int[][] matrixInput() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean correctInput = false;
		int[][] input = new int[3][3];
		while (!correctInput) {
			// Taking Input from the user
			System.out.println("> Please enter the puzzle values rowwise.\n"
					+ "> Enter '0' for blank:");
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.print("\t - Enter value for row " + (i + 1) + ", column " + (j + 1) + ": ");
					input[i][j] = Integer.parseInt(br.readLine());
				}
			}

			// Show board back to user.
			System.out.println("Your board is:\n");
			for (int i = 0; i < 3; i++) {
				System.out.print("\t");
				for (int j = 0; j < 3; j++) {
					if (input[i][j] == 0)
						System.out.print(" \t");
					else
						System.out.print(input[i][j] + "\t");
				}
				System.out.println();
			}

			System.out.print("\nConfirm the board? [y/n]: ");
			char accept = br.readLine().charAt(0);
			if (accept == 'Y' || accept == 'y')
				correctInput = true;
			else
				correctInput = false;
		}
		return input;
	}

	// utility function to clone an array
	public static int[][] cloneGrid(int[][] board) {
		int[][] clone = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				clone[i][j] = board[i][j];
			}
		}
		return clone;
	}
	
	// get grid coordinates
	public static int[][] getGridCoordinates(int[][] grid) {
		int[][] gridCoordinates = new int[9][2];
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				gridCoordinates[grid[i][j]][0] = i;
				gridCoordinates[grid[i][j]][1] = j;
			}
		}
		return gridCoordinates;
	}
}