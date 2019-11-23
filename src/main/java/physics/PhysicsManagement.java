package physics;

import game.interfaces.RenderInterface;

import java.awt.*;

public class PhysicsManagement implements RenderInterface {

    private float gravity;

    public PhysicsManagement(){
        this.gravity = 0.08F;
    }

    public PhysicsManagement(float gravity) {
        this.gravity = gravity;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    @Override
    public void render(Graphics g) {
    }

    @Override
    public void tick() {
    }



}
