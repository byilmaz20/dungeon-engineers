package src.UI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import src.GameObjects.*;
import src.Mechanics.Direction;
import src.Mechanics.Direction.DirectionEnum;
import src.Mechanics.GridEnvironment;
import src.Mechanics.Timer;
import src.GameController.TimeController;

public class PlayModeScreen extends UIScreen implements KeyListener{
    private final int gridWidth = 25; // Number of columns
    private final int gridHeight = 25; // Number of rows
    private final int baseCellSize = 25; // Base size of each cell
    private final double scaleFactor = 0.558999993; // Scale factor for resizing the grid
    private JPanel[][] gridPanels; // Panels for each grid cell

    private JButton pauseGameButton;
    private JButton helpButton;
    private JButton exitButton;
    private HallTypes hallType;
    private ImageIcon hallimage;
    private Timer timer;
    private double remainingTime;
    private TimeController timeController;
    private JLabel timeLabel;


    private GridEnvironment gridEnvironment; // Reference to the GridEnvironment


    public PlayModeScreen(GridEnvironment gridEnvironment, TimeController timeController) {
        super(650, 650, "Play Mode Screen", 
        "src/Images/BackgroundImages/HALL.png");
            timer = timeController.getTimer();
            this.timer = timer;
            this.hallType = gridEnvironment.getHall().hallType;
            this.gridEnvironment = gridEnvironment;
            
            

            // Set up grid listener to update UI on grid changes
            this.gridEnvironment.setGridChangeListener(this::updateGridFromEnvironment);
            this.timer.setTimeChangeListener(this::updateTime);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            initializeComponents();
            initializeGrid();
            updateGridFromEnvironment(gridEnvironment.getMap()); // Initialize grid with current map
            updateTime(timer.getRemainingTime());

            // Ensure the component is focusable and has focus
            setFocusable(true);
            requestFocusInWindow();

            addKeyListener(this);

            setVisible(true);
            System.out.println("Play Mode Screen Initialized");
            timeController.startGame();

    }
    
    

    private void initializeComponents() {
        setBackgroundImage();
        setHallTypeImage();
        setPauseGameButton();
        setHelpButton();
        setExitButton();
        setTimeDisplay();
        
    }
    private void setHallTypeImage() {
        //add hall type image to the screen
        String path = "";
        switch (this.hallType) {
            case AIR -> path = "src/Images/BackgroundImages/air.png";
            case EARTH -> path = "src/Images/BackgroundImages/earth.png";
            case FIRE -> path = "src/Images/BackgroundImages/fire.png";
            case WATER -> path = "src/Images/BackgroundImages/water.png";
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
        pauseGameButton.setBounds(506, 30, 48, 45); // Butonun boyutlarını ve pozisyonunu ayarla
        pauseGameButton.setOpaque(false);
        pauseGameButton.setContentAreaFilled(false);
        pauseGameButton.setBorderPainted(false);
    
        // Pause ve resume ikonlarını yükle
        Icon pauseIcon = new ImageIcon(new ImageIcon("src/Images/ObjectImages/pauseIcon.png")
                .getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)); // Pause ikonu
                
        Icon resumeIcon = new ImageIcon(new ImageIcon("src/Images/ObjectImages/ResumeIcon.png")
                .getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)); // Resume ikonu
    
        pauseGameButton.setIcon(pauseIcon); // İlk başta pause ikonunu göster
    
        pauseGameButton.addActionListener(e -> {
            if (pauseGameButton.getIcon().equals(pauseIcon)) {
                pauseGameButton.setIcon(resumeIcon); // Resume ikonuna geçiş yap
                timer.pauseTimer(); // Oyunu duraklat
                System.out.println("Game Paused!");
            } else {
                // Resume butonuna tıklandığında
                pauseGameButton.setIcon(pauseIcon); // Pause ikonuna geri dön
                timer.resumeTimer(); // Oyunu devam ettir
                System.out.println("Game Resumed!"); 
                //resumeGame();
            }
        });
    
