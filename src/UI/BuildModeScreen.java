package src.UI;
import src.GameController.GameController;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class BuildModeScreen extends JPanel {

    private final Map<String, JPanel> hallPanels; // Store hall panels
    private final Map<String, String> objectImages; // Store object image paths
    private final Map<String, Integer> hallObjectCounts; // Track object counts in each hall
    private final Map<String, Integer> hallMinimumCounts; // Define minimum object counts per hall
    private final int hallGridSize = 25; // 25x25 grid for each hall
    private ImageIcon selectedObjectIcon; // Currently selected object icon

    public BuildModeScreen() {
        hallPanels = new HashMap<>();
        objectImages = new HashMap<>();
        hallObjectCounts = new HashMap<>();
        hallMinimumCounts = new HashMap<>();

        // Define object images
        setupObjectImages();

        // Define minimum object counts for each hall
        setupHallConstraints();

        setLayout(new BorderLayout());

        // Right Panel for objects to select
        JPanel objectPanel = createObjectPanel();
        add(objectPanel, BorderLayout.EAST);

        // Main panel for halls
        JPanel hallPanel = createHallPanel();
        add(hallPanel, BorderLayout.CENTER);

        if (true) {
            JFrame frame = new JFrame("Build Mode");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setContentPane(this);
            frame.setVisible(true);
        }
    }

    private void setupObjectImages() {
        // Define object image paths
        objectImages.put("Barrel", "src/Images/ObjectImages/Barrel.png");
        objectImages.put("Stair", "src/Images/ObjectImages/Stair.png");
        objectImages.put("1Box", "src/Images/ObjectImages/1Box.png");
        objectImages.put("2Box", "src/Images/ObjectImages/2Box.png");
        objectImages.put("Rectangle", "src/Images/ObjectImages/Rectangle.png");
        objectImages.put("Skull", "src/Images/ObjectImages/Skull.png");
        objectImages.put("Chest", "src/Images/ObjectImages/Chest.png");
        objectImages.put("Potion", "src/Images/ObjectImages/Potion.png");
    }

    private void setupHallConstraints() {
        hallMinimumCounts.put("Hall of Earth", 6);
        hallMinimumCounts.put("Hall of Air", 9);
        hallMinimumCounts.put("Hall of Water", 13);
        hallMinimumCounts.put("Hall of Fire", 17);

        // Initialize object counts for each hall
        hallObjectCounts.put("Hall of Earth", 0);
        hallObjectCounts.put("Hall of Air", 0);
        hallObjectCounts.put("Hall of Water", 0);
        hallObjectCounts.put("Hall of Fire", 0);
    }

    private JPanel createObjectPanel() {
        // Custom JPanel to paint the background image
        JPanel objectPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("src/Images/ObjectImages/buildmode.png").getImage();
    
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image, scaling it to fit the panel size
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        objectPanel.setLayout(new GridBagLayout());
        objectPanel.setPreferredSize(new Dimension(150, 0));
    
        // Add title to the top of the panel
        //JLabel title = new JLabel("", SwingConstants.CENTER);
        //title.setForeground(Color.BLACK);
        //title.setFont(new Font("Arial", Font.BOLD, 18));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        gbc.insets = new Insets(100, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        //objectPanel.add(title, gbc);


        // Exit Button at the Top with Image
        JButton exitButton = new JButton();
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);

        // Load the exit button image
        ImageIcon exitIcon = new ImageIcon("src/Images/ObjectImages/exit.png");
        Image scaledExitImage = exitIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        exitButton.setIcon(new ImageIcon(scaledExitImage));
        // Add action listener for exiting the window
        exitButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose(); // Close current window
            new MainMenuScreen(); // Open Main Menu
        });
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 20, 0); // Padding around the button


        gbc.anchor = GridBagConstraints.CENTER;
        objectPanel.add(exitButton, gbc);
        // Add object icons to the panel
        String[] objects = objectImages.keySet().toArray(new String[0]);
        for (int i = 0; i < objects.length; i++) {
            JLabel objectLabel = new JLabel();
    
            // Load and set object image
            String imagePath = objectImages.get(objects[i]);
            if (imagePath != null) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                objectLabel.setIcon(new ImageIcon(scaledImage));
            }
    
            objectLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectObject(objectLabel);
                }
            });
    
            gbc.gridy = i + 1;
            gbc.insets = new Insets(10, 0, 10, 0);
            objectPanel.add(objectLabel, gbc);
        }
    
        // Add "Start Game" button at the bottom
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> checkRequirementsAndStartGame());
    
        gbc.gridy = objects.length + 1;
        gbc.insets = new Insets(20, 0, 10, 0);
        objectPanel.add(startGameButton, gbc);
    
        return objectPanel;
    }
    

    
    private JPanel createHallPanel() {
        JPanel hallPanel = new JPanel();
        hallPanel.setLayout(new GridLayout(2, 2, 10, 10)); // Grid for 4 halls
        hallPanel.setBackground(new Color(40, 40, 40));
    
        String[] hallNames = {"Hall of Water", "Hall of Earth", "Hall of Fire", "Hall of Air"};
    
        for (String hallName : hallNames) {
            // Custom JPanel for the hall with a background image
            JPanel hall = new JPanel() {
                private final Image backgroundImage = new ImageIcon("src/Images/BackgroundImages/hallbackground.png").getImage();
    
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            };
            TitledBorder titledBorder = BorderFactory.createTitledBorder(hallName);
            titledBorder.setTitleColor(Color.WHITE);
            hall.setBorder(titledBorder);
            hall.setLayout(new GridLayout(hallGridSize, hallGridSize)); // 25x25 grid layout
    
            // Add empty cells for the 25x25 grid
            for (int i = 0; i < hallGridSize * hallGridSize; i++) {
                JLabel cell = new JLabel();
                cell.setOpaque(false); // Transparent background for the grid cells
                //cell.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1)); // Grid lines
    
                // Add mouse listener for placing objects
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        placeSelectedObject(cell, hallName);
                    }
                });
    
                hall.add(cell);
            }
    
            hallPanels.put(hallName, hall);
            hallPanel.add(hall);
        }
    
        return hallPanel;
    }
    

    private void selectObject(JLabel objectLabel) {
        // Highlight the selected object visually (optional)
        for (Component comp : objectLabel.getParent().getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Reset others
            }
        }
        objectLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2)); // Highlight selected

        // Set the selected object icon
        selectedObjectIcon = (ImageIcon) objectLabel.getIcon();
    }

    private void placeSelectedObject(JLabel cell, String hallName) {
        if (selectedObjectIcon == null) {
            JOptionPane.showMessageDialog(this, "No object selected. Please select an object first.");
            return;
        }

        if (cell.getIcon() != null) {
            JOptionPane.showMessageDialog(this, "This cell is already occupied!");
            return;
        }

        // Place the object in the cell
        cell.setIcon(selectedObjectIcon);

        // Increment object count for the hall
        hallObjectCounts.put(hallName, hallObjectCounts.get(hallName) + 1);
    }

    private void checkRequirementsAndStartGame() {
        StringBuilder errorMessage = new StringBuilder();
        boolean allRequirementsMet = true;

        for (String hallName : hallMinimumCounts.keySet()) {
            int currentCount = hallObjectCounts.getOrDefault(hallName, 0);
            int requiredCount = hallMinimumCounts.get(hallName);

            if (currentCount < requiredCount) {
                allRequirementsMet = false;
                errorMessage.append(hallName)
                        .append(" needs at least ")
                        .append(requiredCount - currentCount)
                        .append(" more objects.\n");
            }
        }

        if (allRequirementsMet) {
            JOptionPane.showMessageDialog(this, "Game starting!");
            new GameController(); 
        } else {
            JOptionPane.showMessageDialog(this, "Cannot start the game:\n" + errorMessage);
        }
    }

    public JPanel getHall(String hallName) {
        return hallPanels.get(hallName);
    }

}



