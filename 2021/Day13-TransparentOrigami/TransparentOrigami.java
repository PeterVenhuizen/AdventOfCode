import java.io.*;
import java.util.*;

public class TransparentOrigami {

	public static FoldingPaper readDots(String inputFile) throws IOException {
		Scanner sc = new Scanner(new File(inputFile));

		List<int[]> coordinates = new ArrayList<>();
		List<String> folds = new ArrayList<>();
		int M = 0; // y
		int N = 0; // x

		while (sc.hasNext()) {
			String line = sc.nextLine();
			// System.out.println(line);

			if (line.contains(",")) {
				int[] coor = new int[2];
				coor[0] = Integer.parseInt(line.split(",")[0]);
				coor[1] = Integer.parseInt(line.split(",")[1]);
				coordinates.add(coor);

				if (coor[0] > N) N = coor[0];
				if (coor[1] > M) M = coor[1];
			}

			else if (line.contains("=")) {
				folds.add(line.split(" ")[2]);
			}
		}

		sc.close();
		return new FoldingPaper(M+1, N+1, coordinates, folds);
	}
	public static void main(String[] args) {

		try {
			// FoldingPaper test = readDots("test_input.txt");
			// System.out.println(test.toString());
			// test.fold();

			FoldingPaper answer = readDots("input.txt");
			answer.fold();
			System.out.println(answer.toString());

		} catch (IOException e) {}

	}
}
