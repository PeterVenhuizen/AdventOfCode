import java.io.*;
import java.util.function.Consumer;

class Dive {

    public static void main(String[] args) {

        Submarine submarine = new Submarine();
        ProperSubmarine properSubmarine = new ProperSubmarine();
        try {
            // part 1 test
            // submarine.processInstructions("day_2_test_input.txt");
            // System.out.println("Part 1 test (should be 150): " + submarine.getFinalPosition());

            // part 1 solution
            submarine.processInstructions("day_2_input.txt");
            System.out.println("Part 1 answer: " + submarine.getFinalPosition());

            // part 2 test
            // properSubmarine.processInstructions("day_2_test_input.txt");
            // System.out.println("Part 2 test (should be 900): " + submarine.getFinalPosition());

            // part 2 solution
            properSubmarine.processInstructions("day_2_input.txt");
            System.out.println("Part 2 answer: " + properSubmarine.getFinalPosition());

        } catch (FileNotFoundException e) {}

        // System.out.println(submarine.getFinalPosition());
        // Consumer<Integer> moveForward = submarine::forward;
        // moveForward.accept(10);
        // System.out.println(submarine.getFinalPosition());

    }
}