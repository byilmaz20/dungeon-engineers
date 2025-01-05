package src.GameObjects;
import javax.swing.*;

import src.GameController.GameFlowController;
import src.UI.MainMenuScreen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;

public class LoadGameScreen extends JFrame implements Serializable{
    private static final String SAVE_DIR = "Saves"; // Directory for saved files

    public LoadGameScreen() {
        setTitle("Load Game");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Label
        JLabel headerLabel = new JLabel("Select a saved game to load:", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(headerLabel, BorderLayout.NORTH);

        // File List Panel
        DefaultListModel<String> fileListModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(fileListModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane fileScrollPane = new JScrollPane(fileList);
        fileScrollPane.setBorder(BorderFactory.createTitledBorder("Saved Files"));
        add(fileScrollPane, BorderLayout.CENTER);

        // Load files into the list
        File saveDir = new File(SAVE_DIR);
        if (!saveDir.exists()) saveDir.mkdir(); // Create directory if it doesn't exist

        File[] saveFiles = saveDir.listFiles();
        if (saveFiles != null) {
            for (File file : saveFiles) {
                fileListModel.addElement(file.getName());
            }
        } else {
            fileListModel.addElement("No saved games found.");
        }

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton loadButton = new JButton("Load Selected");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(loadButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load Button Action Listener
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFile = fileList.getSelectedValue();
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(LoadGameScreen.this, "Please select a save file.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String filePath = SAVE_DIR + File.separator + selectedFile;
                GameFlowController gameFlowController = LoadGame.loadGame(filePath);
                gameFlowController.getPlayModeController().getPlayModeScreen().resumeGame();
                if (gameFlowController != null) {
                    JOptionPane.showMessageDialog(LoadGameScreen.this, "Game Loaded! Player: " + gameFlowController.getCurrentHall().getHallTypes());
                    // Start the loaded game or transition to the game screen
                } else {
                    JOptionPane.showMessageDialog(LoadGameScreen.this, "Failed to load the game file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Cancel Button Action Listener
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the load game screen
                new MainMenuScreen();
            }
        });

        setVisible(true);
    }
    
}
