package Algorithm;

import java.util.Random;

class QueensState {


    private int[] state = new int[] {};
    private int numOfConflict = -1;

    public QueensState(int[] state) {
        this.state = state;
    }

    public int[] getState() {
        return state;
    }

    public int getTotalConflict() {
        if (numOfConflict == -1) {
            // Calculate conflicts if already have not been calculated
            numOfConflict = countConflict();
        }

        return numOfConflict;
    }

    private int countConflict() {
        int count = 0;
        for (int x = 0; x < this.state.length; x++) {
            for (int y = x + 1; y < state.length; y++) {
                if (hasConflict(x, state[x], y, state[y])) {
                    count++;
                }
            }
        }

        return count;
    }


    private boolean hasConflict(int x1, int y1, int x2, int y2) {
        int xDistance = x1 - x2;
        int yDistance = y1 - y2;

        if (y1 == y2) {
            return true;
        } else if (Math.abs(xDistance) == Math.abs(yDistance)) {
            return true;
        } else {
            return false;
        }
    }


    public static QueensState getRandomQueen(int number) {
        int[] array = new int[number];

        Random r = new Random();
        for (int x = 0; x < number; x++) {
            // set a random position for a queen. The position range is from 0 to number.
            array[x] = r.nextInt(number);
        }

        QueensState queen = new QueensState(array);

        return queen;
    }
}
