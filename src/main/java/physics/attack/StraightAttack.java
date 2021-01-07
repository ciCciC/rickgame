package physics.attack;

import gfx.tile.Bullet;
import physics.RandomNum;

public class StraightAttack extends Attack {

    @Override
    protected void generate() {
        Bullet bullet = generateTile();
        bullet.velX = (float) RandomNum.generateRandomNum(10, 30);
        this.getHandlerInstance().addBullets(bullet);
    }
}
