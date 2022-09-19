package entity;

import audio.MusicPlayer;
import enums.*;
import game.*;
import gfx.tile.bullet.Beam;
import gfx.tile.bullet.Bullet;
import gfx.tile.*;
import gfx.tile.bullet.TwirlBullet;
import physics.*;
import physics.interfaces.*;

import java.awt.*;
import java.io.File;

public class AlienEnemy extends Entity implements CollisionBoundsAI {

    public File filePath;
    public Color color;
    public boolean deleted;

    public MusicPlayer music;

    public boolean jumpAICommand;

    public int areaWidth;
    public int rightAreaWidth, leftAreaWidth;

    public int analyseBeamId;

    //    private float gravity = 0.08F;
    private float MAX_SPEED;

    private Font font;

    private int jumpingPower;

    private Bullet bullet;
    private Beam beam;

    private TimeManager timeManagerRight, timeManagerLeft;

    public AlienEnemy(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        this.velX = 2;
        this.color = Color.YELLOW;
        this.MAX_SPEED = 10;
        this.facing = 0;
        this.font = new Font("Monospaced", Font.PLAIN, 40);
        this.deleted = false;
        this.falling = true;
        this.jumpingPower = 5;
        this.areaWidth = 200;
        this.initSeeingArea();
        this.initBeamAnalyser();
        this.timeManagerRight = new TimeManager();
        this.timeManagerLeft = new TimeManager();
    }

    public int getRightAreaWidth() {
        return rightAreaWidth;
    }

    public int getLeftAreaWidth() {
        return leftAreaWidth;
    }

    private void initSeeingArea() {
        this.leftAreaWidth = this.areaWidth;
        this.rightAreaWidth = this.areaWidth;
    }

    public void setSpeed(float speed) {
        this.MAX_SPEED = speed;
    }

    public float getSpeed() {
        return this.MAX_SPEED;
    }

    public int getJumpingPower() {
        return jumpingPower;
    }

    public void setJumpingPower(int jumpingPower) {
        this.jumpingPower = jumpingPower;
    }

    public void addFile(File filepath) {
        this.filePath = filepath;
    }

    public boolean deleteTouchedFile() {
        System.out.println("Box contents: " + this.filePath.getName());
        this.deleted = true;
        this.beam.hide = true;

        if (!this.filePath.getName().contains("MEMEZ")) {
            this.deleted = this.filePath.delete();
        }
//        this.music = new MusicPlayer(Soundlist.deadalien, AudioType.Sound);
//        this.music.run();
        return this.deleted;
    }

    @Override
    public void render(Graphics g) {
        g.setFont(font);
        g.setColor(Color.RED);
        g.drawString(this.filePath.getPath(), x, y);

        if (!deleted) {
            g.drawImage(Game.alienShip.getBufferedImage(), x, y, width, height, null);
        } else {
            this.hide = true;
        }

        RectangleBound.renderBoundAI(g, this, Color.RED);
        RectangleBound.renderSeeingArea(g, this, Color.YELLOW);
    }

    @Override
    protected void collision() {

        for (Tile tile : getHandlerInstance().tiles) {

            if (Id.isWall(tile.getId()) && tile.solid) {

                if (getBoundsTop().intersects(tile.getBoundsBottom())) {
                    y = tile.getY() + height;
                    setVelY(0);
                }

                if (this.facing == 1) {
                    if (getBoundsRightAI().intersects(tile.getBoundsLeft()) && !this.isJumping()) {
                        this.jumpAI(-this.getJumpingPower());
                    }
                }

                if (this.facing == -1) {
                    if (getBoundsLeftAI().intersects(tile.getBoundsRight()) && !this.isJumping()) {
                        this.jumpAI(-this.getJumpingPower());
                    }
                }

                if (getBoundsBottom().intersects(tile.getBoundsTop())) {
                    y = tile.getY() - height;
                    setVelY(0);
                    falling = false;
                    jumping = false;
                } else {
                    falling = true;
                }

                if (getBoundsRight().intersects(tile.getBoundsLeft())) {
                    x = tile.getX() - width;
//                    jumpAI(-5);
                    this.setVelX(-2);
                }

                if (getBoundsLeft().intersects(tile.getBoundsRight())) {
                    x = tile.getX() + width;
                    this.setVelX(2);
                }

                if (this.getRightArea().intersects(tile.getBoundsLeft())) {
                    this.rightAreaWidth += -10;
                }

                if (this.getLeftArea().intersects(tile.getBoundsRight())) {
                    this.leftAreaWidth += -10;
                }
            }
        }

        for (Entity entity : getHandlerInstance().entity) {
            if (entity.getId() == Id.player) {
//                Entity player = entity;

//                if (getBoundsTop().intersects(alien.getBounds())) {
//                    y = entity.getY() + height;
//                }
//
//                if (getBounds().intersects(alien.getBoundsTop())) {
//                    y = entity.getY() - height;
//                    falling = false;
//                } else {
//                    falling = true;
//                }

                if (this.getRightArea().intersects(entity.getBoundsLeft())) {
                    this.shoot(2, 1, entity, timeManagerRight);
                }

                if (this.getLeftArea().intersects(entity.getBoundsRight())) {
                    this.shoot(2, -1, entity, timeManagerLeft);
                }

                this.beamBehavior(entity);
            }
        }
    }

