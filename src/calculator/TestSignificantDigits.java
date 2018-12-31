package calculator;

public class TestSignificantDigits {
    public static void main(String[] args) throws Exception {
        String value = "3.0800";
        runTest(value, 5);

        value = "0.00418";
        runTest(value, 3);

        value = "7.09";
        runTest(value, 3);

        value = "91600";
        runTest(value, 3);

        value = "0.003005";
        runTest(value, 4);

        value = "3.2000";
        runTest(value, 5);

        value = "300.300";
        runTest(value, 6);

        value = "300.00300";
        runTest(value, 8);
    }

    private static void runTest(String value, int expected) throws Exception {
        int actual = SignificantFigures.getNumSignificantFigures(value);
        if (actual != expected) {
            throw new Exception("Error: Expected - " + expected + ", Actual - " + actual);
        } else {
            System.out.println("Passed! " + value + " has " + actual + " significant figures.");
        }
    }
}
