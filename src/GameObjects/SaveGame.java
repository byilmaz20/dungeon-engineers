package src.GameObjects;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import src.GameController.GameFlowController;
import src.GameController.SaveGameController;

import java.io.IOException;

public class SaveGame implements Serializable{   
        public SaveGame() {
            // Create a new JFrame for the save dialog
            JFrame saveFrame = new JFrame("Save Game");
            saveFrame.setSize(400, 150);
            saveFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            saveFrame.setLayout(new BorderLayout());
    
            // Label to prompt the user
            JLabel label = new JLabel("Enter a name for your save file:", SwingConstants.CENTER);
            saveFrame.add(label, BorderLayout.NORTH);
    
            // Input field for the save name
            JTextField saveNameField = new JTextField();
            saveFrame.add(saveNameField, BorderLayout.CENTER);
            // Panel for buttons
            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            saveFrame.add(buttonPanel, BorderLayout.SOUTH);
    
            
            // Save button action listener
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String saveName = saveNameField.getText().trim();
    
                    if (saveName.isEmpty()) {
                        JOptionPane.showMessageDialog(saveFrame, "Save name cannot be empty!", 
                                                      "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
    
                    String saveDir = "Saves";
                    File saveFolder = new File(saveDir);
    
                    // Ensure the save folder exists
                    if (!saveFolder.exists()) saveFolder.mkdirs();
    
                    String filePath = saveDir + File.separator + saveName + ".dat";
    
                    try (FileOutputStream fileOut = new FileOutputStream(filePath);
                        ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                        // Serialize the game state and save to file
                        SaveGameController sgc = new SaveGameController();
                        out.writeObject(sgc.getCurrentHall());
                        out.writeObject(sgc.getCurrentHallIndex());
                        //out.writeObject(sgc.getGridEnvironment());
                        //out.writeObject(sgc.getPlayModeScreen());
                        //out.writeObject(sgc.getTimeController());

                    JOptionPane.showMessageDialog(saveFrame, 
                                                  "Game saved successfully as \"" + saveName + "\".",
                                                  "Save Successful", JOptionPane.INFORMATION_MESSAGE);
                    saveFrame.dispose(); // Close the dialog after saving
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(saveFrame, 
                                                  "Failed to save the game: " + ex.getMessage(),
                                                  "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Cancel button action listener
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFrame.dispose(); // Close the dialog without saving
            }
        });

        saveFrame.setVisible(true); // Show the save dialog
    }

   

    
    
}
