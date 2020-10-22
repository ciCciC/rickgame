package physics.attack;

import gfx.tile.Tile;
import physics.RandomNum;

import java.util.stream.IntStream;

public class WideAttack extends Attack {

    public WideAttack(int fireLength) {
        this.setFireLength(fireLength);
    }

    private void calculateAttack(Tile tile, int index) {
        tile.velX = calculateCosTimesSpeed(tile, index);
        tile.velY = calculateSinTimesSpeed(tile, index);
        System.out.println("speed: " + tile.velX);
    }

    private float calculateCosTimesSpeed(Tile tile, int index) {
        return (float) (Math.cos(index * Math.PI) * tile.speed);
    }

    private float calculateSinTimesSpeed(Tile tile, int index) {
        return (float) Math.floor((Math.sin((index * index) * Math.PI) * tile.speed));
    }

    @Override
    protected void generate() {
        IntStream
                .rangeClosed(0, this.fireLength)
                .forEach(index -> {
                    Tile tile = generateTile();
                    tile.speed = (float) RandomNum.generateRandomNum(10, 30);
                    this.calculateAttack(tile, index);
                    this.getHandlerInstance().addBullets(tile);
                });
    }
}
