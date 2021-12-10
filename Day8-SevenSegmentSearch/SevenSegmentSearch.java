import java.io.*;
import java.util.*;

public class SevenSegmentSearch {

    public static DisplayGroup readyDisplay(String inputFile) throws FileNotFoundException {
        
        List<SevenSegmentDisplay> displays = new ArrayList<>();
        Scanner sc = new Scanner(new File(inputFile));
        while (sc.hasNext()) {
            String[] line = sc.nextLine().split(" ");
            int pipeIndex = Arrays.asList(line).indexOf("|");
            String[] uniqueSignalPattern = Arrays.copyOfRange(line, 0, pipeIndex);
            String[] fourDigitOutput = Arrays.copyOfRange(line, pipeIndex + 1, line.length);

            displays.add(new SevenSegmentDisplay(uniqueSignalPattern, fourDigitOutput));
        }
        sc.close();

        return new DisplayGroup(displays);
    }
    public static void main(String[] args) {
        /* 
            Segment configuration: 
                aaaa
               b    c
               b    c
                dddd
               e    f
               e    f
                gggg

            Digit: composing segments - number of segments in digit
            0: abc-efg - 6
            1: --c--f- - 2
            2: a-cde-g - 5
            3: a-cd-fg - 5
            4: -bcd-f- - 4
            5: abc--fg - 5
            6: ab-defg - 6
            7: a-c--f- - 3
            8: abcdefg - 7
            9: abcd-fg - 6
        */

        try {
            DisplayGroup testDisplay = readyDisplay("test_input.txt");
            System.out.println("Test part 1 (should be 26): " + testDisplay.getOccurencesOf1478());

            DisplayGroup display = readyDisplay("input.txt");
            System.out.println("Answer part 1: " + display.getOccurencesOf1478());
                        
            System.out.println("Test part 2 (should be 61229): " + testDisplay.decodeEverything());
            System.out.println("Answer part 2: " + display.decodeEverything());

        } catch (FileNotFoundException e) {}

    }
}
