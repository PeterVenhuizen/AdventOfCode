import java.util.*;
import java.util.stream.Collectors;

record Node(int x, int y) {
    public List<Node> adjacent() {
        return List.of(new Node(x-1 , y), new Node(x+1, y), new Node(x, y-1), new Node(x, y+1));
    }
}

class Path {
    private List<Node> path;
    private int cost;

    public Path(Node start, int cost) {
        this.path = new ArrayList<>();
        this.path.add(start);
        this.cost = cost;
    }

    public List<Node> path() {
        return this.path;
    }

    public Node last() {
        return this.path.get(this.path.size() - 1);
    }

    public int cost() {
        return this.cost;
    }
}

public class Dijkstra {
    private Map<Node, Integer> map;
    private Node end;

    public Dijkstra(Map<Node, Integer> map) {
        this.map = map;
        setEndNode();
    }

    private void setEndNode() {
        this.end = new Node(
            this.map.keySet().stream().mapToInt(n -> n.x()).max().orElseThrow(),
            this.map.keySet().stream().mapToInt(n -> n.y()).max().orElseThrow()
        );
    }

    public int getLowestRisk() {
		// Queue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
		Queue<Path> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.cost()));
		Set<Node> visited = new HashSet<>();
		
		// the start node has a cost of zero
        // Node start = new Node(0, 0, 0);
        Path start = new Path(new Node(0, 0), 0);
		queue.add(start);

		// loop while there are still things to look at and the next item
		// in the queue isn't the end coord
		while (!queue.isEmpty() && !queue.peek().last().equals(this.end)) {

			// get the Node with the lowest cost from the queue
            // Node node = queue.poll();
            Path bestPath = queue.poll();
            Node last = bestPath.last();

			// go over all neighbours and get those we haven't visited yet
            // List<Node> notVisited = node.getAdjacent()
            List<Node> notVisited = last.adjacent()
				.stream()
				.filter(this.map::containsKey) // see if it is a valid coord
				.filter(n -> !visited.contains(n)) // see if we haven't visited here
				.collect(Collectors.toList());

			// update the node costs + add to queue and store node as visited
			notVisited.forEach(n -> {
                // Node next = new Node(n.x, n.y, node.cost + this.map.get(n));
                // Node next = new Node(n.x, n.y, node.cost + this.map.get(n));
                Path updatedPath = new Path(new Node(n.x(), n.y()), bestPath.cost() + this.map.get(n));
                visited.add(n);
                queue.add(updatedPath);
			});

		}
		// return queue.peek().cost;
		return queue.peek().cost();
	} 

}
