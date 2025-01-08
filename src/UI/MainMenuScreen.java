package src.UI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;
import src.GameController.GameModeController;


public class MainMenuScreen extends UIScreen {

    private JButton startGameButton;
    private JButton loadGameButton;
    private JButton helpButton;
    private JButton exitButton;
    private TransparentButton easyModeButton;
    private TransparentButton hardModeButton;
    private String selectedMode = "hard";

    public MainMenuScreen() {
        super(1090, 810, "Main Menu", 
        "src/Images/BackgroundImages/mainMenuBackground.png");        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initializeComponents();
    
        setVisible(true);
    }

    private void initializeComponents() {
        setBackgroundImage();
        setGameModeButtons();
        setStartGameButton();
        setLoadGameButton();
        setHelpButton();
        setExitButton();
    }
    private void setGameModeButtons() {
        
        easyModeButton = new TransparentButton();
        hardModeButton = new TransparentButton();
    
        easyModeButton.setBounds(390, 95, 230, 80);
        easyModeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        hardModeButton.setBounds(620, 95, 230, 80);
        hardModeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        GameModeController.getInstance().setGameMode("hard");
    
        easyModeButton.addActionListener(e -> {
            selectedMode = "easy";
            GameModeController.getInstance().setGameMode("easy");
            updateButtonStyles();
        });
    
        hardModeButton.addActionListener(e -> {
            selectedMode = "hard";
            GameModeController.getInstance().setGameMode("hard");
            updateButtonStyles();
        });
    
        backgroundPanel.add(easyModeButton);
        backgroundPanel.add(hardModeButton);
    
        updateButtonStyles(); // Apply initial styles
    }
    
    private void updateButtonStyles() {
        setModeButtonStyle(easyModeButton, "easy".equals(selectedMode));
        setModeButtonStyle(hardModeButton, "hard".equals(selectedMode));
    }
    
    // Apply styles to the buttons
    private void setModeButtonStyle(TransparentButton button, boolean isSelected) {
        if (isSelected) {
            button.setForeground(Color.WHITE); // White text for selected
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Bold text
            button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3)); // Blue border
            button.setCustomBackground(new Color(101, 67, 33, 150)); // Semi-transparent brown

        } else {
            button.setForeground(Color.LIGHT_GRAY); // Gray text for unselected
            button.setFont(new Font("Arial", Font.PLAIN, 16)); // Regular text
            button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Gray border
            button.setCustomBackground(new Color(0, 0, 0, 0)); // Fully transparent background
        }
    }
    
    
    private void setStartGameButton() {
        startGameButton = new JButton();
        startGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startGameButton.setBounds(510, 594, 250, 70);
        startGameButton.setOpaque(false);
        startGameButton.setContentAreaFilled(false);
        startGameButton.setBorderPainted(false);
    
        startGameButton.addActionListener(e -> {
            this.dispose();
            new BuildModeScreen(); // Pass the selected mode
        });
        backgroundPanel.add(startGameButton);
    }
    private void setLoadGameButton() {
        loadGameButton = new JButton();
        loadGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loadGameButton.setBounds(510, 670, 250, 60);
        loadGameButton.setOpaque(false);
        loadGameButton.setContentAreaFilled(false);
        loadGameButton.setBorderPainted(false);
    
        loadGameButton.addActionListener(e -> {
            this.dispose();
            new LoadGameScreen(); // Pass the selected mode
        });
        backgroundPanel.add(loadGameButton);
    }
    


    private void setHelpButton() {
        helpButton = new JButton();
        helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        helpButton.setBounds(510, 740, 250, 60);
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
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setBounds(510, 810, 250, 80);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        backgroundPanel.add(exitButton);
    }


}
