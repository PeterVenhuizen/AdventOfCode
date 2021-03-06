import java.util.stream.*;
import java.util.*;

public class NavigationSubsystem {
    private List<SyntaxChecker> syntax;

    public NavigationSubsystem(List<SyntaxChecker> syntax) {
        this.syntax = syntax;
    }

    public int getSyntaxErrorScore() {
        return syntax.stream()
            .map(s -> s.getCorruptedScore())
            .reduce(0, Integer::sum);
    }

    public Long getMiddleAutocompleteScore() {
        List<Long> scores = syntax.stream()
            .map(s -> s.getAutocompleteScore())
            .filter(s -> s != 0)
            .collect(Collectors.toList());
        
        Collections.sort(scores);
        return scores.get((scores.size() / 2));
    }
}
