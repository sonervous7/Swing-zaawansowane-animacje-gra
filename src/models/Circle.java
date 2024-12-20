package models;

import java.awt.*;
import java.awt.geom.*;

public class Circle extends Figure {

    public Circle(int w, int h) {
        super(w, h);
    }

    @Override
    protected Shape initializeShape() {
        int diameter = 20 + rand.nextInt(30);
        int x = rand.nextInt(width - diameter);
        int y = rand.nextInt(height - diameter);

        return new Ellipse2D.Double(x, y, diameter, diameter);
    }
}

