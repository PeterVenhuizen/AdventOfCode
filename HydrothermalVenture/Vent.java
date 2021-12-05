import java.util.*;
import java.util.regex.*;

enum Lines {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL
}

class Vent {
    public int[] x;
    public int[] y;
    private Lines orientation;

    public Vent(String ventString) {
        this.x = new int[2];
        this.y = new int[2];
        this.initVent(ventString);
    }

    private void initVent(String ventString) {
        // System.out.println(ventString);
        String regex = "(\\d+),(\\d+) -> (\\d+),(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ventString);

        if (matcher.find()) {
            this.x[0] = Integer.parseInt(matcher.group(1));
            this.y[0] = Integer.parseInt(matcher.group(2));
            this.x[1] = Integer.parseInt(matcher.group(3));
            this.y[1] = Integer.parseInt(matcher.group(4));
        }

        if (this.x[0] == this.x[1]) {
            this.orientation = Lines.VERTICAL;
        } else if (this.y[0] == this.y[1]) {
            this.orientation = Lines.HORIZONTAL;
        } else {
            this.orientation = Lines.DIAGONAL;
        }

    }

    public List<Point> getPoints() {
        List<Point> points = new ArrayList<>();

        int x1 = this.x[0];
        int x2 = this.x[1];
        int y1 = this.y[0];
        int y2 = this.y[1];

        System.out.println(x1 + ", " + x2 + ", " + y1 + ", " + y2);
        while (x1 != x2 || y1 != y2) {
            System.out.println("x: " + x1 + ", y: " + y1);
            points.add(new Point(x1, y1));

            if (x1 != x2) {
                x1 = (x1 < x2) ? ++x1 : --x1;
            }

            if (y1 != y2) {
                y1 = (y1 < y2) ? ++y1 : --y1;
            }
        }
        points.add(new Point(x1, y1));
        System.out.println(points.size());
        return points;
    }

    public boolean isHorizontal() {
        return this.orientation == Lines.HORIZONTAL;
    }

    public boolean isVertical() {
        return this.orientation == Lines.VERTICAL;
    }

    public boolean isDiagonal() {
        return this.orientation == Lines.DIAGONAL;
    }

    public int getMin(int[] values) {
        return Arrays.stream(values).min().getAsInt();
    }

    public int getMax(int[] values) {
        return Arrays.stream(values).max().getAsInt();
    }

    public int getMinX() {
        return this.getMin(this.x);
    }

    public int getMaxX() {
        return this.getMax(this.x);
    }

    public int getMinY() {
        return this.getMin(this.y);
    }

    public int getMaxY() {
        return this.getMax(this.y);
    }


}