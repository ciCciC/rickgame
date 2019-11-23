package physics;

import java.util.Random;

public class RandomNum {

    public static int generateZeroOrOne(){
        Random r = new Random();
        float tmp = r.nextFloat();
        String pointer = Math.round(tmp) == 1 ? "Right: " : "Left: ";
        System.out.println(pointer + Math.round(tmp));
        return Math.round(tmp);
    }

    public static int generateRandomX(int WIDTH){
        Random r = new Random();
        int tmp = r.nextInt(WIDTH);
        System.out.println("Random x: " + tmp);
        return tmp;
    }

    public static int generateRandomY(int HEIGHT){
        Random r = new Random();
        int tmp = r.nextInt(HEIGHT);
        System.out.println("Random Y: " + tmp);
        return tmp;
    }

    public int negativeOrPositive(){
        Random r = new Random();
        int tmp = (int)r.nextGaussian();
        System.out.println("FLOAT: " + tmp);
        return tmp;
    }

    public static int generateRandomNum(int range){
        Random r = new Random();
        int tmp = r.nextInt(range);
        return tmp;
    }

    public static int generateRandomNum(int low, int high){
        Random r = new Random();
        int result = r.nextInt(high-low)+low;
        return result;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            if(i == 5){
                continue;
            }

            System.out.println("num: " + i);
        }
    }
}
