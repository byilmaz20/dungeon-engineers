package src.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import static java.lang.Math.abs;
import javax.swing.*;

import src.GameController.BuildModeController;
import src.GameController.GameFlowController;
import src.GameController.GameModeController;
import src.GameController.ITimeControllers;
import src.GameController.SaveGameController;
import src.GameController.TimeController;
import src.GameObjects.*;
import src.GameObjects.Obstacles.ObstacleType;
import src.Mechanics.Direction;
import src.Mechanics.Direction.DirectionEnum;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;
import src.Mechanics.SoundManager;

public class PlayModeScreen extends UIScreen implements KeyListener{
    private final int gridWidth = 25; // Number of columns
    private final int gridHeight = 25; // Number of rows
    private final int baseCellSize = 25; // Base size of each cell
    private final double scaleFactor = 0.558999993; // Scale factor for resizing the grid
    private JPanel[][] gridPanels; // Panels for each grid cell
    private boolean isPaused = false;
    private boolean bPressed = false;

    private JButton pauseGameButton;
    private JButton helpButton;
    private JButton exitButton;
    private HallTypes hallType;
    private ImageIcon hallimage;
    private Timer timer;
    private double remainingTime;
    private TimeController timeController;
    private JLabel timeLabel;
    private JPanel lifePanel;
    private JPanel inventoryPanel;
    private JPanel[][] tintPanels = new JPanel[gridHeight][gridWidth]; // Array to hold tints for each cell
    private SoundManager buttonClickSound;
    private SoundManager buttonMoveSound;
    private SoundManager buttonCollectEnchantmentSound;
    private SoundManager applyEnchantment;
    private SoundManager DoorOpenSound;
    private MP3PLAYER remainingTimeSound;

    private GridEnvironment gridEnvironment; // Reference to the GridEnvironment



