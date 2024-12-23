package src.UI;

import java.awt.*;
import javax.swing.*;
import src.GameObjects.*;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

public class PlayModeScreen extends UIScreen {
    private final int gridWidth = 25; // Number of columns
    private final int gridHeight = 25; // Number of rows
    private final int baseCellSize = 25; // Base size of each cell
    private final double scaleFactor = 0.54686665897154587; // Scale factor for resizing the grid
    private JPanel[][] gridPanels; // Panels for each grid cell

    private JButton pauseGameButton;
    private JButton helpButton;
    private JButton exitButton;
    private HallTypes hallType;
    private ImageIcon hallimage;

    private GridEnvironment gridEnvironment; // Reference to the GridEnvironment

    public PlayModeScreen(HallTypes hallType, GridEnvironment gridEnvironment) {
        super(650, 650, "Play Mode Screen", 
        "src/Images/BackgroundImages/HALL.png");
        this.hallType = hallType;
        this.gridEnvironment = gridEnvironment;

        // Set up grid listener to update UI on grid changes
        this.gridEnvironment.setGridChangeListener(this::updateGridFromEnvironment);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
        initializeGrid();
        updateGridFromEnvironment(gridEnvironment.getMap()); // Initialize grid with current map

        setVisible(true);
    }

    private void initializeComponents() {
        setBackgroundImage();
        setHallTypeImage();
        setPauseGameButton();
        setHelpButton();
        setExitButton();
        
    }
    private void setHallTypeImage() {
        //add hall type image to the screen
        String path = "";
        switch (this.hallType) {
            case AIR -> path = "/Users/ceylin/Desktop/comp302/project_code/projectrepo/src/Images/BackgroundImages/air.png";
            case EARTH -> path = "/Users/ceylin/Desktop/comp302/project_code/projectrepo/src/Images/BackgroundImages/earth.png";
            case FIRE -> path = "/Users/ceylin/Desktop/comp302/project_code/projectrepo/src/Images/BackgroundImages/fire.png";
            case WATER -> path = "/Users/ceylin/Desktop/comp302/project_code/projectrepo/src/Images/BackgroundImages/water.png";
        }
        hallimage = new ImageIcon(path);
        Image resizedHallImage = hallimage.getImage().getScaledInstance(100, 60, Image.SCALE_SMOOTH); // Desired width and height
        ImageIcon resizedHallIcon = new ImageIcon(resizedHallImage);
        JLabel label = new JLabel(resizedHallIcon);
        this.setLayout(null);
        label.setBounds(170, 0, 200, 100);
        this.add(label, BorderLayout.CENTER);
        this.setVisible(true);
    }
    private void setPauseGameButton() {
        pauseGameButton = new JButton();
        pauseGameButton.setBounds(526, 33, 35, 35);
        pauseGameButton.setOpaque(false);
        pauseGameButton.setContentAreaFilled(false);
        pauseGameButton.setBorderPainted(false);
        pauseGameButton.addActionListener(e -> {
            this.setVisible(true);
            //TODO:new PauseScreen();
        });
        backgroundPanel.add(pauseGameButton);
    }
    private void setHelpButton() {
        helpButton = new JButton();
        helpButton.setBounds(468, 33, 35, 35);
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
        exitButton.setBounds(586, 35, 35, 35);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        backgroundPanel.add(exitButton);
    }

    private void initializeGrid() {
        // Scale the grid panel and cells based on the scale factor
        int scaledCellSize = (int) (baseCellSize * scaleFactor); // Calculate new cell size
        int gridPanelWidth = gridWidth * scaledCellSize;
        int gridPanelHeight = gridHeight * scaledCellSize;

        // Center the grid panel within the display
        int xOffset = 66; // Center horizontally
        int yOffset = 212; // Center vertically
        JPanel gridPanel = new JPanel(new GridLayout(gridHeight, gridWidth));
        gridPanel.setBounds(xOffset, yOffset, gridPanelWidth, gridPanelHeight); // Scale grid panel dimensions
        gridPanel.setOpaque(false); // Transparent grid

        gridPanels = new JPanel[gridHeight][gridWidth];

        // Initialize each cell with the new scaled size
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(scaledCellSize, scaledCellSize)); // Update cell size
                //cell.setOpaque(false); // Transparent cells
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to cells
                cell.setLayout(new BorderLayout());

