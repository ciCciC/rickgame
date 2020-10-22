package entity;

import enums.Id;
import game.Game;
import gfx.sprite.Sprite;
import gfx.tile.Tile;
import physics.RotateRectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rick extends Entity {

    private final float MAX_SPEED = 20;

    private Sprite spriteRick;

    private float counter;

    private boolean alreadyUnlocked;

    public Rick(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        super.setGravity(0.25F);
        staticFacing = 0;
        this.jumping = false;
        this.falling = true;
        this.isShooting = false;
        this.alreadyUnlocked = false;
//        playerRick = new Sprite(new BufferedImage[16], Game.Game.sheet, 8, false, true);
        spriteRick = new Sprite(new BufferedImage[9], 10, true, true);
    }

    @Override
    protected void collision() {
        // Tile collisions
        for (Tile tile : this.getHandlerInstance().tiles) {

//            if(tile.getId() == Id.wall || tile.getId() == Id.wallLeft ||
//                    tile.getId() == Id.wallRight ||
//                    tile.getId() == Id.squareWall || tile.getId() == Id.box ||
//                    tile.getId() == Id.magicWall){

            if(Id.isWall(tile.getId()) && tile.solid){

                this.doUnlock(tile);

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
//        RectangleBound.renderBound(g, this, Color.CYAN);
    }

    private void animation(Graphics g){
        //No rotation
//        g.drawImage(playerRick.animation[playerRick.getIndex()], x, y, width, height, null);

        //Do rotation
        RotateRectangle.Render(g, spriteRick, x, y, degrees);

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

        this.animationMotion();
//        this.gifAnimation();

        this.collision();
    }

    private void doUnlock(Tile tile){

        if(getBoundsRight().intersects(tile.getBoundsLeft())){
            if(tile.getId() == Id.magicWall && !this.alreadyUnlocked){
                this.setUnlock(true);
                this.alreadyUnlocked = true;
            }else{
                this.setUnlock(false);
                this.alreadyUnlocked = false;
            }
        }

        if(getBoundsLeft().intersects(tile.getBounds())){

            if(tile.getId() == Id.magicWall && !this.alreadyUnlocked){
                this.setUnlock(true);
                this.alreadyUnlocked = true;
            }else{
                this.setUnlock(false);
                this.alreadyUnlocked = false;
            }
        }

    }

    private void gifAnimation(){

        this.counter += 5;

        if(counter > 0.05){
            if(spriteRick.getIndex() == spriteRick.animation.length-1){
                spriteRick.setIndex(0);
            }else{
                spriteRick.nextIndex();
            }
        }
    }

    private void animationMotion(){
        if(staticFacing == 0){
            spriteRick.setIndex(0);

        }else if(facing == -1){

            if(spriteRick.getIndex() == 2){
                spriteRick.setIndex(4);
            }else if(spriteRick.getIndex() != 2 || spriteRick.getIndex() != 4){
                spriteRick.setIndex(2);
            }

        }else if(facing == 1){
            if(spriteRick.getIndex() < 6 || spriteRick.getIndex() == spriteRick.animation.length-1){
                spriteRick.setIndex(6);
            }else{
                spriteRick.setIndex(8);
            }
        }
    }

    @Override
    public Rectangle getBoundsBottom(){
        return new Rectangle(x+(width/2)-((width/2)/2), y+(height/2), width/2, height/2);
    }

    @Override
    public Rectangle getBoundsTop(){
        return new Rectangle(x+(width/2)-((width/2)/2), y, width/2, height/2);
    }

    @Override
    public Rectangle getBoundsRight(){
        return new Rectangle(x+width-15, y+5, 15, height-20);
    }

    @Override
    public Rectangle getBoundsLeft(){
        return new Rectangle(x, y+5, 15, height-20);
    }

}
