package game.config;

import lombok.Getter;

@Getter
public class GameProperty {
    private static final GameProperty instance = new GameProperty();

    private final int WIDTH = 320;//270//320
    private final int HEIGHT = 200; //14*10//180
    private final int SCALE = 4;
    private final int CPUSLEEP = 10; // 10 == circa 80 fps
    private final int BUFFERSIZE = 3;
    String TITLE = "Alien Invaders Game";

    public synchronized static GameProperty getInstance(){
        if(instance == null) {
            return new GameProperty();
        }
        return instance;
    }

    public int getFrameWidth() {
        return WIDTH * SCALE;
    }

    public int getFrameHeight() {
        return HEIGHT * SCALE;
    }
}
