package src.UI;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class MainMenuScreen extends UIScreen {

    private JButton startGameButton;
    private JButton helpButton;
    private JButton exitButton;

    public MainMenuScreen() {
        super(600, 600, "Main Menu", 
                "/Users/begumyilmaz/Documents/okul/koç/4.1/comp302/project/projectrepo/src/Images/BackgroundImages/mainMenuBackground.png");        

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
        startGameButton = new JButton("Start Game");
        startGameButton.setBounds(254, 250, 129, 33);
        
        startGameButton.addActionListener(e -> {
            this.dispose();
            new PlayModeScreen();
        });
        backgroundPanel.add(startGameButton);
    }

    private void setHelpButton() {
        helpButton = new JButton("Help");
        helpButton.setBounds(254, 285, 129, 33);
        helpButton.setFont(new Font("Arial", Font.BOLD, 16));
        helpButton.addActionListener(e -> {
            this.dispose();
            new HelpScreen();
        });
        backgroundPanel.add(helpButton);
    }

    private void setExitButton() {
        exitButton = new JButton("Exit");
        exitButton.setBounds(254, 320, 129, 33);
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.addActionListener(e -> System.exit(0));
        backgroundPanel.add(exitButton);
    }

}
