package ui;

import models.Emerald;
import models.Figure;
import models.Platform;
import player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private final Image backgroundImage;
    private final Image heartImage;

    private final ArrayList<Platform> platforms = new ArrayList<>();
    private final ArrayList<Figure> figures = new ArrayList<>();
    private final ArrayList<Emerald> emeralds = new ArrayList<>();

    private final Player player;

    public GamePanel(Player player) {
        this.backgroundImage = new ImageIcon("resources/background.png").getImage();
        this.heartImage = new ImageIcon("resources/heart.png").getImage();
        this.player = player;

        addPlatforms();
        setFocusable(true);
        requestFocusInWindow();
        addKeyStrokes();
    }

    private void addPlatforms() {
        platforms.add(new Platform(0, 700, 1200, 100)); // Pełna szerokość ekranu
        platforms.add(new Platform(100, 550, 200, 20));
        platforms.add(new Platform(300, 400, 200, 20));
        platforms.add(new Platform(600, 350, 150, 20));
        platforms.add(new Platform(200, 300, 200, 20));
        platforms.add(new Platform(40, 450, 200, 20));
        platforms.add(new Platform(40, 200, 200, 20));
        platforms.add(new Platform(100, 100, 200, 20));
        platforms.add(new Platform(750, 250, 200, 20));
        platforms.add(new Platform(800, 550, 200, 20));
        platforms.add(new Platform(500, 550, 200, 20));
        platforms.add(new Platform(1000, 90, 200, 20));
    }

    private void addKeyStrokes() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> player.setMovingLeft(true);
                    case KeyEvent.VK_RIGHT -> player.setMovingRight(true);
                    case KeyEvent.VK_UP -> player.jump();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> player.setMovingLeft(false);
                    case KeyEvent.VK_RIGHT -> player.setMovingRight(false);
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    private void drawHearts(Graphics g) {
        int lives = player.getLives();
        int heartSize = 30;
        int padding = 10;

        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, 10 + i * (heartSize + padding), 10, heartSize, heartSize, this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawPlatforms(g);
        drawHearts(g);
        drawPlayer(g);
        drawEmeralds(g);
        drawFigures(g);
    }

    private void drawBackground(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.CYAN);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void drawPlatforms(Graphics g) {
        for (Platform platform : platforms) {
            platform.draw(g);
        }
    }

    private void drawPlayer(Graphics g) {
        if (player != null) {
            player.draw(g);
        }
    }

    private void drawEmeralds(Graphics g) {
        for (Emerald emerald : emeralds) {
            emerald.draw(g);
        }
    }

    private void drawFigures(Graphics g) {
        synchronized (figures) {
            for (Figure fig : figures) {
                fig.draw((Graphics2D) g);
            }
        }
    }

    public void addFigure(Figure f) {
        figures.add(f);
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public ArrayList<Emerald> getEmeralds() {
        return emeralds;
    }
}
