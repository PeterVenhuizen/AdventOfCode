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
                // System.out.println(coor);
                this.points.put(coor, new Point(x, y, Integer.parseInt(ints.get(x))));
            }
            // System.out.println(stringToList(l));
            y++;
        }
    }

    private boolean isLowPoint(Point point) {
        List<String> neighbors = point.getNeigbors();
        // System.out.println(neighbors);
        for (String n : neighbors) {
            if (this.points.containsKey(n)) {
                Point neigh = this.points.get(n);
                // System.out.println("this: " + point.getHeight() + "neigh: " + neigh.getHeight());
                if (point.getHeight() >= neigh.getHeight()) {
                    return false;
                }
            }
        }
        return true;
    }

    // private List<String> expandBasin(Point point, List<String> basin) {
    //     List<String> neighbors = point.getNeigbors();
    //     for (String n : neighbors) {
    //         if (this.points.containsKey(n) && !basin.contains(n)) {
    //             Point neigh = this.points.get(n);
    //             if (neigh.getHeight() != 9) {
    //                 basin.add(n);
    //             }
    //         } 
    //     }
    //     return basin;
    // }

    public int getRiskLevel() {
        return this.points.keySet()
            .stream()
            .map(k -> this.points.get(k))
            .filter(p -> isLowPoint(p))
            .map(p -> p.getRisk())
            .reduce(0, Integer::sum);
    }

    public int expandBasin(List<String> toLookAt, List<String> seen, List<String> basin) {
        // System.out.println("look: " + toLookAt);
        // System.out.println("seen: " + seen);
        // System.out.println("basin: " + basin);
        if (toLookAt.size() == 0) {
            return basin.size();
        } else {

            // get all part of basin

            try {
                Point point = this.points.get(toLookAt.remove(0));
                if (point.getHeight() != 9) {
                    List<String> neighbors = point.getNeigbors();
                    List<String> partOfBasin = neighbors.stream()
                        .filter(k -> this.points.containsKey(k))
                        .map(k -> this.points.get(k))
                        .filter(p -> p.getHeight() != 9)
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
            List<String> toLookAt = p.getNeigbors();
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