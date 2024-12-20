package models;

import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public abstract class Figure {
    protected Area area;
    protected Shape baseShape;
    protected double sf;
    protected double an;
    protected int dx;
    protected int dy;
    protected Color clr;
    protected int width;
    protected int height;
    protected static final Random rand = new Random();

    public Figure(int w, int h) {
        this.width = w;
        this.height = h;
        dx = 1 + rand.nextInt(5);
        dy = 1 + rand.nextInt(5);
        double minScale = 0.5;
        double maxScale = 1.0;
        sf = minScale + (maxScale - minScale) * rand.nextDouble();
        an = 0.1 * rand.nextDouble();
        clr = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 200 + rand.nextInt(56));
        baseShape = initializeShape();
        area = new Area(baseShape);
    }

    protected abstract Shape initializeShape();

    public void updatePosition() {
        Rectangle bounds = area.getBounds();
        int cx = bounds.x + bounds.width / 2;
        int cy = bounds.y + bounds.height / 2;

        if (cx < 0 || cx > width) {
            dx = -dx;
        }
        if (cy < 0 || cy > height) {
            dy = -dy;
        }

        AffineTransform aft = new AffineTransform();
        aft.translate(dx, dy);
        area.transform(aft);
    }

    public void draw(Graphics2D g) {
        g.setColor(clr);
        g.fill(area);
        g.setColor(clr.darker());
        g.draw(area);
    }

    public Rectangle getBounds() {
        return area.getBounds();
    }

    public Area getArea() {
        return area;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    // Zostawiłem na razie i nie wykorzystywałem ich
    public void increaseScale(double val) {
        sf += val;
    }

    public void rotate(double angle) {
        an += angle;
    }
}

