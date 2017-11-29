import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * @author Vaijyant Tomar
 */

public class LrtaGui extends JFrame implements ActionListener {

    ArrayList<LocationTile> locationTilesList = new ArrayList<>();

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
        setTitle("LRTA*");


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
        drawObstacles();

        int[] start = {1, 1};
        int[] goal = {98, 98};

        setStartAndGoal(start, goal);

        //adding listeners


    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

                    envFloorPanel.add(locationTile);
                }
            }
            envFloorPanel.revalidate();
            envFloorPanel.repaint();
        }
    }

    private void drawObstacles() {
        ArrayList<int[]> obstacleList = Config.getObstacleList();

        for (int[] obstacle : obstacleList) {
            LocationTile locationTile = locationTilesList.get(obstacle[0] * Config.SIZE + obstacle[1]);
            locationTile.setHasObstacle(true);

        }

    }

    private void setStartAndGoal(int[] start, int[] goal) {
        LocationTile startLocationTile = locationTilesList.get(start[0] * Config.SIZE + start[1]);
        startLocationTile.setBackground(Color.GREEN);

        LocationTile goalLocationTile = locationTilesList.get(goal[0] * Config.SIZE + goal[1]);
        goalLocationTile.setBackground(Color.RED);

    }
}
