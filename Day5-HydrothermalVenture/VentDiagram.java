import java.util.*;
import java.util.function.Predicate;

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

    public void mapVents(Predicate<Vent> ventFilter) {
        this.vents.stream()
            .filter(ventFilter)
            .forEach(vent -> {
                List<Point> points = vent.getPoints();
                points.forEach(p -> {
                    this.ventDiagram[p.y][p.x]++;
                });
            });
    }

    public int getDangerousAreas() {
        return (int) Arrays.stream(this.ventDiagram)
            .flatMapToInt(x -> Arrays.stream(x))
            .filter(v -> v >= 2)
            .count();
    }

    public void print() {
        for (int i = 0; i < this.ventDiagram.length; i++) {
            System.out.println(Arrays.toString(this.ventDiagram[i]));
        }
    }
}