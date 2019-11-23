package entity;

import audio.MusicPlayer;
import enums.Id;
import game.Game;
import game.Handler;
import gfx.tile.Tile;
import physics.RectangleBound;
import physics.interfaces.CollisionBoundsAI;

import java.awt.*;
import java.io.File;

public class ZombieEnemy extends Entity implements CollisionBoundsAI {

    public File filePath;
    public Color color;
    public boolean deleted;

    public MusicPlayer music;

    private final float MAX_SPEED = 10;

    private int setFacing;

    public ZombieEnemy(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        velX = 2;
        color = Color.YELLOW;
        this.deleted = false;
        this.falling = true;
        this.setFacing = 1;
    }

    @Override
    protected void collision() {

        for (Tile tile : handler.tiles) {

            if(Id.isWall(tile.getId()) && tile.solid)
            {
                if(getBoundsTop().intersects(tile.getBoundsBottom())){
                    y = tile.getY() + height;
                    setVelY(0);
                }

                if(getBoundsRight().intersects(tile.getBoundsLeft())){
                    x = tile.getX() - width;
                    this.setVelX(-2);
                    setFacing = -1;
                }

                if(getBoundsLeft().intersects(tile.getBoundsRight())){
                    x = tile.getX() + width;
                    this.setVelX(2);
                    setFacing = 1;
                }

                if(getBoundsBottom().intersects(tile.getBoundsTop())){
                    y = tile.getY() - height;
                    setVelY(0);
                    falling = false;
                    jumping = false;
                }else{
                    falling = true;
                }
            }
        }


//        for (Entity entity : handler.entity) {
//
//            if(entity.id == Id.player){
//
//                if (getBoundsTop().intersects(entity.getBoundsBottom())) {
//                    y = entity.getY() + height;
//                }
//
//                if (getBoundsBottom().intersects(entity.getBoundsTop())) {
//                    y = entity.getY() - height;
//                    falling = false;
//                } else {
//                    falling = true;
//                }
//
//                if (getBoundsRight().intersects(entity.getBoundsLeft())) {
//                    x = entity.getX() - width;
//                    setVelX(-2);
//                    setFacing = -1;
//                }
//
//                if (getBoundsLeft().intersects(entity.getBoundsRight())) {
//                    x = entity.getX() + width;
//                    setVelX(2);
//                    setFacing = 1;
//                }
//            }
//        }
    }

    @Override
    public void render(Graphics g) {
        g.setFont(new Font("Monospaced", Font.PLAIN, 40));
        g.setColor(Color.RED);
        g.drawString("Zombie", x, y);

        if(!deleted){
            if(this.setFacing == -1){
                g.drawImage(Game.zombieEnemy.getGifImage(), x+width, y, -width, height, null);
            }else{
                g.drawImage(Game.zombieEnemy.getGifImage(), x, y, width, height, null);
            }
        }else{
            this.hide = true;
        }

        RectangleBound.renderBound(g, this, Color.CYAN);
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

        this.collision();
    }

    @Override
    public Rectangle getBoundsTop(){
        return new Rectangle(x+(width/2)-((width/2)/2), y, width/2, height/2);
    }

    @Override
    public Rectangle getBoundsRight(){
        return new Rectangle(x+width-15, y+5, 15, height-10);
    }

    @Override
    public Rectangle getBoundsLeft(){
        return new Rectangle(x, y+5, 15, height-10);
    }

    @Override
    public Rectangle getBoundsBottom() {
        return new Rectangle(x+(width/2)-((width/2)/2), y+(height/2), width/2, (height/2));
    }

    @Override
    public Rectangle getBoundsLeftAI() {
        return new Rectangle(x-(width*2), y+5, 50, height-10);
    }

    @Override
    public Rectangle getBoundsRightAI() {
        return new Rectangle(x+(width*2), y+5, 50, height-10);
    }
}
