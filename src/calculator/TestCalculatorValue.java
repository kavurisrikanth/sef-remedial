package calculator;

import UNumber.UNumber;

import java.math.BigDecimal;

/**
 * <p> Title: TestCalculatorValue </p>
 * 
 * <p> Description: A component of the Calculator application </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2017 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @author Saireddy Goutham 
 * 
 * @version 4.00	2017-10-18	Initial baseline 
 * 
 */

public class TestCalculatorValue {

	/**********
	 * This class roots the execution of the test of the CalculatorValue class.  The application 
	 * tests the class by invoking the class methods and checking the result to see if the results 
	 * are proper.
	 * 
	 */
	
	/*********************************************************************************************/
	
	/**********
	 * The check method compares an Expected String to an Actual String and returns true if the 
	 * Strings match and false otherwise.  In addition, the Strings are displayed to the console
	 * and a message is display stating whether or not there is a difference.  If there is a
	 * difference, the character at the point of the difference in the actual String is replaced
	 * with a "?" and both are displayed making it clear what character is the start of the
	 * difference
	 * 
	 * @param expected	The String object of the expected value
	 * @param actual		The String object of the actual value
	 */
	private static boolean check(String expected, String actual) {
		// Display the input parameters
		System.out.println("***Expected String");
		System.out.println(expected);
		System.out.println("***Actual String");
		System.out.println(actual);
		
		// Check to see if there is a difference
		int lesserLength = expected.length();
		if (lesserLength > actual.length()) lesserLength = actual.length();
		int ndx = 0;
		while (ndx < lesserLength && expected.charAt(ndx) == actual.charAt(ndx))
			ndx++;
		
		// Explain why the loop terminated and if there is a difference make it clear to the user
		if (ndx < lesserLength || lesserLength < expected.length() || lesserLength < actual.length()) {
			System.out.println("*** There is a difference!\n" + expected.substring(0, ndx) + "? <-----");
			return false;
		}
		System.out.println("*** There is no difference!\n");
		return true;
	}
	
	/*********************************************************************************************/
	
	/**********
	 * This main method roots the execution of this test.  The method ignores the program
	 * parameters.  After initializing several local variables, it performs a sequence of
	 * tests, displaying information accordingly and tallying the number of successes and
	 * failures.
	 * 
	 * @param args	Ignored by this application.
	 */
	public static void main(String[] args) throws Exception {
//		runBasicTests();
		runHohmannTests();
	}

