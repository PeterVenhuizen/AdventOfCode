import java.io.*;
import java.util.*;

public class GiantSquid {

    public static BingoSubsystem readyBingoSubsystem(String inputFile) 
        throws FileNotFoundException {
            Scanner sc = new Scanner(new File(inputFile));

            // first line contains the draws
            List<String> draws = Arrays.asList(sc.nextLine().split(","));

            // after that come the boards, consisting of five numbers per 
            // line and five lines per board
            List<Bingo> bingoBoards = new ArrayList<>();
            List<String> newBoard = new ArrayList<>();
            while (sc.hasNext()) {

                String line = sc.nextLine();
                boolean isBoardComplete = line.isEmpty() && newBoard.size() == 25;
                if (isBoardComplete) {
                    bingoBoards.add(new Bingo(newBoard));
                    newBoard = new ArrayList<>();
                }

                else if (!line.isEmpty()) {
                    String[] lineArray = line.trim().replaceAll("\\s+", " ").split(" ");
                    for (int i = 0; i < lineArray.length; i++) {
                        newBoard.add(lineArray[i]);
                    }
                }
            }
            sc.close();

            bingoBoards.add(new Bingo(newBoard));
            return new BingoSubsystem(draws, bingoBoards);
        }

    public static void main(String[] args) {

        try {

            BingoSubsystem testSystem = readyBingoSubsystem("test_input.txt");
            testSystem.play();

            BingoSubsystem bingoSystem = readyBingoSubsystem("input.txt");
            bingoSystem.play();

            // part 1 test
            System.out.println("Part 1 test (should be 4512): " + testSystem.getFirstWinner().getFinalScore());
            System.out.println("Part 1 answer: " + bingoSystem.getFirstWinner().getFinalScore());
            
            // part 2 test
            System.out.println("Part 2 test (should be 1924): " + testSystem.getLastWinner().getFinalScore());
            System.out.println("Part 2 answer: " + bingoSystem.getLastWinner().getFinalScore());

        } catch (IOException e) {}
    }
}
