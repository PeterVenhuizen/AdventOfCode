import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.math.BigInteger;

class LanternfishTracker {
    private List<Fish> fish;

    public LanternfishTracker(List<Fish> fish) {
        this.fish = fish;
    }

    public int simulate(int days) {
        while (days > 0) {

            List<Fish> newFish = new ArrayList<>();
            for (Fish f : this.fish) {
                f.mature();
                if (f.isReadyToSpawn()) {
                    newFish.add(new Fish());
                }
            }
            
            List<Fish> allFish = Stream.concat(this.fish.stream(), newFish.stream())
                .collect(Collectors.toList());
            this.fish = allFish;

            days--;
        }
        return this.fish.size();
    }

    // public int memorySimulate(int days) {
    public BigInteger memorySimulate(int days) {
        // List<Integer> nFish = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        List<BigInteger> nFish = new ArrayList<>();
        // List<List<String>> nFish = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            int n = i;
            int nThisAge = (int) this.fish.stream().filter(f -> f.getDays() == n).count();
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
        return this.fish.stream()
            .map(f -> f.toString())
            .collect(Collectors.joining(","));
    }
}