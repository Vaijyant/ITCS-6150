package GUI;

import javax.swing.*;
import java.awt.*;

public class ChessSquare extends JLabel {

    private int location;
    private int numOfQueens;

    public ChessSquare(int location, int numOfQueens) {
        this.location = location;
        this.numOfQueens = numOfQueens;

        setHorizontalAlignment(CENTER);
        setQueen(false);
    }

    public void setQueen(boolean isQueen) {
        setOpaque(true);
        setPreferredSize(new Dimension(20, 20));
        setMaximumSize(new Dimension(50, 50));
        setText("");

        if (isQueen) {
            // if it is a queen, fill yellow color and put ♛ character.
            setBackground(new java.awt.Color(255,255,128));
            setText("♛");
        }
        else {
            int row = location / numOfQueens;
            int col = location % numOfQueens;

            if ((row + col) % 2 == 1) {
                setBackground(new java.awt.Color(0, 0, 0));
            }
            else {
                setBackground(new java.awt.Color(255, 255, 255));
            }
        }
    }
}