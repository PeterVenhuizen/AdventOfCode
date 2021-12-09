import java.util.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.util.function.Predicate;

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
            VentDiagram ventDiagram = initVentDiagram("input.txt");

            Predicate<Vent> isHorizontalOrVerticalVent = v -> !v.isDiagonal();
            Predicate<Vent> isDiagonalVent = v -> v.isDiagonal();

            // part 1 test
            testDiagram.mapVents(isHorizontalOrVerticalVent);
            // testDiagram.print();
            System.out.println("Part 1 test (should be 5): " + testDiagram.getDangerousAreas());

            // part 1 answer
            ventDiagram.mapVents(isHorizontalOrVerticalVent);
            System.out.println("Part 1 answer: " + ventDiagram.getDangerousAreas());

            // part 1 test
            testDiagram.mapVents(isDiagonalVent);
            // testDiagram.print();
            System.out.println("Part 2 test (should be 12): " + testDiagram.getDangerousAreas());

            // part 2 answer
            ventDiagram.mapVents(isDiagonalVent);
            System.out.println("Part 2 answer: " + ventDiagram.getDangerousAreas());

        } catch (IOException e) {}
    }
}
