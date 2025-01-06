package src.UI;
import javax.swing.*;


public class MainMenuScreen extends UIScreen {

    private JButton startGameButton;
    private JButton helpButton;
    private JButton exitButton;

    public MainMenuScreen() {
        super(1090, 810, "Main Menu", 
        "src/Images/BackgroundImages/mainMenuBackground.png");        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initializeComponents();
    
        setVisible(true);
    }

    private void initializeComponents() {
        setBackgroundImage();
        setStartGameButton();
        setHelpButton();
        setExitButton();
    }

    private void setStartGameButton() {
        startGameButton = new JButton();
        //startGameButton.setBounds(254, 250, 129, 33);
        startGameButton.setBounds(510, 577, 250, 75);

        startGameButton.setOpaque(false);
        startGameButton.setContentAreaFilled(false);
        startGameButton.setBorderPainted(false);
        startGameButton.addActionListener(e -> {
            this.dispose();
            new BuildModeScreen();
        });
        backgroundPanel.add(startGameButton);
    }

    private void setHelpButton() {
        helpButton = new JButton();
        helpButton.setBounds(510, 664, 250, 75);
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
        exitButton.setBounds(510, 753, 250, 75);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        backgroundPanel.add(exitButton);
    }

}
