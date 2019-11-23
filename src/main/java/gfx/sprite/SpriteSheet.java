package gfx.sprite;

import enums.FolderType;
import manager.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {

    private BufferedImage sheet;

    public SpriteSheet(String filename){
        try {
            sheet = ImageIO.read(new File(Resource
                    .getResourcePath(FolderType.ress,
                            filename)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public BufferedImage getSprite(int x, int y){
        return sheet.getSubimage(x*32-32, y*32-32, 32, 32);
    }

    public BufferedImage getSpriteBigHeight(int x, int y){
        return sheet.getSubimage(x*32-32, y*32-32, 32, 64);
    }

    public BufferedImage getSpriteBigHeightandWidth(int x, int y){
        return sheet.getSubimage(x*32-32, y*32-32, 64, 64);
    }
}
