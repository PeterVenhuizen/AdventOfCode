import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Cavern {
    private Map<String, Octopus> octopi;
    private List<String> octopiOrdered;

    public Cavern(String inputFile) throws IOException {

        Scanner sc = new Scanner(new File(inputFile));

        this.octopi = new HashMap<>();
        this.octopiOrdered = new ArrayList<>();

        int x = 0, y = 0;

        while (sc.hasNext()) {
            char[] energyLevels = sc.nextLine().toCharArray();
            for (char energy : energyLevels) {
                String coord = String.format("%d,%d", x, y);
                this.octopi.put(coord, new Octopus(Character.getNumericValue(energy)));
                octopiOrdered.add(coord);

                if (++x == 10) {
                // if (++x == 5) {
                    x = 0;
                    y++;
                }
            }
        }
        sc.close();

        // set adjacent octopi
        for (String coord : this.octopi.keySet()) {
            x = Integer.parseInt(coord.split(",")[0]);
            y = Integer.parseInt(coord.split(",")[1]);
            List<Octopus> adjacentOctopi = getAdjacent(x, y);
            Octopus octo = this.octopi.get(coord);
            octo.setAdjacent(adjacentOctopi);
        }
    }

    private List<Octopus> getAdjacent(int x, int y) {
        int maxX = 10;
        int maxY = 10;
        // int maxX = 5;
        // int maxY = 5;
        int delta[][] = {{-1,-1,},{-1,0,},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

        List<Octopus> adjacentOctopi = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int dx = x + delta[i][0];
            int dy = y + delta[i][1];
            if (dx >= 0 && dx < maxX && dy >= 0 && dy < maxY) {
                String coord = String.format("%d,%d", dx, dy);
                adjacentOctopi.add(this.octopi.get(coord));
            }
        }

        return adjacentOctopi;
    }

    public int flash(int days) {
        int totalFlashes = 0;
        // System.out.println(this.toString());
        int steps = 0;
        while (days > 0) {
            int flashesBeforeToday = totalFlashes;
            // visit each octopus
            for (String coord : this.octopiOrdered) {
                Octopus octo = this.octopi.get(coord);

                // System.out.println(this.toString());

                // see if they didn't flash yet
                if (!octo.isPoweredDown()) {

                    // increase energy level
                    octo.increaseEnergyLevel();

                    // ask if they are going to flash
                    if (octo.isReadyToFlash()) {
                        totalFlashes += keepFlashing(new ArrayList<Octopus>(Arrays.asList(octo)));
                        
                        // totalFlashes++;
                        // octo.powerDownOctoForToday();

                        // // get all adjacent who didn't flash yet and
                        // // increase their energy level
                        // octo.getAdjacent()
                        //     .stream()
                        //     .filter(o -> o.didNotYetFlash())
                        //     .forEach(o -> o.increaseEnergyLevel());

                        // System.out.println(this.toString());

                    }
                }               
            }

            steps++;

            if (this.octopi.values().stream().allMatch(o -> o.getEnergyLevel() == 0)) {
                System.out.println(steps);
                break;
            }

            // restart all octopi again
            this.octopi.values().forEach(o -> o.restart());

            // System.out.println(this.toString());
            // System.out.println("\n=====================\n");
            --days;
        }
        return totalFlashes;
    }

    private int keepFlashing(List<Octopus> flashable) {
        if (flashable.size() == 0) {
            return 0;
        }

        else {
            Octopus octo = flashable.remove(0);
            // System.out.println(flashable.size());
            octo.powerDownForToday();
            
            // increase energy level
            octo.getAdjacent()
                .stream()
                .filter(o -> !o.isPoweredDown())
                .forEach(o -> o.increaseEnergyLevel());

            // get now flashable
            List<Octopus> nowFlashAble = octo.getAdjacent()
                .stream()
                .filter(o -> !o.isPoweredDown() && o.isReadyToFlash())
                .collect(Collectors.toList());

            nowFlashAble.forEach(o -> o.powerDownForToday());

            List<Octopus> keepOnFlashing = Stream.concat(flashable.stream(), nowFlashAble.stream())
                .collect(Collectors.toList());

            // System.out.println(this.toString());

            return 1 + keepFlashing(keepOnFlashing);
            // return 1;
        }
    }

    @Override
    public String toString() {
        String output = "";
        int x = 0;
        for (String coord : this.octopiOrdered) {
            Octopus octo = this.octopi.get(coord);
            output += octo.toString();
            if (++x == 10) {
            // if (++x == 5) {
                x = 0;
                output += "\n";
            }
        }
        return output;
    }

}
