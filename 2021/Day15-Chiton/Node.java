import java.util.*;

class Node {
    public int x;
    public int y;
    public int cost;

    public Node(int x, int y) {
        this(x, y, 0);
    }
    public Node(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public List<Node> getAdjacent() {
        return List.of(new Node(x-1 , y), new Node(x+1, y), new Node(x, y-1), new Node(x, y+1));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Node)) return false;

        Node other = (Node)o;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return 17 * x + 31 * y;
    }
}