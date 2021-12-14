import java.io.*;
import java.util.*;

public class ExtendedPolymerization {

	public static Polymer initPolymer(String inputFile) throws IOException {
		Scanner sc = new Scanner(new File(inputFile));
		String template = sc.nextLine();

		Map<String, String> pairs = new HashMap<>();
		while (sc.hasNext()) {
			String line = sc.nextLine();
			if (line.contains("->")) {
				String pair = line.split(" -> ")[0];
				String insert = line.split(" -> ")[1];
				pairs.put(pair, insert);
			}
		}

		sc.close();
		return new Polymer(template, pairs);
	}

	public static void main(String[] args) {
		try {
			// Polymer test1 = initPolymer("test_input.txt");
			// System.out.println("Test part 1 (should be 1588): " + test1.grow(10));

			Polymer answer = initPolymer("input.txt");
			System.out.println("Answer part 1: " + answer.grow(10));

			Polymer answer2 = initPolymer("input.txt");
			// System.out.println("Answer part 1: " + answer2.growAgain(10));

			// Polymer test2 = initPolymer("test_input.txt");
			// System.out.println("Test part 1 (should be 1588): " + test1.growAgain(10));
			// System.out.println("Test part 2 (should be 2188189693529): " + test2.growAgain(40));

			// Polymer answer2 = initPolymer("input.txt");
			System.out.println("Answer part 2: " + answer2.growAgain(40));

		} catch (IOException e) {}
	}
}