        backgroundPanel.add(pauseGameButton); // Butonu arayüze ekle
    }
    
    private void setTimeDisplay() {
        timeLabel = new JLabel("" + remainingTime);
        timeLabel.setBounds(530, 180, 150, 50); // Adjust size and position as needed
        timeLabel.setFont(new Font("Arial", Font.BOLD, 50)); // Set custom font
        timeLabel.setForeground(Color.BLACK); // Set text color
        timeLabel.setOpaque(false); // Allow background color
        backgroundPanel.add(timeLabel);
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
        int scaledCellSize = (int) (baseCellSize * scaleFactor); // Calculate new cell size
        int gridPanelWidth = gridWidth * scaledCellSize + 10; // Add a bit more width for the increased push
        int gridPanelHeight = gridHeight * scaledCellSize;
    
        // Center the grid panel within the display
        int xOffset = 64; // Adjust the horizontal center slightly for the shifts
        int yOffset = 211; // Center vertically
    
        JPanel gridPanel = new JPanel(null); // Use null layout for custom positioning
        gridPanel.setBounds(xOffset, yOffset, gridPanelWidth, gridPanelHeight);
        gridPanel.setOpaque(false); // Transparent grid
    
        gridPanels = new JPanel[gridHeight][gridWidth];
    
        int baseShift = 2; // A subtle base shift applied uniformly across the grid
        int maxShift = 6; // Maximum shift for cells at the far right
    
        // Initialize each cell with the updated shifts
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                JPanel cell = new JPanel();
                cell.setOpaque(false); // Make the cell transparent
                cell.setBorder(null);  // Remove the border to avoid visible separation
                cell.setLayout(new BorderLayout());
    
                // Calculate gradient shift
                int shift;
                if (x < gridWidth / 2) {
                    shift = baseShift - 1; // Slight negative shift for the left side
                } else {
                    // Gradual positive shift for the right side
                    double factor = (double) (x - gridWidth / 2) / (gridWidth / 2); // Normalized position (0 to 1)
                    shift = baseShift + (int) (maxShift * factor); // Base shift + scaled shift
                }
    
                int cellX = x * scaledCellSize + shift;
                int cellY = y * scaledCellSize;
    
                cell.setBounds(cellX, cellY, scaledCellSize, scaledCellSize);
    
                // Attach MouseListener to each cell
                final int cellXIndex = x;
                final int cellYIndex = y;
                cell.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleCellClick(cellXIndex, cellYIndex);
                    }
                });
    
                gridPanels[y][x] = cell;
                gridPanel.add(cell);
            }
        }
    
        // Add the grid panel to the background panel
        backgroundPanel.add(gridPanel);
        revalidate();
        repaint();
    }
    

    private void updateTime(double remainingTime) { //TODO text eklenecek
        this.remainingTime = remainingTime;
        System.out.println("Time updated: " + remainingTime);
        timeLabel.setText("" + (int) remainingTime);
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
                if (entity instanceof Rune) {
                    System.out.println("Rune found at position: " + x + ", " + y);
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
        } else if (entity instanceof Rune) {
            //TODO: need to add rune image
            return "src/Images/ObjectImages/lure.png";
        }
        return null; // Return null if no image path is available
    }
    @Override
    public boolean isFocusable() {
        return true;
    }
        @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                System.out.println("Left key pressed");
                gridEnvironment.moveEntity(gridEnvironment.hero, new Direction(DirectionEnum.LEFT));
                break;
            case KeyEvent.VK_RIGHT:
                gridEnvironment.moveEntity(gridEnvironment.hero, new Direction(DirectionEnum.RIGHT));
                break;
            case KeyEvent.VK_UP:
                gridEnvironment.moveEntity(gridEnvironment.hero, new Direction(DirectionEnum.UP));
                break;
            case KeyEvent.VK_DOWN:
                gridEnvironment.moveEntity(gridEnvironment.hero, new Direction(DirectionEnum.DOWN));
                break;
            default:
                System.out.println("Unhandled Key: " + keyCode);
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // Do nothing
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    private void handleCellClick(int x, int y) {        
        if (gridEnvironment.map[x][y] instanceof Rune) {
            System.out.println("Rune has been clicked: " + x + ", " + y);
            gridEnvironment.checkRuneFound();
        } 
    }
    public void resumeGame(){
        this.dispose();
        new PlayModeScreen(gridEnvironment, timeController);
    }
    
}