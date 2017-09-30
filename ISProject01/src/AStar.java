/* To implement the A* algorithm and apply it to the problem
 * of 8-puzzle
 * 
 * Author	:	Vaijyant Tomar
 * Niner ID	:	800990636
 * 
 * */

import java.io.*;
public class AStar {
	public static void main(String[] args) throws IOException {
		
		int board[][] = new int[3][3];
		int solvedBoard[][] = new int[3][3];
		System.out.println("8-puzzle problem");
		board = getInput();
		solvedBoard = solveEightPuzzle(board);
		
		
	}
	//Method to solve 8 puzzle using A* algorithm
	public static int[][] solveEightPuzzle(int board[][]){
		
		
		return board;
	}
	// Method to take input from the user
	public static int[][] getInput() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int input[][] = new int[3][3];
		boolean correctInput = false;
		while(!correctInput) {
			//Taking Input from the user
			System.out.println("Enter the puzzle values in row-wise. Enter '0' for blank space: ");
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					System.out.println("Enter value for row "+(i+1)+", column "+(j+1)+":");
					input[i][j] = Integer.parseInt(br.readLine());
				}
			}
			
			//Showing the user what he has entered to confirm the board he entered.
			System.out.println("Your matrix is: ");
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					if(input[i][j] == 0)
						System.out.print(" \t");
					else
						System.out.print(input[i][j]+"\t");
				}
				System.out.println();
			}
			
			System.out.println("Accept the board [y/n]:");
			char accept = br.readLine().charAt(0);
			if(accept=='Y' || accept == 'y')
				correctInput = true;
			else
				correctInput = false;
			
		}
		return input;
	}
}