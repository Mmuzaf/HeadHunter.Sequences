import java.util.ArrayList;

/**
 * Created by Maksim.Muzafarov on 10.09.14.
 */
public class DigitMathOperations {

    /**
     *
     * @param first input digit
     * @param second input digit
     * @return [result digit] if first digit >= second digit and [10 + result digit] in others
     */
    public static Byte minus(Byte first, Byte second) {
        if (first != Constants.ANY_DIGIT & second != Constants.ANY_DIGIT) {
            // add 20 to first digit instead of 10
            return ((first - second) >= 0) ? (byte) (first - second) : (byte) (20 + first - second);
        } else {
            return Constants.ANY_DIGIT;
        }
    }

    public static Byte sum(Byte first, Byte second) {
        if (first != Constants.ANY_DIGIT & second != Constants.ANY_DIGIT) {
            return (byte) (first + second);
        } else {
            return Constants.ANY_DIGIT;
        }
    }

    public static Byte divide(Byte numerator, Byte denominator) {
        if (numerator != Constants.ANY_DIGIT & denominator != Constants.ANY_DIGIT) {
            return (byte) (numerator / denominator);
        } else {
            return Constants.ANY_DIGIT;
        }
    }

    private static Byte getNextStepDigit(Byte digit) {
        byte result = divide(digit, (byte) 10);
        return (result == Constants.ANY_DIGIT) ? 0 : result;
    }

    public static Byte mod(Byte numerator, Byte denominator) {
        if (numerator != Constants.ANY_DIGIT & denominator != Constants.ANY_DIGIT) {
            return (byte) (numerator % denominator);
        } else {
            return Constants.ANY_DIGIT;
        }
    }

    public static boolean equals(Byte a, Byte b) {
        return !(a != Constants.ANY_DIGIT & b != Constants.ANY_DIGIT) || a.equals(b);

    }

    public static DecomposedNumber sum(DecomposedNumber A, DecomposedNumber B) {
        ArrayList<Byte> result = new ArrayList<Byte>();
        int maxDigitsNumberSize = Math.max(A.size, B.size);
        byte addToHighest = 0;
        byte digitOfA, digitOfB, currentDigit;
        int i = 0;
        while((i < maxDigitsNumberSize) || addToHighest > 0) {
            digitOfA = sum(
                    ((i < A.size) ? A.digits.get(i) : 0),
                    addToHighest
            );
            digitOfB = (i < B.size) ? B.digits.get(i) : 0;
            byte digitResult = sum(digitOfA, digitOfB);
            currentDigit = mod(digitResult, (byte) 10);
            addToHighest = getNextStepDigit(digitResult);
            result.add(currentDigit);
            i++;
        }
        return new DecomposedNumber(result);
    }

    public static DecomposedNumber minus(DecomposedNumber first, DecomposedNumber second) throws RuntimeException{
        ArrayList<Byte> result = new ArrayList<Byte>();
        if (first.size < second.size)
            throw new RuntimeException("Negative numbers are not allowed.");
        // Save highest digit of the first number
        byte highestDigit = first.digits.get(first.size - 1);
        byte takeFromHighest = 0;
        byte digitOfFirst, digitOfSecond, currentDigit, preStepDigit;
        int i = 0;
        while((i < first.size) || takeFromHighest > 0) {
            if ((i >= first.size) & takeFromHighest > 0)
                throw new RuntimeException("Negative numbers are not allowed.");
            // decrease current digit on takeFromHighest value from previous step
            preStepDigit = minus(first.digits.get(i), takeFromHighest);
            digitOfFirst = mod(preStepDigit, (byte) 10);
            // if current digit was [0] then takeFromHighest++
            takeFromHighest = getNextStepDigit(preStepDigit);
            digitOfSecond = (i < second.size) ? second.digits.get(i) : 0;

            byte digitResult = minus(digitOfFirst, digitOfSecond);
            currentDigit = mod(digitResult, (byte) 10);
            takeFromHighest += getNextStepDigit(digitResult);
            // no need to add the last 0 digit
            if (!((i == first.size - 1) & (currentDigit == 0) & (highestDigit != 0))) {
                result.add(currentDigit);
            }
            i++;
        }
        return new DecomposedNumber(result);
    }

}