	private static void runBasicTests() {
		// Display the header message to the console and initialize local variables
		System.out.println("Test CalculatorValue Class\n");
		int numPassed = 0;
		int numFailed = 0;

		// 1. Perform a default constructor test
		CalculatorValue test = new CalculatorValue();						// Perform the test

		System.out.println("1. No input given");								// No input that was given

		// Check the actual output against the expected.  If they match, the test has been passed and display the proper
		// message and tally the result
		if (check("measuredValue = 0\nerrorMessage = \n", test.debugToString())) {
			numPassed++;
			System.out.println("\tPass");
		}
		// If they do not match, display that there was a failure and tally that result
		else {
			numFailed++;
			System.out.println("\tFail");
		}
		System.out.println();


		// 2. Perform a constructor test with a long
		test = new CalculatorValue(1234567890123456L);						// Perform the test

		System.out.println("2. Input: 1234567890123456L");

		// Check the actual output against the expected.  If they match, the test has been passed and display the proper
		// message and tally the result
		if (check("measuredValue = 1234567890123456\nerrorMessage = \n", test.debugToString())) {
			numPassed++;
			System.out.println("\tPass");
		}
		// If they do not match, display that there was a failure and tally that result
		else {
			numFailed++;
			System.out.println("\tFail");
		}
		System.out.println();


		// 3. Perform a copy constructor test
		CalculatorValue t = new CalculatorValue(1234567890123456L);			// Set up the test
		t.setErrorMessage("The error message string");

		test = new CalculatorValue(t);										// Perform the test

		System.out.println("3. Input:\n1234567890123456L\nThe error message string\n");

		// Check the actual output against the expected.  If they match, the test has been passed and display the proper
		// message and tally the result
		if (check("measuredValue = 1234567890123456\nerrorMessage = The error message string\n", test.debugToString())) {
			numPassed++;
			System.out.println("\tPass");
		}
		// If they do not match, display that there was a failure and tally that result
		else {
			numFailed++;
			System.out.println("\tFail");
		}
		System.out.println();


		// 4. Perform a constructor test with a string
		test = new CalculatorValue("1234567890123456","0.00");						// Perform the test

		System.out.println("4. Input: \"1234567890123456\"");

		// Check the actual output against the expected.  If they match, the test has been passed and display the proper
		// message and tally the result
		if (check("measuredValue = 1234567890123456\nerrorMessage = \n", test.debugToString())) {
			numPassed++;
			System.out.println("\tPass");
		}
		// If they do not match, display that there was a failure and tally that result
		else {
			numFailed++;
			System.out.println("\tFail");
		}
		System.out.println();


		// 5. Perform addition
		CalculatorValue left = new CalculatorValue("1","0.00");						// Set up the test
		CalculatorValue right = new CalculatorValue("2","0.00");

		left.add(right);														// Perform the test

		System.out.println("5. Addition Input: \n1\n2");

		// Check the actual output against the expected.  If they match, the test has been passed and display the proper
		// message and tally the result
		if (check("measuredValue = 3\nerrorMessage = \n", left.debugToString())) {
			numPassed++;
			System.out.println("\tPass");
		}
		// If they do not match, display that there was a failure and tally that result
		else {
			numFailed++;
			System.out.println("\tFail");
		}
		System.out.println();


		// 6. Perform subtraction
		left = new CalculatorValue("1","0.00");										// Set up the test
		right = new CalculatorValue("2","0.00");

		left.sub(right);														// Perform the test

		System.out.println("5. Subtraction Input: \n1\n2");

		// Check the actual output against the expected.  If they match, the test has been passed and display the proper
		// message and tally the result
		if (check("measuredValue = -1\nerrorMessage = \n", left.debugToString())) {
			numPassed++;
			System.out.println("\tPass");
		}
		// If they do not match, display that there was a failure and tally that result
		else {
			numFailed++;
			System.out.println("\tFail");
		}
		System.out.println();


		// 7. Perform multiplication
		left = new CalculatorValue("1","0.00");										// Set up the test
		right = new CalculatorValue("2","0.00");

		left.mpy(right);														// Perform the test

		System.out.println("7. Multiplication Input: \n1\n2");

		// Check the actual output against the expected.  If they match, the test has been passed and display the proper
		// message and tally the result
		if (check("measuredValue = 2\nerrorMessage = \n", left.debugToString())) {
			numPassed++;
			System.out.println("\tPass");
		}
		// If they do not match, display that there was a failure and tally that result
		else {
			numFailed++;
			System.out.println("\tFail");
		}
		System.out.println();


		// 8. Perform division
		left = new CalculatorValue("1","0.00");										// Set up the test
		right = new CalculatorValue("2","0.00");

		left.div(right);														// Perform the test

		System.out.println("8. Division Input: \n1\n2");

		// Check the actual output against the expected.  If they match, the test has been passed and display the proper
		// message and tally the result
		if (check("measuredValue = 0\nerrorMessage = \n", left.debugToString())) {
			numPassed++;
			System.out.println("\tPass");
		}
		// If they do not match, display that there was a failure and tally that result
		else {
			numFailed++;
			System.out.println("\tFail");
		}
		System.out.println();


		System.out.println("Number of tests passed: " + numPassed);
		System.out.println("Number of tests failed: " + numFailed);
	}

