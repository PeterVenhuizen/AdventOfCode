import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Chiton {

	// public static Map<Coord, Integer> parseMap(String inputFile) throws IOException {
	public static Map<Node, Integer> parseMap(String inputFile) throws IOException {
		List<String> lines = Files
			.lines(Paths.get(inputFile))
			.collect(Collectors.toList());
		
		// Map<Coord, Integer> map = new HashMap<>();
		Map<Node, Integer> map = new HashMap<>();
		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				// map.put(new Coord(x, y), Character.getNumericValue(line.charAt(x)));
				map.put(new Node(x, y), Character.getNumericValue(line.charAt(x)));
			}
		}

		return map;
	}

	// private static int getMaxX(Map<Coord, Integer> map) {
	private static int getMaxX(Map<Node, Integer> map) {
		// return map.keySet().stream().mapToInt(Coord::x).max().orElseThrow();
		// return map.keySet().stream().mapToInt(Node::x).max().orElseThrow();
		return map.keySet().stream().mapToInt(n -> n.x).max().orElseThrow();
	}

	// private static int getMaxY(Map<Coord, Integer> map) {
	private static int getMaxY(Map<Node, Integer> map) {
		// return map.keySet().stream().mapToInt(Coord::y).max().orElseThrow();
		// return map.keySet().stream().mapToInt(Node::y).max().orElseThrow();
		return map.keySet().stream().mapToInt(n -> n.y).max().orElseThrow();
	}

	private static int increaseByN(int v, int n) {
		return ((v + n) % 9) == 0 ? 9 : (v + n) % 9;
	}

	// private static Map<Coord, Integer> growMap(Map<Coord, Integer> map) {
	private static Map<Node, Integer> growMap(Map<Node, Integer> map) {
		// Map<Coord, Integer> biggerMap = new HashMap<>();
		Map<Node, Integer> biggerMap = new HashMap<>();

		int maxX = getMaxX(map);
		int maxY = getMaxY(map);

		for (int x = 0; x < 5 * (maxX + 1); x++) {
			for (int y = 0; y < 5 * (maxY + 1); y++) {
				int shiftBy = (x / (maxX + 1) + y / (maxY + 1)) % 9;
				int originalVal = map.get(new Node(x % (maxX + 1), y % (maxY + 1)));
				biggerMap.put(new Node(x, y), increaseByN(originalVal, shiftBy));
			}
		}

		return biggerMap;
	}

	// public static Dijkstra initDijkstra(Map<Coord, Integer> map) {
	public static Dijkstra initDijkstra(Map<Node, Integer> map) {
	    // Coord end = new Coord(getMaxX(map), getMaxY(map));
	    Node end = new Node(getMaxX(map), getMaxY(map));
		return new Dijkstra(map, end);
	}

	public static void main(String[] args) {

		try {
			// Map<Coord, Integer> testMap = parseMap("test_input.txt");
			Map<Node, Integer> testMap = parseMap("test_input.txt");
			Dijkstra test = initDijkstra(testMap);
			System.out.println("Test part 1 (should be 40): " + test.getLowestRisk());

			// Map<Coord, Integer> answerMap = parseMap("input.txt");
			Map<Node, Integer> answerMap = parseMap("input.txt");
			Dijkstra answer = initDijkstra(answerMap);
			System.out.println("Answer part 1: " + answer.getLowestRisk());

			// Map<Coord, Integer> testBiggerMap = growMap(testMap);
			Map<Node, Integer> testBiggerMap = growMap(testMap);
			Dijkstra test2 = initDijkstra(testBiggerMap);
			System.out.println("Test part 2 (should be 315): " + test2.getLowestRisk());

			// Map<Coord, Integer> answerBiggerMap = growMap(answerMap);
			Map<Node, Integer> answerBiggerMap = growMap(answerMap);
			Dijkstra answer2 = initDijkstra(answerBiggerMap);
			System.out.println("Answer part 2: " + answer2.getLowestRisk());

		} catch (IOException e) {}

	}
}
