package gfx.tile;

import enums.Id;
import game.Game;
import game.Handler;
import gfx.sprite.Sprite;

import java.awt.*;

public class Explosion extends Tile {

    private float counter;

    private Sprite explosions;

    public Explosion(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
        this.counter = 0;
        explosions = new Sprite(Game.explosion, Game.sheet, 6, false, false);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(explosions.animation[explosions.getIndex()], x, y, width, height, null);
    }

    @Override
    public void tick() {
        this.counter += 5;

        if(counter >= 10){
            counter = 0;
            if(explosions.getIndex() == explosions.animation.length-1){
                handler.explosions.remove(this);
                this.hide = true;
            }else{
                explosions.nextIndex();
            }
        }
    }
}
