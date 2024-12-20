package threads_and_collisions;

import models.Emerald;
import models.Platform;
import player.Player;
import ui.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class EmeraldSpawner implements Runnable {
    private final ArrayList<Emerald> emeralds;
    private final ArrayList<Platform> platforms;
    private final Player player;
    private final GamePanel gamePanel;
    private final int spawnDelay = 3000; // Czas między spawnami (ms)
    private boolean running = true;

    public EmeraldSpawner(ArrayList<Emerald> emeralds, ArrayList<Platform> platforms, Player player, GamePanel gamePanel) {
        this.emeralds = emeralds;
        this.platforms = platforms;
        this.player = player;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        Random rand = new Random();

        while (running) {
            spawnEmeraldIfPossible(rand);
            gamePanel.repaint();
            sleep();
        }
    }

    private void spawnEmeraldIfPossible(Random rand) {
        if (emeralds.size() < 5) {
            Platform platform;
            int x, y;

            do {
                platform = platforms.get(rand.nextInt(platforms.size()));
                x = platform.getX() + rand.nextInt(platform.getWidth() - 20);
                y = platform.getY() - 20;
            } while (player.getBounds().intersects(new Rectangle(x, y, 20, 20)));

            emeralds.add(new Emerald(x, y));
            System.out.println("Emerald dodany na (" + x + ", " + y + ")");
        }
    }

    private void sleep() {
        try {
            Thread.sleep(spawnDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        running = false;
    }
}

