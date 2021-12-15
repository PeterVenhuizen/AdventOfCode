import java.util.*;
import java.util.stream.Collectors;

public class Dijkstra {
    private Map<Node, Integer> map;
    private Node end;

    public Dijkstra(Map<Node, Integer> map) {
        this.map = map;
        setEndNode();
    }

    private void setEndNode() {
        this.end = new Node(
            this.map.keySet().stream().mapToInt(n -> n.x).max().orElseThrow(),
            this.map.keySet().stream().mapToInt(n -> n.y).max().orElseThrow()
        );
    }

    public int getLowestRisk() {
		Queue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
		Set<Node> visited = new HashSet<>();
		
		// the start node has a cost of zero
        Node start = new Node(0, 0, 0);
		queue.add(start);

		// loop while there are still things to look at and the next item
		// in the queue isn't the end coord
		while (!queue.isEmpty() && !queue.peek().equals(this.end)) {

			// get the Node with the lowest cost from the queue
            Node node = queue.poll();

			// go over all neighbours and get those we haven't visited yet
            List<Node> notVisited = node.getAdjacent()
				.stream()
				.filter(this.map::containsKey) // see if it is a valid coord
				.filter(n -> !visited.contains(n)) // see if we haven't visited here
				.collect(Collectors.toList());

			// update the node costs + add to queue and store node as visited
			notVisited.forEach(n -> {
                Node next = new Node(n.x, n.y, node.cost + this.map.get(n));
                visited.add(n);
                queue.add(next);
			});

		}
		return queue.peek().cost;
	} 

}
