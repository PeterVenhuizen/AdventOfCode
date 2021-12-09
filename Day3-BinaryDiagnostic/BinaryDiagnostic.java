import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

class BinaryDiagnostic {
    
    public static List<String> readDiagnosticReport(String inputFile) throws IOException {
        return Files
            .lines(Paths.get(inputFile))
            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        try {

            DiagnosticReport testReport = new DiagnosticReport(
                readDiagnosticReport("test_input.txt"));
            DiagnosticReport diagnosticReport = new DiagnosticReport(
                readDiagnosticReport("input.txt"));

            System.out.println("Part 1 test (should be 198): " + testReport.getPowerConsumption());
            System.out.println("Part 1 answer: " + diagnosticReport.getPowerConsumption());
            
            System.out.println("Part 2 test (should be 230): " + testReport.getLifeSupportRating());
            System.out.println("Part 2 answer: " + diagnosticReport.getLifeSupportRating());

        } catch (IOException e) {}
    }
}
