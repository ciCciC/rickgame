package gfx.sprite;

import enums.FolderType;
import gfx.TileProperty;
import manager.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {

    private final static SpriteSheet instance = new SpriteSheet();

    private BufferedImage sheet;

    /**
     * Size of the frame in the sheet
     */
    private final int sX = 32;

    /**
     * Size of the frame in the sheet
     */
    private final int sY = 32;

    public SpriteSheet(){
//        String filename = "spritesheet3.png";
        String filename = "spritesheet3ads.png";
        try {
            sheet = ImageIO.read(new File(Resource
                    .getResourcePath(FolderType.ress,
                            filename)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized static SpriteSheet getInstance() {
        if(instance == null){
            return new SpriteSheet();
        }
        return instance;
    }

    public BufferedImage getSprite(int x, int y){
        return sheet.getSubimage(x*sX-sX, y*sY-sY, sX, sY);
    }

    public BufferedImage getSpriteBigHeight(int x, int y){
        return sheet.getSubimage(x*sX-sX, y*sY-sY, sX, TileProperty.HEIGHT);
    }

    public BufferedImage getSpriteBigHeightAndWidth(int x, int y){
        return sheet.getSubimage(x*sX-sX, y*sY-sY, TileProperty.WIDTH, TileProperty.HEIGHT);
    }
}
