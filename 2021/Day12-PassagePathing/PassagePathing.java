import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class PathFinder {
	private Map<String, List<String>> caves;
	private List<String> paths;

	public PathFinder(String inputFile) throws IOException {
		this.caves = new HashMap<>();
		this.paths = new ArrayList<>();
		mapCaves(inputFile);
	}

	private void mapCaves(String inputFile) throws IOException {
		Scanner sc = new Scanner(new File(inputFile));
		while (sc.hasNext()) {
			String line = sc.nextLine();
			String caveA = line.split("-")[0];
			String caveB = line.split("-")[1];

			boolean notGoingPastTheEnd = !caveA.equals("end") && !caveB.equals("start");
			if (notGoingPastTheEnd) {
				this.caves.computeIfAbsent(caveA, k -> new ArrayList<>()).add(caveB);
			}

			boolean notGoingBackToStart = !caveA.equals("start") && !caveB.equals("end");
			if (notGoingBackToStart) {
				this.caves.computeIfAbsent(caveB, k -> new ArrayList<>()).add(caveA);
			}
		}
		sc.close();
	}

	private List<String> getOptions(String pos, List<String> route,
		boolean aSingleSmallCaveMayBeRevisited) {

		// see which small caves I have already visited
		List<String> visitedSmall = route.stream()
			.filter(p -> p.toLowerCase().equals(p))
			.collect(Collectors.toList());

		// for all small caves, see how often I have visited them
		Map<String, Long> counts =
			route.stream()
				.filter(e -> e.toLowerCase() == e && !e.equals("start"))
				.collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		
		List<String> possible = this.caves.containsKey(pos) ? this.caves.get(pos) : new ArrayList<>();

		// I allowed to visit a single small cave twice
		boolean aSmallCaveHasNotBeenRevisited = counts.values().stream().allMatch(v -> v != 2);
		if (aSingleSmallCaveMayBeRevisited && aSmallCaveHasNotBeenRevisited) {
			return possible;
		} 
		
		else {
			// I can only go to big caves, or small caves I haven't yet
			// visited
			return possible.stream()
				.filter(e -> !visitedSmall.contains(e))
				.collect(Collectors.toList());
		}
		
	}

	private void findPaths(String pos, List<String> path, boolean aSingleSmallCaveMayBeRevisited) {

		// extend path
		path.add(pos);

		// see where I can go next
		List<String> options = getOptions(pos, path, aSingleSmallCaveMayBeRevisited);

		// explore all different possible paths
		for (String op : options) {
			// clone my path, because of Java pointers
			List<String> pathClone = new ArrayList<>();
			for (String p : path) pathClone.add(p);
			
			// explore this path
			findPaths(op, pathClone, aSingleSmallCaveMayBeRevisited);
		}

		boolean reachedTheEnd = pos.equals("end");
		if (reachedTheEnd) {
			this.paths.add(String.join(",", path));
		}

	}

	public int getNumberOfPossiblePaths(boolean aSingleSmallCaveMayBeRevisited) {
		findPaths("start", new ArrayList<>(), aSingleSmallCaveMayBeRevisited);
		return this.paths.size();
	}
}

public class PassagePathing {
	
	public static void main(String[] args) {

		try {
			PathFinder test = new PathFinder("test_input.txt");
			System.out.println("Test part 1 (should be 10): " + test.getNumberOfPossiblePaths(false));

			PathFinder answer = new PathFinder("input.txt");
			System.out.println("Answer part 1: " + answer.getNumberOfPossiblePaths(false));

			PathFinder test2 = new PathFinder("test_input.txt");
			System.out.println("Test part 2 (should be 36): " + test2.getNumberOfPossiblePaths(true));

			PathFinder answer2 = new PathFinder("input.txt");
			System.out.println("Answer part 2: " + answer2.getNumberOfPossiblePaths(true));

		} catch (IOException e) {}

	}
}
