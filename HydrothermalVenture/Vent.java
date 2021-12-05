import java.util.*;
import java.util.regex.*;

class Vent {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private boolean isDiagonal;

    public Vent(String ventString) {
        this.initVent(ventString);
    }

    private void initVent(String ventString) {
        String regex = "(\\d+),(\\d+) -> (\\d+),(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ventString);

        if (matcher.find()) {
            this.x1 = Integer.parseInt(matcher.group(1));
            this.y1 = Integer.parseInt(matcher.group(2));
            this.x2 = Integer.parseInt(matcher.group(3));
            this.y2 = Integer.parseInt(matcher.group(4));
        }

        this.isDiagonal = (this.x1 != this.x2 && this.y1 != this.y2)
            ? true : false;
    }

    public List<Point> getPoints() {
        List<Point> points = new ArrayList<>();

        int x = this.x1;
        int y = this.y1;

        while (x != this.x2 || y != this.y2) {
            points.add(new Point(x, y));

            if (x != this.x2) {
                x = (x < this.x2) ? ++x : --x;
            }

            if (y != this.y2) {
                y = (y < this.y2) ? ++y : --y;
            }
        }
        points.add(new Point(x, y));
        return points;
    }

    public boolean isDiagonal() {
        return this.isDiagonal;
    }

    public int getMaxX() {
        return (this.x1 > this.x2) ? this.x1 : this.x2;
    }

    public int getMaxY() {
        return (this.y1 > this.y2) ? this.y1 : this.y2;
    }

}