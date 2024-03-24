import org.pcj.PCJ;
import org.pcj.RegisterStorage;
import org.pcj.StartPoint;
import org.pcj.Storage;


@RegisterStorage(MatrixMultiplicator.Shared.class)
public class MatrixMultiplicator implements StartPoint {
    public static Matrix initA;
    public static Matrix initB;
    public static Matrix resultC;
    public long[] c;

    @Storage(MatrixMultiplicator.class)
    enum Shared {c}

    @Override
    public void main() {
        System.out.println("Start " + PCJ.myId());
        int matrixSize = initA.getCols();
        if (PCJ.myId() == 0) {
//            System.out.println("A: " + initA.toString());
//            System.out.println("B: " + initB.toString());
            PCJ.put(new long[matrixSize * matrixSize], 0, Shared.c);
        }
        int blocksNum = (int) Math.sqrt(PCJ.threadCount());

        int normalBlockSize = matrixSize / blocksNum;
        int myBlockSizeCol = (PCJ.myId() % blocksNum == blocksNum - 1) ?
                matrixSize - normalBlockSize * (blocksNum - 1) : normalBlockSize;
        int myBlockSizeRow = (PCJ.myId() / blocksNum == blocksNum - 1) ?
                matrixSize - normalBlockSize * (blocksNum - 1) : normalBlockSize;

        int rowBlockStart = (PCJ.myId() / blocksNum) * normalBlockSize;
        int colBlockStart = (PCJ.myId() % blocksNum) * normalBlockSize;

        PCJ.barrier();
        if (PCJ.myId() < blocksNum * blocksNum) {
//            System.out.println("Before for " + PCJ.myId());
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

        PCJ.barrier();
//        System.out.println("After barrier2 " + PCJ.myId());
        if (PCJ.myId() == 0) {
            MatrixMultiplicator.resultC = new Matrix((long[]) PCJ.get(0, Shared.c), matrixSize);
//            System.out.println(Arrays.toString((long[]) PCJ.get(0, Shared.c)));
        }
    }
}