package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import Algorithm.*;

public class NQueenGUI extends JFrame implements ActionListener {

    private static final int MAX_QUEENS = 100;
    private static JTextField numOfQueens;
    private static JTextArea result;
    private static JPanel chessPanel;
    private static int[] states = new int[]{};
    private static JRadioButton optHillClimbing, opt;

    private static JButton runButton;

    /**
     * main() Entry point to the program
     */
    public static void main(String[] args) {
        /*
        * Setting the look and feel
        * */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NQueenGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NQueenGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NQueenGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NQueenGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        NQueenGUI nQueenGUI = new NQueenGUI();

        nQueenGUI.setSize(750, 750);
        nQueenGUI.setLocationRelativeTo(null);
        nQueenGUI.setVisible(true);
        nQueenGUI.setResizable(false);
    }

    /**
     * Constructor for the Main Class
     */

    public NQueenGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("N-Queens Problem");

        /*
        * Setting Up panels
        * */
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();

        jPanel1.setMaximumSize(new Dimension(500, 500));
        jPanel1.setMinimumSize(new Dimension(500, 500));
        jPanel1.setPreferredSize(new Dimension(500, 500));

        jPanel2.setMaximumSize(new Dimension(250, 500));
        jPanel2.setMinimumSize(new Dimension(250, 500));
        jPanel2.setPreferredSize(new Dimension(250, 500));

        JTextArea resultArea = new JTextArea(12, 10);
        resultArea.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(resultArea);

        getContentPane().add(jPanel1, BorderLayout.WEST);
        getContentPane().add(jPanel2, BorderLayout.CENTER);
        getContentPane().add(jScrollPane, BorderLayout.PAGE_END);

        /*
        * Setting Up elements
        */
        jPanel2.setLayout(new GridLayout(20, 1));

        jPanel2.add(new JLabel("Select Algorithm:"));

        ButtonGroup group = new ButtonGroup();
        JRadioButton button1 = new JRadioButton("Hill Climbing");
        group.add(button1);
        button1.setSelected(true);// default algorithm
        jPanel2.add(button1);

        JRadioButton button2 = new JRadioButton("Min Conflicts");
        group.add(button2);
        jPanel2.add(button2);
        jPanel2.add(new JLabel("Input value of N:"));

        JTextField text = new JTextField("7");
        jPanel2.add(text);

        runButton = new JButton("Run");
        jPanel2.add(runButton);

        /*
        * Setting Up statics
        */
        numOfQueens = text;
        optHillClimbing = button1;
        result = resultArea;

        chessPanel = jPanel1;
        drawBoard();

        runButton.addActionListener(this);

        /*
         * Displaying the startup message.
         */
        displayResult("Please provide the value of N and click run to solve the N-Queens problem.");

    }

    /**
     * Overridden method for event handling
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == runButton) {
            drawBoard();

            if (Integer.parseInt(numOfQueens.getText()) < 4) {
                displayResult("Can't place the Queens.\r\n" +
                        "Please provide more than 3 queens.");
                return;
            }

            if (optHillClimbing.isSelected()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HillClimbing hc = new HillClimbing();

                        Calendar start = Calendar.getInstance();
                        hc.run(Integer.valueOf(numOfQueens.getText()));
                        Calendar end = Calendar.getInstance();

                        appendResult("Time for run: "
                                + ((end.getTimeInMillis() - start.getTimeInMillis()) / 1000.0) + " seconds");
                    }
                }).start();
            }
        }
    }

    /**
     * Find the next move for the current queen position
     */
    public static void moveQueens(int[] queensPositionList) {
        if (chessPanel != null && chessPanel.getComponentCount() > 0) {
            if (states.length > 0) {
                for (int col = 0; col < queensPositionList.length; col++) {
                    if (states[col] != queensPositionList[col]) {
                        ChessSquare chessSquare = (ChessSquare) chessPanel.getComponent(col + states[col]
                                * states.length);
                        chessSquare.setQueen(false);

                        ChessSquare queenSquare = (ChessSquare) chessPanel.getComponent(col + queensPositionList[col]
                                * queensPositionList.length);
                        queenSquare.setQueen(true);
                    }
                }
            } else {
                for (int col = 0; col < queensPositionList.length; col++) {
                    ChessSquare queenSquare = (ChessSquare) chessPanel.getComponent(col + queensPositionList[col]
                            * queensPositionList.length);
                    queenSquare.setQueen(true);
                }
            }

            states = queensPositionList;
            chessPanel.revalidate();
            chessPanel.repaint();
        }
    }

    /**
     * Draw the Chess board
     */
    private void drawBoard() {
        int n = Integer.parseInt(numOfQueens.getText());

        if (n * n != chessPanel.getComponentCount()) {
            chessPanel.setLayout(new GridLayout(n, n, 0, 0));
            states = new int[]{};
            chessPanel.removeAll();

            if (n <= MAX_QUEENS) {
                for (int i = 0; i < (n * n); i++) {
                    ChessSquare chessSquare = new ChessSquare(i, n);
                    chessPanel.add(chessSquare);
                }
            }

            chessPanel.revalidate();
            chessPanel.repaint();
        }
    }

    /**
     * Append to the result screen
     */
    public static void appendResult(String str) {
        if (result != null) {
            result.append(str + "\r\n\r\n");
        }
    }

    /**
     * Show message on result screen
     */
    public static void displayResult(String str) {
        if (result != null)
            result.setText(str + "\r\n");
    }
}
