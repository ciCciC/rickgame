package physics;

import gfx.sprite.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RotateRectangle {

    public static void Render(Graphics g, Sprite sprite, int x, int y, double degrees){

        Graphics2D g2d = (Graphics2D)g.create();
        g2d.rotate(Math.toRadians(degrees), x+30, y+25);
        g2d.drawImage(sprite.animation[sprite.getIndex()], x, y, null);
        g2d.dispose();
    }

    public static void rotate(Graphics2D g2d, int x, int y, int width, int height, double radAngle, Shape rect, Color color){
        AffineTransform tx = new AffineTransform();
        tx.translate(x, y);
        tx.rotate(radAngle);
        g2d.setColor(color);
        g2d.fill(tx.createTransformedShape(rect));
        g2d.dispose();
    }

    public static void setStroke(Graphics2D g2d, int x, int y, double radAngle, Shape rect, Color color, int tickness){
        AffineTransform tx = new AffineTransform();
        tx.translate(x, y);
        tx.rotate(radAngle);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(tickness));
        g2d.draw(tx.createTransformedShape(rect));
        g2d.dispose();
    }
}
