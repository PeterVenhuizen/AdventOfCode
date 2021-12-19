import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.lang.Math;
import java.util.stream.Collectors;

public class SnailFish {

	/*
		* addition
			- [1,2] + [[3,4],5] = [[1,2],[[3,4],5]]

		* explode if at a depth of five or above, the left most pair explodes
			- the left value is added to first regular value to its left (if any)
			- the right value is added to the first regular value to its right (if any)
			- the exploding pair is replaced by a zero (0)

		* split if a number is 10 or greater, the leftmost regular number splits
			- the left number of the pair is divided by two and rounded DOWN (11 / 2 -> 5)
			- the right number is divided by two and rounded UP (11 / 2 -> 6)
			- [0, 13] -> [0, [6,7]]

		Always first check if something explodes, before checking for a split. If no reductions
		(explodes, splits) are possible, the number that remains is the actual result

		* magnitude -> the magnitude of a pair is 3 times the left element plus 2 times 
			the right element
			- [9, 1] = 3*9 + 2*1 = 29
			- magnitude calculation is recursive:
				+ [[9,1],[1,9]] = [[3*9 + 2*1],[3*1 + 2*9]] = [29,21] = 3*29 + 2*21 = 129
	*/

	private static int snailFishMath(String filename) throws IOException {

		Scanner sc = new Scanner(new File(filename));
		
		String snailfishNumber = sc.nextLine();
		while (sc.hasNext()) {
			snailfishNumber = addition(snailfishNumber, sc.nextLine());
			snailfishNumber = reduce(snailfishNumber);
			// System.out.println("FINAL: " + snailfishNumber);
		}
		sc.close();

		return finishHomeworkAssignment(snailfishNumber);
	}

