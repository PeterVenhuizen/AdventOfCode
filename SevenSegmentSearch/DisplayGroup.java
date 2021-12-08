import java.util.*;

public class DisplayGroup {
    private List<SevenSegmentDisplay> displays;

    public DisplayGroup(List<SevenSegmentDisplay> displays) {
        this.displays = displays;
    }

    public int getOccurencesOf1478() {
        return this.displays.stream()
            .map(d -> d.get1478())
            .reduce(0, Integer::sum);
    }

    public int decodeEverything() {
        return this.displays.stream()
            .map(d -> d.decode())
            .reduce(0, Integer::sum);
    }
}
