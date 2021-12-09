import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class DiagnosticReport {
    private List<String> reportData;
    
    public DiagnosticReport(List<String> reportData) {
        this.reportData = reportData;
    }

    /* get byte string for a specific column index */
    private String getBitColumnAtIndex(List<String> data, int index) {
        return data.stream()
            .map(v -> Character.toString(v.charAt(index)))
            .collect(Collectors.joining(""));
    }

    /* count the occurences for each bit */
    private Map<String, Long> getOccurences(String bitColumn) {
        return Arrays.asList(bitColumn.split(""))
            .stream()
            .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
    }

    private String getMostCommon(Map<String, Long> occurences) {
        return (occurences.get("0") > occurences.get("1")) 
            ? "0" : "1";
    }

    private String getMostCommonWithDefault(Map<String, Long> occurences) {
        if (occurences.get("0") == occurences.get("1")) {
            return "1";
        }
        return getMostCommon(occurences);
    }

    private String getLeastCommon(Map<String, Long> occurences) {
        return (occurences.get("0") < occurences.get("1")) 
            ? "0" : "1";
    }

    private String getLeastCommonWithDefault(Map<String, Long> occurences) {
        if (occurences.get("0") == occurences.get("1")) {
            return "0";
        }
        return getLeastCommon(occurences);
    }

    private String getWantedBit(List<String> data, int index,
        Function<Map<String, Long>, String> func) {

        String bitColumn = getBitColumnAtIndex(data, index);
        Map<String, Long> occurences = getOccurences(bitColumn);
        return func.apply(occurences);
    }

    private String computePowerConsumption(int index, String binaryString, 
        Function<Map<String, Long>, String> func) {
    
        boolean isAtTheEndOfNumber = index == this.reportData.get(0).length();
        if (isAtTheEndOfNumber) {
            return binaryString;
        }

        String bit = getWantedBit(this.reportData, index, func);
        return computePowerConsumption(index + 1, binaryString + bit, func);
    }

    private String computeLifeSupportRating(List<String> data, int index, 
        String defaultBit, Function<Map<String, Long>, String> func) {

        if (data.size() == 1) {
            return data.get(0);
        }

        String bit = getWantedBit(data, index, func);
        List<String> dataSubset = data.stream()
            .filter(v -> Character.toString(v.charAt(index)).equals(bit))
            .collect(Collectors.toList());

        return computeLifeSupportRating(dataSubset, index + 1, defaultBit, func);
    }

    private int binaryStringToDecimal(String binaryString) {
        return Integer.parseInt(binaryString, 2);
    }

    public int getPowerConsumption() {
        String gammaRate = computePowerConsumption(0, "", this::getMostCommon);
        String epsilonRate = computePowerConsumption(0, "", this::getLeastCommon);
        return binaryStringToDecimal(gammaRate) * binaryStringToDecimal(epsilonRate);
    }

    public int getLifeSupportRating() {
        String O2GeneratorRating = computeLifeSupportRating(this.reportData, 0, "0", 
            this::getMostCommonWithDefault);
        String CO2ScrubberRating = computeLifeSupportRating(this.reportData, 0, "0",
            this::getLeastCommonWithDefault);
        return binaryStringToDecimal(O2GeneratorRating) * binaryStringToDecimal(CO2ScrubberRating);
    }

}