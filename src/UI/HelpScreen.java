package src.UI;

import java.awt.*;
import javax.swing.*;

import src.GameController.TimeController;

public class HelpScreen extends UIScreen {
    private JButton BackGameButton;
    private JPanel leftPanel;
    private UIScreen previous_Screen;

    public HelpScreen(UIScreen previous_Screen) {
        super(1200, 900, "Help", "src/Images/BackgroundImages/helpbackground.png");
        initializeComponents();
        setVisible(true);
       this.previous_Screen= previous_Screen;
    }

    private void initializeComponents() {
        setBackgroundImage();
        backgroundPanel.setLayout(new BorderLayout());
        setupLeftPanel();
        JScrollPane scrollableLeftPanel = new JScrollPane(leftPanel);
        scrollableLeftPanel.setOpaque(false);
        scrollableLeftPanel.getViewport().setOpaque(false);
        scrollableLeftPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 80, 0));
        scrollableLeftPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableLeftPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollableLeftPanel.getVerticalScrollBar().setUnitIncrement(30);

        
        backgroundPanel.add(scrollableLeftPanel, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> scrollableLeftPanel.getVerticalScrollBar().setValue(0));

        setBackButton();
    }

    private void setupLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false); 
    
        
        leftPanel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150)); 
    
        
        
        addObjectToLeftPanel("src/Images/ObjectImages/player.png", "Player",
                "The character is controlled by the player. The hero’s main goal is 'escaping the dungeon'. " +
                        "The player navigates the hero by pressing the arrow keys on the keyboard. The player " +
                        "should avoid the monsters and find the runes to open the hall’s doors.");

        addObjectToLeftPanel("", "Time",
                        "For each hall, the player has a time limit to pass the dungeon."+
                        "If the timer expires, the game is over.");

        addObjectToLeftPanel("src/Images/ObjectImages/exit1.png", "Exit Button",
                        "This button closes the game windows and opens the main menu screen. ");

        addObjectToLeftPanel("src/Images/ObjectImages/pause1.png", "Pause Button",
                        "This button pauses the game.");

        addObjectToLeftPanel("src/Images/ObjectImages/play1.png", "Resume Button",
                        "This button resumes the paused game.");

        addObjectToLeftPanel("src/Images/ObjectImages/heart.png", "Lives",
                "The hero has 3 lives at the beginning of the game. If the hero loses all their lives, the " +
                    "game is over. The hero can collect extra lives during the game. The hero's lives are displayed " +
                        "on the right side of the game." );

        addObjectToLeftPanel("src/Images/ObjectImages/Inventory.png", "Inventory",
            "Inventory displays the collected enchantments and their counts.");

        addTitleToLeftPanel("MONSTERS");

        addObjectToLeftPanel("", "",
        "3 different types of monsters get spawned in the halls every 8 seconds (The type of the "+
            "spawned monster is random.). They are trying to prevent the hero’s escape from the dungeon "+
                "by attacking the hero." );

        addObjectToLeftPanel("src/Images/ObjectImages/archer.png", "Archer Monster",
                "Type of an enemy that can shoot arrows within the range of 4 grids. It cannot detect " +
                        "the player if the player uses a cloak of protection.");
        addObjectToLeftPanel("src/Images/ObjectImages/wizard.png", "Wizard Monster",
                "Type of an enemy that can teleport the rune to a random location every 5 seconds.");
        addObjectToLeftPanel("src/Images/ObjectImages/fighter.png", "Fighter Monster",
                "Type of an enemy that can only attack the player if the player is next to the monster. " +
                        "It can be distracted by luring gems.");
    
       
        addTitleToLeftPanel("ENCHANTMENTS");
        addObjectToLeftPanel("", "",
            "Enchantments appear every 12 seconds in a random location. The player can collect "+
                "these enchantments by left-clicking on them. If the player does not collect the enchantments "+
                    "in 6 seconds, they disappear. They can be collected from any grid; the player does not need to "+
                        "be next to enchantments to collect them.");
        addObjectToLeftPanel("src/Images/ObjectImages/reveal.png", "Reveal",
                "To use this enchantment, the player must press the “R” button on the keyboard after " +
                        "collecting it. The “reveal” enchantment shows a rectangle size of 4x4 where the rune is hidden.");
        addObjectToLeftPanel("src/Images/ObjectImages/cloak.png", "Cloak of Protection",
                "To use this enchantment, the player must press the “P” button on the keyboard after " +
                        "collecting it. When the player uses the “cloak of protection” enchantment, the hero cannot be " +
                        "seen by the archer monster for 20 seconds.");
        addObjectToLeftPanel("src/Images/ObjectImages/lure.png", "Luring Gem",
                "To use this enchantment, the player needs to press the “B” button and then one of the " +
                        "following buttons “W”, “A”, “S” or “D” to select which direction to throw the lure after " +
                        "collecting it. The “Luring gem” enchantment is used to fool the fighter monster. The fighter " +
                        "monster follows the gem.");
        addObjectToLeftPanel("src/Images/ObjectImages/extra_life.png", "Extra Life",
                "This enchantment increases the hero’s lives by one. Like the extra time enchantment, the addition " +
                        "of the extra life happens the moment it is collected.");
        addObjectToLeftPanel("src/Images/ObjectImages/extra_time.png", "Extra Time",
                "When the user collects an 'extra life' enchantment, the hero’s lives are increased by 1.");

        addTitleToLeftPanel("BUILD MODE");

        addObjectToLeftPanel("", "",
            "The game starts in the build mode (after play a new game is clicked from the main menu). In "+
                "build mode, player designs the insides of the halls by placing objects. The minimum criteria "+
                    "for each hall is as follows:\r\n" +
                        "● There must be at least 6 objects in the earth hall.\r\n" + 
                        "● There must be at least 9 objects in the air hall. \r\n" +
                        "● There must be at least 13 objects in the water hall. \r\n" +
                        "● There must be at least 17 objects in the fire hall. \r");

        addTitleToLeftPanel("GAMEPLAY");

        addObjectToLeftPanel("", "",
            "The hero starts the game in the first hall of the dungeon. The hero’s main goal is to "+
                "escape from the dungeon by passing through 4 halls. The hero passes through the halls in the given order: Hall of "+
                    "Earth, Hall of Air, Hall of Water, and Hall of Fire. When the hero passes through the Hall of " +
                        "Fire the player wins the game.");
    }
    

    private void addObjectToLeftPanel(String imagePath, String name, String description) {
        
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setOpaque(false); 
    
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false); 
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
    
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        imageLabel.setIcon(icon);
        itemPanel.add(imageLabel, BorderLayout.WEST);
    
       
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(50, 25, 10, 50)); 
    
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
        nameLabel.setForeground(Color.BLACK); 
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFocusable(false);
        textPanel.add(nameLabel);
    
        
        JTextArea descriptionLabel = new JTextArea(description);
        descriptionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        descriptionLabel.setForeground(Color.BLACK); 
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setWrapStyleWord(true);
        descriptionLabel.setOpaque(false); 
        descriptionLabel.setEditable(false);
        descriptionLabel.setFocusable(false);
    
        
        descriptionLabel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        textPanel.add(descriptionLabel);
    
        itemPanel.add(textPanel, BorderLayout.CENTER);
    
        
        leftPanel.add(itemPanel);
    
        
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void setBackButton() {
        BackGameButton = new JButton("BACK");
        BackGameButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        BackGameButton.addActionListener(e -> {
            this.dispose();
            if (previous_Screen instanceof PlayModeScreen) {
                ((PlayModeScreen) previous_Screen).resumeGame();
                //((PlayModeScreen) previous_Screen).setVisible(true);
            }
            else{
                this.previous_Screen.setVisible(true);
            }          
        });

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); 
        buttonPanel.add(BackGameButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0)); 
        buttonPanel.add(BackGameButton, BorderLayout.CENTER);
    }

    private void addTitleToLeftPanel(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40)); 
        titleLabel.setForeground(Color.RED); 
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        titleLabel.setFocusable(false);
    
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}
