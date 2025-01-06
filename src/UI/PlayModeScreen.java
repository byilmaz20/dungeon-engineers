package src.UI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import src.GameController.ITimeControllers;
import src.GameController.TimeController;
import src.GameObjects.*;
import src.GameObjects.Obstacles.ObstacleType;
import src.Mechanics.Direction;
import src.Mechanics.Direction.DirectionEnum;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

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
    private JLabel lifeLabel;


    private GridEnvironment gridEnvironment; // Reference to the GridEnvironment


    public PlayModeScreen(GridEnvironment gridEnvironment, TimeController timeController, double remainingTime) {

        super(1200, 900, "Play Mode Screen", 

        "src/Images/BackgroundImages/HALL.png");
        //650x650
        this.timeController = timeController;
        this.timer = this.timeController.getTimer();
        this.hallType = gridEnvironment.getHall().hallType;
        this.gridEnvironment = gridEnvironment;
        
        // Set up grid listener to update UI on grid changes
        this.gridEnvironment.setGridChangeListener(this::updateGridFromEnvironment);
        // setGridChangeListener is assigning updateGridFromEnvironment 
        // as the implementation of the onGridChanged method in the GridChangeListener interface.
        this.timer.setTimeChangeListener(this::updateTime);
        this.gridEnvironment.getHero().setLifeCountListener(this::updateLifeCount);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
        initializeGrid();
        populateInitialEntities(this.gridEnvironment.map);
        updateGridFromEnvironment(gridEnvironment.getMap()); // Initialize grid with current map
        

        // Ensure the component is focusable and has focus
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(this);

        setVisible(true);
        System.out.println("Play Mode Screen Initialized");
        timeController.startGame();
        updateTime(remainingTime);
    }
    
    
    private void initializeComponents() {
        setBackgroundImage();
        setHallTypeImage();
        setPauseGameButton();
        setHelpButton();
        setExitButton();
        setTimeDisplay();
        setLifeCountDisplay();
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
        label.setBounds(430, 1, 200, 150);
        this.add(label, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    private void setPauseGameButton() {
        pauseGameButton = new JButton();
        pauseGameButton.setBounds(937, 37, 77, 77); // Butonun boyutlarını ve pozisyonunu ayarla
        pauseGameButton.setOpaque(false);
        pauseGameButton.setContentAreaFilled(false);
        pauseGameButton.setBorderPainted(false);
    
        // Pause ve resume ikonlarını yükle
        Icon pauseIcon = new ImageIcon(new ImageIcon("src/Images/ObjectImages/pauseIcon.png")
                .getImage().getScaledInstance(77, 77, Image.SCALE_SMOOTH)); // Pause ikonu
                
        Icon resumeIcon = new ImageIcon(new ImageIcon("src/Images/ObjectImages/ResumeIcon.png")
                .getImage().getScaledInstance(77, 77, Image.SCALE_SMOOTH)); // Resume ikonu
    
        pauseGameButton.setIcon(pauseIcon); // İlk başta pause ikonunu göster
    
        pauseGameButton.addActionListener(e -> {
            if (pauseGameButton.getIcon().equals(pauseIcon)) {
                pauseGameButton.setIcon(resumeIcon); // Resume ikonuna geçiş yap
                System.out.println("Game Paused!");
            } else {
                // Resume butonuna tıklandığında
                pauseGameButton.setIcon(pauseIcon); // Pause ikonuna geri dön
                System.out.println("Game Resumed!"); 
                resumeGame();
            }
            for (ITimeControllers timeController : gridEnvironment.getTimeControllers()) {
                timeController.pressPauseButton();
            }
        });
    
        backgroundPanel.add(pauseGameButton); // Butonu arayüze ekle
    }
    
    private void setLifeCountDisplay() {
        lifeLabel = new JLabel("Lives: " + gridEnvironment.getHero().getLives());
        lifeLabel.setBounds(970, 250, 150, 50); 
        lifeLabel.setFont(new Font("Arial", Font.BOLD, 30)); 
        //lifeLabel.setForeground(Color.BLACK); 
        lifeLabel.setForeground(Color.decode("#262b2d"));
        lifeLabel.setOpaque(false); 
        backgroundPanel.add(lifeLabel); 
    }

    private void setTimeDisplay() {
        timeLabel = new JLabel("" + remainingTime);
        timeLabel.setBounds(900, 250, 150, 50); 
        timeLabel.setFont(new Font("Arial", Font.BOLD, 50)); 
        //timeLabel.setForeground(Color.BLACK); 
        timeLabel.setForeground(Color.decode("#262b2d"));
        timeLabel.setOpaque(false); 
        backgroundPanel.add(timeLabel);
    }
        
    private void setHelpButton() {
        helpButton = new JButton();
        helpButton.setBounds(827, 37, 77, 77);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
            helpButton.addActionListener(e -> {
            this.setVisible(false);
            //time pause olmalı 
            for (ITimeControllers timeController : gridEnvironment.getTimeControllers()) {
                timeController.pressPauseButton();
            }
            timeController.setIsPausedToHelp();
            new HelpScreen(this);
            //TODO: IF CALLED FROM THE MAIN SCREEN, ARRANGE IT
        });
        backgroundPanel.add(helpButton);
    }
    private void setExitButton() {
        exitButton = new JButton();
        exitButton.setBounds(1045, 37, 77, 77);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        backgroundPanel.add(exitButton);
    }

    private void oldinitializeGrid() {
        double scaledCellSize = 19.5;
        System.out.println("scaledCellSize: " + scaledCellSize);
        double gridPanelWidth = gridWidth * scaledCellSize + 10; // Add a bit more width for the increased push
        double gridPanelHeight = gridHeight * scaledCellSize;
    
        int xOffset = 170; 
        int yOffset = 290; 
    
        JPanel gridPanel = new JPanel(null); // Use null layout for custom positioning
        gridPanel.setBounds(xOffset, yOffset, (int) gridPanelWidth, (int) gridPanelHeight);

        gridPanel.setOpaque(true); 
    
        gridPanels = new JPanel[gridHeight][gridWidth];
    
        int baseShift = 2; // A subtle base shift applied uniformly across the grid
        int maxShift = 6; // Maximum shift for cells at the far right
    
        // Initialize each cell with the updated shifts
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                JPanel cell = new JPanel();
                cell.setOpaque(true); // Make the cell transparent
                cell.setBackground(new Color(240, 240, 240)); // Light background for visibility (optional)
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));  // Remove the border to avoid visible separation
                //cell.setBorder(null);
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
    
                double cellX = x * scaledCellSize + shift;
                double cellY = y * scaledCellSize;
    
                cell.setBounds((int) cellX, (int) cellY,  (int) scaledCellSize, (int) scaledCellSize);
    
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
    
    private void initializeGridnew() {
        double cellSize = 20; 
        int gridWidth = 25; 
        int gridHeight = 25; 
    
        double gridPanelWidth = gridWidth * cellSize; 
        double gridPanelHeight = gridHeight * cellSize;
    
        int xOffset = 170; 
        int yOffset = 287; 
    
        JPanel gridPanel = new JPanel(null); 
        gridPanel.setBounds(xOffset, yOffset, (int) Math.ceil(gridPanelWidth), (int) Math.ceil(gridPanelHeight));
        gridPanel.setOpaque(false); 
        gridPanel.setBackground(new Color(200, 200, 200)); 
    
        gridPanels = new JPanel[gridHeight][gridWidth];
    
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                JPanel cell = new JPanel();
                cell.setOpaque(false);
                cell.setBackground(new Color(240, 240, 240));
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                cell.setLayout(new BorderLayout());
    
                // Calculate precise position for each cell
                int cellX = (int) Math.round(x * cellSize);
                int cellY = (int) Math.round(y * cellSize);
    
                cell.setBounds(cellX, cellY, (int) Math.ceil(cellSize), (int) Math.ceil(cellSize));
    
                // Mouse listener for interactions
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
    
        // Add the grid panel to the background
        backgroundPanel.add(gridPanel);
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }
    
    private void initializeGrid() {
        double cellWidth = 20; // Width of each cell
        double cellHeight = 19.7; // Height of each cell
        int gridWidth = 25; // Number of columns
        int gridHeight = 25; // Number of rows
    
        double gridPanelWidth = gridWidth * cellWidth; 
        double gridPanelHeight = gridHeight * cellHeight;
    
        int xOffset = 170; 
        int yOffset = 290; 
    
        // Create a JPanel for the grid with null layout for manual positioning
        JPanel gridPanel = new JPanel(null); 
        gridPanel.setBounds(xOffset, yOffset, (int) Math.ceil(gridPanelWidth), (int) Math.ceil(gridPanelHeight));
        gridPanel.setOpaque(false); 
        gridPanel.setBackground(new Color(200, 200, 200)); 
    
        gridPanels = new JPanel[gridHeight][gridWidth];
    
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                JPanel cell = new JPanel();
                cell.setOpaque(false); // Transparent cell background
                cell.setBackground(new Color(240, 240, 240)); // Light background
                //cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Cell borders
                cell.setLayout(new BorderLayout());
    
                // Calculate precise position for each cell
                int cellX = (int) Math.round(x * cellWidth);
                int cellY = (int) Math.round(y * cellHeight);
    
                cell.setBounds(cellX, cellY, (int) Math.ceil(cellWidth), (int) Math.ceil(cellHeight));
    
                // Mouse listener for interactions
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
    
        // Add the grid panel to the background
        backgroundPanel.add(gridPanel);
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }
      
    private void populateInitialEntities(Entity[][] map) {
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                Entity entity = map[x][y];
                if (entity != null) {
                    JPanel cell = gridPanels[y][x];
                    JLabel entityLabel = createEntityLabel(entity);
                    cell.add(entityLabel, BorderLayout.CENTER);
                    cell.revalidate();
                    cell.repaint();
                }
            }
        }
    }

    private void updateTime(double remainingTime) { //TODO text eklenecek
        this.remainingTime = remainingTime;
        //System.out.println("Time updated: " + remainingTime);
        timeLabel.setText("" + (int) remainingTime);
    }

    private void updateLifeCount(int lifeCount) {
        lifeLabel.setText("Lives: " + lifeCount);
    }

    private void updateGridFromEnvironment(Entity[][] map, PositionPoint... changedPositions) {
        for (PositionPoint pos : changedPositions) {
            JPanel cell = gridPanels[pos.y][pos.x];
            cell.removeAll(); // Clear only this cell

            Entity entity = map[pos.x][pos.y];
            if (entity != null) {
                JLabel entityLabel = createEntityLabel(entity);
                cell.add(entityLabel, BorderLayout.CENTER);
            }

            cell.revalidate();
            cell.repaint();
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
            Obstacles obs = gridEnvironment.getRuneInObject();
            if (obs.getType()==ObstacleType.SKULL){return "src/Images/ObjectImages/Skull.png";}
            if (obs.getType()==ObstacleType.STAIR){return "src/Images/ObjectImages/Stair.png";}
            if (obs.getType()==ObstacleType.RECTANGLE){return "src/Images/ObjectImages/Rectangle.png";}
            if (obs.getType()==ObstacleType.ONE_BOX){return "src/Images/ObjectImages/1Box.png";}
            if (obs.getType()==ObstacleType.TWO_BOX){return "src/Images/ObjectImages/2Box.png";}
            if (obs.getType()==ObstacleType.BARREL){return "src/Images/ObjectImages/Barrel.png";}
            if (obs.getType()==ObstacleType.CHEST){return "src/Images/ObjectImages/Chest.png";}
            if (obs.getType()==ObstacleType.POTION){return "src/Images/ObjectImages/Potion.png";}
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
        //System.out.println("Key Pressed");
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
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
    public void resumeGame(){ //TODO
        this.dispose();
        System.out.println(this.timeController.getTimer().getRemainingTime() + " is left!");
        this.timeController.setInitializeTime(this.timeController.getTimer());
        new PlayModeScreen(this.gridEnvironment, this.timeController, this.timeController.getTimer().getRemainingTime());
    }
    public TimeController getTimeController() {
        // TODO Auto-generated method stub
        return this.timeController;
    }
    
}