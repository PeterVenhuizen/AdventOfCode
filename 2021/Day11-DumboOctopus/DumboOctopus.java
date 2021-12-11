import java.io.IOException;

public class DumboOctopus {
	public static void main(String[] args) {

		try {
			Cavern test1 = new Cavern("test_input.txt");
			System.out.println("Test part 1 (should be 1656): " + test1.flash(100));
			// System.out.println(test1.toString());

			Cavern cavern1 = new Cavern("input.txt");
			System.out.println("Answer part 1: " + cavern1.flash(100));

			Cavern test2 = new Cavern("test_input.txt");
			System.out.println("Test part 2 (should be 195): " + test2.flash(Integer.MAX_VALUE));
			// System.out.println(test2.toString());

			Cavern cavern2 = new Cavern("input.txt");
			System.out.println("Answer part 2: " + cavern2.flash(Integer.MAX_VALUE));

		} catch (IOException e) {}

	}
}
