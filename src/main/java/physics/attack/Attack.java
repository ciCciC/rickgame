package physics.attack;

import entity.Entity;
import game.Handler;
import gfx.TileProperty;
import gfx.tile.Tile;
import lombok.Setter;

@Setter
public abstract class Attack {
    protected int fireLength;

    protected abstract void generate(Tile tile);

    public void attack(Tile tile) {
        generate(tile);
    }

    public Handler getHandlerInstance() {
        return Handler.getInstance();
    }

    public Entity getPlayer() {
        return Handler.getInstance().getPlayer();
    }

    @SuppressWarnings("unchecked")
    public <T extends Tile> T calculatePosition(Tile tile) {
        Entity player = getPlayer();
        int distance_pos = player.getX();

        if (player.getFacing() == 1) {
            distance_pos += player.getWidth();
        } else if(player.getFacing() == -1) {
            distance_pos -= 10;
        }

        tile.x = distance_pos;
        tile.y = player.getBoundsRight().y + 15;
        tile.width = TileProperty.WIDTH;
        tile.height = 20;
        tile.changeFacing(player.facing);
        return (T) tile;
    }
}
