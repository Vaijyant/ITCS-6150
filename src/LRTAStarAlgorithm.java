import java.util.ArrayList;

public class LRTAStarAlgorithm {
    int[]start;
    int[] goal;
    ArrayList<LocationTile> locationTileList;

    public LRTAStarAlgorithm(int[]start, int[] goal, ArrayList<LocationTile> locationTileList) {
        this.start = start;
        this.goal = goal;
        this.locationTileList = locationTileList;
    }
}
