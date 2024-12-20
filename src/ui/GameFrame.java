package ui;

import models.Circle;
import models.Emerald;
import models.Figure;
import models.Square;
import player.Player;
import threads_and_collisions.CollisionManager;
import threads_and_collisions.EmeraldSpawner;
import threads_and_collisions.FiguresLoop;
import threads_and_collisions.GameLoop;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class GameFrame extends JFrame {

    public GameFrame() {
        setupFrame();

        Player player = new Player(100, 100);
        GamePanel gamePanel = new GamePanel(player);
        add(gamePanel);

        setVisible(true);

        CollisionManager collisionManager = new CollisionManager();

        ArrayList<Figure> figures = createFigures(gamePanel);
        initializeGameLoop(gamePanel, player);
        initializeFiguresLoops(figures, gamePanel, collisionManager);
        initializeEmeraldSpawner(gamePanel, player);
    }

    private void setupFrame() {
        setTitle("Gra Platformowa");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private ArrayList<Figure> createFigures(GamePanel gamePanel) {
        int w = gamePanel.getWidth();
        int h = gamePanel.getHeight();

        ArrayList<Figure> figures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Figure fig = (i % 2 == 0) ? new Square(w, h) : new Circle(w, h);
            figures.add(fig);
            gamePanel.addFigure(fig);
        }
        return figures;
    }

    private void initializeGameLoop(GamePanel gamePanel, Player player) {
        GameLoop gameLoop = new GameLoop(gamePanel, player);
        Thread gameThread = new Thread(gameLoop);
        gameThread.start();
    }

    private void initializeFiguresLoops(ArrayList<Figure> figures, GamePanel gamePanel, CollisionManager collisionManager) {
        for (Figure f : figures) {
            FiguresLoop figuresLoop = new FiguresLoop(
                    new ArrayList<>(Collections.singletonList(f)),
                    gamePanel.getPlatforms(),
                    collisionManager,
                    gamePanel
            );
            Thread figThread = new Thread(figuresLoop);
            figThread.start();
        }
    }

    private void initializeEmeraldSpawner(GamePanel gamePanel, Player player) {
        ArrayList<Emerald> emeralds = gamePanel.getEmeralds();
        EmeraldSpawner emeraldSpawner = new EmeraldSpawner(
                emeralds,
                gamePanel.getPlatforms(),
                player,
                gamePanel
        );
        Thread emeraldThread = new Thread(emeraldSpawner);
        emeraldThread.start();
    }
}
