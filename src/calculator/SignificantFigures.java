package calculator;

import UNumber.UNumber;

import java.text.DecimalFormat;

/**
 * <p> Title: SignificantFigures Class. </p>
 *
 * <p> Description: A component of a JavaFX demonstration application that performs computations </p>
 *
 * <p> Copyright: Lynn Robert Carter © 2017 </p>
 *
 * @author Kavuri Srikanth
 *
 * @version 1.00	2018-12-22 UNumber implementation of the CalculatorValue class along with units.
 */

public class SignificantFigures {
    /**
     * This method returns the number of significant digits in a number.
     * @param x - The number, in String form.
     * @return  - The number of significant digits in x.
     */
    static int getNumSignificantFigures(String x) {
        // In case the number coming in is in scientific format, remove the E bits.
        int eIndex = x.indexOf('E');
        if (eIndex != -1)
            x = x.substring(0, eIndex);

        // This method doesn't actually do the computation.
        if (x.indexOf('.') == -1) {
            // First, check for a decimal point. If there isn't one, just pass the string on
            // for computation.
            return getNumSignificantFiguresInt(x);
        } else {
            int i = x.length() - 1, pointIndex = x.indexOf('.'), ans = 0;
            while (i >= pointIndex && x.charAt(i) == '0') {
                ans++;
                i--;
            }

            // Pass the pieces on for computation.
            StringBuilder sb = new StringBuilder(x);
            sb.deleteCharAt(pointIndex);
            ans += getNumSignificantFiguresInt(sb.toString());

            // And return the sum of the results.
            return ans;
        }
    }

    /**
     * This method performs the counting of the number of significant digits in a number.
     * @param x - The number. This number is assumed to be a non-negative integer.
     * @return - The number of significant digits in x.
     */
    private static int getNumSignificantFiguresInt(String x) {
        int left = 0, right = x.length() - 1, ans = 0;

        while (left <= right && x.charAt(left) == '0')		// Ignore all leading zeros.
            left++;

        if (left > right)
            return 0;

        while (x.charAt(right) == '0') {
            // Ignore trailing zeros too. These are counted before the number even gets to this
            // method.
            right--;
        }

        ans += (right - left + 1);			// Calculate the number of significant digits.
        // The logic is that everything that's significant MUST
        // lie between two non-zero digits.

        return ans;
    }

    /**
     * This method takes in a number string and formats it so that it displays a set number of digits after
     * the decimal point.
     * @param number - The number.
     * @param numDig - The number of digits after the decimal point.
     * @return - The formatted string.
     */
    public static String formatNumberString(UNumber number, int numDig) {

        // Validation
        if (number == null || number.toString().isEmpty())
            return "";

        // Split the number by E
        String[] pieces = number.toString().split("E");

        // into decimal and exponent parts.
        String decimalPart = pieces[0],
                exponentPart = pieces[1];

        // Next, check if the numbers have signs indicating positive or negative numbers.
        char decimalSign = '\0', exponentSign = '\0';
        if (decimalPart.charAt(0) == '+' || decimalPart.charAt(0) == '-') {
            decimalSign = decimalPart.charAt(0);
            decimalPart = decimalPart.substring(1);
        }

        if (exponentPart.charAt(0) == '+' || exponentPart.charAt(0) == '-') {
            exponentSign = exponentPart.charAt(0);
            exponentPart = exponentPart.substring(1);
        }

        // Remove trailing zeros from the numbers. Obsolete code.
//            decimalPart = dropZeros(decimalPart);
//            exponentPart = dropZeros(exponentPart);

        // Convert number strings to numbers and get the final result.
        double decimalNumber = Double.parseDouble(decimalPart),
                exponentNumber = Double.parseDouble(exponentPart);

        if (decimalSign == '-')
            decimalNumber *= -1;

        if (exponentSign == '-')
            exponentNumber *= -1;

        double finalNum = decimalNumber * Math.pow(10, exponentNumber);

        // Build a format string
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        if (numDig > 0) {
            sb.append(".");
            for (int i = 0; i < numDig; i++) {
                sb.append("#");
            }
        }

        // And return the number in the correct format.
        DecimalFormat df = new DecimalFormat(sb.toString());
        return df.format(finalNum);
    }

    /**
     * Drops zeros from a number represented as a string. This method is obsolete.
     * @param number
     * @return
     */
//    private static String dropZeros(String number) {
//        if (number.length() <= 1)
//            return number;
//
//        if (number.indexOf('.') == -1) {
//            // If there is no decimal point...
//            int start = 0, end = number.length() - 1;
//            while (start <= end && number.charAt(start) == '0') start++;
//            while (start <= end && number.charAt(end) == '0') end--;
//
//            return number.substring(start, end + 1);
//        } else {
//            // If there is a decimal point...
//            StringBuilder s = new StringBuilder();
//            String[] pieces = number.split("\\.");
//
//            if (pieces.length > 2) {
//                return "";
//            }
//
//            if (pieces.length == 0)
//                return "";
//
//            s.append(dropZeros(pieces[0]));
//
//            if (pieces.length == 2) {
//                s.append(".");
//                s.append(dropZeros(pieces[1]));
//            }
//
//            return s.toString();
//        }
//    }
}
