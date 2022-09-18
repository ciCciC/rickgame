package game;

import comparator.ObjectComparator;
import entity.AlienEnemy;
import entity.Daniel;
import entity.Entity;
import entity.Rick;
import enums.Id;
import game.util.GameArea;
import gfx.tile.*;
import lombok.NoArgsConstructor;
import physics.TimeManager;
import physics.attack.StraightAttack;
import physics.attack.WideAttack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


public class Handler {

    private final static Handler instance = new Handler();

    public static int kills = 0;

    //    HIER MOET AANDACHT AAN BESTEED WORDEN VANWEGE MEMORY EFFICIENTIE!
    public LinkedList<Entity> entity = new LinkedList<>();

    public CopyOnWriteArrayList<Tile> tiles = new CopyOnWriteArrayList<>();

    public CopyOnWriteArrayList<File> filePaths = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<Tile> bullets = new CopyOnWriteArrayList<>();

    public CopyOnWriteArrayList<Tile> explosions = new CopyOnWriteArrayList<>();

    public LinkedList<TimeManager> timeManagers = new LinkedList<>();

    private Rick player;

    public Random ranX, ranY;

    public final int TILE_SIZE = 64;

    public synchronized static Handler getInstance(){
        if(instance == null) {
            return new Handler();
        }
        return instance;
    }

    public void render(Graphics g) {

        for (Tile t : tiles) {
            if (getGameArea().getVisibleArea() != null && t.getBounds().intersects(getGameArea().getVisibleArea())) {
                if (!t.hide) {
                    t.render(g);
                }
            }
//            t.render(g);
        }

        for (Entity e : entity) {
//            if (Game.getVisibleArea() != null && e.getBounds().intersects(Game.getVisibleArea())) {
//                if (!e.hide) {
//                    e.render(g);
//                }
////                e.render(g);
//            }

            if (!e.hide) {
                e.render(g);
            }
        }

//        for (Bullet b : bullets) {
//            if (Game.getVisibleArea() != null && b.getBounds().intersects(Game.getVisibleArea())) {
//                b.render(g);
//            }
//        }

        bullets.forEach(bullet -> {
            if (getGameArea().getVisibleArea() != null && bullet.getBounds().intersects(getGameArea().getVisibleArea())) {
                bullet.render(g);
            } else {
                bullets.remove(bullet);
            }
        });

        this.explosions.forEach((explosion) -> explosion.render(g));

        this.timeManagers.forEach(time -> time.render(g));
    }

    public void tick() {

        tiles.sort(new ObjectComparator());

//        for (Tile t : tiles) {
////            t.tick();
//
//            if (Game.getVisibleArea() != null && t.getBounds().intersects(Game.getVisibleArea())) {
//                if (!t.hide) {
//                    t.tick();
//                }
//            }
//        }

        this.tiles.forEach(tile -> {
            if (getGameArea().getVisibleArea() != null && tile.getBounds().intersects(getGameArea().getVisibleArea())) {
                if (!tile.hide) {
                    tile.tick();
                }
            }
        });

        entity.sort(new ObjectComparator());

        this.entity = this.entity.stream()
                .filter(val -> !val.hide)
                .collect(Collectors.toCollection(LinkedList::new));

        for (Entity e : entity) {
//            if (getGameArea().getVisibleArea() != null && e.getBounds().intersects(getGameArea().getVisibleArea())) {
//                if (!e.hide) {
//                    e.tick();
//                }
//
////                e.tick();
//            }

            if (!e.hide) {
                e.tick();
            }
        }

        this.bullets = this.bullets.stream()
                .filter(val -> !val.hide)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        bullets.forEach(bullet -> {
            if (getGameArea().getVisibleArea() != null && bullet.getBounds().intersects(getGameArea().getVisibleArea())) {
                bullet.tick();
            } else {
                bullets.remove(bullet);
            }
        });

//        this.bullets.forEach(bullet -> {
//            if (getGameArea().getVisibleArea() != null && bullet.getBounds().intersects(getGameArea().getVisibleArea())) {
//                bullet.tick();
//            } else {
//                bullets.remove(bullet);
//            }
//        });

//        for (Bullet b : bullets) {
//            if (getGameArea().getVisibleArea() != null && b.getBounds().intersects(getGameArea().getVisibleArea())) {
//                b.tick();
//            }
//        }
        this.explosions = this.explosions.stream()
                .filter(val -> !val.hide)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        this.explosions.forEach(Tile::tick);

        this.timeManagers = this.timeManagers.stream()
                .filter(val -> !val.isNotBusy())
                .collect(Collectors.toCollection(LinkedList::new));

        this.timeManagers.forEach(TimeManager::tick);
    }

    public void addEntity(Entity en) {
        entity.add(en);
    }

    public void removeEntity(Entity en) {
        entity.remove(en);
    }

    public void addTile(Tile ti) {
        tiles.add(ti);
    }

    public void addBullets(Tile bullet) {
        bullets.add(bullet);
    }

    public void addExplosion(Tile explosion) {
        explosions.add(explosion);
    }

    public void removeExplosion(Tile explosion) {
        explosions.remove(explosion);
    }

    public void removeTile(Tile ti) {
        tiles.remove(ti);
    }

    public int getBullitSize() {
        return this.bullets.size();
    }

    public int getTileSize() {
        return this.tiles.size();
    }

    public void addTimeManagers(TimeManager timeManager){
        this.timeManagers.add(timeManager);
    }

