package game;

import audio.MusicPlayer;
import audio.Musiclist;
import entity.AlienEnemy;
import entity.Entity;
import entity.Rick;
import enums.AudioType;
import enums.Id;
import gfx.statictile.TextBox;
import gfx.tile.Box;
import gfx.tile.Bullet;
import gfx.tile.Tile;
import physics.RandomNum;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ConcurrentModificationException;

public class KeyInput extends KeyAdapter {

    private Handler handler;

    private MusicPlayer music;

    private KeyManagement keyManagement;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        Entity entity = handler.entity.stream()
                .filter(val -> val.id == Id.player && !val.hide)
                .findFirst()
                .get();

        this.playerKey(entity, e, key);
        this.unlockCode(entity, e);

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        Entity entity = handler.entity.stream()
                .filter(val -> val.id == Id.player)
                .findFirst()
                .get();

        if (key == KeyEvent.VK_UP) {
            entity.setVelY(0);
        } else if (key == KeyEvent.VK_DOWN) {
            entity.setVelY(0);
        } else if (key == KeyEvent.VK_RIGHT) {
            entity.setVelX(0);
            entity.staticFacing = 0;
            entity.degrees = 0;
        } else if (key == KeyEvent.VK_LEFT) {
            entity.setVelX(0);
            entity.staticFacing = 0;
            entity.degrees = 0;
        } else if (key == KeyEvent.VK_X) {
//                pl.setShooting(false);
        }
    }

    public void setKeyManagement(KeyManagement keyManagement) {
        this.keyManagement = keyManagement;
    }

    private void generateBullits(Entity pl, Id id) {
        int fireLength = 10;

        Tile bul = null;

        for (int i = 0; i < fireLength; i++) {

            if (Id.poop == id) {
//                bul = new Poop(pl.getX() + 15, pl.getY() + 15, 64, 64, true, Id.bullet, handler);
            } else if (Id.bullet == id) {
                bul = new Bullet(pl.getX() + 15, pl.getY() + 15, 64, 64, true, Id.bullet, handler);
            }

            bul.setFacing(pl.facing);

            bul.speed = (float) RandomNum.generateRandomNum(10, 30);

            //Wide attack
            this.doWideAttack(bul, i);
            //Circle attack
//            this.doCircleAttak(bul);

            handler.addBullets(bul);
            System.out.println("Bull:" + bul.toString());
        }

//        this.music = new MusicPlayer(Soundlist.uganda_klock, AudioType.Sound);
//        this.music.run();
    }

    /**
     * *
     *
     * @param bul bullit object that has to be shot in wide sequence
     * @param i firelenght position
     */
    private void doWideAttack(Tile bul, int i) {
        float velx = (float) (Math.cos(i * Math.PI) * bul.speed);
        float vely = (float) (Math.sin((i * i) * Math.PI) * bul.speed);

        bul.velX = velx;
        bul.velY = (float) Math.floor(vely);
    }

    /**
     * *
     *
     * @param bul bullit object that has to be shot in circle
     */
    private void doCircleAttak(Tile bul) {
        float degree = (float) RandomNum.generateRandomNum(10, 360);

        float velx = (float) (Math.cos(degree) * bul.speed);
        float vely = (float) (Math.sin(degree) * bul.speed);

        bul.velX = velx;
        bul.velY = (float) Math.floor(vely);
    }

    private void generateAlienships(int amount, Entity player, String name) {
        int maxNum = 1500;
        float degree = (float) RandomNum.generateRandomNum(10, 360);

        for (int i = 0; i < amount; i++) {
            try {
                float velx = (float) (Math.cos(degree));
                float vely = (float) (Math.sin(degree));

//                File tmp = new File("[" + (handler.entity.size() + (i+1)) + "] ./MEMEZ");
                File tmp = new File(name + " " + (i+1));

                AlienEnemy generatedAlien = new AlienEnemy((int) (Math.random() * player.x),
                        (int) (Math.random() * player.y), 64, 64, Id.alienEnemy, handler);

                generatedAlien.addFile(tmp);
                handler.addEntity(generatedAlien);

            } catch (ConcurrentModificationException ex) {
                System.out.println("Exception generateAlienships method");
            }
        }
    }

    private void unlockCode(Entity entity, KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_U) {
            TextBox codeBox = new TextBox(entity.getX(), entity.getY(), true, Id.textbox, handler, keyManagement);
            if (entity.getUnlock()) {
                handler.addTile(codeBox);
                keyManagement.setUnlockCode(e.getKeyChar());
                System.out.println("KeyChar: " + e.getKeyChar());
            } else {
                keyManagement.deleteUnlockCode();
                for (Tile tile : handler.tiles) {
                    if (tile.getId() == Id.textbox) {
                        tile.hide = true;
                    }
                }
//                handler.tile.removeIf(x -> x.getId() == Id.textbox);
            }
        }

        if (entity.getUnlock()) {
            keyManagement.setUnlockCode(e.getKeyChar());
        } else {
            keyManagement.deleteUnlockCode();
            for (Tile tile : handler.tiles) {
                if (tile.getId() == Id.textbox) {
                    tile.hide = true;
                }
            }
//            handler.tile.removeIf(x -> x.getId() == Id.textbox);
        }
    }

    private void playerKey(Entity entity, KeyEvent e, int key) {

//        Rick pl = (Rick) entity;
        Entity pl = entity;

        if (key == KeyEvent.VK_UP) {

        } else if (key == KeyEvent.VK_DOWN) {
            System.out.println("DOWN");
            entity.setVelY(5);
        } else if (key == KeyEvent.VK_RIGHT) {
            System.out.println("RIGHT");
            entity.setVelX(5);
            entity.staticFacing = 1;
            entity.facing = 1;
        } else if (key == KeyEvent.VK_LEFT) {
            System.out.println("LEFT");
            entity.setVelX(-5);
            entity.staticFacing = -1;
            entity.facing = -1;

        } else if (key == KeyEvent.VK_SPACE && !entity.isJumping()) {
            System.out.println("SPACE");
            entity.setJumping(true);
            entity.setFalling(false);
            entity.setVelY(-10);
            this.music = new MusicPlayer(Musiclist.Soundlist.jumping, AudioType.Sound);
            this.music.run();

        } else if (key == KeyEvent.VK_X) {
            pl.shoot();

        } else if (key == KeyEvent.VK_Z) {

            if (handler.getBullitSize() < 40) {
//                this.generateBullits(pl, Id.poop);
            }

        } else if (key == KeyEvent.VK_V) {

            this.generateAlienships(1, pl, "Demonzz");
            System.out.println("ALIEN INVASION");

        } else if (key == KeyEvent.VK_B) {
            int distanceFromPl = 50;
            distanceFromPl = pl.facing == -1 ? pl.getX() - distanceFromPl : pl.getX() + distanceFromPl;

            handler.addTile(new Box(distanceFromPl, pl.getY(), 64, 64, true, Id.box, handler));

        } else if (key == KeyEvent.VK_SHIFT) {

            pl.setVelX(pl.facing == 1 ? 15 : -15);

        } else if (key == KeyEvent.VK_R) {
            entity.hide = true;
            handler.addEntity(new Rick(400, 400, 64, 64, Id.player, handler));
        }
    }
}
