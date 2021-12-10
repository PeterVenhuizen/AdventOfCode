import java.util.stream.*;
import java.math.BigInteger;
import java.util.*;

public class NavigationSubsystem {
    private List<SyntaxChecker> syntax;

    public NavigationSubsystem(List<SyntaxChecker> syntax) {
        this.syntax = syntax;
    }

    public int getSyntaxErrorScore() {
        return syntax.stream()
            .map(s -> s.getScore())
            .reduce(0, Integer::sum);
    }

    public BigInteger getMiddleScore() {
        List<BigInteger> scores = syntax.stream()
            .map(s -> s.autocomplete())
            .filter(s -> !s.equals(new BigInteger("0")))
            .collect(Collectors.toList());
        
        Collections.sort(scores);
        System.out.println(scores);
        System.out.println(scores.size() + " " + scores.size() / 2);
        return scores.get((scores.size() / 2));
    }
}
