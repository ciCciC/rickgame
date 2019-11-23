package physics;

import entity.AlienEnemy;
import entity.Entity;
import gfx.tile.Tile;
import physics.interfaces.CollisionBoundsAI;

import java.awt.*;
import java.awt.geom.Arc2D;

public class RectangleBound {

    // TODO: fix the bounds! Make a interface with the getBounds methods so you can give every entity/tile as a object!
    public static void renderBound(Graphics g, Entity e, Color color){

        Graphics2D g2d = (Graphics2D)g;

        Color randomColor = null;

        if(color == null){
            int R = (int) (Math.random( )*256);
            int G = (int)(Math.random( )*256);
            int B= (int)(Math.random( )*256);
            randomColor = new Color(R, G, B);
        }

        g2d.setColor(randomColor);
        g2d.setColor(color == null ? randomColor : color);
        g2d.draw(e.getBoundsTop());
        g2d.setColor(Color.BLUE);
        g2d.draw(e.getBoundsLeft());
        g2d.setColor(Color.BLUE);
        g2d.draw(e.getBoundsRight());
        g2d.setColor(color == null ? randomColor : color);
        g2d.draw(e.getBoundsBottom());

        g2d.setColor(Color.red);
        g2d.draw(e.getBounds());
    }

    public static void renderBoundAI(Graphics g, Tile e, Color color){
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.RED);
        g2d.draw(e.getBounds());

        g2d.setColor(color);
        g2d.draw(e.getBoundsTop());
        g2d.setColor(Color.BLUE);
        g2d.draw(e.getBoundsLeft());
        g2d.setColor(Color.BLUE);
        g2d.draw(e.getBoundsRight());
        g2d.setColor(color);
        g2d.draw(e.getBoundsBottom());
    }

    public static void renderBoundAI(Graphics g, Entity e, Color color){
        Graphics2D g2d = (Graphics2D)g;

        CollisionBoundsAI entityAi = (CollisionBoundsAI) e;

        g2d.setColor(color);
        g2d.draw(e.getBounds());
        g2d.draw(e.getBoundsTop());

        g2d.setColor(e.facing == -1 ? Color.GREEN : color);
        g2d.draw(entityAi.getBoundsLeftAI());

        g2d.setColor(e.facing == 1 ? Color.GREEN : color);
        g2d.draw(entityAi.getBoundsRightAI());
    }

    public static void kortTijdigRenderBound(Graphics g, Tile e, Color color){

        Graphics2D g2d = (Graphics2D)g;

        Color randomColor = null;

        if(color == null){
            int R = (int) (Math.random( )*256);
            int G = (int)(Math.random( )*256);
            int B= (int)(Math.random( )*256);
            randomColor = new Color(R, G, B);
        }

        g2d.setColor(randomColor);
        g2d.setColor(color == null ? randomColor : color);
        g2d.draw(e.getBoundsTop());
        g2d.setColor(Color.BLUE);
        g2d.draw(e.getBoundsLeft());
        g2d.setColor(Color.BLUE);
        g2d.draw(e.getBoundsRight());
        g2d.setColor(color == null ? randomColor : color);
        g2d.draw(e.getBoundsBottom());
    }

    public static void renderSeeingArea(Graphics g, AlienEnemy entity, Color color){
        Graphics2D g2d = (Graphics2D)g;

        AlienEnemy alienEnemy = entity;

        Rectangle leftRec = entity.getLeftArea();
        Rectangle rightRec = entity.getRightArea();

        g2d.setColor(color);
        g2d.draw(new Arc2D.Float(leftRec.x, leftRec.y, leftRec.width + alienEnemy.getLeftAreaWidth(), leftRec.height*2, 90, 90, Arc2D.PIE));

        g2d.setColor(color);
        g2d.draw(new Arc2D.Float(rightRec.x - rightRec.width, rightRec.y, rightRec.width + alienEnemy.getRightAreaWidth(), rightRec.height*2, 0, 90, Arc2D.PIE));

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(entity.getLeftArea());

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(entity.getRightArea());
    }
}
