import java.util.Arrays;

/**
 * Created by Maksim.Muzafarov on 08.09.14.
 */
public class Utils {

    public static byte[] concatenateByteArrays(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    // Search position of current record in 1234567891011121314... sequence
    public static int positionInSequence(SearchResultRecord rec) {
        int result = 1;
        for(int i = 1; i < rec.number.toIntValue(); i++) {
            result += Integer.toString(i).length();
        }
        return result - rec.cycleOffset;
    }
}
