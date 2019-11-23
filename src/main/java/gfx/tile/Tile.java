package gfx.tile;

import audio.MusicPlayer;
import enums.Id;
import game.Handler;
import physics.interfaces.CollisionBounds;
import physics.interfaces.DepthOfField;

import java.awt.*;

public abstract class Tile implements DepthOfField, CollisionBounds {

    public int x, y;
    public int width, height;
    public int depthPos;

    public boolean solid = false;
    protected int countHit;

    public float velX, velY;

    public float speed;

    public MusicPlayer music;

    private int facing;

    public Id id;

    public Handler handler;

    public boolean hide;

    public Tile(int x, int y, int width, int height, boolean solid, Id id, Handler handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.id = id;
        this.handler = handler;
        this.facing = 0;
        this.countHit = 0;
        this.depthPos = 2;
//        this.hide = false;
    }

    public void setFacing(int facing){
        this.facing = facing;
        this.velX = facing == 1 ? this.velX : -this.velX;
    }

    public int getFacing(){
        return this.facing;
    }

    public abstract void render(Graphics g);

    public abstract void tick();

    public void die(){
        handler.removeTile(this);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Id getId(){
        return id;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public Rectangle getBounds(){
        return new Rectangle(this.getX(), this.getY(), this.width, this.height);
    }

    @Override
    public Rectangle getBoundsRight(){
        return new Rectangle(x+width-5, y+5, 5, height-10);
    }

    @Override
    public Rectangle getBoundsLeft(){
        return new Rectangle(x, y+5, 5, height-10);
    }

    @Override
    public Rectangle getBoundsBottom(){
        return new Rectangle(this.getX()+5, this.getBottomLine()-5, this.width-10, 5);
    }

    @Override
    public Rectangle getBoundsTop(){
        return new Rectangle(x+5, y, width-10, 5);
    }

    public int getRightLine(){
        return this.getX() + this.getWidth();
    }

    public int getLeftLine(){
        return this.getX();
    }

    public int getBottomLine(){
        return this.getY() + this.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void setDepth(int pos) {
        this.depthPos = pos;
    }

    @Override
    public int getDepth() {
        return this.depthPos;
    }

    @Override
    public int hashCode(){
        return (x+y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tile other = (Tile) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }
}
