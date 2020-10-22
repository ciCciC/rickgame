package gfx.tile;

import enums.Id;
import game.Game;
import game.Handler;
import gfx.sprite.Sprite;
import physics.interfaces.Broken;

import java.awt.*;

public class SquareWall extends Tile implements Broken {

    public SquareWall(int x, int y, int width, int height, boolean solid, Id id) {
        super(x, y, width, height, solid, id);
        sprite = new Sprite( 1, 7);
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
            g.drawImage(Game.squareWall.getBufferedImage(), x, y, (width/2), height, null);
        }else{
            g.drawImage(Game.squareWall.getBufferedImage(), x, y, width, height, null);
        }
    }
}
