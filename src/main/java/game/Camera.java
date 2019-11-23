package game;

import entity.Entity;

public class Camera {

    public int x, y;
    private Game game;

    public void tick(Entity player, Game game){
        this.game = game;
        this.setX(-player.getX() + Game.getFrameWidth()/2);
        this.setY(-player.getY() + Game.getFrameWidth()/3);
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
}
