import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomArrayToFileGenerator {
    public static void main(String[] args) {
        int n = 10; // Change the size of the array as needed

        // Generate random array
        long[] randomArray = generateRandomArray(n);

        // Write array to a text file
        writeArrayToFile(randomArray, "random_array.txt");

        System.out.println("Random array of size " + n + " generated and written to random_array.txt");

        // Read array from file and print it
        long[] readArray = readArrayFromFile("random_array.txt");
        if (readArray != null) {
            System.out.println("Array read from file:");
            printArray(readArray);
        }
    }

    public static long[] generateRandomArray(int n) {
        long[] arr = new long[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = (long) random.nextInt(200_000) - 100_000 ;
        }
        return arr;
    }

    private static void writeArrayToFile(long[] arr, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < arr.length; i++) {
                writer.write(Long.toString(arr[i]));
                if (i < arr.length - 1) {
                    writer.write(" "); // Add space between numbers
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private static long[] readArrayFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            String[] values = line.split(" ");
            long[] arr = new long[values.length];
            for (int i = 0; i < values.length; i++) {
                arr[i] = Long.parseLong(values[i]);
            }
            reader.close();
            return arr;
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
            return null;
        }
    }

    private static void printArray(long[] arr) {
        for (long value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}