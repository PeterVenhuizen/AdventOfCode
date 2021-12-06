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

    public BigInteger simulateMemoryEfficient(int days) {

        /* 
            Create a List with the index being the days until when they will
            spawn again and the BigInteger value representing the total number
            of fish for that day

            numberOfFish.get(0) -> number of fish who will spawn again in zero days
            numberOfFish.get(1) -> number of fish who will spawn again in one day
            etc.
        */
        List<BigInteger> numberOfFish = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int daysLeft = i;
            int numberOfFishThatSpawnOnThisDay = (int) this.school.stream()
                .filter(f -> f.getDays() == daysLeft)
                .count();
            numberOfFish.add(new BigInteger(Integer.toString(numberOfFishThatSpawnOnThisDay)));
        }

        while (days > 0) {

            // The fish at the start of the list will spawn today. Remove
            // them from the list, so that the fish that will spawn tomorrow
            // will be next.
            BigInteger numberOfSpawningFish = numberOfFish.remove(0);

            // Fish that spawned before, take six days to spawn again, so add
            // them to the other fish that spawn in six days
            numberOfFish.set(6, numberOfFish.get(6).add(numberOfSpawningFish));

            // New fish take 8 days to spawn, so add them to the end of the
            // list, which will be at the new index 8
            numberOfFish.add(numberOfSpawningFish);

            days--;
        }

        BigInteger total = new BigInteger("0");
        for (BigInteger n : numberOfFish) {
            total = total.add(n);
        }

        return total;
    }

    @Override
    public String toString() {
        return this.school.stream()
            .map(f -> f.toString())
            .collect(Collectors.joining(","));
    }
}