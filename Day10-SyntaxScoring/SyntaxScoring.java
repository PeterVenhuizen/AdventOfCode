import java.io.*;
import java.util.*;

public class Day10 {

    public static NavigationSubsystem getNavigationSubsystem(String inputFile) throws IOException {
        List<SyntaxChecker> syntax = new ArrayList<>();
		Scanner sc = new Scanner(new File(inputFile));
		while (sc.hasNext()) {
			String line = sc.nextLine();
			char[] chunks = line.toCharArray();
			syntax.add(new SyntaxChecker(chunks));
		}
		sc.close();
		return new NavigationSubsystem(syntax);
    }

	public static void main(String[] args) {
		try {
			NavigationSubsystem test = getNavigationSubsystem("test_input.txt");
			System.out.println("Test part 1 (should be 26397): " + test.getSyntaxErrorScore());

			NavigationSubsystem nav = getNavigationSubsystem("input.txt");
			System.out.println("Answer part 1: " + nav.getSyntaxErrorScore());

			System.out.println("Test part 2 (should be 288957): " + test.getMiddleScore());
			System.out.println("Answer part 2: " + nav.getMiddleScore());

		} catch (IOException e) {}
	}
}
