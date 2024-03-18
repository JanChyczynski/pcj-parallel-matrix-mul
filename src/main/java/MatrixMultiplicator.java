import org.pcj.PCJ;
import org.pcj.RegisterStorage;
import org.pcj.StartPoint;
import org.pcj.Storage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@RegisterStorage(MatrixMultiplicator.Shared.class)
public class MatrixMultiplicator implements StartPoint {
    public static Matrix initA;
    public static Matrix initB;
//    public Matrix a;
//    public Matrix b;
    public long[] c;

    @Storage(MatrixMultiplicator.class)
//    enum Shared {a, b, c}
    enum Shared {c}

    @Override
    public void main() throws Throwable {
        System.out.println("Start " + PCJ.myId());
        int matrixSize = initA.getCols();
        if (PCJ.myId() == 0) {
            System.out.println("A: " + initA.toString());
            System.out.println("B: " + initB.toString());
            PCJ.put(new long[matrixSize*matrixSize], 0, Shared.c);
        }
        int blocksNum = (int) Math.sqrt(PCJ.threadCount());

        int normalBlockSize = matrixSize / blocksNum;
        int myBlockSizeCol = (PCJ.myId() % blocksNum == blocksNum - 1) ?
                matrixSize - normalBlockSize * (blocksNum - 1) : normalBlockSize;
        int myBlockSizeRow = (PCJ.myId() / blocksNum == blocksNum - 1) ?
                matrixSize - normalBlockSize * (blocksNum - 1) : normalBlockSize;

        int rowBlockStart = (PCJ.myId() / blocksNum) * normalBlockSize;
        int colBlockStart = (PCJ.myId() % blocksNum) * normalBlockSize;

//        System.out.println("Before barrier1 " + PCJ.myId());
        PCJ.barrier();
//        System.out.println("After barrier1 " + PCJ.myId());
        if (PCJ.myId() < blocksNum * blocksNum) {
            System.out.println("Before for " + PCJ.myId());
            for (int col = colBlockStart; col < colBlockStart + myBlockSizeCol; col++) {
                for (int row = rowBlockStart; row < rowBlockStart + myBlockSizeRow; row++) {
                    long cellValue = 0;
                    for (int i = 0; i < matrixSize; i++) {
                        cellValue += initA.get(row, i) * initB.get(i, col);
                    }
                    PCJ.put(cellValue, 0, Shared.c, row * matrixSize + col);
                }
            }
        }

//        System.out.println("Before barrier2 " + PCJ.myId());
        PCJ.barrier();
        System.out.println("After barrier2 " + PCJ.myId());
        if (PCJ.myId() == 0) {
            System.out.println(Arrays.toString((long[]) PCJ.get(0, Shared.c)));
        }

//        System.out.println(PCJ.get(0, Shared.a).toString());
//        System.out.println(PCJ.get(0, Shared.b).toString());


    }
}