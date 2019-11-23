package gfx.tile;

import enums.Id;
import game.Game;
import game.Handler;

import java.awt.*;

public class SideWallRight extends Tile {

    public SideWallRight(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.sideWallRight.getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {

    }
}