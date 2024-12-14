package src.UI;

import javax.swing.*;
import java.awt.*;

public class HelpScreen extends UIScreen {
    private JButton BackGameButton;
    private JPanel leftPanel;

    public HelpScreen() {
        super(600, 600, "Help", "src/Images/BackgroundImages/helpbackground.png");
        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        setBackgroundImage();

    
    backgroundPanel.setLayout(new BorderLayout());

    
    setupLeftPanel();

    
    JScrollPane scrollableLeftPanel = new JScrollPane(leftPanel);
    scrollableLeftPanel.setOpaque(false);
    scrollableLeftPanel.getViewport().setOpaque(false);
    scrollableLeftPanel.setBorder(null); 
    scrollableLeftPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollableLeftPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


    
    backgroundPanel.add(scrollableLeftPanel, BorderLayout.CENTER);

    
    setBackButton();
    }

    private void setupLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(600, 1500));
        leftPanel.setOpaque(false); 

        
        addObjectToLeftPanel("src/Images/ObjectImages/player.png", "Player",
                "The character is controlled by the player. The hero’s main goal is 'escaping the dungeon'. " +
                        "The player navigates the hero by pressing the arrow keys on the keyboard. The player " +
                        "should avoid the monsters and find the runes to open the hall’s doors.");

        addObjectToLeftPanel("src/Images/ObjectImages/archer.png", "Archer",
                "Type of an enemy that can shoot arrows within the range of 4 grids. It cannot detect " +
                        "the player if the player uses a cloak of protection.");

        addObjectToLeftPanel("src/Images/ObjectImages/wizard.png", "Wizard",
                "Type of an enemy that can teleport the rune to a random location every 5 seconds. " );

        addObjectToLeftPanel("src/Images/ObjectImages/fighter.png", "Fighter",
                "Type of an enemy that can only attack the player if the player is next to the monster.  " +
                         "It can be distracted by luring gems.");

        addObjectToLeftPanel("src/Images/ObjectImages/reveal.png", "Reveal",
                "To use this enchantment, the player must press the “R” button on the keyboard after  "+
                    "collecting it. The “reveal” enchantment shows a rectangle size of 4x4 where the rune is"+
                        " hidden." );

        addObjectToLeftPanel("src/Images/ObjectImages/cloak.png", "Cloak of Protection",
                "To use this enchantment, the player must press the “P” button on the keyboard after "+
                "collecting it. When the player uses the “cloak of protection” enchantment, the hero cannot be "+
                "seen by the archer monster for 20 seconds." );

        addObjectToLeftPanel("src/Images/ObjectImages/lure.png", "Luring Gem",
                "To use this enchantment, the player needs to press the “B” button and then one of the"+
                "following buttons “W”, “A”, “S” or “D” to select which direction to throw the lure after "+
                "collecting it. The “Luring gem” enchantment is used to fool the fighter monster. The fighter "+
                "monster follows the gem." );

        addObjectToLeftPanel("src/Images/ObjectImages/magic_heart.png", "Extra Life",
                "This enchantment increases the hero’s lives be one. Like extra " +
                         "time enchantment, the addition of the extra life happens the\r\n" + //
                                                          "moment it is collected.");
            
    }

    private void addObjectToLeftPanel(String imagePath, String name, String description) {
        
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setOpaque(false); 
    
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false); 
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
    
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        imageLabel.setIcon(icon);
        itemPanel.add(imageLabel, BorderLayout.WEST);
    
       
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(50, 25, 10, 50)); 
    
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.BLUE); 
        textPanel.add(nameLabel);
    
        
        JTextArea descriptionLabel = new JTextArea(description);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setForeground(Color.DARK_GRAY); 
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setWrapStyleWord(true);
        descriptionLabel.setOpaque(false); 
        descriptionLabel.setEditable(false);
    
        
        descriptionLabel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        textPanel.add(descriptionLabel);
    
        itemPanel.add(textPanel, BorderLayout.CENTER);
    
        
        leftPanel.add(itemPanel);
    
        
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void setBackButton() {
        BackGameButton = new JButton("Back");
        BackGameButton.addActionListener(e -> {
            this.dispose();
        });

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); 
        buttonPanel.add(BackGameButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
