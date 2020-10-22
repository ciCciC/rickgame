package gfx.tile;

import enums.Id;
import game.Game;
import game.Handler;
import gfx.sprite.Sprite;
import physics.interfaces.Broken;
import java.awt.*;

public class WallLeft extends Tile implements Broken {

    public WallLeft(int x, int y, int width, int height, boolean solid, Id id){
        super(x, y, width, height, solid, id);
        sprite = new Sprite( 1, 1);
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        this.brokenAnimation(g2d);

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
    }

    @Override
    public void brokenAnimation(Graphics2D g) {
        if(this.countHit > 0){
            g.drawImage(Game.wallLeft.getBufferedImage(), x, y, (width/2), height, null);
        }else{
            g.drawImage(Game.wallLeft.getBufferedImage(), x, y, width, height, null);
        }
    }
}
