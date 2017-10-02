/* To implement the A* algorithm and apply it to the problem
 * of 8-puzzle
 * 
 * Author	:	Vaijyant Tomar
 * Niner ID	:	800990636
 * 
 * */

//Class to save various properties of the board
class Board{
	int[][] grid;	//configuration of the grid
	int f;			//total cost of the gird
	int h;			//heuristic cost of the grid
	int g;			//step cost of the grid
	int level;		//level of the current grid
	Board parent;	//Parent of the current board
	public Board() {
		// TODO Auto-generated constructor stub
	}
	public int[][] getGrid() {
		return grid;
	}
	public void setGrid(int[][] grid) {
		this.grid = grid;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getF() {
		f = this.getG() +this.getH();
		return f;
	}
	public Board getParent() {
		return parent;
	}
	public void setParent(Board parent) {
		this.parent = parent;
	}
}