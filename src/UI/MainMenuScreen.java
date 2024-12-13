package src.UI;
import javax.swing.*;
public class MainMenuScreen extends JFrame{

    JPanel backgroundPanel = new JPanel();

    public MainMenuScreen(){
        super("Main Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(null);
        //setBackgroundImage(); //todo sonra yapıcam -begum

        backgroundPanel.setLayout(null);
        backgroundPanel.setSize(800, 600);
        this.setStartGameButton();
        this.setHelpButton();
        this.setExitButton();
        this.add(backgroundPanel);

        
    }
    private void setStartGameButton() {
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setBounds(300, 200, 200, 50);
        startGameButton.addActionListener(e -> {
            this.dispose();
            new PlayModeScreen();
        });
        backgroundPanel.add(startGameButton);
        setVisible(true);

    }
    private void setHelpButton() {
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(300, 300, 200, 50);
        helpButton.addActionListener(e -> {
            this.dispose();
            new HelpScreen();
        });
        backgroundPanel.add(helpButton);
    }
    private void setExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(300, 400, 200, 50);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        backgroundPanel.add(exitButton);
    }
    // private void setBackgroundImage() {
    //     ImageIcon background = new ImageIcon("src/UI/images/mainMenuBackground.jpg");
    //     JLabel backgroundLabel = new JLabel(background);
    //     backgroundLabel.setBounds(0, 0, 800, 600);
    //     backgroundPanel.add(backgroundLabel);
    // }
}