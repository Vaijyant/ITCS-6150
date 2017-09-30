/* To implement the A* algorithm and apply it to the problem
 * of 8-puzzle
 * 
 * Author	:	Vaijyant Tomar
 * Niner ID	:	800990636
 * 
 * */

import java.util.Comparator;

//Class to create comparator object for Boards object in PriorityQueues
//The priority queue gets sorted as per the value of f, the total cost, stored in every Board object
public class BoardComparator implements Comparator<Board> {
	@Override
	public int compare(Board b1, Board b2) {
		if (b1.getF() < b2.getF()) {
			return -1;
		}
		if (b1.getF() > b2.getF()) {
			return 1;
		}
		return 0;
	}
}