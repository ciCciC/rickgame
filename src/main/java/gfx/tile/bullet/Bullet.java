package gfx.tile.bullet;

import entity.AlienEnemy;
import entity.Entity;
import enums.Id;
import game.Game;
import game.Handler;
import gfx.sprite.Sprite;
import gfx.tile.Explosion;
import gfx.tile.Tile;
import lombok.NoArgsConstructor;

import java.awt.*;

public class Bullet extends Tile {
    private Id target;
    private Point destination;
    private double growth;

    private int Xd, Yd;
    private double radAngle;

    public Bullet(boolean solid, Id target) {
        super(solid, Id.bullet);
        this.setTarget(target);
        this.velX = 4;
        this.destination = new Point();
        sprite = Game.bullet;
    }

    //    private xAngle
    public Bullet(int x, int y, int width, int height, boolean solid, Id id) {
        super(x, y, width, height, solid, id);
        this.velX = 4;
        this.destination = new Point();
        sprite = Game.bullet;
    }

    public Id getTarget() {
        return target;
    }

    public void setTarget(Id target) {
        this.target = target;
    }

    public void setDestination(int x, int y) {
        this.destination.setLocation(x, y);
        this.growth = (this.destination.getY() - this.getY()) / this.getY();

        this.Xd = Math.abs(this.destination.x - this.getX());
        this.Yd = Math.abs(this.destination.y - this.getY());
        this.radAngle = Math.atan2(this.destination.y, this.destination.x) * 100;
//        this.radAngle = Math.asin((this.Yd/ this.Xd)) / Math.PI * 180;
    }

    public Point getDestination() {
        return this.destination;
    }

//    public void changeFacing(int facing) {
//        this.setFacing(facing);
//        this.velX = facing == -1 ? this.velX : -this.velX;
//    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

//        if (this.getTarget() == Id.player) {
//            this.radAngle += 1;
//            RectangleRotate.rotate(g2d, x, y, width, height, this.radAngle);
//        } else {
//            if (this.getFacing() == -1) {
//                g2d.drawImage(Game.Game.bullet.getBufferedImage(), x + width, y, -width, height, null);
//            } else if (this.getFacing() == 1) {
//                g2d.drawImage(Game.Game.bullet.getBufferedImage(), x, y, width, height, null);
//            }
//        }

//        System.out.println("Facing: " + this.getFacing());

        if (this.getFacing() == -1 && this.velX < 0) {
            g2d.drawImage(sprite.getBufferedImage(), x, y, width, height, null);
        } else if(this.getFacing() == -1 && this.velX > 0) {
            g2d.drawImage(sprite.getBufferedImage(), x, y, -width, height, null);
        } else if (this.getFacing() == 1 && this.velX < 0) {
            g2d.drawImage(sprite.getBufferedImage(), x, y, -width, height, null);
        } else if (this.getFacing() == 1 && this.velX > 0) {
            g2d.drawImage(sprite.getBufferedImage(), x, y, width, height, null);
        }
    }

    @Override
    public void tick() {
        x += this.getFacing() == -1 ? -velX : velX;
        y += velY + (this.growth * 4);

        this.bulletCollision();
    }

    private void bulletCollision() {
        boolean isHit = false;

        for (Entity tile : getHandlerInstance().entity) {

            if (tile.getId() == this.getTarget()) {

                if (getBoundsBottom().intersects(tile.getBoundsTop())
                || getBoundsTop().intersects(tile.getBoundsBottom())
                || getBoundsRight().intersects(tile.getBoundsLeft())
                || getBoundsLeft().intersects(tile.getBoundsRight())) {
                    isHit = true;
                }

                if (isHit) {
                    getHandlerInstance().addExplosion(new Explosion(tile.getX(), tile.getY(), 64, 64, true, Id.explosion));
//                    For deletion of a file.
                    if (this.target == Id.alienEnemy) {
                        AlienEnemy alienEnemy = (AlienEnemy) tile;
                        alienEnemy.deleteTouchedFile();
                        tile.hide = true;
                    }
                    this.hide = true;
                    Handler.kills++;
//                    this.music = new MusicPlayer("alienexplosion", AudioType.Sound);
//                    this.music.run();
                    break;
                }
            }
        }
    }

    public double getRadAngle() {
        return radAngle;
    }

    public void setRadAngle(double radAngle) {
        this.radAngle = radAngle;
    }

    @Override
    public String toString() {
        return "Bullet X= " + this.velX + " | " + "Y= " + this.velY;
    }
}
