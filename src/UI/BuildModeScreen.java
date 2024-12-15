package src.UI;
import src.GameController.GameController;

import javax.swing.*;
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
        JPanel objectPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for centering
        objectPanel.setBackground(new Color(60, 60, 60));
        objectPanel.setPreferredSize(new Dimension(150, 0));

        JLabel title = new JLabel("Build Mode", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        // Add title at the top
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 20, 0); // Spacing around the title
        gbc.anchor = GridBagConstraints.CENTER;
        objectPanel.add(title, gbc);

        // Add objects in the center
        String[] objects = objectImages.keySet().toArray(new String[0]);
        for (int i = 0; i < objects.length; i++) {
            JLabel objectLabel = new JLabel();

            // Load and set object image
            String imagePath = objectImages.get(objects[i]);
            if (imagePath != null) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Scale image
                objectLabel.setIcon(new ImageIcon(scaledImage));
            }

            // Add mouse listener for selection
            objectLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectObject(objectLabel);
                }
            });

            gbc.gridy = i + 1; // Increment row for each object
            gbc.insets = new Insets(10, 0, 10, 0); // Spacing between objects
            objectPanel.add(objectLabel, gbc);
        }

        // Add "Start Game" button at the bottom
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> checkRequirementsAndStartGame());
        gbc.gridy = objects.length + 1; // Place after objects
        gbc.insets = new Insets(20, 0, 10, 0); // Spacing around the button
        objectPanel.add(startGameButton, gbc);

        return objectPanel;
    }

    private JPanel createHallPanel() {
        JPanel hallPanel = new JPanel();
        hallPanel.setLayout(new GridLayout(2, 2, 10, 10)); // Grid for 4 halls
        hallPanel.setBackground(new Color(40, 40, 40));

        String[] hallNames = {"Hall of Water", "Hall of Earth", "Hall of Fire", "Hall of Air"};
        for (String hallName : hallNames) {
            JPanel hall = new JPanel();
            hall.setBorder(BorderFactory.createTitledBorder(hallName));
            hall.setLayout(new GridLayout(hallGridSize, hallGridSize)); // 25x25 grid layout
            hall.setBackground(new Color(80, 60, 60));

            // Add empty cells for the 25x25 grid
            for (int i = 0; i < hallGridSize * hallGridSize; i++) {
                JLabel cell = new JLabel();
                cell.setOpaque(true);
                cell.setBackground(new Color(80, 60, 60)); // Match hall background color
                cell.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); // Optional grid lines

                // Add mouse listener for placing the selected object
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        placeSelectedObject(cell, hallName);
                    }
                });

                hall.add(cell); // Add the cell to the hall grid
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



