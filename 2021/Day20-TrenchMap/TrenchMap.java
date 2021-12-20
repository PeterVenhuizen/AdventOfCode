import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class ImageEnhancer {
	private String algorithm;
	private char[][] image;

	public ImageEnhancer(String algorithm, char[][] image) {
		this.algorithm = algorithm;
		this.image = image;
	}

	private void develop() {
		for (char[] row : this.image) {
			System.out.println(String.valueOf(row));
		}
	}

	private String getPixelSquare(int i, int j, char bg) {

		List<Character> square = new ArrayList<>();
		for (int di = -1; di <= 1; di++) {
			for (int dj = -1; dj <= 1; dj++) {

				try {
					square.add(this.image[i+di][j+dj]);
				} catch (IndexOutOfBoundsException e) {
					square.add(bg);
				}
			}
		}

		return square.stream()
			.map(String::valueOf)
			.collect(Collectors.joining());
	}

	private int pixelSquareToIndex(String square) {
		square = square.replaceAll("#", "1").replaceAll("\\.", "0");
		return Integer.parseInt(square, 2);
	}

	public void enhance(int times) {

		// develop(); // print image

		// The 'infinite' pixels (...,...,... -> 000_000_000) equal a decimal value of 0
		// If the image enhancement algorithm value at position 0 is a '#', the infinite
		// pixel value will be 512 (111_111_111) the next time. If the value at position
		// 511 is a '.', the background pixels swap every time.
 		char bg = '.';
		boolean needToFlipBackground = this.algorithm.charAt(0) == '#'
			&& this.algorithm.charAt(this.algorithm.length() - 1) == '.';

		for (int t = 0; t < times; t++) {

			char[][] enhanced = new char[this.image.length + 2][];

			// expand the image
			for (int i = -1; i < this.image.length + 1; i++) {

				String row = "";
				for (int j = -1; j < this.image.length + 1; j++) {
					String square = getPixelSquare(i, j, bg);
					int pixelIndex = pixelSquareToIndex(square);
					row += this.algorithm.substring(pixelIndex, pixelIndex+1);
				}
				enhanced[i+1] = row.toCharArray();
			}

			if (needToFlipBackground) {
				bg = (bg == '#') ? '.' : '#';
			}

			this.image = enhanced;
			// develop();

		}
	}

	public int countPixels() {
		String imageString = "";
		for (int i = 0; i < this.image.length; i++) {
			imageString += String.valueOf(this.image[i]);
		}
		return (int) imageString.chars()
			.filter(c -> c == '#')
			.count();
	}

}

public class TrenchMap {



	private static ImageEnhancer scanOceanTrench(String filename) throws IOException {
		Scanner sc = new Scanner(new File(filename));
	
		String algorithm = sc.nextLine();
	
		List<char[]> tmp = new ArrayList<>();
		while (sc.hasNext()) {
	
			String line = sc.nextLine();
			if (!line.isEmpty()) {
				tmp.add(line.toCharArray());
			}	
		}

		char[][] image = new char[tmp.size()][tmp.size()];
		for (int i = 0; i < tmp.size(); i++) image[i] = tmp.get(i);

		return new ImageEnhancer(algorithm, image);
	}
	

	public static void main(String[] args) {
		try {
			ImageEnhancer test = scanOceanTrench("test_input.txt");
			test.enhance(2);
			System.out.println("Test part 1 (should be 35): " + test.countPixels());

			ImageEnhancer answer = scanOceanTrench("input.txt");
			answer.enhance(2);
			System.out.println("Answer part 1: " + answer.countPixels());

			test.enhance(48);
			System.out.println("Test part 2 (should be 3351): " + test.countPixels());

			answer.enhance(48);
			System.out.println("Answer part 2: " + answer.countPixels());

			// more_input (2) -> 5326
			// input (2) -> 5249

			// more_input (50) -> 17096
			// input (50) -> 15714

			// ImageEnhancer answer = scanOceanTrench("more_input.txt");
			// answer.enhance(2);
			// System.out.println("Answer part 1: " + answer.countPixels());

		} catch (IOException e) {}
	}
}
