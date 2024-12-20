package threads_and_collisions;

import models.Figure;
import models.Platform;
import ui.GamePanel;

import javax.swing.*;
import java.util.ArrayList;

public class FiguresLoop implements Runnable {
    private final ArrayList<Figure> figures;
    private final ArrayList<Platform> platforms;
    private final CollisionManager collisionManager;
    private final GamePanel gamePanel;
    private boolean running = true;
    private final int delay = 16;

    public FiguresLoop(ArrayList<Figure> figures, ArrayList<Platform> platforms, CollisionManager collisionManager, GamePanel gamePanel) {
        this.figures = figures;
        this.platforms = platforms;
        this.collisionManager = collisionManager;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        while (running) {
            updateFigures();
            SwingUtilities.invokeLater(gamePanel::repaint);
            sleep();
        }
    }

    private void updateFigures() {
        synchronized (figures) {
            for (Figure f : figures) {
                f.updatePosition();
                collisionManager.checkFigurePlatformCollision(f, platforms);
                collisionManager.checkFiguresCollision(figures);
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        running = false;
    }
}


