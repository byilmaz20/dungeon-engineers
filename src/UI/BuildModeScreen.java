package src.UI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import src.GameController.BuildModeController;
import src.GameController.GameFlowController;
import src.GameObjects.Hall;

public class BuildModeScreen extends JPanel {

    private final Map<String, JPanel> hallPanels; // Store hall panels
    private final Map<String, String> objectImages; // Store object image paths
    private final Map<String, Integer> hallObjectCounts; // Track object counts in each hall
    private final Map<String, Integer> hallMinimumCounts; // Define minimum object counts per hall
    private final int hallGridSize = 25; // 25x25 grid for each hall
    private ImageIcon selectedObjectIcon; // Currently selected object icon
    public static final Map<String, Map<Point, String>> hallObjectPlacements = new HashMap<>(); //store the coordinates of each object for each hall
    private JFrame frame;
    private static int[] indexes = new int[1000];
    
    public BuildModeScreen() {
        hallPanels = new HashMap<>();
        objectImages = new HashMap<>();
        hallObjectCounts = new HashMap<>();
        hallMinimumCounts = new HashMap<>();

        setupObjectImages();
        setupHallConstraints();

        setLayout(new BorderLayout());

        JPanel objectPanel = createObjectPanel();
        add(objectPanel, BorderLayout.EAST);

        initializeHallObjectPlacements();

        JPanel hallPanel = createHallPanel();
        add(hallPanel, BorderLayout.CENTER);
        

        if (true) {
            frame = new JFrame("Build Mode");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setSize(1210, 1000);
            frame.setSize(1200, 900);
            frame.setResizable(false);
            
            frame.setContentPane(this);
            frame.setVisible(true);
        }
    }
    private void initializeHallObjectPlacements() {
        String[] hallNames = {"Hall of WATER", "Hall of EARTH", "Hall of FIRE", "Hall of AIR"};
        for (String hallName : hallNames) {
            hallObjectPlacements.put(hallName, new HashMap<>());
        }
    }
    private void setupObjectImages() {
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
        hallMinimumCounts.put("Hall of EARTH", 6);
        hallMinimumCounts.put("Hall of AIR", 9);
        hallMinimumCounts.put("Hall of WATER", 13);
        hallMinimumCounts.put("Hall of FIRE", 17);


        hallObjectCounts.put("Hall of EARTH", 0);
        hallObjectCounts.put("Hall of AIR", 0);
        hallObjectCounts.put("Hall of WATER", 0);
        hallObjectCounts.put("Hall of FIRE", 0);
    }

