package gfx.tile;

import enums.Id;

public interface TileFactory {

    static Tile newInstance(Boolean solid, Id id) {

        if (id == Id.bullet) {
            return new Bullet(solid, id);
        } else if (id == Id.cube) {
            return new Cube(solid, id);
        } else if (id == Id.twirl) {
            return new TwirlBullet(solid, id);
        } else {
            throw new IllegalArgumentException("Id doesnt exist in the Factory.");
        }
    }
}
