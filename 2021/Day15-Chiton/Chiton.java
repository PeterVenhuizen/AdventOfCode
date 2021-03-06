import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Chiton {

	public static Map<Node, Integer> parseMap(String inputFile) throws IOException {
		List<String> lines = Files
			.lines(Paths.get(inputFile))
			.collect(Collectors.toList());
		
		Map<Node, Integer> map = new HashMap<>();
		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				map.put(new Node(x, y), Character.getNumericValue(line.charAt(x)));
			}
		}

		return map;
	}

	private static int increaseByN(int v, int n) {
		return ((v + n) % 9) == 0 ? 9 : (v + n) % 9;
	}

	private static Map<Node, Integer> growMap(Map<Node, Integer> map) {
		Map<Node, Integer> biggerMap = new HashMap<>();

		int maxX = map.keySet().stream().mapToInt(n -> n.x()).max().orElseThrow();
		int maxY = map.keySet().stream().mapToInt(n -> n.y()).max().orElseThrow();

		for (int x = 0; x < 5 * (maxX + 1); x++) {
			for (int y = 0; y < 5 * (maxY + 1); y++) {
				int shiftBy = (x / (maxX + 1) + y / (maxY + 1)) % 9;
				int originalVal = map.get(new Node(x % (maxX + 1), y % (maxY + 1)));
				biggerMap.put(new Node(x, y), increaseByN(originalVal, shiftBy));
			}
		}

		return biggerMap;
	}

	public static void main(String[] args) {

		try {
			Map<Node, Integer> testMap = parseMap("test_input.txt");
			Dijkstra test = new Dijkstra(testMap);
			System.out.println("Test part 1 (should be 40): " + test.getLowestRisk());

			Map<Node, Integer> answerMap = parseMap("input.txt");
			Dijkstra answer = new Dijkstra(answerMap);
			System.out.println("Answer part 1: " + answer.getLowestRisk());

			Map<Node, Integer> testBiggerMap = growMap(testMap);
			Dijkstra test2 = new Dijkstra(testBiggerMap);
			System.out.println("Test part 2 (should be 315): " + test2.getLowestRisk());

			Map<Node, Integer> answerBiggerMap = growMap(answerMap);
			Dijkstra answer2 = new Dijkstra(answerBiggerMap);
			System.out.println("Answer part 2: " + answer2.getLowestRisk());
		} catch (IOException e) {}

	}
}