	private static int secondAssignment(String filename) throws IOException {

		List<String> lines = Files.lines(Paths.get(filename)).collect(Collectors.toList());
		List<Integer> numbers = new ArrayList<>();

		String snailfishNumber = "";

		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.size(); j++) {
				if (i != j) {
					snailfishNumber = addition(lines.get(i), lines.get(j));
					snailfishNumber = reduce(snailfishNumber);
					numbers.add(finishHomeworkAssignment(snailfishNumber));
				}
			}

		}

		return Collections.max(numbers);
	}

	private static String addition(String sfN1, String sfN2) {
		return String.format("[%s,%s]", sfN1, sfN2);
	}

	private static boolean nextMatches(String snailfishNumber, int pos, String next) {
		return snailfishNumber.substring(pos, pos + 1).matches(next);
	}

	private static int nextToNumber(String snailfishNumber, int pos) {
		try {
			return Integer.parseInt(snailfishNumber.substring(pos, pos + 2));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private static String reduce(String snailfishNumber) {
		int pos = 0, depth = 0;
		boolean keepReducing = true;

		boolean allowedToSplit = false;

		while (keepReducing) {

			// System.out.println("pos: " + pos + " | " + depth + " -> " + snailfishNumber);

			// going deeper
			if (nextMatches(snailfishNumber, pos, "\\[")) {
				++depth;
				++pos;
			} 

			// going outwards again
			else if (nextMatches(snailfishNumber, pos, "\\]")) {
				--depth;
				++pos;
			}

			// see if it is a number
			else if (nextMatches(snailfishNumber, pos, "\\d")) {

				boolean needToExplode = depth >= 5;
				boolean needToSplit = nextToNumber(snailfishNumber, pos) >= 10;

				// see if I need to explode
				if (needToExplode) {
					snailfishNumber = explode(snailfishNumber, pos);
					pos = 0;
					depth = 0;
				}

				// see if I need to split
				else if (allowedToSplit && needToSplit) {
					snailfishNumber = split(snailfishNumber, pos);
					pos = 0;
					depth = 0;
				}

				else {
					++pos;
				}

			}

			else {
				++pos;
			}

			boolean endOfTheNumber = pos == snailfishNumber.length() - 1;
			if (endOfTheNumber && !allowedToSplit) {
				allowedToSplit = true;
				pos = 0;
				depth = 0;
			}

			keepReducing = pos < snailfishNumber.length() - 1;
		}
		return snailfishNumber;
	}


	private static String explode(String snailfishNumber, int pos) {

		// System.out.println("EXPLODE: " + snailfishNumber + " pos: " + pos);

		// build the new snailfishNumber
		String sfN = "";

		// Pattern explosion = Pattern.compile("^(.*?)\\[(\\d{1,2}),(\\d{1,2})\\](.*)$");
		Pattern explosion = Pattern.compile("^\\[(\\d{1,2}),(\\d{1,2})\\](.*)$");
		Matcher matcher = explosion.matcher(snailfishNumber.substring(pos - 1));

		Pattern numberPattern = Pattern.compile("(\\d{1,})");

		if (matcher.matches()) {
			// String leftPart = matcher.group(1);
			String leftPart = snailfishNumber.substring(0, pos - 1);
			int leftNumber = Integer.parseInt(matcher.group(1));
			int rightNumber = Integer.parseInt(matcher.group(2));
			String rightPart = matcher.group(3);

			// look for a number to the left
			Matcher leftMatcher = numberPattern.matcher(leftPart);
			int leftMatches = (int) leftMatcher.results().count();
			leftMatcher.reset();
			if (leftMatches > 0) {
				int i = 1;
				while (leftMatcher.find()) {
					// last regular number before the exploding pair
					if (i == leftMatches) {
						// copy everything before the last regular number
						sfN += leftPart.substring(0, leftMatcher.start());
						// add the exploding number
						sfN += Integer.parseInt(leftMatcher.group(1)) + leftNumber;
						// copy everything between the last number and the pos
						sfN += leftPart.substring(leftMatcher.end()); 
					}
					++i;
				}
			} else {
				sfN += leftPart;
			}

			// loof for a number to the right
			Matcher rightMatcher = numberPattern.matcher(rightPart);
			int rightMatches = (int) rightMatcher.results().count();
			rightMatcher.reset();
			if (rightMatches > 0) {
				int i = 1;
				while (rightMatcher.find()) {
					if (i == 1) {
						sfN += "0";
						// copy everything between the second exploding number and the first regular number
						sfN += rightPart.substring(0, rightMatcher.start());
						// add the numbers
						sfN += (rightNumber + Integer.parseInt(rightMatcher.group(1)));
						// copy the rest
						sfN += rightPart.substring(rightMatcher.end());
					}
					++i;
				}
			} else {
				sfN += "0" + rightPart;
			}

			// System.out.println(leftPart + " | " + leftNumber + " | " + rightNumber + " | " + rightPart);
			// System.out.println(sfN);
		}
		return sfN;
	}

	private static String split(String snailfishNumber, int pos) {

		// System.out.println("SPLIT: " + snailfishNumber + " pos: " + pos);

		int numberToSplit = Integer.parseInt(snailfishNumber.substring(pos, pos + 2));

		// System.out.println(numberToSplit);

		// divide by two and round down and divide by two and round up
		// add another level of depth around the split pair
		String splitNumber = String.format("[%d,%d]", numberToSplit / 2, (int) Math.ceil(numberToSplit / 2.0));

		String sfN = snailfishNumber.substring(0, pos); // left part
		sfN += splitNumber; // split number
		sfN += snailfishNumber.substring(pos + 2); // right part

		// System.out.println(sfN);

		return sfN;
	}

	private static int finishHomeworkAssignment(String snailfishNumber) {
		boolean homeworkIsDone = false;

		while (!homeworkIsDone) {
			// System.out.println(snailfishNumber);
			snailfishNumber = magnitude(snailfishNumber);
			// System.out.println(snailfishNumber + " " + snailfishNumber.contains("["));

			homeworkIsDone = !snailfishNumber.contains("[");
			// homeworkIsDone = true;
		}
		return Integer.parseInt(snailfishNumber);
	}

	private static String magnitude(String snailfishNumber) {
		// left * 3 + right * 2, recursively

		// build the new snailfishNumber
		String sfN = "";

		// Pattern explosion = Pattern.compile("^(.*?)\\[(\\d{1,2}),(\\d{1,2})\\](.*)$");
		Pattern magnitudePattern = Pattern.compile("(\\[(\\d+),(\\d+)\\])");
		Matcher matcher = magnitudePattern.matcher(snailfishNumber);

		int nMatches = (int) matcher.results().count();
		matcher.reset();

		if (nMatches > 0) {
			int total = 0;
			boolean done = false;
			while (matcher.find()) {

				for (int i = 1; i <= matcher.groupCount(); i++) {

					if (!done) {
						// System.out.println(i + " : " + matcher.group(i));
						if (i == 1) {
							sfN += snailfishNumber.substring(0, matcher.start());
						} else if (i == 2) {
							total = Integer.parseInt(matcher.group(i)) * 3;
						} else if (i == 3) {
							total += Integer.parseInt(matcher.group(i)) * 2;
							sfN += total;
							sfN += snailfishNumber.substring(matcher.end());
							done = true;
						}
					}
				}

			}
		}

		return sfN;
	}

	public static void main(String[] args) {
		// System.out.println(explode("[[[[[9,8],1],2],3],4]", 5).equals("[[[[0,9],2],3],4]"));
		// System.out.println(explode("[7,[6,[5,[4,[3,2]]]]]", 13).equals("[7,[6,[5,[7,0]]]]"));
		// System.out.println(explode("[[6,[5,[4,[3,2]]]],1]", 11).equals("[[6,[5,[7,0]]],3]"));
		// System.out.println(explode("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", 11).equals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"));
		// System.out.println(explode("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", 25).equals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]"));
		// System.out.println(explode("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]", 17).equals("[[[[0,7],4],[15,[0,13]]],[1,1]]"));

		// System.out.println(split("[10]", 1).equals("[[5,5]]"));
		// System.out.println(split("[11]", 1).equals("[[5,6]]"));
		// System.out.println(split("[[[[0,7],4],[15,[0,13]]],[1,1]]", 13).equals("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]"));
		// System.out.println(split("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", 22).equals("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]"));

		// System.out.println(finishHomeworkAssignment("[9,1]") == 29);
		// System.out.println(finishHomeworkAssignment("[[1,2],[[3,4],5]]") == 143);
		// System.out.println(finishHomeworkAssignment("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]") == 1384);

		try {
			System.out.println("Test part 1 (should be 4140): " + snailFishMath("test_input.txt"));
			System.out.println("Answer part 1: " + snailFishMath("input.txt"));

			System.out.println("Test part 2 (should be 3933): " + secondAssignment("test_input.txt"));
			System.out.println("Answer part 2: " + secondAssignment("input.txt"));
		} catch (IOException e) {}
	}
}
