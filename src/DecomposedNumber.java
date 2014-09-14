import java.util.ArrayList;

/**
 * Created by Maksim.Muzafarov on 08.09.14.
 */
public class DecomposedNumber {
    public ArrayList<Byte> digits; // representation of a number in a set of
                                   // it's digits in descending order
    public int size;

    /**
     *
     * @param digits of decomposed number in descending order!!
     */
    public DecomposedNumber(ArrayList<Byte> digits) {
        this.digits = digits;
        this.size = digits.size();
    }

    public DecomposedNumber(byte digit) {
        this.digits = new ArrayList<Byte>();
        this.digits.add(digit);
        this.size = digits.size();
    }

    public DecomposedNumber(DecomposedNumber number) {
        this.digits = number.digits;
        this.size = number.size;
    }


    public DecomposedNumber plus(DecomposedNumber num) {
        return DigitMathOperations.sum(this, num);
    }

    public DecomposedNumber minus(DecomposedNumber num) {
        return DigitMathOperations.minus(this, num);
    }

    public void incrementMe() {
        DecomposedNumber result = plus(new DecomposedNumber((byte) 1));
        this.digits = result.digits;
        this.size = result.size;
    }

    public void decrementMe() {
        DecomposedNumber result = minus(new DecomposedNumber((byte) 1));
        this.digits = result.digits;
        this.size = result.size;
    }

    /**
     *
     * @return Decomposed Numbers of natural sequences can't begin from 0 digit
     */
    public boolean isNumberCorrect() {
        return this.digits.lastIndexOf((byte) 0) != this.size - 1;
    }

    public int toIntValue() throws RuntimeException {
        int result = 0;
        for(int j = this.digits.size() - 1; j >= 0; j--) {
            if (this.digits.get(j) == Constants.ANY_DIGIT)
                throw new RuntimeException("Cannot convert this Decomposed Number to int value.");
            result += this.digits.get(j) * Math.pow(10, j);
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = digits.hashCode();
        result = 31 * result + size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DecomposedNumber))
            return false;
        DecomposedNumber dn = (DecomposedNumber) obj;
        if (this.size != dn.size)
            return false;
        // iterate and compare each digit
        for (int i = 0; i < digits.size(); i++) {
            if (!DigitMathOperations.equals(digits.get(i), dn.digits.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = "";
        for(int i = (digits.size() - 1); i >= 0; i--) {
            result +=  (digits.get(i) == Constants.ANY_DIGIT) ? "*" : digits.get(i).toString();
        }
        return result;
    }

}
