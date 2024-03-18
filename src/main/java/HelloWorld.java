import java.io.File;
import java.io.IOException;
import org.pcj.*;

public class HelloWorld implements StartPoint {

    public static void main(String[] args) throws IOException {
        String nodesFile  = "nodes.txt";
        PCJ.executionBuilder (HelloWorld.class)
                .addNodes(new File("nodes.txt"))
                .start();
    }

    @Override
    public void main() throws Throwable {
        System.out.println("Hello World from PCJ Thread " + PCJ.myId()
                + " out of " + PCJ.threadCount() );
    }
}