    public PlayModeScreen(GridEnvironment gridEnvironment, TimeController timeController) {

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
        timeController.startTimeController();
        this.remainingTime = this.timer.getRemainingTime();

        updateTime(remainingTime);
        setVisible(true);
        buttonClickSound = new SoundManager("src/voices/clickbutton.wav");
        buttonMoveSound = new SoundManager("src/voices/move.wav");
        buttonCollectEnchantmentSound = new SoundManager("src/voices/CollectEnchantment.wav");
        applyEnchantment = new SoundManager("src/voices/applyenchantment.wav");
        DoorOpenSound = new SoundManager("src/voices/door.wav");
        remainingTimeSound=  new MP3PLAYER("src/voices/runningtime.mp3");
    }
    public void applyRedTint(PositionPoint topLeft, boolean highlightActive) {
    // Clear all previous tints
    clearAllTints();

    if (highlightActive) {
        // Highlight the 4x4 square
        for (int y = topLeft.y; y < topLeft.y + 4; y++) {
            for (int x = topLeft.x; x < topLeft.x + 4; x++) {
                // Ensure the cell is within bounds
                if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight) {
                    // Create a red transparent panel if it doesn't exist
                    if (tintPanels[y][x] == null) {
                        JPanel tintPanel = new JPanel();
                        tintPanel.setOpaque(true);
                        tintPanel.setBackground(new Color(255, 0, 0, 100)); // Semi-transparent red
                        tintPanel.setBounds(gridPanels[y][x].getBounds()); // Match the position and size of the cell
                        tintPanels[y][x] = tintPanel;

                        // Add the tint panel on top of the cell
                        gridPanels[y][x].add(tintPanel, BorderLayout.CENTER);
                        gridPanels[y][x].revalidate();
                        gridPanels[y][x].repaint();
                    }
                }
            }
        }
    }
}
private void clearAllTints() {
    for (int y = 0; y < gridHeight; y++) {
        for (int x = 0; x < gridWidth; x++) {
            if (tintPanels[y][x] != null) {
                gridPanels[y][x].remove(tintPanels[y][x]); // Remove the tint panel
                tintPanels[y][x] = null;
                gridPanels[y][x].revalidate();
                gridPanels[y][x].repaint();
            }
        }
    }
}

    
    
    private void initializeComponents() {
        setBackgroundImage();
        setHallTypeImage();
        setPauseGameButton();
        setHelpButton();
        setExitButton();
        setTimeDisplay();
        setLifeCountDisplay();
        setInventoryDisplay();
        initializeInventoryChangeListener();

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
                
        Icon resumeIcon = new ImageIcon(new ImageIcon("src/Images/ObjectImages/resume5.png")
                .getImage().getScaledInstance(77, 77, Image.SCALE_SMOOTH)); // Resume ikonu
        Icon pauseIcon = new ImageIcon(new ImageIcon("src/Images/ObjectImages/pause5.png")
        .getImage().getScaledInstance(77, 77, Image.SCALE_SMOOTH)); // Pause ikonu
        pauseGameButton.setIcon(pauseIcon); // İlk başta pause ikonunu göster
    
        pauseGameButton.addActionListener(e -> {
            buttonClickSound.playSound();
            if (!isPaused) {
                pauseGame();
            } else {
                resumeGame();
            }
        });
    
        backgroundPanel.add(pauseGameButton); // Butonu arayüze ekle
    }

    private void pauseGame(){
        Icon resumeIcon = new ImageIcon(new ImageIcon("src/Images/ObjectImages/resume5.png")
                .getImage().getScaledInstance(77, 77, Image.SCALE_SMOOTH)); // Resume ikonu
        for (ITimeControllers timeController : gridEnvironment.getTimeControllers()) {
            timeController.pressPauseButton();
        }
        pauseGameButton.setIcon(resumeIcon);
        isPaused = true;
    }
    
    public void resumeGame() {
        Icon pauseIcon = new ImageIcon(new ImageIcon("src/Images/ObjectImages/pause5.png")
                .getImage().getScaledInstance(77, 77, Image.SCALE_SMOOTH)); // Pause icon
    
        for (ITimeControllers timeController : gridEnvironment.getTimeControllers()) {
            timeController.pressPauseButton(); // Resume timers
        }
    
        pauseGameButton.setIcon(pauseIcon); // Update button icon
        isPaused = false; // Update state
        this.requestFocusInWindow();
        this.setFocusable(true);
        this.requestFocus();
        if (getKeyListeners().length == 0) {
            this.addKeyListener(this); // Reattach KeyListener if missing
        }
        //System.out.println(timeController.getTimer().getRemainingTime() + " is left!");
        timeController.setInitialTime(timeController.getTimer().getRemainingTime());
    }
    
    
    private void setLifeCountDisplay() {
        lifePanel = new JPanel();
        //lifePanel.setBounds(970, 250, 150, 50); 
        lifePanel.setBounds(830, 250, 250, 50); 
        lifePanel.setOpaque(false); 
        lifePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Kalpler yatay sıralanır
        backgroundPanel.add(lifePanel);
    
        updateLifeCount(gridEnvironment.getHero().getLives());
    }


        
    private void setHelpButton() {
        helpButton = new JButton();
        helpButton.setBounds(827, 37, 77, 77);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
    
        // Resim eklemek için ImageIcon kullanımı
        ImageIcon helpIcon = new ImageIcon("src/Images/ObjectImages/help5.png"); // Resminizin dosya yolunu buraya yazın
        Image scaledImage = helpIcon.getImage().getScaledInstance(77, 77, Image.SCALE_SMOOTH); // Buton boyutuna göre resmi ölçeklendir
        helpButton.setIcon(new ImageIcon(scaledImage));
    
        helpButton.addActionListener(e -> {
            buttonClickSound.playSound();
            this.setVisible(false);
            // time pause olmalı
            if (!isPaused) pauseGame();
            new HelpScreen(this);
        });
    
        backgroundPanel.add(helpButton);
    }
    
    private void setExitButton() {
        exitButton = new JButton();
        exitButton.setBounds(1045, 37, 77, 77);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
    
        // Resim eklemek için ImageIcon kullanımı
        ImageIcon exitIcon = new ImageIcon("src/Images/ObjectImages/exit5.png"); // Resminizin dosya yolunu buraya yazın
        Image scaledImage = exitIcon.getImage().getScaledInstance(77, 77, Image.SCALE_SMOOTH); // Buton boyutuna göre resmi ölçeklendir
        exitButton.setIcon(new ImageIcon(scaledImage));
    
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClickSound.playSound();
                if (!isPaused) pauseGame();
                int response = JOptionPane.showConfirmDialog(null, "Do you want to save the game before exiting?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                buttonClickSound.playSound();
                if (response == JOptionPane.NO_OPTION) {
                    buttonClickSound.playSound();
                    System.exit(0);
                } else if (response == JOptionPane.YES_OPTION) {
                    String saveName = JOptionPane.showInputDialog(null, "Enter a name for your save file:", "Save Game", JOptionPane.PLAIN_MESSAGE);
                    buttonClickSound.playSound();
                    if (saveName != null && !saveName.trim().isEmpty()) {
                        String saveDir = "Saves";
                        File saveFolder = new File(saveDir);
                        if (!saveFolder.exists()) saveFolder.mkdirs();
                        String filePath = saveDir + File.separator + saveName + ".dat";
                        try (FileOutputStream fileOut = new FileOutputStream(filePath);
                             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                            for (ITimeControllers timeController : gridEnvironment.getTimeControllers()) {
                                timeController.disposeTimer();
                            }
    
                            SaveGameController sgc = new SaveGameController();
    
                            out.writeObject(sgc.getCurrentHall());
                            out.writeObject(sgc.getCurrentHallIndex());
                            out.writeObject(BuildModeController.Halls);
                            out.writeObject(sgc.getPlayModeController().getGrid().getHero());
    
                            // Ensure other objects are serializable before uncommenting
                            // out.writeObject(sgc.getGridEnvironment());
                            // out.writeObject(sgc.getPlayModeScreen());
                            // out.writeObject(sgc.getTimeController());
                            JOptionPane.showMessageDialog(null, "Game saved successfully as \"" + saveName + "\".", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Failed to save the game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // User clicked cancel or entered an invalid name, resume the game
                        resumeGame();
                    }
                } else {
                    // User clicked cancel on the confirm dialog, resume the game
                    resumeGame();
                }
            }
        });
    
        backgroundPanel.add(exitButton);
    }
    
    
    private void setInventoryDisplay() {
    inventoryPanel = new JPanel();
    inventoryPanel.setBounds(868, 480, 220, 100); // Shifted panel slightly downwards
    inventoryPanel.setOpaque(false); 
    inventoryPanel.setLayout(new GridLayout(1, 3, -56, 50)); // Further increased vertical gap

    updateInventoryDisplay(); // Populate inventory items initially

    backgroundPanel.add(inventoryPanel); // Add inventory panel to the background
}

