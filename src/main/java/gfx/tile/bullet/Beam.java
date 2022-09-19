package gfx.tile.bullet;

import enums.Id;
import gfx.tile.Tile;

import java.awt.*;

public class Beam extends Tile {

    private int desX, desY;
    private int index;

    public Beam(int x, int y, int width, boolean solid, Id id) {
        super(x, y, width, 0, solid, id);
        this.desX = 0;
        this.desY = 0;
        this.index = getHandlerInstance().beams.size() + 1;
        System.out.println("BEAM["+this.index+"]");
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.drawLine(this.getX(), this.getY(), this.desX, this.desY);
    }

    @Override
    public void tick() {
    }

    public void setTo(int newX, int newY){
        this.desX = newX;
        this.desY = newY;
    }

    public void setFrom(int newX, int newY){
        this.setX(newX);
        this.setY(newY);
    }
}
