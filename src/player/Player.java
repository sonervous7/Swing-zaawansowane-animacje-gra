package player;


import javax.swing.*;
import java.awt.*;

public class Player {
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private int x, y;
    private final int width = 50, height = 50;
    private final int speed = 5;
    private int velocityY = 0;
    private final int gravity = 2;
    private boolean isOnGround = false;

    private int lives;

    private boolean invincibility; // Nietykalność
    private int invincibilityTime = 2000; // Czas nietykalności (ms)

    private int emeraldsCollected; // Liczba zebranych emeraldów
    private Image playerImage; // Obrazek gracza

    public Player(int spawnX, int spawnY) {
        this.x = spawnX;
        this.y = spawnY;
        this.lives = 2;
        this.invincibility = false;
        this.playerImage = new ImageIcon("resources/Stickman.png").getImage();
        playerImage = playerImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    }

    public int getLives() {
        return lives;
    }

    // Metoda zmniejszająca życie
    public void loseLife() {
        if (!invincibility) {
            lives--;
            setInvincibility(true);
            startInvincibilityTimer();
        }
    }

    public int getEmeraldsCollected() {
        return emeraldsCollected;
    }

    public void collectEmerald() {
        emeraldsCollected++;
    }

    public boolean isInvincible() {
        return invincibility;
    }

    public void setInvincibility(boolean invincibility) {
        this.invincibility = invincibility;
    }

    // Timer wyłączający nietykalność po określonym czasie
    private void startInvincibilityTimer() {
        Timer timer = new Timer(invincibilityTime, e -> {
            setInvincibility(false);
            ((Timer) e.getSource()).stop();
        });
        timer.start();
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void jump() {
        if (isOnGround) { // Gracz może skakać tylko, jeśli stoi na platformie
            // Siła skoku (ujemna, bo idziemy do góry)
            int jumpStrength = -30;
            velocityY = jumpStrength;
            isOnGround = false;
        }
    }

    public void applyGravity() {
        if (!isOnGround) { // Jeśli gracz nie stoi na platformie, grawitacja działa
            velocityY += gravity; // Dodanie prędkość grawitacji
            y += velocityY; // Aktualizacja pozycjy gracza
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public int getHeight() {
        return height;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public void setOnGround(boolean onGround) {
        isOnGround = onGround;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, x, y, null);
    }
}