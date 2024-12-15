package src.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.util.HashMap;
import java.util.Map;

public class BuildModeScreen extends JPanel {

    private final Map<String, JPanel> hallPanels;
    private final Map<String, Integer> objectSizes;
    private final Map<String, String> objectImages; // Map to store image paths
    private final int hallGridSize = 25; // 25x25 squares per hall

    public BuildModeScreen() {
        hallPanels = new HashMap<>();
        objectSizes = new HashMap<>();
        objectImages = new HashMap<>();

        // Define object sizes
        objectSizes.put("Barrel", 2); // Barrel covers 2 squares vertically
        objectSizes.put("Stair", 1);  // Stair covers 1 square
        objectSizes.put("1Box", 1);  // Single Box covers 1 square
        objectSizes.put("2Box", 2);  // Two Boxes cover 2 squares vertically
        objectSizes.put("Rectangle", 1); // Rectangle covers 1 square
        objectSizes.put("Skull", 1);  // Skull covers 1 square
        objectSizes.put("Chest", 1);  // Chest covers 1 square
        objectSizes.put("Potion", 1); // Potion covers 1 square

        // Define object image paths
        objectImages.put("Barrel", "images/barrel.png");
        objectImages.put("Stair", "images/stair.png");
        objectImages.put("1Box", "images/1box.png");
        objectImages.put("2Box", "images/2box.png");
        objectImages.put("Rectangle", "images/rectangle.png");
        objectImages.put("Skull", "images/skull.png");
        objectImages.put("Chest", "images/chest.png");
        objectImages.put("Potion", "images/potion.png");

        setLayout(new BorderLayout());

        // Right Panel for objects to drag
        JPanel objectPanel = new JPanel();
        objectPanel.setLayout(new BoxLayout(objectPanel, BoxLayout.Y_AXIS));
        objectPanel.setBackground(new Color(60, 60, 60));
        objectPanel.setPreferredSize(new Dimension(150, 0));

        JLabel title = new JLabel("Build Mode", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        objectPanel.add(title);

        // Objects to drag
        String[] objects = {"Barrel", "Stair", "1Box", "2Box", "Rectangle", "Skull", "Chest", "Potion"};
        for (String obj : objects) {
            JLabel objectLabel = new JLabel();
            objectLabel.setText(obj); // Set text for accessibility
            objectLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            objectLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            objectLabel.setOpaque(true);
            objectLabel.setBackground(Color.LIGHT_GRAY);
            objectLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            objectLabel.setPreferredSize(new Dimension(100, 100));

            // Load and set object image
            String imagePath = objectImages.get(obj);
            if (imagePath != null) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                objectLabel.setIcon(new ImageIcon(scaledImage));
            }

            // Enable drag functionality
            objectLabel.setTransferHandler(new TransferHandler("text"));

            // Add MouseListener for dragging
            objectLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    JComponent comp = (JComponent) evt.getSource();
                    TransferHandler handler = comp.getTransferHandler();
                    handler.exportAsDrag(comp, evt, TransferHandler.COPY);
                }
            });

            objectPanel.add(objectLabel);
        }

        add(objectPanel, BorderLayout.EAST);

        // Main panel for halls
        JPanel hallPanel = new JPanel();
        hallPanel.setLayout(new GridLayout(2, 2, 10, 10)); // Grid for 4 halls
        hallPanel.setBackground(new Color(40, 40, 40));

        String[] hallNames = {"Hall of Water", "Hall of Earth", "Hall of Fire", "Hall of Air"};
        for (String hallName : hallNames) {
            JPanel hall = new JPanel();
            hall.setBorder(BorderFactory.createTitledBorder(hallName));
            hall.setLayout(new GridLayout(hallGridSize, hallGridSize)); // 25x25 grid for objects inside hall
            hall.setBackground(new Color(80, 60, 60));

            // Add drop target to each hall
            new DropTarget(hall, new DropTargetListener() {
                @Override
                public void dragEnter(DropTargetDragEvent dtde) {
                    hall.setBackground(new Color(100, 100, 100)); // Highlight target
                }

                @Override
                public void dragOver(DropTargetDragEvent dtde) {
                    // Optional: Keep the highlight
                }

                @Override

                
                public void dropActionChanged(DropTargetDragEvent dtde) {
                    // No action needed
                }

                @Override
                public void dragExit(DropTargetEvent dte) {
                    hall.setBackground(new Color(80, 60, 60)); // Reset background
                }

                @Override
                public void drop(DropTargetDropEvent dtde) {
                    try {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY);

                        // Retrieve the dropped data
                        String droppedItem = (String) dtde.getTransferable().getTransferData(DataFlavor.stringFlavor);

                        // Check object size
                        int objectSize = objectSizes.getOrDefault(droppedItem, 1);

                        // Create a JLabel for each square covered by the object
                        for (int i = 0; i < objectSize; i++) {
                            JLabel droppedLabel = new JLabel();
                            droppedLabel.setText(droppedItem); // Set text for accessibility
                            droppedLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                            droppedLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
                            droppedLabel.setOpaque(true);
                            droppedLabel.setBackground(Color.LIGHT_GRAY);
                            droppedLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                            // Load and set object image
                            String imagePath = objectImages.get(droppedItem);
                            if (imagePath != null) {
                                ImageIcon icon = new ImageIcon(imagePath);
                                Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                                droppedLabel.setIcon(new ImageIcon(scaledImage));
                            }

                            // Add MouseListener for deleting the dropped item
                            droppedLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    if (SwingUtilities.isRightMouseButton(evt)) {
                                        JPanel parent = (JPanel) droppedLabel.getParent();
                                        parent.remove(droppedLabel);
                                        parent.revalidate();
                                        parent.repaint();
                                    }
                                }
                            });

                            // Add the JLabel to the hall panel
                            if (hall.getComponentCount() < hallGridSize * hallGridSize) {
                                hall.add(droppedLabel);
                            } else {
                                JOptionPane.showMessageDialog(hall, "No more space in this hall!");
                                break;
                            }
                        }

                        hall.revalidate();
                        hall.repaint();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            hallPanels.put(hallName, hall); // Save reference to each hall
            hallPanel.add(hall);
        }

        add(hallPanel, BorderLayout.CENTER);
    }

    public JPanel getHall(String hallName) {
        return hallPanels.get(hallName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Build Mode");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new BuildModeScreen());
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}
