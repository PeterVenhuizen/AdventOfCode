import java.nio.file.FileAlreadyExistsException;

import java.io.*;
import java.util.*;

public class SevenSegmentSearch {

    public static DisplayGroup readyDisplay(String inputFile) throws FileNotFoundException {
        
        List<SevenSegmentDisplay> displays = new ArrayList<>();
        Scanner sc = new Scanner(new File(inputFile));
        while (sc.hasNext()) {
            String[] line = sc.nextLine().split(" ");
            // System.out.println(Arrays.asList(line).indexOf("|"));
            int pipeIndex = Arrays.asList(line).indexOf("|");
            String[] uniqueSignalPattern = Arrays.copyOfRange(line, 0, pipeIndex);
            String[] fourDigitOutput = Arrays.copyOfRange(line, pipeIndex + 1, line.length);

            // System.out.println(Arrays.toString(uniqueSignalPattern));
            // System.out.println(Arrays.toString(fourDigitOutput));

            displays.add(new SevenSegmentDisplay(uniqueSignalPattern, fourDigitOutput));
        }
        sc.close();

        return new DisplayGroup(displays);
    }
    public static void main(String[] args) {

        // Map<Integer, Map<Integer, String> segments = new HashMap<HashMap<>>();

        // 0: abc-efg  - 6
        // 1: --c--f-  - 2
        // 2: a-cde-g  - 5
        // 3: a-cd-fg  - 5
        // 4: -bcd-f-  - 4
        // 5: abc--fg  - 5
        // 6: ab-defg  - 6
        // 7: a-c--f-  - 3
        // 8: abcdefg  - 7
        // 9: abcd-fg  - 6

        // 0: abc-efg  - 6
        // 6: ab-defg  - 6
        // 7: a-c--f-  - 3
        // 9: abcd-fg  - 6

        try {
            DisplayGroup testDisplay = readyDisplay("input.txt");
            System.out.println("Test part 1 (should be 26): " + testDisplay.getOccurencesOf1478());
            System.out.println(testDisplay.decodeEverything());

            // DisplayGroup display = readyDisplay("input.txt");
            // System.out.println("Answer part 1: " + display.getOccurencesOf1478());
        
        } catch (FileNotFoundException e) {}

    }
}
