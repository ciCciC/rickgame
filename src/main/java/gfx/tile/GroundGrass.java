package gfx.tile;

import enums.Id;
import game.Game;
import game.Handler;

import java.awt.*;

public class GroundGrass extends Tile {
    public GroundGrass(int x, int y, int width, int height, boolean solid, Id id) {
        super(x, y, width, height, solid, id);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.groundGrass.getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {
        //
    }
}
