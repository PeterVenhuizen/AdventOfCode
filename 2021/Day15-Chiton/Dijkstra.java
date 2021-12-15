import java.util.*;
import java.util.stream.Collectors;

// record Coord(int x, int y) {

//     // get all adjacent coordinates
//     List<Coord> getAdjacent() {
//         return List.of(new Coord(x-1 , y), new Coord(x+1, y), new Coord(x, y-1), new Coord(x, y+1));
//     }
// }

// record Node(int x, int y) {
//     // get all adjacent coordinates
//     List<Node> getAdjacent() {
//         return List.of(new Node(x-1 , y), new Node(x+1, y), new Node(x, y-1), new Node(x, y+1));
//     }
// }

// record Path(Node node, int cost) {}

// record Path(Coord coord, int value) implements Comparable<Path> {
//     private static final Comparator<Path> VALUE_COMPARATOR = Comparator.comparingInt(Path::value);

//     @Override
//     public int compareTo(Path o) {
//         return VALUE_COMPARATOR.compare(this, o);
//     }
// }

public class Dijkstra {
    // private Map<Coord, Integer> map;
    // private Coord end;
    private Map<Node, Integer> map;
    private Node end;

    public Dijkstra(Map<Node, Integer> map, Node end) {
        this.map = map;
        this.end = end;
    }

    public int getLowestRisk() {
		// Queue<Path> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.cost()));
		Queue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
		// Set<Coord> visited = new HashSet<>();
		Set<Node> visited = new HashSet<>();
		
		// add the starting coord with a cost of zero
		// queue.add(new Path(new Coord(0, 0), 0));
        // Path start = new Path(new Node(0, 0), 0);
        Node start = new Node(0, 0, 0);
        // start.cost = 0;
		queue.add(start);

		// loop while there are still things to look at and the next item
		// in the queue isn't the end coord
		// while (!queue.isEmpty() && !queue.peek().coord().equals(this.end)) {
		// while (!queue.isEmpty() && !queue.peek().equals(this.end)) {
		// while (!queue.peek().node().equals(this.end)) {
		while (!queue.peek().equals(this.end)) {

			// get the Path with the lowest cost from the queue
			// Path bestPath = queue.poll();
			// Coord nextLowest = bestPath.coord();
            // Path lowest = queue.poll();
            // Node node = lowest.node();
            Node node = queue.poll();

			// go over all neighbours and get those we haven't visited yet
            List<Node> notVisited = node.getAdjacent()
			// List<Coord> notVisited = nextLowest.getAdjacent()
				.stream()
				.filter(this.map::containsKey) // see if it is a valid coord
				.filter(n -> !visited.contains(n)) // see if we haven't visited here
				.collect(Collectors.toList());

			// update the path costs and add to queue and mark coord as visited
			notVisited.forEach(n -> {
                // Node n = new Node(c.x, c.y, cheapest.cost + this.map.get(c));
                // n.cost = cheapest.cost + this.map.get(c);
				// Path p = new Path(c, bestPath.value() + this.map.get(c));
                // Path p = new Path(n, lowest.cost() + this.map.get(n));
                Node next = new Node(n.x, n.y, node.cost + this.map.get(n));
                visited.add(n);
                queue.add(next);
                // visited.add(c);
				// queue.add(p);
			});

		}
		// return queue.peek().value();
		// return queue.peek().cost();
		return queue.peek().cost;
	} 

}