private void updateInventoryDisplay() {
    inventoryPanel.removeAll(); // Clear the inventory panel for updates

    // Icon paths for enchantments
    String cloakIconPath = "src/Images/ObjectImages/cloak.png";
    String revealIconPath = "src/Images/ObjectImages/reveal.png";
    String lureIconPath = "src/Images/ObjectImages/lure.png";

    // Display cloak enchantment with quantity
    addEnchantmentToInventoryDisplay(cloakIconPath, gridEnvironment.getHero().getInventory().getQuantity(EnchantmentTypes.CLOAK_OF_PROTECTION_ENCHANTMENT));

    // Display reveal enchantment with quantity
    addEnchantmentToInventoryDisplay(revealIconPath, gridEnvironment.getHero().getInventory().getQuantity(EnchantmentTypes.REVEAL_ENCHANTMENT));

    // Display lure enchantment with quantity
    addEnchantmentToInventoryDisplay(lureIconPath, gridEnvironment.getHero().getInventory().getQuantity(EnchantmentTypes.LURING_GEM_ENCHANTMENT));

    inventoryPanel.revalidate(); // Refresh the panel
    inventoryPanel.repaint();    // Update the UI
}

private void addEnchantmentToInventoryDisplay(String iconPath, int quantity) {
    JPanel enchantmentPanel = new JPanel();
    enchantmentPanel.setLayout(new BorderLayout()); // Arrange icon and quantity vertically
    enchantmentPanel.setOpaque(false); // Transparent background
    enchantmentPanel.setBorder(BorderFactory.createEmptyBorder(-10, 5, 20, 5)); // Slightly adjusted bottom padding

    ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    JLabel iconLabel = new JLabel(icon); // Icon for enchantment
    JLabel quantityLabel = new JLabel("x" + quantity); // Quantity label
    quantityLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Styling the quantity text
    quantityLabel.setForeground(Color.BLACK); // Text color
    quantityLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align quantity

    enchantmentPanel.add(iconLabel, BorderLayout.CENTER); // Add icon to the center
    enchantmentPanel.add(quantityLabel, BorderLayout.SOUTH); // Add quantity below the icon

    inventoryPanel.add(enchantmentPanel); // Add the enchantment panel to the inventory panel
}
// Ensure inventory updates dynamically
private void initializeInventoryChangeListener() {
    gridEnvironment.getHero().getInventory().setInventoryChangeListener(() -> {
        SwingUtilities.invokeLater(this::updateInventoryDisplay); // Safely update the UI on the Event Dispatch Thread
    });
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
    

private void setTimeDisplay() {
    timeLabel = new JLabel("" + (int) remainingTime);
    timeLabel.setBounds(1070, 185, 150, 50); 
    timeLabel.setFont(new Font("Arial", Font.BOLD, 50)); 
    timeLabel.setForeground(Color.decode("#262b2d")); // Varsayılan renk
    timeLabel.setOpaque(false); 
    backgroundPanel.add(timeLabel);
}


private void updateTime(double remainingTime) { 
    this.remainingTime = remainingTime;

    if (!isPaused) {
        timeLabel.setText("" + (int) remainingTime); // Metni güncelle
    }

    if (remainingTime <= 0) {
        this.dispose();
        pauseGame();
    }

    // 10 ile 1 arasında kaldığında metni kırmızıya çevir, diğer durumlarda varsayılan renge dön
    if (remainingTime <= 10 && remainingTime > 0) {
        timeLabel.setForeground(Color.decode("#B22222")); //borda gibi bir renk 
        remainingTimeSound.playSound();
    } else {
        timeLabel.setForeground(Color.decode("#262b2d")); // Varsayılan renk
    }
}

    private void updateLifeCount(int lifeCount) {
        lifePanel.removeAll(); 
        ImageIcon heartIcon = new ImageIcon(new ImageIcon("src/Images/ObjectImages/heart.png")
                .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)); 

        for (int i = 0; i < lifeCount; i++) {
            JLabel heartLabel = new JLabel(heartIcon);
            lifePanel.add(heartLabel); 
        }
        lifePanel.revalidate(); 
        lifePanel.repaint(); 
        if (gridEnvironment.getHero().getLives() <= 0) {
            GameFlowController.endGame("src/Images/BackgroundImages/gameover.png","No lives remaining!");
            this.dispose();
            pauseGame();
            
}
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
                15, 
                15, 
                Image.SCALE_SMOOTH 
        );
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
        buttonMoveSound.playSound();
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                //System.out.println("Left key pressed");
                buttonMoveSound.playSound();
                gridEnvironment.moveEntity(gridEnvironment.hero, new Direction(DirectionEnum.LEFT));
                
                break;
            case KeyEvent.VK_RIGHT:
                buttonMoveSound.playSound();
                gridEnvironment.moveEntity(gridEnvironment.hero, new Direction(DirectionEnum.RIGHT));
                
                break;
            case KeyEvent.VK_UP:
                buttonMoveSound.playSound();
                gridEnvironment.moveEntity(gridEnvironment.hero, new Direction(DirectionEnum.UP));
                
                break;
            case KeyEvent.VK_DOWN:
                buttonMoveSound.playSound();
                gridEnvironment.moveEntity(gridEnvironment.hero, new Direction(DirectionEnum.DOWN));
                
                break;
            case KeyEvent.VK_P:
                if (gridEnvironment.getHero().getInventory().checkAvailability(EnchantmentTypes.CLOAK_OF_PROTECTION_ENCHANTMENT)){
                    CloakOfProtectionEnchantment cloak = new CloakOfProtectionEnchantment(null, null, gridEnvironment);
                    cloak.applyEffect();
                    applyEnchantment.playSound();
                    gridEnvironment.getHero().getInventory().remove(EnchantmentTypes.CLOAK_OF_PROTECTION_ENCHANTMENT);
                }
                
                break;
            case KeyEvent.VK_R:
                if (gridEnvironment.getHero().getInventory().checkAvailability(EnchantmentTypes.REVEAL_ENCHANTMENT)){
                    RevealEnchantment reveal = new RevealEnchantment(gridEnvironment.getHero().getPosition(),gridEnvironment.getHall(), gridEnvironment);
                    reveal.applyEffect(this);
                    applyEnchantment.playSound();
                    gridEnvironment.getHero().getInventory().remove(EnchantmentTypes.REVEAL_ENCHANTMENT);
                }
                break;
            case KeyEvent.VK_B:
                if (gridEnvironment.getHero().getInventory().checkAvailability(EnchantmentTypes.LURING_GEM_ENCHANTMENT)){
                    bPressed = true;
                    applyEnchantment.playSound();///////fırlatma sesi olmalı
                    gridEnvironment.getHero().getInventory().remove(EnchantmentTypes.LURING_GEM_ENCHANTMENT);
                    
                }
                
                break;
            case KeyEvent.VK_W:
                if (bPressed){
                    LuringGemEnchantment lureW = new LuringGemEnchantment(null, null, gridEnvironment);
                    lureW.applyEffect(new Direction(DirectionEnum.UP));
                    bPressed = false;
                }
                break;
            case KeyEvent.VK_A:
                if (bPressed){
                    LuringGemEnchantment lureA = new LuringGemEnchantment(null, null, gridEnvironment);
                    lureA.applyEffect(new Direction(DirectionEnum.LEFT));
                    bPressed = false;
                }
                break;
            case KeyEvent.VK_S:
                if (bPressed){
                    LuringGemEnchantment lureS = new LuringGemEnchantment(null, null, gridEnvironment);
                    lureS.applyEffect(new Direction(DirectionEnum.DOWN));
                    bPressed = false;
                }
                break;
            case KeyEvent.VK_D:
                if (bPressed){
                    LuringGemEnchantment lureD = new LuringGemEnchantment(null, null, gridEnvironment);
                    lureD.applyEffect(new Direction(DirectionEnum.RIGHT));
                    bPressed = false;
                }
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
        
        if (x == gridEnvironment.rune.getPosition().x && y == gridEnvironment.rune.getPosition().y) {
            //System.out.println("Rune has been clicked: " + x + ", " + y);
            checkRuneFound();
        }
        if (gridEnvironment.map[x][y] instanceof Enchantment && !isPaused) {
            Enchantment enchantment = (Enchantment) gridEnvironment.map[x][y];
            String mode = GameModeController.getInstance().getGameMode();
            if (mode.equals("easy")) {
                if (enchantment.getType() == EnchantmentTypes.EXTRA_LIFE_ENCHANTMENT && gridEnvironment.getHero().getLives() < 5) {
                    enchantment.applyEffect();
                    applyEnchantment.playSound();
                    System.out.println("Extra Life Enchantment has been clicked: " + x + ", " + y);
                
                }
            }
            else if (mode.equals("hard")) {
                if (enchantment.getType() == EnchantmentTypes.EXTRA_LIFE_ENCHANTMENT && gridEnvironment.getHero().getLives() < 3) {
                    enchantment.applyEffect();
                    applyEnchantment.playSound();
                    System.out.println("Extra Life Enchantment has been clicked: " + x + ", " + y);
                    
                }
            }
            
            if (enchantment.getType() == EnchantmentTypes.EXTRA_TIME_ENCHANTMENT) {
                enchantment.applyEffect();
                applyEnchantment.playSound();
                System.out.println("Extra Time Enchantment has been clicked: " + x + ", " + y);
                
            }
            else {
                System.out.println("Enchantment has been clicked: " + x + ", " + y);
                buttonCollectEnchantmentSound.playSound();
                gridEnvironment.getHero().getInventory().add(enchantment);
            }
            
            gridEnvironment.removeEntity(enchantment);

        }

    }
    public boolean checkRuneFound() {
        boolean isAdjacent = false;
        if ((abs(gridEnvironment.hero.position.x - gridEnvironment.rune.position.x) == 1 && 
                gridEnvironment.hero.position.y == gridEnvironment.rune.position.y) || 
            (abs(gridEnvironment.hero.position.y - gridEnvironment.rune.position.y) == 1 && 
                gridEnvironment.hero.position.x == gridEnvironment.rune.position.x)) {
            DoorOpenSound.playSound();
            isAdjacent = true;
        }
   
        if (isAdjacent && !isPaused){
            gridEnvironment.rune.found();
            
            return true;
        }
        return false;
    }
    
    public TimeController getTimeController() {
        // TODO Auto-generated method stub
        return this.timeController;
    }
    
}