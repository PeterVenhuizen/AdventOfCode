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

    public static long countDepthIncreases(List<Integer> measurements) {
        return IntStream
            .range(1, measurements.size())
            .filter(i -> measurements.get(i) > measurements.get(i-1))
            .count();      
    }

    public static void main(String[] args) {
        System.out.println(countDepthIncreases(Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)) == 7);

        try {
            List<Integer> depthMeasurements = readInputFileIntoIntegerList("day_1_input.txt");
            System.out.println(countDepthIncreases(depthMeasurements));
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
    }

}