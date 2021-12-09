import java.util.*;
import java.util.stream.*;

class Heightmap {
    private Map<String, Point> points;

    public Heightmap(List<String> heightmapList) {
        initHeightmap(heightmapList);
    }

    private List<String> stringToList(String s) {
        return s.chars().mapToObj(e -> (char)e)
            .map(e -> Character.toString(e))
            .collect(Collectors.toList());
    }

    private void initHeightmap(List<String> list) {

        this.points = new HashMap<String, Point>();

        int y = 0;
        for (String l : list) {
            List<String> ints = stringToList(l);
            for (int x = 0; x < ints.size(); x++) {
                String coor = x + "-" + y;
                this.points.put(coor, new Point(x, y, Integer.parseInt(ints.get(x))));
            }
            y++;
        }
    }

    private List<Point> getValidNeighbors(List<String> neighbors) {
        return neighbors.stream()
            .filter(n -> this.points.containsKey(n))
            .map(p -> this.points.get(p))
            .collect(Collectors.toList());
    }

    private boolean isLowPoint(Point point) {
        List<Point> neighbors = getValidNeighbors(point.getNeighborKeys());
        return neighbors.stream()
            .allMatch(n -> point.getHeight() < n.getHeight());
    }

    public int getRiskLevel() {
        return this.points.keySet()
            .stream()
            .map(k -> this.points.get(k))
            .filter(p -> isLowPoint(p))
            .map(p -> p.getRisk())
            .reduce(0, Integer::sum);
    }

    public int expandBasin(List<Point> toLookAt, List<String> basin) {
        
        if (toLookAt.size() == 0) {
            return basin.size();
        } 
        
        else {
            Point point = toLookAt.remove(0);

            // the initial staring Points might be peaks, so need to avoid those
            if (!point.isHighPoint()) {

                // select other points part of the basin
                List<Point> neighbors = getValidNeighbors(point.getNeighborKeys());
                List<String> basinExtension = neighbors.stream()
                    .filter(p -> !p.isHighPoint())
                    .map(p -> p.getKey())
                    .collect(Collectors.toList());

                // add to the existing basin
                for (String key : basinExtension) {
                    if (!basin.contains(key)) {
                        basin.add(key);
                        toLookAt.add(this.points.get(key));
                    }
                }
            }
            return expandBasin(toLookAt, basin);
        }
    }

    public int getBasins() {

        // get the starting low points
        List<Point> lowpoints = this.points.keySet()
            .stream()
            .map(k -> this.points.get(k))
            .filter(p -> isLowPoint(p))
            .collect(Collectors.toList());

        // expand each low point into a basin
        List<Integer> allBasins = new ArrayList<>();
        for (Point p : lowpoints) {
            List<Point> toLookAt = getValidNeighbors(p.getNeighborKeys());
            int basin = expandBasin(toLookAt, new ArrayList<>());
            allBasins.add(basin);
        }

        Collections.sort(allBasins, Collections.reverseOrder());
        // System.out.println(allBasins);

        return allBasins.get(0) * allBasins.get(1) * allBasins.get(2);
    }

}