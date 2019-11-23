package physics.math;

public class PVector {

    public float x;
    public float y;
    public float z;

    public PVector(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public PVector(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static float [][] vecToMatrix(PVector v){
        float [][] m = new float[3][1];
        m[0][0] = v.x;
        m[1][0] = v.y;
        m[2][0] = v.z;
        return m;
    }

    public static PVector matrixToVec(float [][] m){
        PVector v = new PVector();

        v.x = m[0][0];
        v.y = m[1][0];

        if(m.length > 2){
            v.z = m[2][0];
        }

        return v;
    }

    public void mult(int times){
        this.x *= times;
        this.y *= times;
        this.z *= times;
    }

    @Override
    public String toString() {
        return "PVector{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}