    private void jumpAI(int velY) {
        this.setJumping(true);
        this.setFalling(false);
        this.setVelY(velY);
    }

    private void leftAI(int velX) {
        // -5
        this.setVelX(velX);
    }

    private void rightAI(int velX) {
        // 5
        this.setVelX(velX);
    }

    @Override
    public void tick() {

        int tmpX = x;

        x += velX;
        this.facing = tmpX < x ? 1 : -1;

        y += velY;

        if (falling || jumping) {
            velY += this.getGravity();

            if (velY > MAX_SPEED) {
                velY = MAX_SPEED;
            }
        }

        this.collision();
        this.growAreasWidth();
    }

    private void growAreasWidth() {
        if (this.rightAreaWidth < this.areaWidth) {
            this.rightAreaWidth += 2;
        }

        if (this.leftAreaWidth < this.areaWidth) {
            this.leftAreaWidth += 2;
        }
    }

    private void initBeamAnalyser(){
        this.beam = new Beam(this.getX(), this.getY(), 5, true, Id.beam);
        getHandlerInstance().beams.add(this.beam);
    }

    private void beamBehavior(Entity entity){
        this.beam.setFrom(this.getX()+(this.getWidth()/2), this.getY()+this.getHeight()/2);
        this.beam.setTo(entity.getX()+(entity.getWidth()/2), entity.getY()+(entity.getHeight()/2));
    }

    private void shoot(int duration, int direction, Entity entity, TimeManager timeManager) {
        if (timeManager.isNotBusy()) {
            int distanceFromPl = this.getX() + (this.getWidth() * direction);
            this.bullet = new TwirlBullet(distanceFromPl, this.getY(), 64, 20, true, Id.bullet);
            this.bullet.setFacing(direction);
            this.bullet.setTarget(Id.player);
            this.bullet.setDestination(entity.getX(), entity.getY());

            timeManager.start(duration);
            getHandlerInstance().addTimeManagers(timeManager);
            getHandlerInstance().addBullets(this.bullet);
        }
    }

    @Override
    public Rectangle getBoundsTop() {
        return new Rectangle(x + (width / 2) - ((width / 2) / 2), y, width / 2, height / 2);
    }

    @Override
    public Rectangle getBoundsRight() {
        return new Rectangle(x + width - 5, y + 5, 5, height - 10);
    }

    @Override
    public Rectangle getBoundsLeft() {
        return new Rectangle(x, y + 5, 5, height - 10);
    }

    @Override
    public Rectangle getBoundsLeftAI() {
        return new Rectangle(x - (width * 2), y + 5, 50, height - 10);
    }

    @Override
    public Rectangle getBoundsRightAI() {
        return new Rectangle(x + (width * 2), y + 5, 50, height - 10);
    }

    @Override
    public Rectangle getBoundsBottom() {
        return new Rectangle(x + (width / 2) - ((width / 2) / 2), y + (height / 2), width / 2, height / 2);
    }

    public Rectangle getLeftArea() {
        return new Rectangle(x - this.leftAreaWidth, y - (height*3), this.leftAreaWidth, height * 4);
    }

    public Rectangle getRightArea() {
        return new Rectangle(x + this.getWidth(), y - (height*3), this.rightAreaWidth, height * 4);
    }

}
