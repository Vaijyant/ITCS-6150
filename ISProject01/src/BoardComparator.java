import java.util.Comparator;

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
