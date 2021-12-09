import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class SmokeBasin {

    public static Heightmap scanCave(String inputFile) throws IOException {
        List<String> heightmapInput = Files
            .lines(Paths.get(inputFile))
            .collect(Collectors.toList());
        
        return new Heightmap(heightmapInput);
    }
    public static void main(String[] args) {
        try {
            Heightmap test = scanCave("test_input.txt");
            System.out.println("Test part 1 (should be 15): " + test.getRiskLevel());

            Heightmap smokeBasin = scanCave("input.txt");
            System.out.println("Answer part 1: " + smokeBasin.getRiskLevel());

            System.out.println("Test part 2 (should be 1134): " + test.getBasins());
            System.out.println("Answer part 2: " + smokeBasin.getBasins());

        } catch (IOException e) {}
    }
}
