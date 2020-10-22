package gfx.statictile;

import enums.Id;
import game.Handler;
import game.KeyManagement;
import gfx.tile.Tile;

import java.awt.*;

public class TextBox extends Tile {

    private StringBuilder inputCode;
    private String originCode;
    private KeyManagement keyManagement;

    private static final int WIDTH;
    private static final int HEIGHT;

    private static final String MSG = "Enter code: ";

    static {
        WIDTH = 100;
        HEIGHT = 100;
    }

    public TextBox(int x, int y, boolean solid, Id id, KeyManagement keyManagement) {
        super(x, y-20, WIDTH, HEIGHT, solid, id);
        this.inputCode = new StringBuilder();
        this.keyManagement = keyManagement;
    }

    @Override
    public void render(Graphics g) {
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        g.setColor(Color.YELLOW);
        g.drawString(MSG + this.inputCode.toString(), x, y);
    }

    @Override
    public void tick() {
        this.inputCode = keyManagement.getUnlockCode();
    }
}
