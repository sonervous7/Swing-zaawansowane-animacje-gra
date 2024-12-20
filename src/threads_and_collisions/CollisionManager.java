package threads_and_collisions;

import models.Emerald;
import models.Figure;
import models.Platform;
import player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;

public class CollisionManager {

    public void checkCollisionWithPlatforms(Player player, ArrayList<Platform> platforms) {
        player.setOnGround(false);
        Rectangle playerBounds = player.getBounds();

        for (Platform platform : platforms) {
            if (playerBounds.intersects(platform.getBounds()) && player.getVelocityY() >= 0) {
                player.setY(platform.getY() - player.getHeight());
                player.setVelocityY(0);
                player.setOnGround(true);
                break;
            }
        }
    }

    public void checkPlayerFigureCollision(Player player, ArrayList<Figure> figures) {
        if (player.isInvincible()) return;

        Rectangle playerBounds = player.getBounds();

        for (Figure f : figures) {
            if (playerBounds.intersects(f.getBounds())) {
                player.loseLife();
                System.out.println("Gracz stracił życie! Pozostałe życia: " + player.getLives());

                if (player.getLives() <= 0) {
                    JOptionPane.showMessageDialog(null, "Straciłeś wszystkie życia! Koniec gry.",
                            "Game Over", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                break;
            }
        }
    }

    public void checkPlayerEmeraldCollision(Player player, ArrayList<Emerald> emeralds) {
        Rectangle playerBounds = player.getBounds();

        for (Emerald emerald : emeralds) {
            if (!emerald.isCollected() && playerBounds.intersects(emerald.getBounds())) {
                emerald.collect();
                player.collectEmerald();
                System.out.println("Zebrałeś emerald! Liczba emeraldów: " + player.getEmeraldsCollected());

                if (player.getEmeraldsCollected() >= 5) {
                    JOptionPane.showMessageDialog(null, "Gratulacje! Zebrałeś wszystkie emeraldy! Wygrana!",
                            "Wygrana!", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        }
    }

    public void checkFigurePlatformCollision(Figure f, ArrayList<Platform> platforms) {
        Area figureArea = new Area(f.getArea());
        Rectangle figureBounds = figureArea.getBounds();

        for (Platform p : platforms) {
            Area platformArea = new Area(p.getBounds());
            Area intersection = (Area) figureArea.clone();
            intersection.intersect(platformArea);

            if (!intersection.isEmpty()) {
                if (figureBounds.y + figureBounds.height <= p.getY() + 5) {
                    f.setDy(-Math.abs(f.getDy()));
                } else if (figureBounds.y >= p.getY() + p.getHeight() - 5) {
                    f.setDy(Math.abs(f.getDy()));
                } else if (figureBounds.x + figureBounds.width <= p.getX() + 5) {
                    f.setDx(-Math.abs(f.getDx()));
                } else if (figureBounds.x >= p.getX() + p.getWidth() - 5) {
                    f.setDx(Math.abs(f.getDx()));
                }
                break;
            }
        }
    }

    public void checkFiguresCollision(ArrayList<Figure> figs) {
        for (int i = 0; i < figs.size(); i++) {
            Figure f1 = figs.get(i);
            Area a1 = new Area(f1.getArea());

            for (int j = i + 1; j < figs.size(); j++) {
                Figure f2 = figs.get(j);
                Area a2 = new Area(f2.getArea());
                a2.intersect(a1);

                if (!a2.isEmpty()) {
                    f1.setDx(-f1.getDx());
                    f1.setDy(-f1.getDy());
                    f2.setDx(-f2.getDx());
                    f2.setDy(-f2.getDy());
                }
            }
        }
    }
}



