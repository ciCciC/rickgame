package entity;

import enums.Id;
import game.Game;
import gfx.sprite.Sprite;
import gfx.tile.Tile;
import physics.RotateRectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Daniel extends Entity{

    private final float MAX_SPEED = 20;
    private final Sprite sprite;

    public Daniel(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        super.setGravity(0.25F);
        staticFacing = 0;
        this.jumping = false;
        this.falling = true;
        this.isShooting = false;
        sprite = new Sprite(new BufferedImage[9], 14, true, true);
    }

    @Override
    protected void collision() {

        for (Tile tile : this.getHandlerInstance().tiles) {
            if(Id.isWall(tile.getId()) && tile.solid){

                if(getBoundsTop().intersects(tile.getBoundsBottom())){
                    y = tile.getY() + height;
                    setVelY(0);
                }

                if (getBoundsBottom().intersects(tile.getBoundsTop())) {
                    y = tile.getY() - height;
                    setVelY(0);
                    falling = false;
                    jumping = false;
                }else{
                    falling = true;
                }

                if(getBoundsRight().intersects(tile.getBoundsLeft())){
                    x = tile.getX() - width;
                    setVelX(0);
                }

                if(getBoundsLeft().intersects(tile.getBoundsRight())){
                    x = tile.getX() + width;
                    setVelX(0);
                }
            }
        }

        //Enemy collision
        for(Entity entity : getHandlerInstance().entity){
            if(entity.getId() == Id.zombieEnemy){
                ZombieEnemy tmp = (ZombieEnemy) entity;
                if(getBoundsTop().intersects(tmp.getBoundsBottom())){
                    y = tmp.getY() + tmp.getHeight();
                    setVelY(0);
                }

                if(getBoundsRight().intersects(tmp.getBoundsLeft())){
                    x = tmp.getX() - this.getWidth();
                    setVelX(0);
                }

                if(getBoundsLeft().intersects(tmp.getBoundsRight())){
                    x = tmp.getX() + tmp.getWidth();
                    setVelX(0);
                }

                if (getBoundsBottom().intersects(tmp.getBoundsTop())){
                    y = tmp.getY() - this.getHeight();
                    setVelY(0);
                    falling = false;
                    jumping =false;
                }else{
                    falling = true;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        this.animation(g);
    }

    private void animation(Graphics g){
        RotateRectangle.Render(g, this.sprite, x, y, degrees);
    }

    @Override
    public void tick() {
        Game.testX += velX;
        Game.testY += velY;

        x += velX;
        y += velY;

        if(jumping){

            if(facing == 1){
                degrees += 6.0;
            }else if(facing == -1){
                degrees += -6.0;
            }
        }else{
            degrees = 0;
        }

        if(falling || jumping){
            velY += this.getGravity();

            if(velY > MAX_SPEED){
                velY = MAX_SPEED;
            }
        }

        this.collision();
    }

    @Override
    public Rectangle getBoundsRight() {
        return new Rectangle(x+width-15, y+5, 15, height-20);
    }

    @Override
    public Rectangle getBoundsLeft() {
        return new Rectangle(x, y+5, 15, height-20);
    }

    @Override
    public Rectangle getBoundsBottom() {
        return new Rectangle(x+(width/2)-((width/2)/2), y+(height/2), width/2, height/2);
    }

    @Override
    public Rectangle getBoundsTop() {
        return new Rectangle(x+(width/2)-((width/2)/2), y, width/2, height/2);
    }
}
