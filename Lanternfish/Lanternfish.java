import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Lanternfish {

    public static LanternfishTracker initLanterfishTracker(String inputFile) throws IOException {
        Scanner sc = new Scanner(new File(inputFile));

        List<Fish> school = Arrays.asList(sc.nextLine().split(","))
            .stream()
            .map(age -> new Fish(Integer.parseInt(age)))
            .collect(Collectors.toList());

        LanternfishTracker tracker = new LanternfishTracker(school);

        sc.close();
        return tracker;
    }
    public static void main(String[] args) {
        try {
            LanternfishTracker testTracker = initLanterfishTracker("test_input.txt");
            System.out.println("Test part 1 (should be 26): " + testTracker.simulate(18));

            // LanternfishTracker tracker = initLanterfishTracker("input.txt");
            // System.out.println("Answer part 1: " + tracker.simulate(80));

            // LanternfishTracker testTracker = initLanterfishTracker("test_input.txt");
            System.out.println("Test part 2 (should be 26984457539): " + testTracker.memorySimulate(256));
            // System.out.println("Test part 1 (should be 26): " + testTracker.simulate(256));

            // LanternfishTracker tracker = initLanterfishTracker("input.txt");
            // System.out.println("Answer part 1: " + tracker.simulate(256));
            // System.out.println(tracker.memorySimulate(256));

        } catch (IOException e) {}
    }
}
