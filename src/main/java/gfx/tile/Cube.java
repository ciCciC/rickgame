package gfx.tile;

import entity.Entity;
import enums.Id;
import gfx.CubeGfx;

import java.awt.*;
import java.util.stream.Collectors;

public class Cube extends Tile {

    private CubeGfx cubeGfx3D;

    public Cube(int x, int y, int width, int height, boolean solid, Id id) {
        super(x, y, width, height, solid, id);

        this.cubeGfx3D = new CubeGfx(x, y, 100);
        this.cubeGfx3D.setRectangleSize(width, height);
    }

    @Override
    public void render(Graphics g) {
        this.cubeGfx3D.render(g);

//        RectangleBound.kortTijdigRenderBound(g, this, Color.RED);
    }

    @Override
    public void tick() {
        this.cubeGfx3D.setAngle(0.05);

        for (Entity entity : getHandlerInstance().entity.stream().filter(val -> val.id == Id.alienEnemy).collect(Collectors.toList())) {

            if(getBoundsBottom().intersects(entity.getBoundsTop())){
                System.out.println("RAAAAAKKK");
            }
        }
    }
}
