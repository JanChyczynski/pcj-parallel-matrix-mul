
import java.io.IOException;

public class MatrixMain  {

    public static void main(String[] args) throws IOException {


        int cols = 1000;
        Matrix a = new Matrix(RandomArrayToFileGenerator.generateRandomArray(cols*cols), cols);
        Matrix b = new Matrix(RandomArrayToFileGenerator.generateRandomArray(cols*cols), cols);
        if (Parameters.DEBUG) {
            System.out.println("A:\n" + a);
            System.out.println("B:\n" + b);
        }

        long startTime = System.nanoTime();
        Matrix c = a.multiply(b);
        long endTime = System.nanoTime();

        // Calculate elapsed time in milliseconds
        long elapsedTimeInMillis = (endTime - startTime) / 1000000;

        System.out.println("Result:\n" + c);
        System.out.println(elapsedTimeInMillis);
    }
}