package models;

import javax.swing.*;
import java.awt.*;

public class Emerald {
    private int x, y;
    private final int size = 25;
    private boolean collected = false;
    private Image emeraldImage;

    public Emerald(int x, int y) {
        this.x = x;
        this.y = y;
        this.emeraldImage = new ImageIcon("resources/emerald.png").getImage();
        emeraldImage = emeraldImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    }

    public void draw(Graphics g) {
        if (!collected) {
            g.drawImage(emeraldImage, x, y, size, size, null);
        }
    }

    public void collect() {
        this.collected = true;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public boolean isCollected() {
        return collected;
    }
}


