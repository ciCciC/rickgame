package entity;

import audio.*;
import enums.*;
import game.*;
import gfx.tile.*;
import physics.interfaces.*;

import java.awt.*;

public abstract class Entity implements DepthOfField, CollisionBounds {

    public int x, y;
    public int width, height;
    public int depthPos;

    public float velX, velY;

    public int frame = 0, frameDelay = 0;

    // -1 == left, 0 == center, 1 == right
    public int facing, staticFacing;

    public Id id;

    public Handler handler;

    public boolean jumping = false, falling = true;

    private float gravity;

    public double degrees = 0.0;

    public boolean hide;

    public boolean isShooting;

    private boolean unlock;

    public Entity(int x, int y, int width, int height, Id id, Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
        this.hide = false;
        this.gravity = Game.getPhysicsManagement().getGravity();
    }

    protected abstract void collision();

    public abstract void render(Graphics g);

    public abstract void tick();

    public void die() {
        handler.removeEntity(this);
    }

    public void shoot() {
        int distanceFromPl = this.getX() + (this.getWidth() * this.facing);
        Bullet bul = new Bullet(distanceFromPl, this.getY(), 64, 20, true, Id.bullet, handler);
        bul.setFacing(this.facing);
        bul.setTarget(Id.alienEnemy);
        handler.addBullets(bul);

        MusicPlayer musicLaser = new MusicPlayer(Musiclist.Soundlist.laser1, AudioType.Sound);
        musicLaser.run();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Id getId() {
        return id;
    }

    public float getVelX() {
        return this.velX;
    }

    public float getVelY() {
        return this.velY;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean getFalling() {
        return this.falling;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void setDepth(int pos) {
        this.depthPos = pos;
    }

    @Override
    public int getDepth() {
        return this.depthPos;
    }

    public void setUnlock(boolean changeUnlock) {
        this.unlock = changeUnlock;
    }

    public boolean getUnlock() {
        return this.unlock;
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
    public abstract Rectangle getBoundsRight();

    @Override
    public abstract Rectangle getBoundsLeft();

    @Override
    public abstract Rectangle getBoundsBottom();

    @Override
    public abstract Rectangle getBoundsTop();
}
