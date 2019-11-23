package game;

import entity.Entity;
import game.interfaces.RenderInterface;
import physics.TimeManager;

import java.awt.*;

public class KeyManagement implements RenderInterface {

    private StringBuilder unlockCode;
    private boolean unlockedState;
    private int runningTime;
    private TimeManager timeManager;
    private Handler handler;

    public KeyManagement(Handler handler){
        this.unlockCode = new StringBuilder();
        this.runningTime = 0;
        this.timeManager = new TimeManager();
    }

    public void setUnlockCode(char newInput){
        if(this.unlockCode.length() < 4){
            this.unlockCode.append(newInput);
            this.unlockCode = new StringBuilder(this.unlockCode.toString().replaceAll("[^0-9]", ""));
        }
    }

    public void deleteUnlockCode(){
        this.unlockCode = new StringBuilder();
    }

    public StringBuilder getUnlockCode(){
        return this.unlockCode;
    }

    public void setUnlockedState(boolean state){
        this.unlockedState = state;
    }

    public boolean getUnlockedState(){
        return this.unlockedState;
    }

    // TODO: try to replace it with TimeManager instead of being a behavior of KeyManagement
    private void lock(int duration, Entity entity, TimeManager timeManager) {
        if (timeManager.isNotBusy()) {
//            int distanceFromPl = this.getX() + (this.getWidth() * direction);
//            this.bullet = new TwirleBullet(distanceFromPl, this.getY(), 64, 20, true, Id.bullet, handler);
//            this.bullet.setFacing(direction);
//            this.bullet.setTarget(Id.player);
//            this.bullet.setDestination(entity.getX(), entity.getY());

            timeManager.start(duration);
            this.handler.addTimeManagers(timeManager);
//            handler.addBullets(this.bullet);
        }
    }

    @Override
    public void render(Graphics g) {
    }

    @Override
    public void tick() {

        if(this.getUnlockedState()){
            runningTime++;
            System.out.println("Keymanagement timer: " + this.runningTime/100);
        }else{
            runningTime = 0;
        }
    }

    public int getRunningTime(){
        return this.runningTime;
    }
}
