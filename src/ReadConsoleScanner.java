/**
 * Add comment here
 * <p/>
 * HH_sequence
 * Pakage PACKAGE_NAME
 * By @author Mmuzaf
 * Created at 07.09.14
 */
import java.util.ArrayList;
import java.util.Scanner;

public class ReadConsoleScanner {
    public static void main(String[] args) {

        String rawInput;
        ArrayList<Byte> baseNeedle = new ArrayList<Byte>();

        // Get input sequence
        System.out.println("Enter input sequence here : ");
        Scanner scanIn = new Scanner(System.in);
        rawInput = scanIn.nextLine();
        scanIn.close();
        // Parse input String sequence of numbers to a number of it's digits
        for (int i = 0; i < rawInput.length(); i++) {
            baseNeedle.add((byte) (rawInput.charAt(i) - '0'));
        }

        SequenceSearcher ss = new SequenceSearcher(baseNeedle);
        ArrayList<SearchResultRecord> resultOfSearch = ss.runSearch();

        for(SearchResultRecord s : resultOfSearch) {
            System.out.println("Debug: " + s.toString());
        }

        System.out.println("Your input sequence: " + rawInput);
        int position = Utils.positionInSequence(resultOfSearch.get(resultOfSearch.size() - 1));
        System.out.println("Position your sequence in [1234567891011..] sequence: " + position);

    }
}