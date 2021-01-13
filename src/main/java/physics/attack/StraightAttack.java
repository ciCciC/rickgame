package physics.attack;

import gfx.tile.Tile;
import physics.RandomNum;

public class StraightAttack extends Attack {

    @Override
    protected void generate(Tile tile) {
        tile = calculatePosition(tile);
        tile.velX = (float) RandomNum.generateRandomNum(10, 30);
        this.getHandlerInstance().addBullets(tile);
    }
}