	private static void runHohmannTests() throws Exception {
		// Much like the method above.
		System.out.println("Test CalculatorValue Class for Hohmann transfer\n");
		int numPassed = 0;
		int numFailed = 0;
		int testNum = 1;

		System.out.println("*** Step one. ***");
		CalculatorValue earthDis = new CalculatorValue("149600000", "5e+4", "--:null:0|--:km:1|--:null:0"),
						marsDis  = new CalculatorValue("227920000", "5e+3", "--:null:0|--:km:1|--:null:0"),
						two      = new CalculatorValue("2", "0");
		CalculatorValue smAxis = new CalculatorValue(earthDis);
		smAxis.add(marsDis);
		smAxis.div(two);

		// Now check the result.
		String sumStr = smAxis.toString();
		System.err.println("Test " + testNum + ": Checking for null string.");
		if (sumStr == null || sumStr.length() == 0) {
			throw new Exception("Wrong value of sum!");
		} else
			System.err.println("Test " + testNum + " Passed!");
		testNum++;

		String[] pieces = sumStr.split(" ");
		System.err.println("Test " + testNum + ": Checking for correct format of string.");
		if (pieces.length != 3)
			throw new Exception("Wrong number of values in sum string. There must be only 3!");
		else
			System.err.println("Test " + testNum + " Passed!");
		testNum++;

		/*
			NOTE: There is something wrong with the UNumber compareTo() method. So use BigDecimal here.
		 */
		// Check the measured value.
		BigDecimal actualMV = new BigDecimal(Double.parseDouble(pieces[0])),
					expectedMV = new BigDecimal(Double.parseDouble("188760000"));
		System.err.println("Test " + testNum + ": Checking for correct measured value.");
		if (actualMV.compareTo(expectedMV) != 0) {
			throw new Exception("Wrong value! Expected: " + expectedMV + ", actual: " + actualMV);
		} else
			System.err.println("Test " + testNum + " Passed!");
		testNum++;

		// Check the error term.
		BigDecimal actualET = new BigDecimal(Double.parseDouble(pieces[1])),
					expectedET = new BigDecimal(Double.parseDouble("2.75e4"));
		System.err.println("Test " + testNum + ": Checking for correct error term.");
		if (actualET.compareTo(expectedET) != 0)
			throw new Exception("Wrong value! Expected: " +expectedET + ", actual: " + actualET);
		else
			System.err.println("Test " + testNum + " Passed!");
		testNum++;

		// Check the unit.
		System.err.println("Test " + testNum + ": Checking unit.");
		if (!pieces[2].equals("(km)^1.0"))
			throw new Exception("Wrong unit value! Expected: (km)^1.0, Actual: " + pieces[2]);
		else
			System.err.println("Test " + testNum + " Passed!");
		testNum++;

		System.out.println();
		System.out.println("*** Step two. ***");
		// Get the time periods of Earth and Mars in days
		CalculatorValue earthPeriod  = new CalculatorValue("365.25636", "0.000005", "--:null:0|--:null:0|--:day:1"),
						marsPeriod   = new CalculatorValue("686.6812", "0.00005", "--:null:0|--:null:0|--:day:1");

		// Convert them to seconds
		earthPeriod.changeUnit("--", "", "--", "", "--", "s");
		marsPeriod.changeUnit("--", "", "--", "", "--", "s");

		// Get the strings.
		String earthStr = earthPeriod.toString(),
				marsStr = marsPeriod.toString();

		// Check if the strings are null or empty.
		System.err.println("Test " + testNum + ": Checking that strings are not null or empty.");
		if (earthStr == null || earthStr.isEmpty())
			throw new Exception("Earth string is " + (earthStr == null ? "null!" : "empty!"));
		else if (marsStr == null || marsStr.isEmpty())
			throw new Exception("Mars string is " + (marsStr == null ? "null!" : "empty!"));
		else
			System.err.println("Test " + testNum + " passed!");
		testNum++;

		// Check if Earth string has the proper measured value.
		System.err.println("Test " + testNum + ": Check if Earth string has the proper measured value.");
		String[] earthPieces = earthStr.split(" ");
		actualMV = new BigDecimal(Double.parseDouble(earthPieces[0]));
		expectedMV = new BigDecimal(Double.parseDouble("31558149.504"));
		if (actualMV.compareTo(expectedMV) != 0)
			throw new Exception("Wrong measured value! Expected: " + expectedMV + ", Actual: " + actualMV);
		else
			System.err.println("Test " + testNum + " passed!");
		testNum++;

		// Check if Earth string has the proper error term.
		System.err.println("Test " + testNum + ": Check if Earth string has the proper error term.");
		actualET = new BigDecimal(Double.parseDouble(earthPieces[1]));
		expectedET = new BigDecimal(Double.parseDouble("0.432"));
		if (actualET.compareTo(expectedET) != 0)
			throw new Exception("Wrong error term! Expected: " + expectedET + ", Actual: " + actualET);
		else
			System.err.println("Test " + testNum + " passed!");
		testNum++;

		// Check if Earth string has the proper units.
		System.err.println("Test " + testNum + ": Check if Earth string has the proper units.");
		if (!earthPieces[2].equals("(s)^1.0"))
			throw new Exception("Wrong unit value! Expected: (s)^1.0, Actual: " + earthPieces[2]);
		else
			System.err.println("Test " + testNum + " passed!");
		testNum++;

		// Check if Mars string has the proper measured value.
		System.err.println("Test " + testNum + ": Check if Mars string has the proper measured value.");
		String[] marsPieces = marsStr.split(" ");
		actualMV = new BigDecimal(Double.parseDouble(marsPieces[0]));
		expectedMV = new BigDecimal(Double.parseDouble("59329255.68"));
		if (actualMV.compareTo(expectedMV) != 0)
			throw new Exception("Wrong measured value! Expected: " + expectedMV + ", Actual: " + actualMV);
		else
			System.err.println("Test " + testNum + " passed!");
		testNum++;

		// Check if Mars string has the proper error term.
		System.err.println("Test " + testNum + ": Check if Mars string has the proper error term.");
		actualET = new BigDecimal(Double.parseDouble(marsPieces[1]));
		expectedET = new BigDecimal(Double.parseDouble("4.32"));
		if (actualET.compareTo(expectedET) != 0)
			throw new Exception("Wrong error term! Expected: " + expectedET + ", Actual: " + actualET);
		else
			System.err.println("Test " + testNum + " passed!");
		testNum++;

		// Check if Mars string has the proper units.
		System.err.println("Test " + testNum + ": Check if Mars string has the proper units.");
		if (!earthPieces[2].equals("(s)^1.0"))
			throw new Exception("Wrong unit value! Expected: (s)^1.0, Actual: " + marsPieces[2]);
		else
			System.err.println("Test " + testNum + " passed!");
		testNum++;

		/*
		 * Now the third step.
		 */
		System.out.println();
		System.out.println("*** Step three. ***");
		// Store the constants.
		CalculatorValue four  = new CalculatorValue("4", "0");
		CalculatorValue	pi    = new CalculatorValue("3.141592653589793", "5e-16");
		CalculatorValue GM    = new CalculatorValue("1.327e11", "5e7", "--:null:0|--:km:3|--:s:-2");
		CalculatorValue aCube = new CalculatorValue(smAxis);

		// Debugging.
		CalculatorValue piSq  = new CalculatorValue(pi);
		System.out.println(piSq);

		// OK. First, cube the semi-major axis.
		System.out.println("acubed: " + aCube + ", sm axis: " + smAxis);
		aCube.mpy(smAxis);
		System.out.println("acubed: " + aCube + ", sm axis: " + smAxis);
		aCube.mpy(smAxis);
		System.out.println("acubed: " + aCube + ", sm axis: " + smAxis);

		// Now square the pi
		piSq.mpy(piSq);

		CalculatorValue ansSq = new CalculatorValue(four);	// Take the 4.
		System.out.println("ans sq: " + ansSq);
		ansSq.mpy(piSq);									// Multiply by pi squared.
		System.out.println("ans sq: " + ansSq);
		ansSq.mpy(aCube);									// Multiply by a cubed.
		System.out.println("ans sq: " + ansSq);
		ansSq.div(GM);										// Divide by GM.
		System.out.println("ans sq: " + ansSq);

		// Take the square root.
		CalculatorValue transferOrbitPeriod = new CalculatorValue(ansSq);
		System.out.println("** Taking the square root!");
		transferOrbitPeriod.sqrt();

		// Check the value of pi squared.
		System.err.println("Test " + testNum + ": Check the value of pi squared.");
		System.out.println(piSq);
		System.out.println(transferOrbitPeriod);

		System.out.println();
		System.out.println(two);
		CalculatorValue earthVel = new CalculatorValue(two);
		earthVel.mpy(pi);
		earthVel.mpy(earthDis);
		earthVel.div(earthPeriod);
		System.out.println("Velocity of Earth's orbit: " + earthVel);

		CalculatorValue marsVel = new CalculatorValue(two);
		marsVel.mpy(pi);
		marsVel.mpy(marsDis);
		marsVel.div(marsPeriod);
		System.out.println("Velocity of Mars' orbit: " + marsVel);

		// (2 * pi * semimajoraxis / transferperiod) * sqrt((2 * semimajoraxis / earthdistance) - 1)
		System.out.println("Two: " + two);
		System.out.println("pi: " + pi);
		System.out.println("semi major axis: " + smAxis);
		System.out.println("transfer orbit period: " + transferOrbitPeriod);
		CalculatorValue nonSqrt = new CalculatorValue(two);
		nonSqrt.mpy(pi);
		nonSqrt.mpy(smAxis);
		nonSqrt.div(transferOrbitPeriod);
		System.out.println("non sqrt: " + nonSqrt);

		CalculatorValue sqrt = new CalculatorValue(two),
						one = new CalculatorValue("1.0", "");
		sqrt.mpy(smAxis);
		System.out.println("2 * smaxis: " + sqrt);
		sqrt.div(earthDis);
		System.out.println("2 * smaxis / earthdis: " + sqrt);
		sqrt.sub(one);
		System.out.println("(2 * smaxis/earthdis) - 1: " + sqrt);
		sqrt.sqrt();
		System.out.println("sqrt: " + sqrt);

		CalculatorValue perihilionVel = new CalculatorValue(nonSqrt);
		perihilionVel.mpy(sqrt);
		System.out.println(perihilionVel);

		// Check change in velocity.
		CalculatorValue deltaVel = new CalculatorValue(perihilionVel);
		deltaVel.sub(earthVel);
		System.out.println("Change in velocity: " + deltaVel);
	}
}
