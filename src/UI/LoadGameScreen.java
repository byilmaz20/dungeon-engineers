package src.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import src.GameController.LoadGameController;
import src.Mechanics.SoundManager;

public class LoadGameScreen extends JFrame implements Serializable {
    private static final String SAVE_DIR = "Saves"; // Directory for saved files
    private SoundManager buttonClickSound;

    public LoadGameScreen(MainMenuScreen screen) {
        buttonClickSound = new SoundManager("src/voices/clickbutton.wav");
        setTitle("Load Game");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);

        // Header Label
        JLabel headerLabel = new JLabel("Select a saved game to load:", SwingConstants.CENTER);
        headerLabel.setFont(new Font("PixelFont", Font.BOLD, 22));
        headerLabel.setForeground(new Color(100, 50, 30)); // Darker rustic brown
        add(headerLabel, BorderLayout.NORTH);

        // File List Panel
        DefaultListModel<String> fileListModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(fileListModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setBackground(new Color(200, 180, 150)); // Muted rustic beige
        fileList.setBorder(BorderFactory.createLineBorder(new Color(100, 50, 30), 4));
        fileList.setFont(new Font("PixelFont", Font.PLAIN, 20));
        fileList.setForeground(new Color(80, 40, 20)); // Darker brown for text
        JScrollPane fileScrollPane = new JScrollPane(fileList);
        fileScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(100, 50, 30), 42), "Saved Files", 0, 0, new Font("PixelFont", Font.BOLD, 22), new Color(100, 50, 30)));
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
        buttonPanel.setBackground(new Color(180, 140, 100)); // Rustic tan

        JButton loadButton = new JButton("Load Selected");
        JButton cancelButton = new JButton("Cancel");

        // Style buttons
        styleButton(loadButton);
        styleButton(cancelButton);

        buttonPanel.add(loadButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load Button Action Listener
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClickSound.playSound(); // Tıklama sesi çal
                String selectedFile = fileList.getSelectedValue();
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(LoadGameScreen.this, "Please select a save file.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String filePath = SAVE_DIR + File.separator + selectedFile;
                LoadGameController.loadGame(filePath);
                dispose(); // Close the load game screen
                screen.setVisible(false);
            }
        });

        // Cancel Button Action Listener
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClickSound.playSound(); // Tıklama sesi çal
                dispose(); // Close the load game screen
                //new MainMenuScreen();
            }
        });

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("PixelFont", Font.BOLD, 22));
        button.setBackground(new Color(120, 60, 30)); // Dark rustic brown
        button.setForeground(new Color(100, 50, 30));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(80, 40, 20), 4));
    }
}
