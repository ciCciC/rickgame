package gfx.sprite;

import enums.FolderType;
import enums.ImageType;
import manager.Resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {

    public SpriteSheet sheet;
    public BufferedImage image;
    public BufferedImage [] animation;
    public Image imageGif;

    private int index;

    /**
     * Only an image
     * Info about the parameters
     * @param x = column N
     * @param y = row N
     */
    public Sprite(int x, int y){
        image = SpriteSheet.getInstance().getSprite(x, y);
    }

    /**
     * Only for animation
     * @param animation
     * @param row
     * @param width bigger width
     * @param height bigger height
     */
    public Sprite(BufferedImage [] animation, int row, boolean width, boolean height){
        this.animation = animation;
        this.sheet = SpriteSheet.getInstance();

        for (int i = 0; i < this.animation.length; i++) {
            if(height && !width){
                this.animation[i] = sheet.getSpriteBigHeight(i+1, row);
            }else if(height && width){
                this.animation[i] = sheet.getSpriteBigHeightAndWidth(i+1, row);
            }else if(!height && !width){
                this.animation[i] = sheet.getSprite(i+1, row);
            }
        }

        this.index = 0;
    }

    /**
     * Not in sheet available, gif or jpg file
     * @param folderDir name of the folder
     * @param imageType
     * @param filename name of the file with the extension E.G. zombie.gif.
     * @throws java.io.IOException
     */
    public Sprite(FolderType folderDir, ImageType imageType, String filename) throws IOException {
        if(imageType == ImageType.gif) {
            this.imageGif = new ImageIcon(Resource.getResourcePath(folderDir, filename)).getImage();
        } else {
            this.image = ImageIO.read(new File(Resource.getResourcePath(folderDir, filename)));
        }
    }

    public static void initAnimation(Sprite [] animation, SpriteSheet sheet, int row){
        for (int i = 0; i < animation.length; i++) {
            animation[i] = new Sprite((i + 1), row);
        }
    }

    public BufferedImage getBufferedImage(){
        return image;
    }

    public Image getGifImage(){
        return this.imageGif;
    }

    public int getIndex(){
        return this.index;
    }

    public void setIndex(int newIndex){
        this.index = newIndex;
    }

    public void nextIndex(){
        this.index++;

        if(this.index >= this.animation.length){
            this.index = 0;
        }
    }
}
