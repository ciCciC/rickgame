package physics;

import game.interfaces.RenderInterface;

import java.awt.*;

public class TimeManager implements RenderInterface {

    private int from;
    private int to;
    private boolean stop, start;

    public TimeManager(){
        this.from = 0;
        this.to = 0;
        this.stop = true;
    }

    public int getCountdown() {
        return this.from / 100;
    }

    public void setCountdown(int from) {
        this.from = from;
    }

    public void stop(){
        this.from = 0;
        this.to = 0;
        this.stop = true;
        this.start = false;
    }

    public void start(int until){
        this.from = 0;
        this.to = until;
        this.stop = false;
        this.start = true;
    }

    public boolean isNotBusy(){
        return this.stop;
    }

    @Override
    public void render(Graphics g) {
    }

    @Override
    public void tick() {
        if(this.start && this.getCountdown() < this.to){
            this.from += 1;
        }else{
            this.stop();
        }
    }

}