                gridPanels[y][x] = cell;
                gridPanel.add(cell);
            }
        }

        // Add the grid panel to the background panel
        backgroundPanel.add(gridPanel);
        revalidate();
        repaint();
    }

    private void updateGridFromEnvironment(Entity[][] map) {
        // Clear all cells first
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                JPanel cell = gridPanels[y][x];
                cell.removeAll(); // Clear any existing content
            }
        }

        // Update cells based on the map from GridEnvironment
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                Entity entity = map[x][y]; // Get the entity at this position
                if (entity != null) {
                    JLabel entityLabel = createEntityLabel(entity);
                    gridPanels[y][x].add(entityLabel, BorderLayout.CENTER);
                }
            }
        }

        // Refresh the grid display
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                gridPanels[y][x].revalidate();
                gridPanels[y][x].repaint();
            }
        }
    }

    private JLabel createEntityLabel(Entity entity) {
        String imagePath = getEntityImagePath(entity);
        if (imagePath != null) {
            ImageIcon entityIcon = new ImageIcon(imagePath);
            Image scaledImage = entityIcon.getImage().getScaledInstance(
                    (int) (baseCellSize * scaleFactor), (int) (baseCellSize * scaleFactor), Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaledImage));
        }
        return new JLabel(); // Return empty label if no image path is found
    }

    private String getEntityImagePath(Entity entity) {
        // Determine the entity's image path based on its class and type
        if (entity instanceof Hero) {
            return "src/Images/ObjectImages/player.png";
        } else if (entity instanceof Monster) {
            MonsterTypes type = ((Monster) entity).getType();
            return switch (type) {
                case ArcherMonster -> "src/Images/ObjectImages/archer.png";
                case FighterMonster -> "src/Images/ObjectImages/fighter.png";
                case WizardMonster -> "src/Images/ObjectImages/wizard.png";
            };
        } else if (entity instanceof Obstacles) {
            Obstacles.ObstacleType type = ((Obstacles) entity).getType();
            return switch (type) {
                case SKULL -> "src/Images/ObjectImages/Skull.png";
                case STAIR -> "src/Images/ObjectImages/Stair.png";
                case RECTANGLE -> "src/Images/ObjectImages/Rectangle.png";
                case ONE_BOX -> "src/Images/ObjectImages/1Box.png";
                case TWO_BOX -> "src/Images/ObjectImages/2Box.png";
                case BARREL -> "src/Images/ObjectImages/Barrel.png";
                case CHEST -> "src/Images/ObjectImages/Chest.png";
                case POTION -> "src/Images/ObjectImages/Potion.png";
            };
        } else if (entity instanceof Enchantment) {
            EnchantmentTypes type = ((Enchantment) entity).getType();
            return switch (type) {
                case EXTRA_TIME_ENCHANTMENT -> "src/Images/ObjectImages/extra_time.png";
                case CLOAK_OF_PROTECTION_ENCHANTMENT -> "src/Images/ObjectImages/cloak.png";
                case REVEAL_ENCHANTMENT -> "src/Images/ObjectImages/reveal.png";
                case LURING_GEM_ENCHANTMENT -> "src/Images/ObjectImages/lure.png";
                case EXTRA_LIFE_ENCHANTMENT -> "src/Images/ObjectImages/extra_life.png";
            };
        }
        return null; // Return null if no image path is available
    }

    public static void main(String[] args) {
        PositionPoint heroPosition = new PositionPoint(0, 0);
        PositionPoint runePosition = new PositionPoint(24, 24);
        

        Hall hall = new Hall(HallTypes.EARTH);
        Rune rune = new Rune(runePosition, hall);
        hall.setRune(rune);

        new PlayModeScreen(HallTypes.EARTH, new GridEnvironment(heroPosition, runePosition, hall));
    }
}
