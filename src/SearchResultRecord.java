/**
 * Add comment here
 * <p/>
 * HH_sequence
 * Pakage PACKAGE_NAME
 * By @author Mmuzaf
 * Created at 14.09.14
 */
public class SearchResultRecord {

    public DecomposedNumber number;
    public int cycleOffset;

    public SearchResultRecord(DecomposedNumber number, int cycleOffset) {
        this.number = number;
        this.cycleOffset = cycleOffset;
    }

    @Override
    public String toString() {
        return "[DecomposedNumber] = " + number.toString() + ", [cycleOffset] = " + cycleOffset;
    }
}
