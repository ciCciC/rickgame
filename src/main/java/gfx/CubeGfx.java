package gfx;

import physics.math.MatrixMultiplication;
import physics.math.PVector;
import java.awt.geom.Line2D;

import java.awt.*;

public class CubeGfx {

    public PVector[] points;
    public int strokeWeight;

    public double angle = 0.0;

    private int x;
    private int y;
    private int times;

    private int rectangleW;
    private int rectangleH;

    public double testAngleX = 0.0;

    public CubeGfx(int x, int y, int times){
        strokeWeight = 10;
        this.times = times;
        this.x = x;
        this.y = y;

        initCube();
    }

    public void setRectangleSize(int w, int h){
        this.rectangleW = w/2;
        this.rectangleH = h/2;
    }

    public void initCube(){
        points = new PVector[8];

//        points[0] = new PVector(-0.5F, -0.5F, -0.5F);
//        points[1] = new PVector(0.5F, -0.5F, -0.5F);
//        points[2] = new PVector(0.5F, 0.5F, -0.5F);
//        points[3] = new PVector(-0.5F, 0.5F, -0.5F);
//        points[4] = new PVector(-0.5F, -0.5F, 0.5F);
//        points[5] = new PVector(0.5F, -0.5F, 0.5F);
//        points[6] = new PVector(0.5F, 0.5F, 0.5F);
//        points[7] = new PVector(-0.5F, 0.5F, 0.5F);

//        points[0] = new PVector(-0.5F, -0.5F, -0.5F);
//        points[1] = new PVector(0.5F, -0.5F, -0.5F);
//        points[2] = new PVector(0.5F, 0.5F, -0.5F);
//        points[3] = new PVector(-0.5F, 0.5F, -0.5F);
//        points[4] = new PVector(-0.5F, -0.5F, 0.5F);
//        points[5] = new PVector(0.5F, -0.5F, 0.5F);
//        points[6] = new PVector(0.5F, 0.5F, 0.5F);
//        points[7] = new PVector(-0.5F, 0.5F, 0.5F);

        float c = 4.50F;

        points[0] = new PVector(-0.5F, -0.5F, -c);
        points[1] = new PVector(0.5F, -0.5F, -0.5F);
        points[2] = new PVector(0.5F, c, -0.5F);
        points[3] = new PVector(-0.5F, 0.5F, -0.5F);
        points[4] = new PVector(-c, -0.5F, 0.5F);
        points[5] = new PVector(0.5F, -0.5F, 0.5F);
        points[6] = new PVector(0.5F, c, 0.5F);
        points[7] = new PVector(-0.5F, 0.5F, c);


    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

//        g2d.translate(this.x, this.y);

        float[][] rotationZ = {
                {(float)Math.cos(angle), (float)-Math.sin(angle), 0},
                {(float)Math.sin(angle), (float)Math.cos(angle), 0},
                {0, 0, 1}
        };

        float[][] rotationX = {
                {1, 0, 0},
                {0, (float)Math.cos(angle), (float)-Math.sin(angle)},
                {0, (float)Math.sin(angle), (float)Math.cos(angle)},
        };

        float[][] rotationY = {
                {(float)Math.cos(angle), 0, (float)Math.sin(angle)},
                {0, 1, 0},
                {(float)-Math.sin(angle), 0, (float)Math.cos(angle)},
        };

        PVector [] projected = new PVector[8];

        for (int i = 0; i < points.length; i++) {

            PVector rotated = PVector
                    .matrixToVec(MatrixMultiplication
                            .matmul(rotationY, points[i]));

            rotated = PVector
                    .matrixToVec(MatrixMultiplication
                            .matmul(rotationX, rotated));

            rotated = PVector
                    .matrixToVec(MatrixMultiplication
                            .matmul(rotationZ, rotated));

            float distance = 3;
            float z = 1 / (distance - rotated.z);

            float [][] projection = {
                    {z, 0, 0},
                    {0, z, 0}
            };

            PVector projected2d = PVector
                    .matrixToVec(MatrixMultiplication
                            .matmul(projection, rotated));

            projected2d.mult(times);

            projected[i] = projected2d;
        }

        for (int i = 0; i < projected.length; i++) {
            PVector v = projected[i];
            projected[i].x = this.x + projected[i].x + this.rectangleW;
            projected[i].y = this.y + projected[i].y + this.rectangleH;
            g2d.fillOval(this.x+(int)v.x, this.y+(int)v.y, strokeWeight, strokeWeight);
        }

        // Connecting the edges
        for (int i = 0; i < 4; i++) {
            connect(i, (i+1) % 4, projected, g2d);
            connect(i+4, ((i+1) % 4)+4, projected, g2d);
            connect(i, i+4, projected, g2d);
        }
    }

    private void connect(int i, int j, PVector [] points,Graphics2D g){
        PVector a = points[i];
        PVector b = points[j];
        Line2D.Double line = new Line2D.Double(a.x, a.y, b.x, b.y);
        g.draw(line);
    }

    //This goes to ticks!
    public void setAngle(double speed){
        this.angle += speed;

        this.testAngleX += Math.PI;
    }
}
