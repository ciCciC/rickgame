package game.util;

import entity.Entity;
import game.Handler;
import game.config.GameProperty;

import java.awt.*;

public class GameArea {
    private static final GameArea instance = new GameArea();

    public synchronized static GameArea getInstance(){
        if(instance == null) {
            return new GameArea();
        }
        return instance;
    }

    public Rectangle getVisibleArea() {
        GameProperty gameProperty = GameProperty.getInstance();
        Entity player = Handler.getInstance().getPlayer();
        return new Rectangle(player.getX() - (gameProperty.getFrameWidth() / 2 - 5),
                player.getY() - (gameProperty.getFrameHeight() / 2 - 5), gameProperty.getFrameWidth() + 10, gameProperty.getFrameHeight() + 10);
    }
}
