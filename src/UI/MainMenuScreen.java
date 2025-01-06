package src.UI;
import javax.swing.*;

import src.GameObjects.LoadGameScreen;
import src.GameObjects.SaveGame;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;


public class MainMenuScreen extends UIScreen implements Serializable{

    private JButton startGameButton;
    private JButton loadGameButton;
    private JButton helpButton;
    private JButton exitButton;

    public MainMenuScreen() {
        super(600, 600, "Main Menu", 
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
        setLoadGameButton();
    }

    private void setStartGameButton() {
        startGameButton = new JButton();
        startGameButton.setBounds(254, 250, 129, 33);
        startGameButton.setOpaque(false);
        startGameButton.setContentAreaFilled(false);
        startGameButton.setBorderPainted(false);
        startGameButton.addActionListener(e -> {
            this.dispose();
            new BuildModeScreen();
        });
        backgroundPanel.add(startGameButton);
    }

    private void setLoadGameButton() {
        loadGameButton = new JButton();
        loadGameButton.setBounds(254, 285, 129, 33);
        loadGameButton.setOpaque(false);
        loadGameButton.setContentAreaFilled(false);
        loadGameButton.setBorderPainted(false);
        loadGameButton.addActionListener(e -> {
            this.dispose();
            new LoadGameScreen();
        });
        backgroundPanel.add(loadGameButton);
    }



    private void setHelpButton() {
        helpButton = new JButton();
        helpButton.setBounds(254, 320, 129, 33);
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
        exitButton.setBounds(254, 355, 129, 33);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        backgroundPanel.add(exitButton);
    }

    
}
