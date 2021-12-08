import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.*;

public class SevenSegmentDisplay {
    private Map<Integer, List<String>> segmentMap;
    private String[] fourDigitOutput;

    public SevenSegmentDisplay(String[] uniqueSignalPattern, String[] fourDigitOutput) {
        mapSizeToSegment(uniqueSignalPattern);
        this.fourDigitOutput = fourDigitOutput;
    }

    public int get1478() {
        int count = 0;
        int numbers[] = { 2, 3, 4, 7 };
        for (String segment : this.fourDigitOutput) {
            if (IntStream.of(numbers).anyMatch(n -> n == segment.length())) {
                count++;
            }
        }
        return count;
    }

    private void mapSizeToSegment(String[] uniqueSignalPattern) {
        Map<Integer, List<String>> map = new HashMap<>();
        for (String s: uniqueSignalPattern) {
            map.computeIfAbsent(s.length(), k -> new ArrayList<>()).add(s);
        }
        this.segmentMap = map;
    }

    private List<String> stringToList(String s) {
        return s.chars().mapToObj(e -> (char)e)
            .map(e -> Character.toString(e))
            .collect(Collectors.toList());
    }

    private boolean containsAll(String subList, String superList) {
        return stringToList(superList).containsAll(stringToList(subList));
    }

    private String deduceNumber(List<String> possibles, String filterOn, 
        BiPredicate<String, String> filter) {

        return possibles.stream()
            .filter(v -> filter.test(filterOn, v))
            .collect(Collectors.toList())
            .get(0);
    }

    private String selectNotInList(List<String> possibles, List<String> exclude) {
        return possibles.stream()
            .filter(p -> !exclude.contains(p))
            .collect(Collectors.toList())
            .get(0);
    }

    private <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    private String decodeDigit(List<String> digit, List<String> decoded) {
        for (int i = 0; i < decoded.size(); i++) {
            List<String> decodedList = stringToList(decoded.get(i));
            if (listEqualsIgnoreOrder(digit, decodedList)) {
               return Integer.toString(i);
            }
        }
        return "-";
    }



    public int decode() {

        // segments I can't map to a number yet
        List<String> segmentsOfLengthFive = this.segmentMap.get(5);
        List<String> segmentsOfLengthSix = this.segmentMap.get(6);

        // numbers I already know
        String one = this.segmentMap.get(2).get(0);
        String four = this.segmentMap.get(4).get(0);
        String seven = this.segmentMap.get(3).get(0);
        String eight = this.segmentMap.get(7).get(0);

        // three is the only segment of length 5 that contains all values of seven
        String three = deduceNumber(segmentsOfLengthFive, seven, (x, y) -> containsAll(x, y));

        // six is the only element of length 6 that does not contain all values of seven
        String six = deduceNumber(segmentsOfLengthSix, seven, (x, y) -> !containsAll(x, y));

        // all elements of five appear in six
        String five = deduceNumber(segmentsOfLengthFive, six, (x, y) -> containsAll(y, x));

        // nine contains all values of four
        String nine = deduceNumber(segmentsOfLengthSix, four, (x, y) -> containsAll(x, y));

        // two is the only remaining number of length 5 that I don't yet know
        String two = selectNotInList(segmentsOfLengthFive, Arrays.asList(three, five));

        // zero is the only remaining number of length 6 that I don't yet know
        String zero = selectNotInList(segmentsOfLengthSix, Arrays.asList(six, nine));

        // all decoded numbers
        List<String> decoded = Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine);

        // decode the output
        String output = "";
        for (String digit: this.fourDigitOutput) {
            output += decodeDigit(stringToList(digit), decoded);
        }
        // System.out.println(output);

        return Integer.parseInt(output);
    }

}
