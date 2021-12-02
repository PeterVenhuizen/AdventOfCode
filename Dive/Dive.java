import java.io.*;
import java.util.*;
// import java.util.function.Consumer;

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

class ProperSubmarine extends Submarine {
    protected int aim;

    public ProperSubmarine() {
        super();
        this.aim = 0;
    }

    @Override
    protected void forward(int x) {
        this.horizontalPosition += x;
        this.depth += this.aim * x;
    }

    @Override
    protected void down(int x) {
        this.aim += x;
    }

    @Override
    protected void up(int x) {
        this.aim -= x;
    }
}

class Dive {

    public static void main(String[] args) {

        Submarine sub = new Submarine();
        ProperSubmarine sub2 = new ProperSubmarine();
        try {
            // part 1 test
            // sub.processInstructions("day_2_test_input.txt");
            // System.out.println("Part 1 test (should be 150): " + sub.getFinalPosition());            

            // part 1 solution
            sub.processInstructions("day_2_input.txt");
            System.out.println("Part 1 answer: " + sub.getFinalPosition());

            // part 2 test
            // sub2.processInstructions("day_2_test_input.txt");
            // System.out.println("Part 2 test (should be 900): " + sub.getFinalPosition());   

            // part 2 solution
            sub2.processInstructions("day_2_input.txt");
            System.out.println("Part 2 answer: " + sub2.getFinalPosition());

        } catch (FileNotFoundException e) {}

        // System.out.println(sub.getFinalPosition());
        // Consumer<Integer> moveForward = sub::forward;
        // moveForward.accept(10);
        // System.out.println(sub.getFinalPosition());

    }
}