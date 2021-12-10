import java.util.*;
import java.math.BigInteger;

public class SyntaxChecker {
    private Map<Character, Character> chunksMap;
    private Map<Character, Integer> pointsMap;
    private Map<Character, BigInteger> completionScore;
    private char[] chunks;

    public SyntaxChecker(char[] chunks) {
        this.chunks = chunks;
        init();
    }

    private void init() {
        this.chunksMap = new HashMap<>();
        chunksMap.put('(', ')');
        chunksMap.put('[', ']');
        chunksMap.put('{', '}');
        chunksMap.put('<', '>');

        this.pointsMap = new HashMap<>();
        pointsMap.put(')', 3);
        pointsMap.put(']', 57);
        pointsMap.put('}', 1197);
        pointsMap.put('>', 25137);

        this.completionScore = new HashMap<>();
        completionScore.put(')', new BigInteger("1"));
        completionScore.put(']', new BigInteger("2"));
        completionScore.put('}', new BigInteger("3"));
        completionScore.put('>', new BigInteger("4"));        
    }

    private boolean isOpening(Character c) {
        Character[] openings = new Character[] { '(', '[', '{', '<' };
        return Arrays.asList(openings).contains(c);
    }

    private boolean isValidChunk(Character opening, Character closing) {
        char correctClosing = this.chunksMap.get(opening);
        return correctClosing == closing;
    }

    public int getScore() {
        List<Character> filo = new ArrayList<>();
        for (char c : chunks) {
            int lastIdx = filo.size() - 1;
            if (isOpening(c)) {
                filo.add(c);
            }
            else if (isValidChunk(filo.get(lastIdx), c)) {
                filo.remove(lastIdx);
            }
            else {
                return pointsMap.get(c);
            }
        }
        return 0;
    }

    private boolean isValid() {
        return getScore() == 0;
    }

    public BigInteger autocomplete() {
        if (isValid()) {
            List<Character> filo = new ArrayList<>();
            for (char c : chunks) {
                int lastIdx = filo.size() - 1;
                if (isOpening(c)) {
                    filo.add(c);
                }
                else if (isValidChunk(filo.get(lastIdx), c)) {
                    filo.remove(lastIdx);
                }
            }
            // System.out.println(filo);

            Collections.reverse(filo);
            BigInteger total = new BigInteger("0");
            for (Character f: filo) {
                total = total.multiply(new BigInteger("5"));
                Character closing = this.chunksMap.get(f);
                // total += completionScore.get(closing);
                total = total.add(completionScore.get(closing));
            }
            // System.out.println(total);
            return total;
        }
        return new BigInteger("0");
    }

}
