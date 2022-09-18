package game;

import entity.Entity;
import enums.*;
import gfx.sprite.*;
import gfx.statictile.*;
import manager.Resource;
import physics.PhysicsManagement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 320;//270//320
    public static final int HEIGHT = 180; //14*10//180
    public static final int SCALE = 4;
    public static final String TITLE = "Alien Game";

    // 10 == circa 80 fps
    public static final int CPUSLEEP = 10;

    private Thread thread;
    private boolean running = false;

    private BufferedImage levelImage, forrestImage, backgroundImage;

    private Image gifImage;

    private static int playerX, playerY;

    public static Handler handler;
    public static KeyManagement keyManagement;
    public static PhysicsManagement physicsManagement;
    private Camera cam;

    public static Sprite wall, wallLeft, wallRight;
    public static Sprite squareWall;
    public static Sprite groundGrass;
    public static Sprite sideWallLeft, sideWallRight;
    public static Sprite[] playerWayahime;

    public static Sprite bullet, poop, basketball;
    public static BufferedImage[] explosion;
    public static Sprite alienShip, zombieEnemy;
    public static Sprite christmasTree;
    public static Box topBar;
    public static File chosenLevel;

    public static int backgroundImageX = 0;
    public static int backgroundImageY = 0;

    private KeyInput keyInput;


    // Dit is een test van de speler voor de achtergrond afbeelding.
    public static int testX, testY;

    public Game(File chosenLevelFile) {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        super.setPreferredSize(size);
        super.setMaximumSize(size);
        super.setMinimumSize(size);
        super.setFocusable(true);
        super.setFocusTraversalKeysEnabled(true);
        chosenLevel = chosenLevelFile;
    }

    private void init() {
        handler = Handler.getInstance();
        keyManagement = new KeyManagement();
        physicsManagement = new PhysicsManagement();

        keyInput = new KeyInput();
        keyInput.setKeyManagement(keyManagement);
        this.addKeyListener(keyInput);

        cam = new Camera();

        this.initTiles();

        this.initEntities();

        bullet = new Sprite( 1, 2);
        basketball = new Sprite( 4, 2);
        poop = new Sprite( 1, 3);

        explosion = new BufferedImage[7];

        topBar = new Box(0, 0, 100, 100, false, Id.topbar);

        handler.tiles.add(topBar);

        try {
            levelImage = ImageIO.read(chosenLevel);
        } catch (IOException ex) {
            System.out.println("Can't load level.");
        }

        try {
            this.backgroundImage = ImageIO.read(new File(Resource.getResourcePath(FolderType.images, "flat_night.png")));
        } catch (IOException ex) {
            System.out.println("Can't find background image.");
        }

        handler.createLevel(levelImage);
    }

    private void initTiles(){
        sideWallLeft = new Sprite( 4, 1);
        sideWallRight = new Sprite( 4, 1);
        wall = new Sprite( 2, 1);
        squareWall = new Sprite( 1, 7);
        wallLeft = new Sprite( 1, 1);
        wallRight = new Sprite( 3, 1);

        try {
            christmasTree = new Sprite(FolderType.images, ImageType.gif, "christmastree.gif");
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.INFO, "Cant read file in initTiles()");
        }
    }

    private void initEntities(){
//        playerWayahime = new Sprite[7];

//        Sprite.initAnimation(playerWayahime, sheet, 4);
//        for (int i = 0; i < playerWayahime.length; i++) {
//            playerWayahime[i] = new Sprite(sheet, (i + 1), 4);
//        }

//        alienShip = new Sprite( 2, 2);
        alienShip = new Sprite( 5, 2);
        try {
            zombieEnemy = new Sprite(FolderType.images, ImageType.gif, "zombieEnemy2.gif");
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.INFO, "Cant read file in initEntities()");
        }
    }

    synchronized void start() {
        if (running) {
            return;
        }
        thread = new Thread(this, "Thread");
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        try {
            thread.join();
            running = false;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.exit(1);
    }

    public boolean isRunning(){
        return running;
    }

    public void stopRunning(){
        running = false;
    }

    @Override
    public void run() {
        this.init();
        this.setFocusable(true);
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        long timer = System.currentTimeMillis();

        double delta = 0;
        double ns = 1000000000 / amountOfTicks;

        int frames = 0;
        int ticks = 0;

        while (running) {
            this.requestFocusInWindow();
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                tick();
                ticks++;
                delta--;
            }

            if(running){
                render();
            }

//            this.setFocusable(true);
//            this.requestFocus();

            try {
                Thread.sleep(CPUSLEEP);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

            frames++;

            if (System.currentTimeMillis() - timer > 1000) {

                System.out.println(frames + " Frames per second " + ticks + " Updates per second");
                timer += 1000;
                frames = 0;
                ticks = 0;
            }
        }
        stop();
    }

    public static int clamp(int var, int min, int max){
        if(var >= max){
            return var = max;
        }else if(var <= min){
            return var = min;
        }else{
            return var;
        }
    }

    /***
     * Renderer for sub-renderer
     */
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

//        Static bar e.g. score and level
        this.initStaticBar();

        //Parallax background movement
        try {
            g.drawImage(this.backgroundImage, 0, 0, getFrameWidth(), getFrameHeigth(), null);
        } catch (Exception ex) {
            System.out.println("Can't find background image.");
        }

        this.initParallaxBackgroundImage(g);

        g.translate(cam.getX(), cam.getY());

//        For continues render
        keyManagement.render(g);
        handler.render(g);
        topBar.render(g);

        g.translate(-cam.getX(), -cam.getY());

        g.dispose();
        bs.show();
    }

    private void initParallaxBackgroundImage(Graphics g) {
        if (this.forrestImage != null) {

            if (backgroundImageX < cam.getX() - getFrameWidth()) {
                backgroundImageX += getFrameWidth();
            }

            if (backgroundImageX > cam.getX() + getFrameWidth()) {
                backgroundImageX -= getFrameWidth();
            }

            int xx = backgroundImageX - cam.getX();
            int bufferX = 0;

            if (backgroundImageX > cam.getX()) {
                bufferX = backgroundImageX - getFrameWidth() - cam.getX();
            } else {
                bufferX = backgroundImageX + getFrameWidth() - cam.getX();
            }

            g.drawImage(this.forrestImage, -xx, 0, getFrameWidth(), getFrameHeigth(), null);

            g.drawImage(this.forrestImage, -bufferX, 0, getFrameWidth(), getFrameHeigth(), null);

        } else {
            try {
                this.forrestImage = ImageIO.read(new File(Resource.getResourcePath(FolderType.images, "parallax_trees.png")));
            } catch (IOException ex) {
                System.out.println("Can't find background image.");
            }
        }
    }

    /***
     * Tick for handeling sub-ticks
     */
    public void tick() {

        keyManagement.tick();
        handler.tick();
        physicsManagement.tick();

        handler.entity.stream().filter((e) -> (e.getId() == Id.player)).forEach((e) -> {
            cam.tick(e, this);
        });

    }

    private void initStaticBar(){
        Entity getPlayer = null;
        Box staticTopbar = null;
        try {
            getPlayer = handler.entity.stream()
                    .filter(value -> value.id == Id.player && !value.hide)
                    .findFirst().get();


            staticTopbar = (Box) handler.tiles.stream()
                    .filter(value -> value.id == Id.topbar)
                    .findFirst().get();


            staticTopbar.setX(getPlayer.getX() - (getFrameWidth() / 2 - 5));
            staticTopbar.setY(getPlayer.getY() - (getFrameHeigth() / 2 - 5));

        } catch (java.util.NoSuchElementException e) {
            System.out.println("Cant get player in GAME class.");
        } catch (java.lang.NullPointerException a){
            System.out.println("Previous player doesnt exist");
        }
    }

    public static int getFrameWidth() {
        return WIDTH * SCALE;
    }

    public static int getFrameHeigth() {
        return HEIGHT * SCALE;
    }

//    public static Rectangle getVisibleArea() {
//        for (int i = 0; i < handler.entity.size(); i++) {
//            Entity e = handler.entity.get(i);
//            if (e.getId() == Id.player) {
//
//                playerX = e.getX();
//                playerY = e.getY();
//                return new Rectangle(playerX - (getFrameWidth() / 2 - 5),
//                        playerY - (getFrameHeigth() / 2 - 5), getFrameWidth() + 10, getFrameHeigth() + 10);
//            }
//        }
//        return null;
//    }

    public static PhysicsManagement getPhysicsManagement() {
        return physicsManagement;
    }

    public static void setPhysicsManagement(PhysicsManagement physicsManagement) {
        Game.physicsManagement = physicsManagement;
    }
}
