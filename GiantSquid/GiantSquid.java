import java.io.*;
import java.util.*;

public class GiantSquid {
    public static List<String> DRAWS;
    public static List<Bingo> BOARDS = new ArrayList<>();
    public static Bingo WINNER;
    public static int lastNumber;
    public static Bingo LAST;

    public static void readInput(String inputFile) 
        throws FileNotFoundException {
            Scanner sc = new Scanner(new File(inputFile));
            List<Integer> depthMeasurements = new ArrayList<Integer>();

            // first line are the draws
            GiantSquid.DRAWS = Arrays.asList(sc.nextLine().split(","));

            // 
            List<String> board = new ArrayList<>();
            String line;

            while (sc.hasNext()) {

                // System.out.println(board.size());
                line = sc.nextLine();
                if (line.isEmpty() && board.size() == 25) {
                    GiantSquid.BOARDS.add(new Bingo(board));
                    board = new ArrayList<>();
                }

                else if (!line.isEmpty()) {

                    // System.out.println(line.trim().replaceAll("\\s+", " "));
                    String[] lineArray = line.trim().replaceAll("\\s+", " ").split(" ");

                    for (int i = 0; i < lineArray.length; i++) {
                        board.add(lineArray[i]);
                    }
                }
            }

            GiantSquid.BOARDS.add(new Bingo(board));

            sc.close();
        }

    public static void main(String[] args) {

        try {
            readInput("input.txt");

            List<Bingo> winners = new ArrayList<>();

            List<String> draws = GiantSquid.DRAWS;
            Bingo winningBoard;
            drawloop:
            for (int i = 0; i < draws.size(); i++) {


                String nextDraw = draws.get(i);
                // System.out.println(nextDraw);
                System.out.println("Last number: " + nextDraw);

                for (int j = 0; j < GiantSquid.BOARDS.size(); j++) {
                    Bingo board = GiantSquid.BOARDS.get(j);

                    if (!board.hasWon()) {

                        board.markNumber(nextDraw);

                        if (board.hasWon()) {
                            winners.add(board);
                            System.out.println("Number of winners: " + winners.size());
                            board.printBoard();
                            board.printMarks();
                            
                            if (winners.size() == GiantSquid.BOARDS.size()) {
                                System.out.println(board.getFinalScore(Integer.parseInt(nextDraw)));
                                break drawloop;
                            }
                        }
                    }

                }

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
            }

            // GiantSquid.WINNER.printBoard();
            // System.out.println(GiantSquid.WINNER.getFinalScore(GiantSquid.lastNumber));

        } catch (IOException e) {}



    }
}
