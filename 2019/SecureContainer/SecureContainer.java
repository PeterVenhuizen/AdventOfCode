import java.util.*;
import java.util.function.IntUnaryOperator;

class PossiblePassword {
    protected int password;
    protected char[] pwCharArray;
    protected boolean valid;

    public PossiblePassword(int password) {
        this.password = password;
        this.pwCharArray = this.convertPasswordToCharArray();
        this.valid = this.setPasswordValidity();
    }

    protected char[] convertPasswordToCharArray() {
        String number = String.valueOf(this.password);
        return number.toCharArray();
    }

    protected boolean setPasswordValidity() {
        return this.twoAdjacentDigitsAreTheSame() && this.theDigitsNeverDecrease();
    }

    protected boolean twoAdjacentDigitsAreTheSame() {
        for (int i = 0; i < this.pwCharArray.length - 1; i++) {
            if (this.pwCharArray[i] == this.pwCharArray[i+1]) {
                return true;
            }
        }
        return false;
    }

    protected boolean theDigitsNeverDecrease() {
        for (int i = this.pwCharArray.length - 1; i > 0; i--) {
            if (this.pwCharArray[i] < this.pwCharArray[i-1]) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        return this.valid;
    }
}

class PossiblePasswordPart2 extends PossiblePassword {

    public PossiblePasswordPart2(int password) {
        super(password);
    }

    private boolean adjacentMatchingDigtisAreNotPartOfALargerGroup() {
        
        int numberOfValidGroups = 0;

        List<Character> matchingGroup = new ArrayList<Character>();
        matchingGroup.add(this.pwCharArray[0]);

        for (int i = 1; i < this.pwCharArray.length; i++) {
            char nextChar = this.pwCharArray[i];
            if (nextChar != matchingGroup.get(matchingGroup.size() - 1)) {
                if (matchingGroup.size() == 2) {
                    numberOfValidGroups++;
                }
                matchingGroup.clear();
            }
            matchingGroup.add(nextChar);
        }

        if (matchingGroup.size() == 2) {
            numberOfValidGroups++;
        }

        return numberOfValidGroups >= 1;
    }

    @Override
    protected boolean setPasswordValidity() {
        return this.theDigitsNeverDecrease() 
            && this.adjacentMatchingDigtisAreNotPartOfALargerGroup();
    }
}

public class SecureContainer {

    public static void getNumberOfPossiblePasswords(int lowerBound, int upperBound) {
        int answerToPart1 = 0, answerToPart2 = 0;

        int password = lowerBound;
        while (password <= upperBound) {

            PossiblePassword pw = new PossiblePassword(password);
            if (pw.isValid())
                answerToPart1++;

            PossiblePasswordPart2 pw2 = new PossiblePasswordPart2(password);
            if (pw2.isValid())
                answerToPart2++;

            password++;
        }
        // return possiblePasswords.size();
        System.out.println("Answer for part1: " + answerToPart1);
        System.out.println("Answer for part2: " + answerToPart2);
    }

    public static void main(String[] args) {
        // part 1
        // tests
        System.out.println(new PossiblePassword(111111).isValid());
        System.out.println(new PossiblePassword(223450).isValid());
        System.out.println(new PossiblePassword(123789).isValid());

        // solution 1
        getNumberOfPossiblePasswords(145852, 616942);

        // part 2
        // tests
        System.out.println(new PossiblePasswordPart2(112233).isValid());
        System.out.println(new PossiblePasswordPart2(123444).isValid());
        System.out.println(new PossiblePasswordPart2(111122).isValid());
    }
}