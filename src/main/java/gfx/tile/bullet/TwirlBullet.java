package gfx.tile.bullet;

import enums.Id;
import gfx.tile.bullet.Bullet;
import physics.RotateRectangle;

import java.awt.*;

public class TwirlBullet extends Bullet {

    public TwirlBullet(boolean solid, Id id) {
        super(solid, id);
    }

    public TwirlBullet(int x, int y, int width, int height, boolean solid, Id id) {
        super(x, y, width, height, solid, id);
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
