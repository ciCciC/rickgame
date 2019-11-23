package gfx.tile;

import enums.Id;
import game.Handler;
import physics.RotateRectangle;

import java.awt.*;

public class TwirleBullet extends Bullet {

    public TwirleBullet(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        this.setRadAngle(this.getRadAngle()+1);
        Rectangle rect = new Rectangle(-(width / 2), -(height / 2), width, height);
        RotateRectangle.rotate(g2d, x, y, width, height, this.getRadAngle(), rect, Color.YELLOW);
//        RotateRectangle.setStroke(g2d, x, y, this.getRadAngle(), rect, Color.BLACK, 6);
    }
}