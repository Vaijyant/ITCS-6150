package Algorithm;

import GUI.NQueenGUI;
import java.util.Arrays;
public class HillClimbing {

    public int[] run(int num) {
        NQueenGUI.displayResult("Running ...");

        int numOfRestarts = 0;
        int totalChanges = 0;

        while (numOfRestarts++ < 1000) {
            int numOfChanges = 0;

            QueensState qState = QueensState.getRandomQueen(num);

            if (qState.getTotalConflict() == 0) {
                // The random problem happens to be successful.
                NQueenGUI.appendResult("Successful without search. The state data:" + Arrays.toString(qState.getState()));
                return qState.getState();
            }

            while (qState.getTotalConflict() != 0) {
                numOfChanges++;
                totalChanges++;

                QueensState newQState = getNextQueen(qState);

                if (newQState.getTotalConflict() == qState.getTotalConflict()) {
                    break;
                }

                qState = newQState;

                NQueenGUI.displayResult("Number of Restarts = " + numOfRestarts +
                        "\n\rTotal changes = " + totalChanges +
                        "\n\rChanges in current try = " + numOfChanges +
                        "\n\rConflict = " + qState.getTotalConflict());

                NQueenGUI.moveQueens(qState.getState());

                if (newQState.getTotalConflict() == 0) {
                    // Found the goal state.
                    NQueenGUI.appendResult("Success." +
                            "\nCurrent state: " + Arrays.toString(qState.getState()));
                    return qState.getState();
                }
            }
        }

        // No result found after 1000 times restart.
        NQueenGUI.appendResult("No result found after 1000 restarts. Failed!");
        return new int[] {};
    }

    private QueensState getNextQueen(QueensState currentQueen) {
        int[] currentState = currentQueen.getState();
        int[] newState = currentState;

        for (int col = 0; col < currentState.length; col++) {
            for (int row = 0; row < currentState.length; row++) {

                int[] tempState = Arrays.copyOf(currentState, currentState.length);
                tempState[col] = row;

                QueensState tempQueen = new QueensState(tempState);
                if (tempQueen.getTotalConflict() < currentQueen.getTotalConflict()) {
                    newState = tempState;
                }
            }
        }
        return new QueensState(newState);
    }
}
