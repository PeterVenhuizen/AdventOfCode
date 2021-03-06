import java.io.FileNotFoundException;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

class SonarSweep {

    public static List<Integer> readInputFileIntoIntegerList(String inputFile) 
        throws FileNotFoundException {
            Scanner sc = new Scanner(new File(inputFile));
            List<Integer> depthMeasurements = new ArrayList<Integer>();
            while (sc.hasNext()) {
                depthMeasurements.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();
            return depthMeasurements;
        }

    public static int countDepthIncreases(List<Integer> measurements) {
        return (int) IntStream
            .range(1, measurements.size())
            .filter(i -> measurements.get(i) > measurements.get(i-1))
            .count();
    }

    public static int sumOfList(List<Integer> l) {
        return l.stream().reduce(0, Integer::sum);
    }

    public static int countSlidingWindowDepthIncreases(List<Integer> measurements) {

        int index = 0, depthIncreases = 0, windowSize = 3;
        while (index < measurements.size() - windowSize) {
            try {
                List<Integer> firstWindow = measurements.subList(index, index + 3);
                ++index;
                List<Integer> secondWindow = measurements.subList(index, index + 3);

                if (sumOfList(secondWindow) > sumOfList(firstWindow)) 
                    depthIncreases++;
            } catch (IndexOutOfBoundsException e) {}
        }
        return depthIncreases;
    }

    public static void main(String[] args) {

        // part 1 check
        System.out.println(
            "Part 1 test (should be 7): " 
            + countDepthIncreases(Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)));

        // part 2 check
        System.out.println(
            "Part 2 test (should be 5): "
            + countSlidingWindowDepthIncreases(Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)));

        try {
            List<Integer> depthMeasurements = readInputFileIntoIntegerList("day_1_input.txt");
            
            // part 1 - solution
            System.out.println("Part 1 answer: " + countDepthIncreases(depthMeasurements));

            // part 2 - solution
            System.out.println("Part 2 answer: " + countSlidingWindowDepthIncreases(depthMeasurements));
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
    }

}