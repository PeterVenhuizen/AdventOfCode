import java.util.*;

class Bingo {
    private String[][] board;
    private boolean[][] marked;

    public Bingo(List<String> boardString) {
        this.makeBoard(boardString);
    }

    private void makeBoard(List<String> boardList) {
        this.board = new String[5][5];

        int pos = 0;
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board[row].length; col++) {
                this.board[row][col] = boardList.get(pos);
                pos++;
            }
        }

        this.marked = new boolean[5][5];
    }

    public void markNumber(String number) {
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board[row].length; col++) {
                int pos = row + col;

                if (this.board[row][col].equals(number)) {
                    this.marked[row][col] = true;
                }
            }
        }
    }

    private boolean[] getColumn(boolean[][] marks, int index) {
        boolean[] column = new boolean[marks[index].length];
        for (int i = 0; i < column.length; i++) {
            column[i] = marks[i][index];
        }
        return column;
    }

    private boolean allAreTrue(boolean[] array) {
        for (boolean b : array) {
            if (!b) return false;
        }
        return true;
    }

    public boolean hasWon() {
        // check each row
        for (int row = 0; row < this.marked.length; row++) {
            if (this.allAreTrue(this.marked[row])) {
                return true;
            }
        }

        // check each column
        for (int col = 0; col < this.marked[0].length; col++) {
            boolean[] column = getColumn(this.marked, col);
            if (this.allAreTrue(column)) {
                return true;
            }
        }
        return false;
    }

    public int getFinalScore(int lastNumber) {
        // get the total of all unmarked numbers
        int total = 0;

        for (int i = 0; i < this.marked.length; i++) {
            for (int j = 0; j < this.marked[i].length; j++) {
                if (this.marked[i][j] == false) {
                    total += Integer.parseInt(this.board[i][j]);
                }
            }
        }

        System.out.println(lastNumber);
        System.out.println(total);

        return total * lastNumber;

    }

    public void printBoard() {
        for (int i = 0; i < this.board.length; i++) {
            System.out.println(String.join(" ", this.board[i]));
        }
    }

    public void printMarks() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                System.out.print((this.marked[i][j] ? " X " : " - "));
            }
            System.out.println();
        }
    }
}