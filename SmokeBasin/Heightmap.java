import java.util.*;
import java.util.stream.*;

class Heightmap {
    private Map<String, Point> points;

    public Heightmap(List<String> heightmapList) {
        initHeightmap(heightmapList);
        // findLowPoints();
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
        List<Point> neighbors = getValidNeighbors(point.getNeighbors());
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

    public int expandBasin(List<String> toLookAt, List<String> seen, List<String> basin) {
        if (toLookAt.size() == 0) {
            return basin.size();
        } else {

            // get all part of basin

            try {
                Point point = this.points.get(toLookAt.remove(0));
                if (!point.isHighPoint()) {
                    List<String> neighbors = point.getNeighbors();
                    List<String> partOfBasin = neighbors.stream()
                        .filter(k -> this.points.containsKey(k))
                        .map(k -> this.points.get(k))
                        .filter(p -> !p.isHighPoint())
                        .map(p -> p.getKey())
                        .collect(Collectors.toList());
                    // System.out.println(point.getKey() + " " + partOfBasin);

                    // System.out.println(partOfBasin);

                    // filter those already seen
                    for (String key : partOfBasin) {
                        seen.add(key);
                        
                        if (!basin.contains(key)) {
                            basin.add(key);
                            toLookAt.add(key);
                        }
                    }
                }
            } catch (NullPointerException e) {}

            return expandBasin(toLookAt, seen, basin);

        }
    }

    public int getBasins() {
        List<Point> lowpoints = this.points.keySet()
            .stream()
            .map(k -> this.points.get(k))
            .filter(p -> isLowPoint(p))
            .collect(Collectors.toList());

        List<Integer> allBasins = new ArrayList<>();
        for (Point p : lowpoints) {
            List<String> toLookAt = p.getNeighbors();
            // System.out.println(toLookAt);
            // System.out.println(p.getKey());
            int basin = expandBasin(toLookAt, new ArrayList<>(), new ArrayList<>());
            // System.out.println(p.getKey() + ": " + basin);
            // System.out.println(basin);
            allBasins.add(basin);
        }
        // System.out.println(allBasins);

        Collections.sort(allBasins, Collections.reverseOrder());
        System.out.println(allBasins);

        return allBasins.get(0) * allBasins.get(1) * allBasins.get(2);
    }

}