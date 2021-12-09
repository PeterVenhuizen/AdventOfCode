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

    public int getHeight(){
        return this.height;
    }

    public boolean isHighPoint() {
        return this.height == 9;
    }

    public String getKey() {
        return String.format("%d-%d", this.x, this.y);
    }

    public List<String> getNeighbors() {
        List<String> neighbors = new ArrayList<>();

        for (int delta = -1; delta <= 1; delta += 2) {
            
        }

        // for (int deltaX = -1; deltaX <= 1; deltaX += 2) {
        //     for (int deltaY = -1; deltaY <= 1; deltaY += 2) {

        //         int newX = this.x + deltaX;
        //         int newY = this.y + deltaY;

        //         if (newX >= 0 && newY >= 0) {
        //             neighbors.add(String.format("%d-%d", newX, newY));
        //         }
        //     }
        // }

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

    public int getRisk() {
        return this.height + 1;
    }
}