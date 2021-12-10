import java.util.*;

class Point {
    public int x;
    public int y;
    public int height;

    public Point(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    public String getKey() {
        return String.format("%d-%d", this.x, this.y);
    }

    public List<String> getNeighborKeys() {
        List<String> neighbors = new ArrayList<>();

        // above
        neighbors.add(String.format("%d-%d", this.x, this.y-1));

        // right
        neighbors.add(String.format("%d-%d", this.x+1, this.y));

        // below
        neighbors.add(String.format("%d-%d", this.x, this.y+1));

        // left
        neighbors.add(String.format("%d-%d", this.x-1, this.y));

        return neighbors;
    }

    public int getHeight(){
        return this.height;
    }

    public boolean isHighPoint() {
        return this.height == 9;
    }

    public int getRisk() {
        return this.height + 1;
    }
}