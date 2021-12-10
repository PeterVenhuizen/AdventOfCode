import java.util.*;

public class SyntaxChecker {
    private Map<Character, Character> chunksMap;
    private Map<Character, Integer> pointsMap;
    private Map<Character, Integer> completionScore;
    private char[] chunks;
    private boolean isCorrupt;

    public SyntaxChecker(char[] chunks) {
        this.chunks = chunks;
        this.isCorrupt = false;
        init();
    }

    private void init() {
        this.chunksMap = new HashMap<>() {{
            put('(', ')'); 
            put('[', ']');
            put('{', '}');
            put('<', '>');
        }};

        this.pointsMap = new HashMap<>() {{
            put(')', 3);
            put(']', 57);
            put('}', 1197);
            put('>', 25137);
        }};

        this.completionScore = new HashMap<>() {{
            put(')', 1);
            put(']', 2);
            put('}', 3);
            put('>', 4);
        }};
    }

    private boolean isOpening(Character c) {
        Character[] openings = new Character[] { '(', '[', '{', '<' };
        return Arrays.asList(openings).contains(c);
    }

    private boolean isValidChunk(Character opening, Character closing) {
        char correctClosing = this.chunksMap.get(opening);
        return correctClosing == closing;
    }

    private List<Character> getIncompleteChunks() {
        List<Character> filo = new ArrayList<>();
        for (char c : this.chunks) {
            int lastIdx = filo.size() - 1;
            if (isOpening(c)) {
                filo.add(c);
            }
            else if (isValidChunk(filo.get(lastIdx), c)) {
                filo.remove(lastIdx);
            }
            else {
                this.isCorrupt = true;
                return Arrays.asList(c);
            }
        }
        return filo;
    }

    public int getCorruptedScore() {
        List<Character> incomplete = getIncompleteChunks();
        return (this.isCorrupt) ? pointsMap.get(incomplete.get(0)) : 0;
    }

    public Long getAutocompleteScore() {
        List<Character> incomplete = getIncompleteChunks();
        if (!this.isCorrupt) {
            Collections.reverse(incomplete);
            long total = 0L;
            for (Character c: incomplete) {
                total *= 5;
                Character closing = this.chunksMap.get(c);
                total += completionScore.get(closing);
            }
            return total;
        }
        return 0L;
    }

}
