import java.io.*;
import java.util.*;

class Submarine {
    protected int horizontalPosition;
    protected int depth;

    public Submarine() {
        this.horizontalPosition = 0;
        this.depth = 0;
    }

    public void processInstructions(String inputFile) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(inputFile));
        while (sc.hasNext()) {
            String[] line = sc.nextLine().split("\\s+");
            
            String direction = line[0];
            int x = Integer.parseInt(line[1]);

            switch (direction) {
                case "forward":
                    this.forward(x);
                    break;
                case "up":
                    this.up(x);
                    break;
                case "down":
                    this.down(x);
                    break;
            }
        }
        sc.close();
    }

    protected void forward(int x) {
        this.horizontalPosition += x;
    }

    protected void down(int x) {
        this.depth += x;
    }

    protected void up(int x) {
        this.depth -= x;
    }

    public int getFinalPosition() {
        return this.horizontalPosition * this.depth;
    }
}