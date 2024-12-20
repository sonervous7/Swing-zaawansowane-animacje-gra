package threads_and_collisions;

import player.Player;
import ui.GamePanel;

import javax.swing.*;

public class GameLoop implements Runnable {
    private boolean running = true;
    private final GamePanel gamePanel;
    private final Player player;
    private final CollisionManager collisionManager;

    public GameLoop(GamePanel gamePanel, Player player) {
        this.gamePanel = gamePanel;
        this.player = player;
        this.collisionManager = new CollisionManager();
    }

    @Override
    public void run() {
        final double nsPerTick = 1000000000D / 60D; // 60 FPS
        long lastTime = System.nanoTime();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                updateGameLogic();
                SwingUtilities.invokeLater(gamePanel::repaint);
                delta--;
            }

            sleep();
        }
    }

    private void updateGameLogic() {
        player.applyGravity();
        if (player.isMovingLeft()) player.moveLeft();
        if (player.isMovingRight()) player.moveRight();

        collisionManager.checkCollisionWithPlatforms(player, gamePanel.getPlatforms());
        collisionManager.checkPlayerFigureCollision(player, gamePanel.getFigures());
        collisionManager.checkPlayerEmeraldCollision(player, gamePanel.getEmeralds());
    }

    private void sleep() {
        try {
            Thread.sleep(2); // Małe opóźnienie, aby zmniejszyć obciążenie CPU
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        running = false;
    }
}