    private void initFilesAsAliens(String filePath) {

        File paths = new File(filePath);

        this.filePaths.addAll(Arrays.asList(paths.listFiles()));

        for (File file : paths.listFiles()) {
            if (file.getName().equals(".DS_Store")) {
                this.filePaths.remove(file);
            }
        }
    }

    public void createLevel(BufferedImage level) {

        int width = level.getWidth();
        int height = level.getHeight();

//Dit stukje is om op een leuke wijze de bestanden te vernietigen.
//        String filePath = "./resource/files";
//        initFilesAsAliens(filePath);
        for (int i = 0; i < 5; i++) {
            File tmp = new File("Hakim");
            this.filePaths.add(tmp);
        }

        //Level map
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int pixel = level.getRGB(x, y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                this.initChristmastree(red, green, blue, x, y);
                this.initWalls(red, green, blue, x, y);
                this.initMagicWall(red, green, blue, x, y);
//                this.initPlayer(red, green, blue, x, y);
            }
        }

        //Add filepaths to the box which has a random point in the space!
//        ranX = new Random();
//        ranY = new Random();
//
//        for (int i = 0; i < handler.filePaths.size(); i++) {
//            int xx = ranX.nextInt(1500);
//            int yy = ranY.nextInt(1500);
//            AlienEnemy alienShip = new AlienEnemy(xx, xx, 64, 64, Id.alienEnemy, handler);
//            alienShip.addFile(handler.filePaths.get(i));
//            this.addEntity(alienShip);
//        }
        //Dit is om de AI te testen
//            AlienEnemy alienShip = new AlienEnemy(500, 500, 64, 64, Id.alienEnemy, handler);
//            alienShip.addFile(handler.filePaths.get(0));
//            this.addEntity(alienShip);

        this.initRick(2, 2);
        this.initEnemy("Demonz", 1500);

//        ZombieEnemy zombie1 = new ZombieEnemy(500, 500, 300, 400, Id.zombieEnemy, handler);
//        this.addEntity(zombie1);
    }

    private void initEnemy(String name, int randomRange) {
        ranX = new Random();
        ranY = new Random();

        for (int i = 0; i < this.filePaths.size(); i++) {
            int xx = ranX.nextInt(randomRange);
            int yy = ranY.nextInt(randomRange);
            AlienEnemy enemy = new AlienEnemy(xx, yy, TILE_SIZE, TILE_SIZE, Id.alienEnemy);
            enemy.setDepth(i);
            enemy.addFile(this.filePaths.get(i));
            enemy.setSpeed(5);
            this.addEntity(enemy);
        }
    }

    private void initPlayer(int red, int green, int blue, int x, int y) {
        if (red == 0 && green == 0 && blue == 255) {
//            Player player = new Player(x * 64, y * 64, 64, 64, Id.player, this);
//            Wayahime player = new Wayahime(x * 64, y * 64, 64, 64, Id.player, this);
//            Pewdiepie player = new Pewdiepie(x * 64, y * 64, 64, 64, Id.player, this);
            this.initRick(x, y);
        }
    }

    private void initRick(int x, int y){
        Rick player = new Rick(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, Id.player);
        player.setDepth(2);
        player.setStraightAttack(new StraightAttack());
        player.setWideAttack(new WideAttack(10));
        addEntity(player);
        setPlayer(player);
    }

    private void initWalls(int red, int green, int blue, int x, int y) {
        //Lisa wall left
        if (red == 0 && green == 0 && blue == 0) {
            addTile(new SideWallLeft(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, true, Id.sideWallLeft));
        }

//        if (red == 50 && green == 50 && blue == 50) {
//            addTile(new SideWallRight(x * 64, y * 64, 64, 64, true, Id.sideWallRight, this));
//        }
        if (red == 0 && green == 255 && blue == 0) {
//            addTile(new Wall((x * 64), (y * 64), 64, 64, true, Id.wall, this));
            addTile(new Box((x * TILE_SIZE), (y * TILE_SIZE), TILE_SIZE, TILE_SIZE, true, Id.box));
        }

        if (red == 0 && green == 200 && blue == 0) {
            addTile(new WallLeft(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, true, Id.wallLeft));
        }

        if (red == 0 && green == 100 && blue == 0) {
            addTile(new WallRight(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, true, Id.wallRight));
        }

        // Square wall GREEN SQUARE
        if (red == 0 && green == 210 && blue == 0) {
            addTile(new SquareWall((x * TILE_SIZE), (y * TILE_SIZE), TILE_SIZE, TILE_SIZE, true, Id.squareWall));
        }
    }

    private void initMagicWall(int red, int green, int blue, int x, int y) {
        // Magic Wall BROWN SQUARE
        if (red == 153 && green == 102 && blue == 51) {
            DoorWall magicWall = new DoorWall((x * TILE_SIZE), (y * TILE_SIZE), TILE_SIZE, TILE_SIZE, true, Id.magicWall, Game.keyManagement);
            magicWall.setCode("1234");
            magicWall.setTimerDuration(3);
            addTile(magicWall);
        }
    }

    private void initChristmastree(int red, int green, int blue, int x, int y) {
        int width = 480;
        int height = 640;
        // Christmas tree rgb(102, 153, 0)
        if (red == 102 && green == 153 && blue == 0) {
            Christmastree c_tree = new Christmastree(x * TILE_SIZE, y * TILE_SIZE, width, height, true, Id.christmastree);
            c_tree.setDepth(1);
            addTile(c_tree);
        }
    }

    public Rick getPlayer() {
        return player;
    }

    public void setPlayer(Rick e){
        this.player = e;
    }

    public GameArea getGameArea(){
        return GameArea.getInstance();
    }
}