    private JPanel createObjectPanel() {
        JPanel objectPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("src/Images/ObjectImages/buildmode.png").getImage();
    
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        objectPanel.setLayout(new GridBagLayout());
        objectPanel.setPreferredSize(new Dimension(250, 0));
    
        // Adding title 
        //JLabel title = new JLabel("", SwingConstants.CENTER);
        //title.setForeground(Color.BLACK);
        //title.setFont(new Font("Arial", Font.BOLD, 18));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        gbc.insets = new Insets(100, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        //objectPanel.add(title, gbc);


        // Exit Button 
        JButton exitButton = new JButton();
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        ImageIcon exitIcon = new ImageIcon("src/Images/ObjectImages/exit2.png");
        Image scaledExitImage = exitIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        exitButton.setIcon(new ImageIcon(scaledExitImage));
        exitButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose(); 
            new MainMenuScreen(); 
        });
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 20, 0);  



        gbc.anchor = GridBagConstraints.CENTER;
        objectPanel.add(exitButton, gbc);
        //Placing Object to the panel
        String[] objects = objectImages.keySet().toArray(new String[0]);
        for (int i = 0; i < objects.length; i++) {
            JLabel objectLabel = new JLabel();
            
            String imagePath = objectImages.get(objects[i]);
            if (imagePath != null) {
                ImageIcon icon = new ImageIcon(imagePath);
            
                

                Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                ImageIcon last = new ImageIcon(scaledImage);
                last.setDescription(objects[i]);
                objectLabel.setIcon(last);
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

        
        JButton randomInitButton = new JButton("<html><center>Random<br>Place</center></html>");

        randomInitButton.setFont(new Font("DialogInput", Font.BOLD, 20));
        randomInitButton.setForeground(Color.BLACK);
        randomInitButton.setBackground(new Color(168, 160, 136));
        randomInitButton.setFocusPainted(false);
        randomInitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        randomInitButton.setOpaque(true);
        
        randomInitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                randomInitButton.setBackground(new Color(190, 180, 160));
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                randomInitButton.setBackground(new Color(168, 160, 136));
            }
        });
        
        randomInitButton.addActionListener(e -> startRandomInit());
        
        gbc.gridy = objects.length + 1;
        gbc.insets = new Insets(20, 0, 10, 0);
        objectPanel.add(randomInitButton, gbc);
        
        JButton startGameButton = new JButton("<html><center>Start<br>Game</center></html>");
        
        startGameButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        startGameButton.setForeground(Color.BLACK);
        startGameButton.setBackground(new Color(168, 160, 136));
        startGameButton.setFocusPainted(false);
        startGameButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        startGameButton.setOpaque(true);
        
        startGameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startGameButton.setBackground(new Color(190, 180, 160));
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startGameButton.setBackground(new Color(168, 160, 136));
            }
        });
        
        startGameButton.addActionListener(e -> checkRequirementsAndStartGame());
        
        gbc.gridy = objects.length + 1;
        gbc.insets = new Insets(120, 0, 10, 0);
        objectPanel.add(startGameButton, gbc);
        
        
    
        return objectPanel;
    }
    

    
    private JPanel createHallPanel() {
        JPanel hallPanel = new JPanel();
        hallPanel.setLayout(new GridLayout(2, 2, 10, 10)); 
        hallPanel.setBackground(new Color(40, 40, 40));
    
        String[] hallNames = {"Hall of WATER", "Hall of EARTH", "Hall of FIRE", "Hall of AIR"};
    
        //int[] randomIntegersPosition = randomArrayGenerator(45, 625);
        
        //int indexOfArray = 0;

        for (String hallName : hallNames) {
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
            hall.setLayout(new GridLayout(hallGridSize, hallGridSize)); 
    
            //int minObject = indexOfArray+hallMinimumCounts.get(hallName);
            
            //String[] randObj = randomObjectGenerator(hallMinimumCounts.get(hallName));
            //int objIndex = 0;

            ImageIcon icon = new ImageIcon("src/Images/BackgroundImages/cell.png");
            Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon last = new ImageIcon(scaledImage);
            last.setDescription("cell");

            for (int i = 0; i < hallGridSize * hallGridSize; i++) {
                
                
                JLabel cell = new JLabel();
                
                cell.setIcon(last);
                
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

        for (Component comp : objectLabel.getParent().getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
            }
        }
        objectLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2)); 

        selectedObjectIcon = (ImageIcon) objectLabel.getIcon();
    }

    private void placeSelectedObject(JLabel cell, String hallName) {
        if (((ImageIcon) cell.getIcon()).getDescription()!="cell"){
            
            ImageIcon icon = new ImageIcon("src/Images/BackgroundImages/cell.png");
            Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);    
            ImageIcon last = new ImageIcon(scaledImage);
            last.setDescription("cell");
            cell.setIcon(last);

            JPanel hallPanel = hallPanels.get(hallName);
            int cellIndex = getComponentIndex(hallPanel, cell);
        
            int row = cellIndex / hallGridSize;
            int col = cellIndex % hallGridSize;
            Point coordinates = new Point(row, col);

            hallObjectPlacements.get(hallName).remove(coordinates);
            hallObjectCounts.put(hallName, hallObjectCounts.get(hallName) - 1);

            return;

        }
        if (selectedObjectIcon == null) {
            JOptionPane.showMessageDialog(this, "No object selected. Please select an object first.");
            return;
        }
        
        /*if (cell.getIcon() != null) {
            JOptionPane.showMessageDialog(this, "This cell is already occupied!");
            return;
        }*/
        hallObjectCounts.put(hallName, hallObjectCounts.get(hallName) + 1);

        Image scaledImage = selectedObjectIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon last2 = new ImageIcon(scaledImage);
        cell.setIcon(null);
        cell.setIcon(last2);

        
        JPanel hallPanel = hallPanels.get(hallName);
        int cellIndex = getComponentIndex(hallPanel, cell);
        
        indexes = addElement(indexes, cellIndex);
        int row = cellIndex / hallGridSize;
        int col = cellIndex % hallGridSize;
        Point coordinates = new Point(row, col);

        for (int number : indexes) {
            System.out.println("Element: " + number);
        }
        
        
        String objectName = getObjectNameByIcon(selectedObjectIcon);
        hallObjectPlacements.get(hallName).put(coordinates, objectName);

        //coordinates chceck
        //
        /* for (Point p : hallObjectPlacements.get(hallName).keySet()) {
            System.out.println(hallName);
            System.out.println(hallObjectCounts.get(hallName));
            System.out.println(p.x);
            System.out.println(p.y);
            System.out.println(hallObjectPlacements.get(hallName).get(p));
            
        } */ 
        
        
        
        
        
    }




    private void startRandomInit() {
        int[] randomIntegersPosition = randomArrayGenerator(45, 625);
        int indexOfArray = 0;
        for (String hallName : hallPanels.keySet()) {
            JPanel hall = hallPanels.get(hallName);
    
            int minObject;
            if (hallObjectCounts.get(hallName)<hallMinimumCounts.get(hallName)){
                minObject = indexOfArray+hallMinimumCounts.get(hallName)-hallObjectCounts.get(hallName);
            }
            else{
                minObject = indexOfArray+hallMinimumCounts.get(hallName);
                indexOfArray = minObject;
            }


            String[] randObj;
            if (hallObjectCounts.get(hallName)<hallMinimumCounts.get(hallName)){
                randObj = randomObjectGenerator(hallMinimumCounts.get(hallName)-hallObjectCounts.get(hallName));
                }
            else{
                randObj = randomObjectGenerator(hallMinimumCounts.get(hallName));
                }
            

            int objIndex = 0;

            
            for (int i = 0; i < hallGridSize * hallGridSize; i++) {

                if (indexOfArray < minObject){
                    
                    if (i == randomIntegersPosition[indexOfArray]){
                        
                        String obj = randObj[objIndex];
                        
                        JLabel cell = (JLabel) hall.getComponent(i);

                        ImageIcon icon = new ImageIcon(objectImages.get(obj));
                        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                        ImageIcon last = new ImageIcon(scaledImage);
                        last.setDescription(obj);
                        cell.setIcon(last);
            
                        //hall.add(cell);


                        indexOfArray+=1;
                        objIndex +=1;

                        hallObjectCounts.put(hallName, hallObjectCounts.get(hallName) + 1);
                 
                        int row = i / hallGridSize;
                        int col = i % hallGridSize;
                        Point coordinates = new Point(row, col);
                        //System.out.println(((ImageIcon) cell.getIcon()).getDescription());
                        hallObjectPlacements.get(hallName).put(coordinates, obj);

                        continue;
                    }

                }
                /* JLabel cell = new JLabel();
                ImageIcon icon = new ImageIcon("src/Images/BackgroundImages/cell.png");
            
                

                Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                
                ImageIcon last = new ImageIcon(scaledImage);
                last.setDescription("cell");
                //cell.setOpaque(false); 
                //cell.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1)); // Grid lines
                cell.setIcon(last);
                
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        placeSelectedObject(cell, hallName);
                    }
                });
    
                hall.add(cell); */
                
            }
            
            
        }
        
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
            //JOptionPane.showMessageDialog(this, "Game starting!");     
            
            new BuildModeController();
            for (Map.Entry<String, Hall> entry : BuildModeController.Halls.entrySet()) {
                Hall hall = entry.getValue();
                //System.out.println(hall);
            }
            
            new GameFlowController();
            frame.setVisible(false);
        } else {
            UIManager.put("OptionPane.background", new Color(168, 160, 136)); 
            UIManager.put("Panel.background", new Color(168, 160, 136)); 
            UIManager.put("OptionPane.messageForeground", Color.BLACK); 
            UIManager.put("OptionPane.messageFont", new Font("Monospaced", Font.BOLD, 14)); 
            UIManager.put("Button.background", new Color(190, 180, 160)); 
            UIManager.put("Button.foreground", Color.BLACK); 
            UIManager.put("Button.border", BorderFactory.createLineBorder(Color.BLACK, 2)); 
            UIManager.put("Button.focus", Color.BLACK); 

            JOptionPane.showMessageDialog(
                this, 
                "<html><body style='text-align:left;'>"
                + "Cannot start the game:<br>"
                + errorMessage.toString().replace("\n", "<br>")
                + "</body></html>", 
                "Message", 
                JOptionPane.PLAIN_MESSAGE
            );

            // Reset UIManager changes to avoid affecting other dialogs
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("OptionPane.messageForeground", null);
            UIManager.put("OptionPane.messageFont", null);
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            UIManager.put("Button.border", null);
            UIManager.put("Button.focus", null);
        }
    }

    public JPanel getHall(String hallName) {
        return hallPanels.get(hallName);
    }

    //getting index of a object in the container
    private int getComponentIndex(Container container, Component component) {
        for (int i = 0; i < container.getComponentCount(); i++) {
            if (container.getComponent(i) == component) {
                return i;
            }
        }
        return -1;
    }

    //getting object name from icon
    private String getObjectNameByIcon(ImageIcon icon) {
        if (icon == null) {
            System.err.println("Provided ImageIcon is null.");
            return "Unknown Object";
        }
        String description = icon.getDescription();
        
        if (description == null) {
            System.err.println("ImageIcon description is null for icon: " + icon);
            return "Unknown Object";
        }
        
        return description;
    }
    
    private int[] randomArrayGenerator(int arraySize, int randomLength){
        Set<Integer> uniqueNumbers = new HashSet<>();
   
        Random random = new Random();
        
        
        Set<Integer> excludedSet = new HashSet<>();
        for (int num : indexes) {
            excludedSet.add(num);
        }
        while (uniqueNumbers.size() < arraySize) {
            int number = random.nextInt(randomLength); // Generate a number between 0 and 624
            if (!excludedSet.contains(number) && !uniqueNumbers.contains(number)) {
                uniqueNumbers.add(number); // Add to the generated set
            }
        }
        int[] randomIntegers = uniqueNumbers.stream().mapToInt(Integer::intValue).toArray();
        
        int waterc;
        //System.out.println(waterc);
        int earthc;
        //System.out.println(earthc);
        int airc;
        //System.out.println(airc);
        int firec;
        //System.out.println(firec);

        if (hallObjectCounts.get("Hall of WATER")<=hallMinimumCounts.get("Hall of WATER")){
            waterc = hallMinimumCounts.get("Hall of WATER")- hallObjectCounts.get("Hall of WATER");
        }
        else{
            waterc=hallMinimumCounts.get("Hall of WATER");
        }

        if (hallObjectCounts.get("Hall of EARTH")<=hallMinimumCounts.get("Hall of EARTH")){
            earthc = hallMinimumCounts.get("Hall of EARTH")- hallObjectCounts.get("Hall of EARTH");
        }
        else{
            earthc=hallMinimumCounts.get("Hall of EARTH");
        }

        if (hallObjectCounts.get("Hall of AIR")<=hallMinimumCounts.get("Hall of AIR")){
            airc = hallMinimumCounts.get("Hall of AIR")- hallObjectCounts.get("Hall of AIR");
        }
        else{
            airc=hallMinimumCounts.get("Hall of AIR");
        }

        if (hallObjectCounts.get("Hall of FIRE")<=hallMinimumCounts.get("Hall of FIRE")){
            firec = hallMinimumCounts.get("Hall of FIRE")- hallObjectCounts.get("Hall of FIRE");
        }
        else{
            firec=hallMinimumCounts.get("Hall of FIRE");
        }

        Arrays.sort(randomIntegers, 0, waterc);
        Arrays.sort(randomIntegers, waterc, waterc+earthc);
        Arrays.sort(randomIntegers, waterc+earthc, waterc+earthc+airc);
        Arrays.sort(randomIntegers, waterc+earthc+airc, waterc+earthc+airc+firec);
       
        return randomIntegers;
    }

    private String[] randomObjectGenerator(int arraySize){
        int[] randomIntegers = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < randomIntegers.length; i++) {
            randomIntegers[i] = random.nextInt(8); 
        }

        String[] Strings = {"Barrel", "Stair", "1Box", "2Box", "Rectangle", "Skull", "Chest", "Potion"};
        String[] randomStrings =  new String[arraySize];

        for (int i = 0; i < randomStrings.length; i++) {
            randomStrings[i] = Strings[randomIntegers[i]]; 
        }

        return randomStrings;
    }

    public static boolean isExcluded(int number, int[] excludedNumbers) {
        for (int excluded : excludedNumbers) {
            if (number == excluded) {
                return true;
            }
        }
        return false;
    }

    public static int[] addElement(int[] original, int element) {
        // Create a new array with an additional space
        int[] newArray = new int[original.length + 1];

        // Copy elements from the old array to the new array
        System.arraycopy(original, 0, newArray, 0, original.length);

        // Add the new element at the end
        newArray[newArray.length - 1] = element;

        return newArray;
    }
}



