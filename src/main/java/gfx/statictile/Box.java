package gfx.statictile;

import enums.Id;
import game.Handler;
import gfx.tile.Tile;

import java.awt.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Box extends Tile {

    private int bullits;
    private int alienships;
    private int tiles;
    private int explosions;

    public Box(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
        this.bullits = 0;
        this.alienships = 0;
        this.tiles = 0;
        this.explosions = 0;
    }

    @Override
    public void render(Graphics g) {
        g.setFont(new Font("Monospaced", Font.PLAIN, 30));
        int R = (int) (Math.random( )*256);
        int G = (int)(Math.random( )*256);
        int B= (int)(Math.random( )*256);
        Color randomColor = new Color(R, G, B);
        g.setColor(randomColor);
        g.drawString("XP : " + Handler.kills, x, y);
//        g.drawString("Bullits : " + this.bullits, x, y);
        g.drawString("Aliens : " + this.alienships, x, y+28);
//        g.drawString("Tiles : " + this.tiles, x, y+56);
//        g.drawString("Explosions : " + this.explosions, x, y+84);
    }

    @Override
    public void tick() {
        this.bullits = handler.getBullitSize();

        this.alienships = handler.entity.stream()
                .filter(val -> val.id == Id.alienEnemy)
                .collect(Collectors.toCollection(LinkedList::new))
                .size();

        this.tiles = handler.getTileSize();

        this.explosions = handler.explosions.size();
    }

}
