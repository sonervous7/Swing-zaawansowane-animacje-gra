package models;

import java.awt.*;
import java.awt.geom.*;

public class Square extends Figure {

    public Square(int w, int h) {
        super(w, h);
    }

    @Override
    protected Shape initializeShape() {
        int size = 20 + rand.nextInt(30);
        int x = rand.nextInt(width - size);
        int y = rand.nextInt(height - size);

        return new Rectangle2D.Double(x, y, size, size);
    }
}



