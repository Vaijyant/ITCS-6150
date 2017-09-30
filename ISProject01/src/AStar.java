
/* To implement the A* algorithm and apply it to the problem
 * of 8-puzzle
 * 
 * Author	:	Vaijyant Tomar
 * Niner ID	:	800990636
 * 
 * */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;

public class AStar {

	static final int GOAL_GRID[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
	static PriorityQueue<Board> pq;

	public static void main(String[] args) throws IOException {
		
		System.out.println("8-puzzle problem");
		
		int[][] intialGrid = getInput();
		boolean solvable = isSolvable(intialGrid);
		if(solvable) {
			Board board = new Board();
			board.setGrid(intialGrid);
			board.setG(0);
			board.setH(calculatetHeuristic(intialGrid));
			board.setLevel(0);
			Comparator<Board> comparator = new BoardComparator();
			pq = new PriorityQueue<Board>(comparator);
			pq.add(board);
			int level = solveEightPuzzle(pq);
			System.out.println("Solution at level: " + level);
		}
		else
			System.out.println("The specified grid is not solvable.");		
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
				successorGrids = getSuccessors(board.getGrid());
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
	public static int calculatetHeuristic(int[][] board) {
		int manhattanDistance = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				int value = board[i][j];
				if (value != 0) {
					int targetRow = (value - 1) / 3;
					int targetColumn = (value - 1) % 3;
					int dx = i - targetRow;
					int dy = j - targetColumn;
					manhattanDistance += Math.abs(dx) + Math.abs(dy);
				}
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

	// Method to check if the board is solvable
	public static boolean isSolvable(int[][] board) {
		int inv_count = 0;
		int linearBoard[] = new int[9];
		int index = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				linearBoard[index++] = board[i][j];
			}
		}
		for (int i = 0; i < 9 - 1; i++) {
			for (int j = i + 1; j < 9; j++)
				if (linearBoard[i] != 0 && linearBoard[j] != 0 && linearBoard[i] > linearBoard[j])
					inv_count++;
		}

		if (inv_count % 2 == 0)
			return true;

		// else
		return false;
	}

	// Method to take input from the user
	public static int[][] getInput() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int input[][] = new int[3][3];
		boolean correctInput = false;
		while (!correctInput) {
			// Taking Input from the user
			System.out.println("Enter the puzzle values in row-wise. Enter '0' for blank space: ");
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.println("Enter value for row " + (i + 1) + ", column " + (j + 1) + ":");
					input[i][j] = Integer.parseInt(br.readLine());
				}
			}

			// Showing the user what he has entered to confirm the board he entered.
			System.out.println("Your board is: ");
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (input[i][j] == 0)
						System.out.print(" \t");
					else
						System.out.print(input[i][j] + "\t");
				}
				System.out.println();
			}

			System.out.println("Accept the board [y/n]:");
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
}
