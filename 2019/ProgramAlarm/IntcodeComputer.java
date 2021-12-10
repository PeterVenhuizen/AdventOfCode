import java.util.*;
import java.util.stream.Collectors;

public class IntcodeComputer {

    public static int processInstructions(List<Integer> memory, int instructionPointer) {

        List<Integer> instruction = memory.subList(instructionPointer, instructionPointer + 4);

        // quit
        if (instruction.get(0) == 99) {
            return memory.get(0);
        } else {
            // 1 = add, 2 = multiply
            boolean sumTheValues = instruction.get(0) == 1;

            int firstValue = memory.get(instruction.get(1)); // pos 1 -> value 1
            int secondValue = memory.get(instruction.get(2)); // pos 2 -> value 2
            int resultValueIndex = instruction.get(3); // pos 3 -> where to store

            memory.set(resultValueIndex, (sumTheValues) 
                ? firstValue + secondValue
                : firstValue * secondValue);

            return processInstructions(memory, instructionPointer + 4);
        }

    }
    public static void main(String[] args) {
        String input = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,10,19,2,9,19,23,2,23,10,27,1,6,27,31,1,31,6,35,2,35,10,39,1,39,5,43,2,6,43,47,2,47,10,51,1,51,6,55,1,55,6,59,1,9,59,63,1,63,9,67,1,67,6,71,2,71,13,75,1,75,5,79,1,79,9,83,2,6,83,87,1,87,5,91,2,6,91,95,1,95,9,99,2,6,99,103,1,5,103,107,1,6,107,111,1,111,10,115,2,115,13,119,1,119,6,123,1,123,2,127,1,127,5,0,99,2,14,0,0";
        List<Integer> initMemory = Arrays.stream(input.split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        // part 1
        // initialize
        List<Integer> memory = new ArrayList<Integer>(initMemory);
        memory.set(1, 12);
        memory.set(2, 2);

        // solution
        System.out.println(processInstructions(memory, 0));

        // part 2
        for (int noun = 0; noun < 100; noun++) {
            outerloop:
            for (int verb = 0; verb < 100; verb++) {
                memory = new ArrayList<Integer>(initMemory);
                memory.set(1, noun);
                memory.set(2, verb);

                if (processInstructions(memory, 0) == 19690720) {
                    System.out.println(100 * noun + verb);
                    break outerloop;
                }

            }
        }

    }
}
