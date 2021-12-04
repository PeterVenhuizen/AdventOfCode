import java.io.*;
import java.util.*;

public class GiantSquid {
    // public static List<String> DRAWS;
    // public static List<Bingo> BOARDS = new ArrayList<>();

    public static BingoSubsystem readyBingoSubsystem(String inputFile) 
        throws FileNotFoundException {
            Scanner sc = new Scanner(new File(inputFile));

            // first line are the draws
            List<String> draws = Arrays.asList(sc.nextLine().split(","));
            // GiantSquid.DRAWS = Arrays.asList(sc.nextLine().split(","));

            // after that come the boards
            List<Bingo> bingoBoards = new ArrayList<>();
            List<String> newBoard = new ArrayList<>();
            while (sc.hasNext()) {

                // System.out.println(board.size());
                String line = sc.nextLine();
                if (line.isEmpty() && newBoard.size() == 25) {
                    bingoBoards.add(new Bingo(newBoard));

                    // GiantSquid.BOARDS.add(new Bingo(board));
                    newBoard = new ArrayList<>();
                }

                else if (!line.isEmpty()) {
                    // System.out.println(line.trim().replaceAll("\\s+", " "));
                    String[] lineArray = line.trim().replaceAll("\\s+", " ").split(" ");
                    for (int i = 0; i < lineArray.length; i++) {
                        newBoard.add(lineArray[i]);
                    }
                }
            }

            // GiantSquid.BOARDS.add(new Bingo(board));
            bingoBoards.add(new Bingo(newBoard));

            sc.close();

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


            // readInput("test_input.txt");

            // List<Bingo> winners = new ArrayList<>();

            // List<String> draws = GiantSquid.DRAWS;
            // Bingo winningBoard;
            // drawloop:
            // for (int i = 0; i < draws.size(); i++) {


            //     String nextDraw = draws.get(i);
            //     // System.out.println(nextDraw);
            //     System.out.println("Last number: " + nextDraw);

            //     for (int j = 0; j < GiantSquid.BOARDS.size(); j++) {
            //         Bingo board = GiantSquid.BOARDS.get(j);

            //         if (!board.hasWon()) {

            //             board.markNumber(nextDraw);

            //             if (board.hasWon()) {
            //                 winners.add(board);

            //                 if (winners.size() == 1) {
            //                     System.out.println("First winner: " + board.getFinalScore(Integer.parseInt(nextDraw)));
            //                 }

            //                 System.out.println("Number of winners: " + winners.size());
            //                 // board.printBoard();
            //                 // board.printMarks();
                            
            //                 if (winners.size() == GiantSquid.BOARDS.size()) {
            //                     System.out.println("Last winner: " + board.getFinalScore(Integer.parseInt(nextDraw)));
            //                     break drawloop;
            //                 }
            //             }
            //         }

            //     }

                // GiantSquid.BOARDS.forEach(board -> {
                //     board.markNumber(nextDraw);



                // });

                // winningBoard = GiantSquid.BOARDS
                //     .stream()
                //     .filter(obj -> obj.hasWon())
                //     .findFirst();

                // if (winningBoard.isPresent()) {
                //     GiantSquid.WINNER = winningBoard.get();
                //     GiantSquid.lastNumber = Integer.parseInt(nextDraw);
                //     break;
                // }
            // }

            // GiantSquid.WINNER.printBoard();
            // System.out.println(GiantSquid.WINNER.getFinalScore(GiantSquid.lastNumber));

        } catch (IOException e) {}



    }
}
