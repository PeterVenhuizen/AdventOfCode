import java.util.*;

class FoldingPaper {
    private int M; // rows -> y
    private int N; // columns -> x
    private List<String> folds;
    private boolean[][] paper;

	public FoldingPaper(int M, int N, List<int[]> coordinates, List<String> folds) {
        this.M = M;
        this.N = N;
        this.folds = folds;
        initPaperMatrix(coordinates);
    }

    private void initPaperMatrix(List<int[]> coordinates) {
        this.paper = new boolean[this.M][this.N];
        for (int[] coor : coordinates) {
            int x = coor[0];
            int y = coor[1];
            this.paper[y][x] = true;
        }
    }

    public void fold() {

        int numberOfFolds = 1;
        for (String fold : this.folds) {

            String axis = fold.split("=")[0];
            int n = Integer.parseInt(fold.split("=")[1]);

            // create smaller folded paper
            if (axis.equals("y")) this.M /= 2;
            if (axis.equals("x")) this.N /= 2;
            boolean foldedPaper[][] = new boolean[this.M][this.N];

            // copy all the dots which are already inside the new folded paper
            for (int y = 0; y < this.M; y++) {
                for (int x = 0; x < this.N; x++) {
                    foldedPaper[y][x] = this.paper[y][x];
                }
            }

            // fold up
            if (axis.equals("y")) {

                for (int y = n + 1; y < this.paper.length; y++) {
                    int delta = n - (y - n);
                    for (int x = 0; x < this.N; x++) {
                        if (this.paper[y][x])
                            foldedPaper[delta][x] = this.paper[y][x];
                    }
                }
                this.paper = foldedPaper;
            }

            // fold left
            if (axis.equals("x")) {
                for (int y = 0; y < this.M; y++) {
                    for (int x = n + 1; x < this.paper[y].length; x++) {
                        int delta = n - (x - n);
                        if (this.paper[y][x])
                            foldedPaper[y][delta] = this.paper[y][x];
                    }
                }
                this.paper = foldedPaper;
            }

            // System.out.println(this.toString());

            int dots = 0;
            for (int y = 0; y < this.M; y++) {
                for (int x = 0; x < this.N; x++) {
                    if (this.paper[y][x]) dots++;
                }
            }

            System.out.println(numberOfFolds + " -> " + dots);

            numberOfFolds++;
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < this.M; i++) {
            String row = "";
            for (int j = 0; j < this.N; j++) {
                row += (this.paper[i][j]) ? "#" : " ";
            }
            output = output + row + "\n";
            // output += Arrays.toString(this.paper[i]) + "\n";
        }
        return output;
    }
}