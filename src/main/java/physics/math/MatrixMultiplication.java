package physics.math;

public class MatrixMultiplication {

    public static void main(String[] args) {
        MatrixMultiplication m = new MatrixMultiplication();
        PVector v = new PVector(100, 75, 50);

        float [][] result = matmul(m.projection, v);
        logMatrix(result);

        PVector vv = PVector.matrixToVec(result);
        System.out.println(vv);
    }

    public float [][] projection = {
            {1, 0, 0},
            {0, 1, 0}
    };

    public static void logMatrix(float [][] m){
        int cols = m[0].length;
        int rows = m.length;

        System.out.println(rows + "x" + cols);
        System.out.println("---------------");

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static float [][] matmul(float [][] a, PVector b){
        float [][] m = PVector.vecToMatrix(b);
        return matmul(a, m);
    }

    public static float [][] matmul(float [][] a, float [][] b){
        int colsA = a[0].length;
        int rowsA = a.length;
        int colsB = b[0].length;
        int rowsB = b.length;

        if(colsA != rowsB){
            System.out.println("Cols of A must match rows of B.");
            return null;
        }

        float [][] result = new float[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {

            for (int j = 0; j < colsB; j++) {

                float sum = 0;

                for (int k = 0; k < colsA; k++) {

                    sum += a[i][k] * b[k][j];

                }
                result[i][j] = sum;
            }
        }
        return result;
    }
}
