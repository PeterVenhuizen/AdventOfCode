import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.math.BigInteger;

class LanternfishTracker {
    private List<Fish> school;

    public LanternfishTracker(List<Fish> school) {
        this.school = school;
    }

    public int simulate(int days) {

        // create copy of the school, so part 2 can be run from the 
        // starting state as well
        List<Fish> fishes = this.school.stream()
            .map(fish -> new Fish(fish.getDays()))
            .collect(Collectors.toList());

        while (days > 0) {

            // mature all fish
            fishes.forEach(f -> f.mature());

            // get new fish
            List<Fish> newFish = fishes.stream()
                .filter(fish -> fish.isReadyToSpawn())
                .map(fish -> new Fish())
                .collect(Collectors.toList());
            
            // combine fish schools
            fishes = Stream.concat(fishes.stream(), newFish.stream())
                .collect(Collectors.toList());

            days--;
        }

        return fishes.size();
    }

    // public int memorySimulate(int days) {
    public BigInteger memorySimulate(int days) {
        // List<Integer> nFish = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        List<BigInteger> nFish = new ArrayList<>();
        // List<List<String>> nFish = new ArrayList<>();

        System.out.println(toString());

        for (int i = 0; i < 9; i++) {
            int n = i;
            int nThisAge = (int) this.school.stream().filter(f -> f.getDays() == n).count();
            // List<String> fishSoup = this.fish.stream()
            //     .filter(f -> f.getDays() == n)
            //     .map(f -> "o")
            //     .collect(Collectors.toList());
            // System.out.println(fishSoup.toString());
            // nFish.add(fishSoup);
            // nFish.set(n, nThisAge);
            nFish.add(new BigInteger(Integer.toString(nThisAge)));
        }

        while (days > 0) {

            BigInteger first = nFish.remove(0);
            // int first = nFish.remove(0);

            // nFish.set(6, nFish.get(6) + first);
            nFish.set(6, nFish.get(6).add(first));
            // List<String> oldestFish = nFish.get(0);

            nFish.add(first);

            // System.out.println(days + ": " + first + " " + nFish.size());

            days--;
            // System.out.println(nFish);
            // System.out.println(nFish.stream().reduce(0, Integer::sum));
        }

        BigInteger total = new BigInteger("0");
        for (int i = 0; i < nFish.size(); i++) {
            // System.out.println(nFish.get(i));
            total = total.add(nFish.get(i));
            // System.out.println(total);
        }

        // return nFish.stream().reduce(0, Integer::sum);
        return total;
    }

    @Override
    public String toString() {
        return this.school.stream()
            .map(f -> f.toString())
            .collect(Collectors.joining(","));
    }
}