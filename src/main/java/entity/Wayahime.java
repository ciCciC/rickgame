package entity;

import enums.Id;
import game.Game;
import game.Handler;
import gfx.tile.Tile;

import java.awt.*;

public class Wayahime extends Entity{

    private final float MAX_SPEED = 20;

    public Wayahime(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        this.setGravity(0.25F);
        staticFacing = 0;
        this.jumping = false;
        this.falling = true;
        this.isShooting = false;
    }

    @Override
    public void render(Graphics g) {

        this.animation(g);

    }

    private void animation(Graphics g){
        // -1 == left : 1 == right

        if(staticFacing == 0){
            g.drawImage(Game.playerWayahime[0].getBufferedImage(), x, y, width, height, null);
        }else if (facing == -1 && !jumping && !isShooting) {
            g.drawImage(Game.playerWayahime[1].getBufferedImage(), x, y, width, height, null);
        } else if (facing == 1 && !jumping && !isShooting) {
            g.drawImage(Game.playerWayahime[2].getBufferedImage(), x, y, width, height, null);
        }else if(facing == -1 && jumping && !isShooting){
            g.drawImage(Game.playerWayahime[4].getBufferedImage(), x, y, width, height, null);
        }else if(facing == 1 && jumping && !isShooting){
            g.drawImage(Game.playerWayahime[3].getBufferedImage(), x, y, width, height, null);
        }else if(facing == -1 && isShooting){
            g.drawImage(Game.playerWayahime[5].getBufferedImage(), x, y, width, height, null);
        }else if(facing == 1 && isShooting){
            g.drawImage(Game.playerWayahime[6].getBufferedImage(), x, y, width, height, null);
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if(falling || jumping){
            velY += this.getGravity();

            if(velY > MAX_SPEED){
                velY = MAX_SPEED;
            }
        }

//        System.out.println("Wayahime x: " + x + " - y:" + y);

        this.collision();
    }

    @Override
    protected void collision() {

        // Tile collisions
        for (Tile tile : getHandlerInstance().tiles) {
            if(tile.getId() == Id.wall || tile.getId() == Id.wallLeft || tile.getId() == Id.wallRight){

                if(getBoundsTop().intersects(tile.getBounds())){
                    y = tile.getY() + height;
                    setVelY(0);
                }

                if (getBounds().intersects(tile.getBounds())) {
                    y = tile.getY() - height;
                    setVelY(0);
                    falling = false;
                    jumping = false;
                }else{
                    falling = true;
                }

                if(getBoundsRight().intersects(tile.getBounds())){
                    x = tile.getX() - width;
                    setVelX(0);
                }

                if(getBoundsLeft().intersects(tile.getBounds())){
                    x = tile.getX() + width;
                    setVelX(0);
                }

            }else if(tile.getId() == Id.sideWallLeft){
//                if (getBounds().intersects(tile.getBounds())) {
//                    x = tile.getX() + width;
//                    setVelX(0);
//                }
            }else if(tile.getId() == Id.sideWallRight){
//                if (getBounds().intersects(tile.getBounds())) {
//                    x = tile.getX() - width;
//                    setVelX(0);
//                }
            }
        }

        //Enemy collision
        for(Entity entity : getHandlerInstance().entity){
            if(entity.getId() == Id.alienEnemy){
                AlienEnemy tmp = (AlienEnemy) entity;
                if(getBoundsTop().intersects(entity.getBounds())){
//                    tmp.getTouchedBox();
                    y = entity.getY() + height;
                    setVelY(0);
                }

                if(getBoundsRight().intersects(entity.getBounds())){
//                    tmp.getTouchedBox();
                    x = entity.getX() - width;
                    setVelX(0);
                }

                if(getBoundsLeft().intersects(entity.getBounds())){
//                    tmp.getTouchedBox();
                    x = entity.getX() + width;
                    setVelX(0);
                }

                if (getBounds().intersects(entity.getBounds())) {
                    y = entity.getY() - height;
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
    public Rectangle getBoundsRight() {
        return new Rectangle(x+width-5, y+5, 5, height-10);
    }

    @Override
    public Rectangle getBoundsLeft() {
        return new Rectangle(x, y+5, 5, height-10);
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
