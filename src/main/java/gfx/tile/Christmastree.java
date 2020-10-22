package gfx.tile;

import enums.Id;
import game.Game;
import game.Handler;

import java.awt.*;

public class Christmastree extends Tile {

    public Christmastree(int x, int y, int width, int height, boolean solid, Id id) {
        super(x, y, width, height, solid, id);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.christmasTree.getGifImage(), x, y-height+64, width, height, null);
    }

    @Override
    public void tick() {

    }
}
