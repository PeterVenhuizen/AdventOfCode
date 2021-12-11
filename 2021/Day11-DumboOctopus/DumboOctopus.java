import java.io.IOException;

public class DumboOctopus {
	public static void main(String[] args) {

		try {
			Cavern test = new Cavern("test_input.txt");
			// System.out.println("Test part 1 (should be 1656): " + test.flash(100));
			System.out.println("Test part 1 (should be 1656): " + test.flash(Integer.MAX_VALUE));

			Cavern answer = new Cavern("input.txt");
			// System.out.println("Answer part 1: " + answer.flash(100));
			System.out.println("Answer part 1: " + answer.flash(Integer.MAX_VALUE));

		} catch (IOException e) {}

	}
}
