import java.util.*;
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
            // String s = segment;
            // System.out.println(segment + ": " + segment.length());
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

    private List<String> diff(List<String> bigger, List<String> smaller) {
        return bigger.stream()
            .filter(e -> !smaller.contains(e))
            .collect(Collectors.toList());
    }

    public String decodeDigit(List<String> digit, List<List<String>> decoded) {
        for (int i = 0; i < decoded.size(); i++) {
            List<String> dec = decoded.get(i);
            
            if (listEqualsIgnoreOrder(digit, dec)) {
               return Integer.toString(i);
            }
        }
        return "-";
    }

    public boolean containsAll(List<String> list1, List<String> list2) {
        for (String l: list1) {
            if (!list2.contains(l)) 
                return false;
        }
        return true;
    }

    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public int decode() {

        String[] segments = new String[] { "", "", "", "", "", "", "" };

        List<String> one = stringToList(this.segmentMap.get(2).get(0));
        List<String> four = stringToList(this.segmentMap.get(4).get(0));
        List<String> seven = stringToList(this.segmentMap.get(3).get(0));
        List<String> eight = stringToList(this.segmentMap.get(7).get(0));

        // difference between one and seven goes to index 0
        List<String> seg0 = diff(seven, one);
        segments[0] = seg0.get(0);

        // four contains two elements not in one (index 1 and 3)

        // one goes to index 2 and 5

        // nine contains all values of four
        List<String> nine = this.segmentMap.get(6)
            .stream()
            // .filter(e -> diff(four, stringToList(e)).size() == 0)
            .filter(e -> containsAll(four, stringToList(e)))
            .collect(Collectors.toList());
        System.out.println("nine: " + nine);
        nine = stringToList(nine.get(0));
        // System.out.println(nine);
        // System.out.println(eight);
        
        // difference of nine and eight goes to index 4
        List<String> seg4 = diff(eight, nine);
        segments[4] = seg4.get(0);
        // System.out.println(seg4);

        // two is the only number of length 5 that contains segment 4
        List<String> two = this.segmentMap.get(5)
            .stream()
            .filter(e -> diff(seg4, stringToList(e)).size() == 0)
            .collect(Collectors.toList());
        two = stringToList(two.get(0));
        // System.out.println(two);

        // difference of one and two goes to index 5
        List<String> seg5 = diff(one, two);
        segments[5] = seg5.get(0);

        // difference of seg5 and one goes to index 2
        List<String> seg2 = diff(one, seg5);
        segments[2] = seg2.get(0);

        // for (String l6: this.segmentMap.get(6)) {
        //     System.out.println(l6);
        //     System.out.println(containsAll(seven, stringToList(l6)));
        // }

        // six does not contain all values of seven
        List<String> six = this.segmentMap.get(6)
            .stream()
            .filter(e -> !containsAll(seven, stringToList(e)))
            .collect(Collectors.toList());

        System.out.println("six: " + six);
        six = stringToList(six.get(0));

        // zero is the only remaining number of length 6
        System.out.println(this.segmentMap.get(6));
        List<String> zero = new ArrayList<>();
        for (String l6: this.segmentMap.get(6)) {
            if (!containsAll(stringToList(l6), six) && !containsAll(stringToList(l6), nine))
                zero = stringToList(l6);
        }
        System.out.println(zero);

        // difference between 8 and 0 goes to index 3
        List<String> seg3 = diff(eight, zero);
        segments[3] = seg3.get(0);

        // for (String l6: this.segmentMap.get(6)) {
        //     System.out.println(l6 + ": " + diff(seg2, stringToList(l6)));
        // }

        // difference of seg2+3+5 with four is seg1
        List<String> seg1 = diff(four, Arrays.asList(seg2.get(0), seg3.get(0), seg5.get(0)));
        segments[1] = seg1.get(0);

        // segment 6 is the difference of the segments list and eight
        List<String> seg6 = diff(eight, Arrays.asList(segments));
        segments[6] = seg6.get(0);

        // final
        // System.out.println(Arrays.toString(segments));

        List<String> three = Arrays.asList(segments[0], segments[2], segments[3], segments[5], segments[6]);
        List<String> five = Arrays.asList(segments[0], segments[1], segments[3], segments[5], segments[6]);

        List<List<String>> decoded = Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine);



        String output = "";
        for (String digit: this.fourDigitOutput) {
            // System.out.println(digit);
            output += decodeDigit(stringToList(digit), decoded);
        }
        System.out.println(output);

        // return 0;
        return Integer.parseInt(output);
    }

}
