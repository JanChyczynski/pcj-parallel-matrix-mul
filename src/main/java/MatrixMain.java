
import java.io.IOException;

public class MatrixMain  {

    public static void main(String[] args) throws IOException {
//        Matrix a = new Matrix(new long[]{2, 1, 3, 7}, 2);
//        Matrix b = new Matrix(new long[]{1, 0, 0, 1}, 2);

//        Matrix a = new Matrix(new long[]{2, 1, 3, 7}, 2);
//        Matrix b = new Matrix(new long[]{1, 1, 1, 1}, 2);

        int cols = 5;
        Matrix a = new Matrix(RandomArrayToFileGenerator.generateRandomArray(cols*cols), cols);
        Matrix b = new Matrix(RandomArrayToFileGenerator.generateRandomArray(cols*cols), cols);
        System.out.println("A:\n" + a);
        System.out.println("B:\n" + b);

        Matrix c = a.multiply(b);

        System.out.println("Result:\n" + c);
    }
}