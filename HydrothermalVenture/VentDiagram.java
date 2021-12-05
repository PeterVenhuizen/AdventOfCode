import java.util.*;

class VentDiagram {
    private int[][] ventDiagram;
    private List<Vent> vents;

    public VentDiagram(List<Vent> vents) {
        this.vents = vents;
        this.initVentDiagram();
    }

    private void initVentDiagram() {
        int maxX = 0, maxY = 0;

        /* Get the boundaries of the Vent Diagram by determining the 
        the biggest X and Y values */
        for (Vent vent : this.vents) {

            if (vent.getMaxX() > maxX) 
                maxX = vent.getMaxX();

            if (vent.getMaxY() > maxY)
                maxY = vent.getMaxY();

        }

        this.ventDiagram = new int[maxY+1][maxX+1];
    }

    public void mapHorizontalAndVertical() {
        this.vents.forEach(vent -> {
            if (!vent.isDiagonal()) {
                List<Point> points = vent.getPoints();
                points.forEach(p -> {
                    this.ventDiagram[p.y][p.x]++;
                });
            }
        });
    }

    public void mapDiagonal() {
        this.vents.forEach(vent -> {
            if (vent.isDiagonal()) {

                List<Point> points = vent.getPoints();
                points.forEach(p -> {
                    this.ventDiagram[p.y][p.x]++;
                });

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