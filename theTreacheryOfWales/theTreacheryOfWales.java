import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class theTreacheryOfWales {
    
    public static List<Integer> getInput(String inputFile) throws IOException {
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

    public static int getMinFuel(List<Integer> positions) {
        Integer minFuelReq = Integer.MAX_VALUE;

        int low = Collections.min(positions);
        int high = Collections.max(positions);

        System.out.println(low + " : " + high);
        for (int pos = low; pos <= high; pos++) {
            int thisPos = pos;
            int fuel = positions.stream()
                .map(p -> Math.abs(p - thisPos))
                .reduce(0, Integer::sum);
            // System.out.println(pos + ": " + fuel);

            if (fuel < minFuelReq) 
                minFuelReq = fuel;
        }

        return minFuelReq;
    }

    public static int getExpensiveFuel(List<Integer> positions) {
        Integer minFuelReq = Integer.MAX_VALUE;

        int low = Collections.min(positions);
        int high = Collections.max(positions);

        System.out.println(low + " : " + high);
        for (int pos = low; pos <= high; pos++) {
            int thisPos = pos;
            int fuel = positions.stream()
                .map(p -> getCrabFuel(Math.abs(p - thisPos)))
                .reduce(0, Integer::sum);
            if (fuel < minFuelReq) 
                minFuelReq = fuel;
        }

        return minFuelReq;
    }
    
    public static void main(String[] args) {
        try {
            // List<Integer> positions = getInput("test_input.txt");
            List<Integer> positions = getInput("input.txt");
            // System.out.println(positions);
            // System.out.println(getMinFuel(positions));
            System.out.println(getExpensiveFuel(positions));

            // System.out.println(getCrabFuel(1));
            // System.out.println(getCrabFuel(2));
            // System.out.println(getCrabFuel(3));
            // System.out.println(getCrabFuel(4));
            // System.out.println(getCrabFuel(5));
            // System.out.println(getCrabFuel(11));

        } catch (IOException e) {}
    }
}
