import org.pcj.PCJ;

import java.io.*;

import org.pcj.*;

import java.io.File;
import java.util.Arrays;

public class Matrix implements Serializable {
    private long[] array;

    public int getCols() {
        return cols;
    }

    private int cols;

    public long get(int row, int col) {
        return array[row * getCols() + col];
    }

    public void set(int row, int col, long val) {
        array[row * getCols() + col] = val;
    }

    public Matrix multiply(Matrix other) throws IOException {
        MatrixMultiplicator.initA = this;
        MatrixMultiplicator.initB = other;

        String nodesFile  = "nodes.txt";
        PCJ.executionBuilder (MatrixMultiplicator.class)
                .addNodes(new File("nodes.txt"))
                .start();
        return this;
    }

    @Serial
    private void writeObject(ObjectOutputStream oos)
            throws IOException {
//        oos.defaultWriteObject();
        oos.writeInt(cols);
        oos.writeObject(array);
    }

    @Serial
    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
//        ois.defaultReadObject();
        cols = ois.readInt();
        array = (long[]) ois.readObject();
    }

    public Matrix(long[] array, int cols) {
        this.array = array;
        this.cols = cols;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "array=" + Arrays.toString(array) +
                ", cols=" + cols +
                '}';
    }
}
