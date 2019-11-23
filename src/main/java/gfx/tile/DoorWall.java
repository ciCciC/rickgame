package gfx.tile;

import enums.*;
import game.Handler;
import game.KeyManagement;
import gfx.sprite.Sprite;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoorWall extends Tile {

    private static int counter;

    private int objectIdentity;
    private String originCode;
    private int timerDuration;
    private KeyManagement keyManagement;
    public Sprite magicDoor;

    public DoorWall(int x, int y, int width, int height, boolean solid, Id id, Handler handler, KeyManagement keyManagement) {
        super(x, y, width, height, solid, id, handler);
        this.keyManagement = keyManagement;
        counter++;
        objectIdentity = counter;
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.YELLOW);
//        g.drawRect(x, y, width, height);
//        g.drawLine(x+(width/2), y, x+(width/2), y+64);

//        RectangleBound.renderBoundAI(g, this, Color.RED);

        this.renderSprite(g);
    }

    private void renderSprite(Graphics g){
        try {
            if(this.solid){
                this.magicDoor = new Sprite(FolderType.images, ImageType.jpg, "doorclosed.png");
            }else{
                this.magicDoor = new Sprite(FolderType.images, ImageType.jpg, "dooropen.png");
            }

            g.drawImage(this.magicDoor.getBufferedImage(), x, y, width, height, null);

        } catch (IOException ex) {
            Logger.getLogger(DoorWall.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void tick() {
        if(keyManagement.getUnlockCode().toString().equals(this.originCode) && keyManagement.getRunningTime() < this.getTimerDuration()){
            this.solid = false;
            this.keyManagement.setUnlockedState(true);
        }

        if((keyManagement.getRunningTime() / 100) >= this.getTimerDuration()){
            System.out.println("Zit erin");
            this.solid = true;
            this.keyManagement.setUnlockedState(false);
        }

        System.out.println("currenttime: " + (keyManagement.getRunningTime() / 100) + "/ " + this.getTimerDuration());
    }

    public void setCode(String originCode){
        this.originCode = originCode;
    }

    /***
     *
     * @param timerDuration timerDuration in seconds for being unlocked
     */
    public void setTimerDuration(int timerDuration){
        this.timerDuration = timerDuration;
    }

    public int getTimerDuration(){
        return this.timerDuration;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.objectIdentity;
        hash = 97 * hash + Objects.hashCode(this.originCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DoorWall other = (DoorWall) obj;
        if (this.objectIdentity != other.objectIdentity) {
            return false;
        }
        return true;
    }
}
