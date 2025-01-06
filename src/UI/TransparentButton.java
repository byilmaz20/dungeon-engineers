package src.UI;

import java.awt.*;
import javax.swing.*;

class TransparentButton extends JButton {
    private Color backgroundColor;

    public TransparentButton() {
        super();
        setContentAreaFilled(false); // Disable default background painting
        setOpaque(false); // Make sure transparency works
        setFocusPainted(false); // Remove focus rectangle
        setBorderPainted(false); // Optional: Remove default border
    }

    public void setCustomBackground(Color color) {
        this.backgroundColor = color;
        repaint(); // Force repaint to apply background color
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundColor != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, backgroundColor.getAlpha() / 255f));
            g2.setColor(backgroundColor);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
        super.paintComponent(g);
    }
}
