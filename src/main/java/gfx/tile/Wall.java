package gfx.tile;

import enums.Id;
import game.Game;
import game.Handler;
import physics.interfaces.Broken;

import java.awt.*;

public class Wall extends Tile implements Broken {

    public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        this.brokenAnimation(g2d);
    }

    @Override
    public void tick() {
    }

    @Override
    public void brokenAnimation(Graphics2D g) {
        if(this.countHit > 0){
            g.drawImage(Game.wall.getBufferedImage(), x, y, (width/2), height, null);
        }else{
            g.drawImage(Game.wall.getBufferedImage(), x, y, width, height, null);
//            g.drawOval(x, y, width, height);
//            g.setColor(Color.red);
        }
    }
}
