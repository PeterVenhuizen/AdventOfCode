import java.io.*;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class theTreacheryOfWales {
    
    public static List<Integer> getCrabPositions(String inputFile) throws IOException {
        Scanner sc = new Scanner(new File(inputFile));

        List<Integer> horizontalPos = Arrays.asList(sc.nextLine().split(","))
            .stream()
            .mapToInt(Integer::parseInt)
            .boxed()
            .collect(Collectors.toList());

        sc.close();

        return horizontalPos;
    }

    public static int getCrabFuel(int n) {
        if (n == 0) {
            return n;
        } else {
            return n + getCrabFuel(n - 1);
        }  
    }

    public static int getLowestFuelRequirement(List<Integer> positions, BinaryOperator<Integer> func) {

        Integer minFuelReq = Integer.MAX_VALUE;

        int low = Collections.min(positions);
        int high = Collections.max(positions);

        for (int pos = low; pos <= high; pos++) {
            int thisPos = pos;
            
            int fuel = positions.stream()
                .map(p -> func.apply(p, thisPos))
                .reduce(0, Integer::sum);

            if (fuel < minFuelReq) 
                minFuelReq = fuel;
        }

        return minFuelReq;
    }
    
    public static void main(String[] args) {
        try {

            BinaryOperator<Integer> constantFuelRate = (x, y) -> Math.abs(x - y);
            BinaryOperator<Integer> increasingFuelRate = (x, y) -> getCrabFuel(Math.abs(x - y));

            List<Integer> testPositions = getCrabPositions("test_input.txt");
            List<Integer> positions = getCrabPositions("input.txt");

            System.out.println("Test part 1 (should be 37): " + getLowestFuelRequirement(testPositions, constantFuelRate));
            System.out.println("Answer part 1: " + getLowestFuelRequirement(positions, constantFuelRate));
            
            System.out.println("Test part 2 (should be 168): " + getLowestFuelRequirement(testPositions, increasingFuelRate));
            System.out.println("Answer part 2: " + getLowestFuelRequirement(positions, increasingFuelRate));

        } catch (IOException e) {}
    }
}
