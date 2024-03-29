package physics.attack;

import gfx.tile.Tile;
import gfx.tile.TileFactory;
import physics.RandomNum;

import java.util.stream.IntStream;

public class WideAttack extends Attack {

    public WideAttack(int fireLength) {
        this.setFireLength(fireLength);
    }

    private void calculateAttack(Tile tile, int index) {
        tile.velX = calculateCosTimesSpeed(tile, index);
        tile.velY = calculateSinTimesSpeed(tile, index);
//        System.out.println("speed: " + tile.velX);
    }

    private float calculateCosTimesSpeed(Tile tile, int index) {
        return (float) (Math.cos(index * Math.PI) * tile.speed);
    }

    private float calculateSinTimesSpeed(Tile tile, int index) {
        return (float) Math.floor((Math.sin((index * index) * Math.PI) * tile.speed));
    }

    private void validatePosition(Tile tile) {
        // TODO: Fix position
        int playerPos = getPlayer().getX();
        int playerRight = playerPos + getPlayer().getWidth();

        boolean playerFacingDirection = getPlayer().isFacingLeft() || getPlayer().isFacingRight();

        System.out.println("Tile x pos: \t" + tile.velX);
        System.out.println("Player x pos: \t" + tile.velX);

        int playerLeft = playerPos - 200;

        if(getPlayer().isFacingLeft() && tile.velX > 0) {
            tile.setX(playerRight);
//            return playerRight;
        } else if(getPlayer().isFacingLeft() && tile.velX < 0) {
            tile.setX(playerLeft);
//            return playerPos - 20;
        } else if(getPlayer().isFacingNeutral() && tile.velX > 0) {
            tile.setX(playerRight);
//            return playerRight;
        } else if(getPlayer().isFacingNeutral() && tile.velX < 0) {
            tile.setX(playerLeft);
//            return playerPos - 20;
        }

        System.out.println("Tile x pos: \t" + tile.velX);
        System.out.println("Player x pos: \t" + tile.velX);

//        if (playerFacingDirection && tile.velX > 0) {
//            return playerRight;
//        } else if (playerFacingDirection && tile.velX < 0) {
//            return playerPos - 20;
//        }
//        return playerPos;

//        return playerPos;
    }

    @Override
    protected void generate(Tile tile) {
        IntStream
                .rangeClosed(0, this.fireLength)
                .mapToObj(index -> {
                    Tile newTile = TileFactory.newInstance(tile.solid, tile.id);
                    newTile = calculatePosition(newTile);
                    newTile.speed = (float) RandomNum.generateRandomNum(10, 30);
                    this.calculateAttack(newTile, index);
                    return newTile;
                })
                .forEach(x -> {
                    this.validatePosition(x);
//                    int xPos = this.validatePosition(x);
//                    x.setX(xPos);
                    this.getHandlerInstance().addBullets(x);
                });
    }
}
