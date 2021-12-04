import java.util.*;
import java.util.Collections;

class Bingo {
    private List<String> board;
    private List<Boolean> marked;
    private int boardSize;

    public Bingo(List<String> boardList) {
        this.board = boardList;
        this.boardSize = 5;
        this.marked = Arrays.asList(new Boolean[boardSize*boardSize]);
    }

    public void markNumber(String number) {
        for (int i = 0; i < this.board.size(); i++) {
            if (this.board.get(i).equals(number)) {
                this.marked.set(i, Boolean.TRUE);
            }
        }
    }

    private List<Boolean> getRow(int nthRow) {
        List<Boolean> row = new ArrayList<>();
        for (int i = 0; i < this.boardSize; i++) {
            int numberIndex = (i * this.boardSize) + nthRow;
            row.add(this.marked.get(numberIndex));
        }
        return row;
    }

    private List<Boolean> getColumn(int nthCol) {
        int fromIndex = (nthCol * this.boardSize);
        return this.marked
            .subList(fromIndex, fromIndex + this.boardSize);
    }

    private boolean allAreTrue(List<Boolean> booleanList) {
        return booleanList.stream().allMatch(b -> b != null);
    }

    public boolean hasWon() {

        for (int i = 0; i < this.boardSize; i++) {
            List<Boolean> row = this.getRow(i);
            List<Boolean> col = this.getColumn(i);
            if (this.allAreTrue(row) || this.allAreTrue(col)) {
                return true;
            }
        }
        return false;
    }

    public int getFinalScore(int lastNumber) {
        Integer totalOfUnmarked = 0;
        for (int i = 0; i < this.board.size(); i++) {
            boolean isNotMarked = this.marked.get(i) == null;
            if (isNotMarked) 
                totalOfUnmarked += Integer.parseInt(this.board.get(i));
        }
        return totalOfUnmarked * lastNumber;
    }
}