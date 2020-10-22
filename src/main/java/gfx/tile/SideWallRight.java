package gfx.tile;

import enums.Id;
import game.Game;
import game.Handler;
import gfx.sprite.Sprite;

import java.awt.*;

public class SideWallRight extends Tile {

    public SideWallRight(int x, int y, int width, int height, boolean solid, Id id) {
        super(x, y, width, height, solid, id);
        sprite = new Sprite( 4, 1);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.sideWallRight.getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {

    }
}
