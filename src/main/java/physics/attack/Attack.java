package physics.attack;

import entity.Entity;
import enums.Id;
import game.Handler;
import gfx.tile.Bullet;
import gfx.TileProperty;
import lombok.Setter;

@Setter
public abstract class Attack {
    protected int fireLength;

    protected abstract void generate();

    public void attack(){
        generate();
    }

    public Handler getHandlerInstance() {
        return Handler.getInstance();
    }

    Entity getPlayer(){
        return Handler.getInstance().getPlayer();
    }

    Bullet generateTile(){
        Entity player = getPlayer();
        int distance_pos = player.getX();
        if(player.getFacing() == 1) {
            distance_pos += player.getWidth();
        }
        Bullet bullet = new Bullet(distance_pos, player.getBoundsRight().y + 15,
                TileProperty.WIDTH, 20, true, Id.bullet);
        bullet.changeFacing(player.facing);
        bullet.setTarget(Id.alienEnemy);
        return bullet;
    }
}
