package src.UI;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import src.GameObjects.HallTypes;

public class PlayModeScreen extends UIScreen {
    private JButton pauseGameButton;
    private JButton helpButton;
    private JButton exitButton;
    private ImageIcon hallimage;
    private HallTypes hallType;


    public PlayModeScreen() {
        super(600, 600, "Play Mode Screen", "/Users/ceylin/Desktop/comp302/project_code/projectrepo/src/Images/BackgroundImages/HALL.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        setVisible(true);
    }
    public void setHallType(HallTypes hallType) {
        this.hallType = hallType;
    }
    //press on Pause pausecontroller 

    private void initializeComponents() {
        setHallTypeImage();
        setPauseGameButton();
        setHelpButton();
        setExitButton();
    }
    private void setHallTypeImage() {
        //add hall type image to the screen
        String path = "";
        switch (this.hallType) {
            case HallTypes.AIR:
                path= "/Users/ceylin/Desktop/comp302/project_code/projectrepo/src/Images/BackgroundImages/air.png";
            case HallTypes.EARTH:
                path= "/Users/ceylin/Desktop/comp302/project_code/projectrepo/src/Images/BackgroundImages/earth.png";
            case HallTypes.FIRE:
                path= "/Users/ceylin/Desktop/comp302/project_code/projectrepo/src/Images/BackgroundImages/fire.png";
            case HallTypes.WATER:
                path= "/Users/ceylin/Desktop/comp302/project_code/projectrepo/src/Images/BackgroundImages/water.png";
        }
        hallimage = new ImageIcon(path);
        JLabel label = new JLabel(hallimage);
        this.add(label);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
    }
    private void setPauseGameButton() {
        pauseGameButton = new JButton();
        pauseGameButton.setBounds(254, 250, 129, 33);
        pauseGameButton.setOpaque(false);
        pauseGameButton.setContentAreaFilled(false);
        pauseGameButton.setBorderPainted(false);
        pauseGameButton.addActionListener(e -> {
            this.setVisible(false);
            //TODO:new PauseScreen();
        });
        backgroundPanel.add(pauseGameButton);
    }
    private void setHelpButton() {
        helpButton = new JButton();
        helpButton.setBounds(254, 285, 129, 33);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
            helpButton.addActionListener(e -> {
            this.setVisible(false);
            new HelpScreen(this);
            //TODO: IF CALLED FROM THE MAIN SCREEN, ARRANGE IT
        });
        backgroundPanel.add(helpButton);
    }
    private void setExitButton() {
        exitButton = new JButton();
        exitButton.setBounds(254, 320, 129, 33);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        backgroundPanel.add(exitButton);
    }
}
