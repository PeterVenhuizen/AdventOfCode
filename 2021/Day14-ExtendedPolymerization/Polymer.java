import java.util.*;
import java.util.stream.Collectors;

class Polymer {
    private String template;
    private Map<String, String> pairs;
    private Map<String, Long> counts;

    public Polymer(String template, Map<String, String> pairs) {
        this.template = template;
        // this.polymer = polymer;
        this.pairs = pairs;
        this.counts = new HashMap<>();
    }

    public long grow(int steps) {
        while (steps > 0) {

            List<Character> grown = new ArrayList<>();

            // find all available pairs by creating a sliding window of length two
            // and slide one position each time
            String pair = "";
            for (int i = 0; i < template.length() - 1; i++) {
                pair = this.template.substring(i, i+2);

                // always add the first of the pair
                grown.add(pair.charAt(0));

                if (this.pairs.containsKey(pair)) {
                    // grown.add(pair.charAt(0));
                    grown.add(this.pairs.get(pair).charAt(0));
                }
            }

            // add the last of the last pair
            grown.add(pair.charAt(1));

            this.template = grown.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
            // System.out.println(newTemplate);

            // do all possible insertions
            steps--;
        }

        // get the frequency
        Map<Character, Long> freq = this.template.chars()
            .mapToObj(c -> (char)c)
            .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        // return most minus least frequent
        long max = Collections.max(freq.values());
        long min = Collections.min(freq.values());


        
        // System.out.println(freq);
        // System.out.println("max: " + max + ", min: " + min);

        return (max - min);
    }

    public long growAgain(int steps) {
        
        // count the number of single letters -> monomers
        Map<Character, Long> freq = this.template.chars()
            .mapToObj(c -> (char)c)
            .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        // count the number of pairs -> dimers
        for (int i = 0; i < this.template.length() - 1; i++) {
            String pair = this.template.substring(i, i+2);

            if (!this.counts.containsKey(pair)) {
                this.counts.put(pair, 1L);
            } else {
                long c = this.counts.get(pair);
                this.counts.put(pair, c + 1);
            }
        }

        // System.out.println(this.pairs.size());
        // System.out.println("Freq: " + freq);        
        // System.out.println("Counts: " + this.counts);

        while (steps > 0) {

            // System.out.println(steps + "\n");

            Map<String, Long> updated = new HashMap<>();
            for (String pair : this.pairs.keySet()) {
                if (this.counts.containsKey(pair)) {

                    String insert = this.pairs.get(pair);

                    // get the counts of the original pair
                    long pairCount = this.counts.containsKey(pair) ? this.counts.get(pair) : 0;

                    // NN -> NCN. First pair = NC, second pair = CN
                    // count first pair is counts this.counts.get("CN") + this.counts.get("NN");
                    String firstPair = String.valueOf(pair.charAt(0)) + insert;
                    long firstCount = updated.containsKey(firstPair) ? updated.get(firstPair) : 0;
                    updated.put(firstPair, pairCount + firstCount);
                    
                    // count second pair is counts this.counts.get("NC") + this.counts.get("NN");
                    String secondPair = insert + String.valueOf(pair.charAt(1));
                    long secondCount = updated.containsKey(secondPair) ? updated.get(secondPair) : 0;
                    updated.put(secondPair, pairCount + secondCount);

                    // System.out.println(String.format("%s (%d) + %s -> %s (%d), %s (%d)", pair, pairCount, insert, firstPair, firstCount, secondPair, secondCount));
            
                    // System.out.println(pair + " -> " + insert);
                    // System.out.println(firstPair + " | " + secondPair);

                    // now the counts for the original pair should go to zero
                    // updated.put(pair, 0L);

                    // count the added letter only, because this the only new one
                    Character insertChar = insert.charAt(0);
                    long insertCount = freq.containsKey(insertChar) ? freq.get(insertChar) : 0;
                    freq.put(insertChar, insertCount + pairCount);

                    // System.out.println(String.format("%s (%d) -> (%d)", insert, insertCount, insertCount + pairCount));

                }
            }

            this.counts = updated;
            // System.out.println("Freq: " + freq);
            // System.out.println("Counts: " + this.counts);

            steps--;
        }

        // System.out.println("Freq: " + freq);
        // System.out.println(freq.size());

        long max = Collections.max(freq.values());
        long min = Collections.min(freq.values());

        return max - min;
    }
}
