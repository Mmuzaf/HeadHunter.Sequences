import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Maksim.Muzafarov on 08.09.14.
 */
public class SequenceSearcher {

    ArrayList<Byte> extendedNeedle; // List of any digits and base sequence
                                    // Example: [any digits list][base digits sequence][any digits list]
    ArrayList<Byte> baseNeedle;
    int anyDigitsListSize; // Size of added sub list with any digits

    private ArrayList<Byte> getAnyDigitsList(int listSize) {
        ArrayList<Byte> result = new ArrayList<Byte>();
        do {
            result.add(Constants.ANY_DIGIT);
            listSize--;
        } while (listSize > 0);
        return result;
    }

    public SequenceSearcher(ArrayList<Byte> needle) {
        this.baseNeedle = needle;
        // anyDigitsListSize must be greater than input sequence size
        this.anyDigitsListSize = needle.size() + 1;
        // increase array by adding to it any digits list
        ArrayList<Byte> anyDigitsList = getAnyDigitsList(anyDigitsListSize);
        this.extendedNeedle = new ArrayList<Byte>();
        this.extendedNeedle.addAll(anyDigitsList);
        this.extendedNeedle.addAll(needle);
        this.extendedNeedle.addAll(anyDigitsList);
    }

    private DecomposedNumber getBaseNumber(int baseNumberLength, int offset) {
        ArrayList<Byte> digits = new ArrayList<Byte>();
        digits.addAll(
                this.extendedNeedle.subList(
                        anyDigitsListSize + offset,
                        anyDigitsListSize + offset + baseNumberLength
                )
        );
        Collections.reverse(digits);
        return new DecomposedNumber(digits);
    }

    private int getStartSearchIndex(int baseNumberLength, int offset) {
        return this.anyDigitsListSize - (baseNumberLength - offset);
    }

    private int getEndSearchIndex(int baseNumberLength) {
        return this.anyDigitsListSize + this.baseNeedle.size() + baseNumberLength - 1;
    }

    /**
     * Try to find min length of base number which is the part of natural sequence numbers
     */
    public ArrayList<SearchResultRecord> runSearch() {
        ArrayList<SearchResultRecord> searchResult = new ArrayList<SearchResultRecord>();
        // Start processing with base number length = input sequence size
        int minNumberLength = baseNeedle.size();
        // Main loop
        do {
            int cycleOffset = 0;
            System.out.println("minNumberLength = " + minNumberLength);
            do {
                // Is this number are part of natural numbers sequence?
                DecomposedNumber base = getBaseNumber(minNumberLength, cycleOffset);
                System.out.println("base number = " + base.toString());
                System.out.println("offset = " + cycleOffset);

                if (base.isNumberCorrect()) {
                    DecomposedNumber numberToCompare = new DecomposedNumber(base);
                    numberToCompare.decrementMe();
                    int j = getStartSearchIndex(numberToCompare.size, cycleOffset);
                    // check if number doesn't begins from 0 digit
                    boolean isPartOfNaturalSequence = true;
                    while ((j < getEndSearchIndex(numberToCompare.size)) & isPartOfNaturalSequence) {
                        // generate new DecomposedNumber
                        ArrayList<Byte> currentStepList = new ArrayList<Byte>();
                        //System.out.println("j = " + j);
                        currentStepList.addAll(extendedNeedle.subList(j, j + numberToCompare.size));
                        Collections.reverse(currentStepList);
                        DecomposedNumber numberFromExtendedList = new DecomposedNumber(currentStepList);
                        // compare with base
                        isPartOfNaturalSequence = numberFromExtendedList.equals(numberToCompare);
                        // get size before increment!!!
                        j += numberToCompare.size;
                        numberToCompare.incrementMe();
                    }
                    // If this base number is part of the sequence of natural numbers save it and it's cycleOffset
                    // and for this numberLength and cycleOffset runSearch is end's
                    if (isPartOfNaturalSequence) {
                        searchResult.add(new SearchResultRecord(base, cycleOffset));
                        break;
                    }
                }
                // check next offset
                cycleOffset++;
            } while (cycleOffset < minNumberLength);
            // decrease number length
            minNumberLength--;
        } while (minNumberLength > 0);

        return searchResult;
    }

}
