package physics.attack;

import gfx.tile.Tile;
import gfx.tile.TileFactory;
import physics.RandomNum;

import java.util.stream.IntStream;

public class CircleAttack extends Attack {

    public CircleAttack(int fireLength) {
        this.setFireLength(fireLength);
    }

    private void calculateAttack(Tile tile) {
        float degree = genRandomDegree();
        tile.velX = calculateCosAndSpeed(tile, degree);
        tile.velY = calculateSinAndSpeed(tile, degree);
    }

    private float genRandomDegree(){
        return (float) RandomNum.generateRandomNum(10, 360);
    }

    private float calculateCosAndSpeed(Tile tile, float degree) {
        return (float) (Math.cos(degree) * tile.speed);
    }

    private float calculateSinAndSpeed(Tile tile, float degree) {
        return (float)  Math.floor((Math.sin(degree) * tile.speed));
    }

    @Override
    protected void generate(Tile tile) {
        IntStream
                .rangeClosed(0, fireLength)
                .forEach(index -> {
                    Tile newTile = TileFactory.newInstance(tile.solid, tile.id);
                    newTile = calculatePosition(newTile);
                    newTile.speed = (float) RandomNum.generateRandomNum(10, 30);
                    this.calculateAttack(newTile);
                    this.getHandlerInstance().addBullets(newTile);
                });
    }
}
