import java.util.*;

public class BingoSubsystem {
    private List<String> draws;
    private List<Bingo> boards;
    private int winnerCounter;
    private Bingo firstWinner;
    private Bingo lastWinner;

    public BingoSubsystem(List<String> draws, List<Bingo> boards) {
        this.draws = draws;
        this.boards = boards;
        this.winnerCounter = 0;
    }

    public void play() {
        boolean keepPlaying = this.winnerCounter < this.boards.size();
        
        int drawIndex = 0;
        while (keepPlaying) {

            String nextDraw = this.draws.get(drawIndex);
            this.boards.forEach(board -> {
                if (!board.hasWon()) {
                    board.markNumber(nextDraw);

                    if (board.hasWon()) {
                        board.setWinningNumber(Integer.parseInt(nextDraw));
                        this.winnerCounter++;

                        boolean isFirstWinner = this.winnerCounter == 1;
                        if (isFirstWinner) 
                            this.firstWinner = board;

                        boolean isLastWinner = this.winnerCounter == this.boards.size();
                        if (isLastWinner) 
                            this.lastWinner = board;
                    }
                }

            });
            drawIndex++;
            keepPlaying = this.winnerCounter < this.boards.size();
        }
    }

    public Bingo getFirstWinner() {
        return this.firstWinner;
    }

    public Bingo getLastWinner() {
        return this.lastWinner;
    }

}
