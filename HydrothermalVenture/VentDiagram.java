import java.util.*;

class VentDiagram {
    private int[][] ventDiagram;
    private List<Vent> vents;

    public VentDiagram(List<Vent> vents) {
        this.vents = vents;
        this.initVentDiagram();
    }

    private void initVentDiagram() {
        // int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = 0, maxY = 0;

        /* Get the boundaries of the Vent Diagram by determining the 
        the biggest X and Y values */
        for (Vent vent : this.vents) {

            if (vent.getMaxX() > maxX) 
                maxX = vent.getMaxX();

            if (vent.getMaxY() > maxY)
                maxY = vent.getMaxY();

            // int newMinX, newMaxX, newMinY, newMaxY;

            // if ((newMinX = vent.getMin(vent.x)) < minX) {
            //     minX = newMinX;
            // }

            // if ((newMaxX = vent.getMax(vent.x)) > maxX) {
            //     maxX = newMaxX;
            // }

            // if ((newMinY = vent.getMin(vent.y)) < minY) {
            //     minY = newMinY;
            // }

            // if ((newMaxY = vent.getMax(vent.y)) > maxY) {
            //     maxY = newMaxY;
            // }
        }

        // System.out.println("Min X :" + minX);
        // System.out.println("Max X :" + maxX);
        // System.out.println("Min Y :" + minY);
        // System.out.println("Max Y :" + maxY);

        this.ventDiagram = new int[maxY+1][maxX+1];
        // System.out.println(this.ventDiagram.length);
        // System.out.println(this.ventDiagram[0].length);
    }

    public void mapHorizontalAndVertical() {
        this.vents.forEach(vent -> {
            if (!vent.isDiagonal()) {
                List<Point> points = vent.getPoints();
                points.forEach(p -> {
                    this.ventDiagram[p.y][p.x]++;
                });
            }

            // if (vent.isHorizontal()) {
            //     int minX = vent.getMinX();
            //     int maxX = vent.getMaxX();
            //     int y = vent.getMinY();

            //     for (int x = minX; x <= maxX; x++) {
            //         try {
            //             this.ventDiagram[y][x]++;
            //         } catch (IndexOutOfBoundsException e) {}
            //     }
            // }

            // else if (vent.isVertical()) {
            //     int minY = vent.getMinY();
            //     int maxY = vent.getMaxY();
            //     int x = vent.getMinX();

            //     for (int y = minY; y <= maxY; y++) {
            //         try {
            //             this.ventDiagram[y][x]++;
            //         } catch (IndexOutOfBoundsException e) {}
            //     }
            // }
        });
    }

    public void mapDiagonal() {
        this.vents.forEach(vent -> {
            if (vent.isDiagonal()) {

                // System.out.println(Arrays.toString(vent.x) + " | " + Arrays.toString(vent.y));
                int x1 = vent.x[0];
                int x2 = vent.x[1];
                int y1 = vent.y[0];
                int y2 = vent.y[1];

                int x = x1;
                int y = y1;

                while (x != x2 && y != y2) {
                    this.ventDiagram[y][x]++;
                    // System.out.println("x: " + x + ", y: " + y);
                    x = (x1 < x2) ? x + 1 : x - 1;
                    y = (y1 < y2) ? y + 1 : y - 1;
                }
                this.ventDiagram[y][x]++;
            }
        });
    }

    public int getDangerousAreas() {
        int dangerous = 0;
        for (int i = 0; i < this.ventDiagram.length; i++) {
            for (int j = 0; j < this.ventDiagram[i].length; j++) {
                if (this.ventDiagram[i][j] >= 2) {
                    dangerous++;
                }
            }
        }
        return dangerous;
    }

    public void print() {
        for (int i = 0; i < this.ventDiagram.length; i++) {
            System.out.println(Arrays.toString(this.ventDiagram[i]));
        }
    }
}