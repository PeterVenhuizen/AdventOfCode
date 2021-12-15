import java.util.*;
import java.util.stream.Collectors;

record Coord(int x, int y) {

    // get all adjacent coordinates
    List<Coord> getAdjacent() {
        return List.of(new Coord(x-1 , y), new Coord(x+1, y), new Coord(x, y-1), new Coord(x, y+1));
    }
}

record Path(Coord coord, int value) implements Comparable<Path> {
    private static final Comparator<Path> VALUE_COMPARATOR = Comparator.comparingInt(Path::value);

    @Override
    public int compareTo(Path o) {
        return VALUE_COMPARATOR.compare(this, o);
    }
}

public class Dijkstra {
    private Map<Coord, Integer> map;
    private Coord end;

    public Dijkstra(Map<Coord, Integer> map, Coord end) {
        this.map = map;
        this.end = end;
    }

    public int getLowestRisk() {
		Queue<Path> queue = new PriorityQueue();
		Set<Coord> visited = new HashSet<>();
		
		// add the starting coord with a cost of zero
		queue.add(new Path(new Coord(0, 0), 0));

		// loop while there are still things to look at and the next item
		// in the queue isn't the end coord
		while (!queue.isEmpty() && !queue.peek().coord().equals(this.end)) {

			// get the Path with the lowest cost from the queue
			Path bestPath = queue.poll();
			Coord nextLowest = bestPath.coord();

			// go over all neighbours and get those we haven't visited yet
			List<Coord> notVisited = nextLowest.getAdjacent()
				.stream()
				.filter(this.map::containsKey) // see if it is a valid coord
				.filter(c -> !visited.contains(c)) // see if we haven't visited here
				.collect(Collectors.toList());

			// update the path costs and add to queue and mark coord as visited
			notVisited.forEach(c -> {
				Path p = new Path(c, bestPath.value() + this.map.get(c));
				visited.add(c);
				queue.add(p);
			});

		}
		return queue.peek().value();
	} 

}
