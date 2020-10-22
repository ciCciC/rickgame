package gfx.tile;

import enums.Id;
import physics.RectangleBound;

import java.awt.*;

public class Box extends Tile {

    public Box(int x, int y, int width, int height, boolean solid, Id id){
        super(x, y, width, height, solid, id);
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        this.animation(g2d);
    }

    @Override
    public void tick() {

    }

    public void animation(Graphics2D g2d) {

        int R = (int) (Math.random()*256);
        int G = (int)(Math.random()*256);
        int B= (int)(Math.random()*256);

        Color randomColor = new Color(R, G, B);
        g2d.setColor(randomColor);
//        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(x, y, width, height);

        RectangleBound.shortTimeRenderBound(g2d, this, randomColor);
    }

    @Override
    public Rectangle getBoundsRight(){
        return new Rectangle(x+(width/2), y+5, (width/2), height-10);
    }

    @Override
    public Rectangle getBoundsLeft(){
        return new Rectangle(x, y+5, (width/2), height-10);
    }

    @Override
    public Rectangle getBoundsBottom(){
        return new Rectangle(this.getX()+5, this.getBottomLine()-5, this.width-10, 5);
    }

    @Override
    public Rectangle getBoundsTop(){
        return new Rectangle(x+5, y, width-10, 5);
    }
}
