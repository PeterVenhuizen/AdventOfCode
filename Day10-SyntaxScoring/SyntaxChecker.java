import java.util.*;
import java.math.BigInteger;

public class SyntaxChecker {
    private Map<Character, Character> chunksMap;
    private Map<Character, Integer> pointsMap;
    private Map<Character, BigInteger> completionScore;
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
            put(')', new BigInteger("1"));
            put(']', new BigInteger("2"));
            put('}', new BigInteger("3"));
            put('>', new BigInteger("4"));
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

    public BigInteger getAutocompleteScore() {
        List<Character> incomplete = getIncompleteChunks();
        if (!this.isCorrupt) {
            Collections.reverse(incomplete);
            BigInteger total = new BigInteger("0");
            for (Character c: incomplete) {
                total = total.multiply(new BigInteger("5"));
                Character closing = this.chunksMap.get(c);
                total = total.add(completionScore.get(closing));
            }
            return total;
        }
        return new BigInteger("0");
    }

}
