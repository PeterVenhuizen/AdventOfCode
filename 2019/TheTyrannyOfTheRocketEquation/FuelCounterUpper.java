import java.io.*;
import java.util.*;
import java.util.function.IntUnaryOperator;

class Recursive<I> {
    public I func;
}

class FuelCounterUpper {

    public static ArrayList<Integer> readInputFile(String inputFile) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(inputFile));
        ArrayList<Integer> moduleMasses = new ArrayList<Integer>();
        while (sc.hasNextLine()) {
            moduleMasses.add(Integer.parseInt(sc.nextLine()));
        }
        sc.close();
        return moduleMasses;
    }

    public static int calcFuel(ArrayList<Integer> moduleMasses, IntUnaryOperator op) {
        return moduleMasses.stream()
            .map(m -> op.applyAsInt(m))
            .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {

        try {
            ArrayList<Integer> moduleMasses = readInputFile(args[0]);

            // part 1
            IntUnaryOperator calcBasicFuel = mass -> mass / 3 - 2;

            // tests
            System.out.println(calcBasicFuel.applyAsInt(12) == 2);
            System.out.println(calcBasicFuel.applyAsInt(14) == 2);
            System.out.println(calcBasicFuel.applyAsInt(1969) == 654);
            System.out.println(calcBasicFuel.applyAsInt(100756) == 33583);

            // solution
            System.out.println(calcFuel(moduleMasses, calcBasicFuel));

            // part 2
            Recursive<IntUnaryOperator> calcFuelForFuel = new Recursive<>();
            calcFuelForFuel.func = mass -> {
                int fuel = calcBasicFuel.applyAsInt(mass);
                if (fuel / 3 - 2 <= 0) {
                    return fuel;
                } else {
                    return fuel + calcFuelForFuel.func.applyAsInt(fuel);
                }
            };

            // tests
            System.out.println(calcFuelForFuel.func.applyAsInt(14) == 2);
            System.out.println(calcFuelForFuel.func.applyAsInt(1969) == 966);
            System.out.println(calcFuelForFuel.func.applyAsInt(100756) == 50346);

            // solution
            System.out.println(calcFuel(moduleMasses, calcFuelForFuel.func));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Could not find input");
            System.exit(0);
        }

    }
}