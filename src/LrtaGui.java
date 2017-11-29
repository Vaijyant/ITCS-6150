import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * @author Vaijyant Tomar
 */

public class LrtaGui extends JFrame implements ActionListener {

    static ArrayList<LocationTile> locationTilesList = new ArrayList<>();
    static int[] START;
    static int[] GOAL;

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LrtaGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LrtaGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LrtaGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LrtaGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //Setting up the GUI Components
        LrtaGui LrtaGui = new LrtaGui();

        LrtaGui.setSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        LrtaGui.setLocationRelativeTo(null);
        LrtaGui.setVisible(true);
        LrtaGui.setResizable(false);


        JOptionPane.showMessageDialog(null, "Please select input csv file to render obstacles.\n" +
                "\nPress OK to continue...");
        drawObstacles();
        setStartAndGoal();
    }


    /**
     * Constructor to Initialize GUI components
     */
    JPanel envFloorPanel;
    JPanel messagePanel;
    JTextArea resultArea;
    JScrollPane jMessageScrollPane;

    public LrtaGui() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Robot Motion Planning using LRTA*");

        JOptionPane.showMessageDialog(null, "Welcome to Robot Motion Planning using LRTA*.\n" +
                "\nPress OK to continue...");

        //setting up containers
        envFloorPanel = new JPanel();
        envFloorPanel.setMaximumSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        envFloorPanel.setMinimumSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        envFloorPanel.setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));

        resultArea = new JTextArea();
        resultArea.setLineWrap(true);
        jMessageScrollPane = new JScrollPane(resultArea);
        jMessageScrollPane.setMaximumSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT - Config.SCREEN_WIDTH));
        jMessageScrollPane.setMinimumSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT - Config.SCREEN_WIDTH));
        jMessageScrollPane.setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT - Config.SCREEN_WIDTH));


        envFloorPanel.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(envFloorPanel, BorderLayout.CENTER);
        getContentPane().add(jMessageScrollPane, BorderLayout.SOUTH);


        //drawing the floor
        drawFloor();


        //adding listeners


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.toString());
    }


    private void drawFloor() {

        if (Config.SIZE * Config.SIZE != envFloorPanel.getComponentCount()) {
            envFloorPanel.setLayout(new GridLayout(Config.SIZE, Config.SIZE, 0, 0));

            envFloorPanel.removeAll();

            for (int i = 0; i < Config.SIZE; i++) {
                for (int j = 0; j < Config.SIZE; j++) {
                    int[] location = {i, j}; //Row, Column
                    LocationTile locationTile = new LocationTile(location);
                    locationTilesList.add(locationTile);

                    locationTile.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (locationTile.hasObstacle) {
                                JOptionPane.showMessageDialog(null, "Cannot add a point on an obstacle.\n" +
                                        "Select a valid area.\n" +
                                        "\nPress OK to continue...");
                                return;
                            }
                            switch(Config.CLICK_COUNT){
                                case 0:
                                    locationTile.setBackground(Color.GREEN);
                                    START = locationTile.getTileLocation();
                                    Config.CLICK_COUNT++;
                                    break;
                                case 1:
                                    locationTile.setBackground(Color.RED);
                                    Config.CLICK_COUNT++;
                                    GOAL = locationTile.getTileLocation();
                                    startLRTAStarAlgorithm();
                                    break;
                                    default:
                            }
                        }
                    });
                    envFloorPanel.add(locationTile);
                }
            }
            envFloorPanel.revalidate();
            envFloorPanel.repaint();
        }
    }

    private static void drawObstacles() {
        ArrayList<int[]> obstacleList = Config.getObstacleList();

        for (int[] obstacle : obstacleList) {
            LocationTile locationTile = locationTilesList.get(obstacle[0] * Config.SIZE + obstacle[1]);
            locationTile.setHasObstacle(true);
        }
    }

    private static void setStartAndGoal() {
        JOptionPane.showMessageDialog(null, "Click on the grid to mark start and goal positions.\n" +
                "\nPress OK to continue...");

    }
    void startLRTAStarAlgorithm(){
        new LRTAStarAlgorithm(START, GOAL, locationTilesList);
    }
}
