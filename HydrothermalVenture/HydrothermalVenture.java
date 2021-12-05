import java.util.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.stream.Collectors;

public class HydrothermalVenture {

    public static VentDiagram initVentDiagram(String inputFile) throws IOException {
        List<Vent> vents = Files
            .lines(Paths.get(inputFile))
            .map(line -> new Vent(line))
            .collect(Collectors.toList());

        return new VentDiagram(vents);
    }
    public static void main(String[] args) {
        try {
            VentDiagram testDiagram = initVentDiagram("test_input.txt");
            testDiagram.mapHorizontalAndVertical();
            // testDiagram.print();
            System.out.println("Part 1 test (should be 5): " + testDiagram.getDangerousAreas());

            // testDiagram.mapDiagonal();
            // testDiagram.print();
            // System.out.println("Part 2 test (should be 12): " + testDiagram.getDangerousAreas());

        } catch (IOException e) {}
    }
}
