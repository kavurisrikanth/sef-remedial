package calculator;

import java.math.BigDecimal;
import java.util.Scanner;
import UNumber.UNumber;

import static calculator.SignificantFigures.formatNumberString;
import static calculator.SignificantFigures.getNumSignificantFigures;


/**
 * <p> Title: CalculatorValue Class. </p>
 * 
 * <p> Description: A component of a JavaFX demonstration application that performs computations </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2017 </p>
 * 
 * @author Lynn Robert Carter - Baseline
 * 
 * @author Kavuri Srikanth
 * 
 * @version 4.00	2017-10-18 Long integer implementation of the CalculatorValue class 
 * @version 5.00	2018-12-22 UNumber implementation of the CalculatorValue class along with units.
 */

public class CalculatorValue {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These variables define a CalculatorValue object.
	UNumber measuredValue, errorTerm;	// The basics
	UNumber estimatedErrorTerm;			// An estimation of the error term, if one isn't provided.
	UNumber number;						// Placeholder variables
	String string;
	String errorMessageMV = "";			// Error messages.
	String errorMessageET = "";
	String errorMessageUnit = "";

	Unit myUnit;						// The ever so important unit.
	int pdDigitsMV = 0, pdDigitsET = 0;
	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	/*****
	 * This is the default constructor
	 */
	public CalculatorValue() {
	}

	/*****
	 * This constructor creates a calculator value based on a long integer. For future calculators, it
	 * is best to avoid using this constructor.
	 */
	public CalculatorValue(double v) {
		measuredValue = new UNumber(v);
	}

	/*****
	 * This copy constructor creates a duplicate of an already existing calculator value
	 */
	public CalculatorValue(CalculatorValue v) {
		measuredValue = new UNumber(v.measuredValue);
		errorTerm = new UNumber(v.errorTerm);
		
		errorMessageMV = v.errorMessageMV;
		errorMessageET = v.errorMessageET;

		this.myUnit = new Unit(v.myUnit);
		this.estimatedErrorTerm = new UNumber(v.estimatedErrorTerm);

		this.pdDigitsMV = v.pdDigitsMV;
		this.pdDigitsET = v.pdDigitsET;
	}

	/*
		The following code is no longer used.
	 */
//	public CalculatorValue(String s,String t) {
//		measuredValue = 0;
//		errorTerm=0;
//		if (s.length() == 0) {								// If there is nothing there,
//			errorMessageMV = "***Error*** Input is empty";		// signal an error
//			return;
//		}
//		/* If the first character is a plus sign, ignore it.*/
//		int start = 0;										// Start at character position zero
//		boolean negative = false;							// Assume the value is not negative
//		if (s.charAt(start) == '+')							// See if the first character is '+'
//			 start++;										// If so, skip it and ignore it
//
//		// If the first character is a minus sign, skip over it, but remember it
//		else if (s.charAt(start) == '-'){					// See if the first character is '-'
//			start++;											// if so, skip it
//			negative = true;									// but do not ignore it
//		}
//
//		// See if the user-entered string can be converted into an integer value
//		Scanner tempScanner = new Scanner(s.substring(start));// Create scanner for the digits
//		errorMessageMV=MeasuredValueRecognizer.checkMeasureValue(s.substring(start));
//		if(errorMessageMV.equals("")) {
//			measuredValue = tempScanner.nextDouble();				// Convert the value and check to see
//		}else {
//			tempScanner.close();
//			return;
//		}
//
//		if (tempScanner.hasNext()) {							// that there is nothing else is
//			errorMessageMV = "***Error*** Excess data"; 		// following the value.  If so, it
//			tempScanner.close();								// is an error.  Therefore we must
//			measuredValue = 0;								// return a zero value.
//			return;
//		}
//		tempScanner.close();
//
//		errorMessageMV = "";
//		if (negative)										// Return the proper value based
//			measuredValue = -measuredValue;					// on the state of the flag that
//
//		if(t.length()==0) {
//			// The logic below is to automatically add an error of plus-minus 5
//			// after the significant figures of the measured value.
//
//			errorTerm=Double.parseDouble(getEstimatedErrorTerm(s));
//			System.out.println("Calculated error term: " + errorTerm);
//			errorMessageET="";
//		} else {
//			// See if the user-entered string can be converted into an integer value
//			Scanner tempScanner1 = new Scanner(t);// Create scanner for the digits
//			errorMessageET=MeasuredValueRecognizer.checkMeasureValue(t);
//			if(errorMessageET.equals("")) {
//				errorTerm = tempScanner1.nextDouble();				// Convert the value and check to see
//			}else {
//				tempScanner1.close();
//				return;
//			}
//			if (tempScanner1.hasNext()) {							// that there is nothing else is
//				errorMessageET = "***Error*** Excess data"; 		// following the value.  If so, it
//				tempScanner1.close();								// is an error.  Therefore we must
//				errorTerm = 0;								// return a zero value.
//				return;
//			}
//			tempScanner1.close();
//			errorMessageET = "";
//		}
//	}

	/**
	 * This public constructor initializes a CalculatorValue object from three strings.
	 * @param v - The measured value.
	 * @param e - The error term.
	 * @param u - The unit.
	 */
	public CalculatorValue(String v, String e, String u) {
		// Reset the error message.
		errorMessageUnit = "";

		getNumberFromString(v);							// Parse the measured value string.
		pdDigitsMV = getNumPostDecimalDigits(v);		// Get the number of digits after the decimal point.
		System.out.println("Error term pdd: " + pdDigitsMV + " for et: " + v);
		measuredValue = new UNumber(number);			// Store the number and the error message.
		errorMessageMV  = string;

		getNumberFromString(e);							// The same logic as the measured value above.
		pdDigitsET = getNumPostDecimalDigits(e);
		System.out.println("Error term pdd: " + pdDigitsET + " for et: " + e);
		errorTerm = new UNumber(number);
		errorMessageET = string;

		// Predict the error term here.
		String mvStr = formatNumberString(measuredValue, getNumSignificantFigures(errorTerm.toString()));
		BigDecimal temp = new BigDecimal(mvStr);
		getNumberFromString(getEstimatedErrorTerm(temp.toString()));
		estimatedErrorTerm = new UNumber(number);

		// Validation
		if (u == null) {
			myUnit = null;
			return;
		}

		// Now set up units.
		myUnit = new Unit(u);
		errorMessageUnit = myUnit.getErrorMsg();
	}

	/* The following constructor is obsolete, but is kept around for the Test classes. */
	public CalculatorValue(String v, String e) {
		this(v, e, "");
	}

	/**
	 * This private method calculates the number of digits after the decimal point
	 * that the user intended.
	 * @param s - The number string.
	 * @return - The number of digits after the decimal point.
	 */
	private int getNumPostDecimalDigits(String s) {
		// This method looks at the string representation of a number
		// and, without converting it to a double, counts the number of digits
		// after the decimal point.
		// This should give the best indication of what the user INTENDED to be
		// the number of significant digits in the number they entered.

		if (s.isEmpty())
			return 0;

		// Check if the user entered the number in scientific format.
		char e = 'E';
		int dIndex = s.indexOf('.'), eIndex = s.indexOf(e);
		if (eIndex == -1) {
			e = 'e';
			eIndex = s.indexOf(e);
		}

		// Going case by case...
		// If there is no decimal point and no e, then the user entered an integer.
		if (dIndex == -1 && eIndex == -1)
			return 0;

		// If there is a decimal point but no e, then the work is straightforward.
		// Just split the number by the . and count.
		if (dIndex != -1 && eIndex == -1) {
			s = s.substring(dIndex + 1);
			return s.length();
		}

		// If there is no decimal point but there is an e, then we come across two
		// other cases depending on the number after the e. If this number is positive, then
		// we have nothing to do. But if this number is negative, then we need to imagine the
		// decimal point having moved that many spaces and then count.
		if (dIndex == -1 && eIndex != -1) {
			String postStr = s.substring(eIndex + 1);
			try {
				int power = Integer.parseInt(postStr);
				if (power >= 0)
					return 0;
				else
					return Math.abs(power);
			} catch (Exception exc) {
				return 0;
			}

		}

		// Finally, we come to the most complicated case. We have both a decimal point and
		// an e.
		// In this case, we get the number of digits after the decimal point and then compare
		// that number to the number after the e.
		String[] pieces = s.split(e == 'e' ? "e" : "E");
		int pow = pieces.length > 1 ? Integer.parseInt(pieces[1]) : 0,
			numDig = pieces.length > 0 ? getNumPostDecimalDigits(pieces[0]) : 0;
		if (pow < 0)
			return numDig + Math.abs(pow);
		else {
			int diff = numDig - pow;
			if (diff > 0)
				return diff;
			else
				return 0;
		}
	}

	/*
		The following method was replaced by getNumberFromString() so as to allow for more modularity.
	 */
//	private void getMeasuredValueFromString(String s) {
//		System.out.println("Reached getMVFromString. s: " + s);
//		measuredValue = new UNumber(0.0);
//		if (s.length() == 0) {								// If there is nothing there,
//			errorMessageMV = "***Error*** Input is empty";		// signal an error
//			return;
//		}
//		// If the first character is a plus sign, ignore it.
//		int start = 0;										// Start at character position zero
//		boolean negative = false;							// Assume the value is not negative
//		if (s.charAt(start) == '+')							// See if the first character is '+'
//			start++;										// If so, skip it and ignore it
//
//			// If the first character is a minus sign, skip over it, but remember it
//		else if (s.charAt(start) == '-'){					// See if the first character is '-'
//			start++;											// if so, skip it
//			negative = true;									// but do not ignore it
//		}
//
//		// See if the user-entered string can be converted into an integer value
//		Scanner tempScanner = new Scanner(s.substring(start));// Create scanner for the digits
//		if (!tempScanner.hasNextDouble()) {					// See if the next token is a valid
//			errorMessageMV = "***Error*** Invalid value"; 		// integer value.  If not, signal there
//			tempScanner.close();								// return a zero
//			return;
//		}
//
//		// Convert the user-entered string to a integer value and see if something else is following it
//		measuredValue = new UNumber(tempScanner.nextDouble());				// Convert the value and check to see
//		if (tempScanner.hasNext()) {							// that there is nothing else is
//			errorMessageMV = "***Error*** Excess data"; 		// following the value.  If so, it
//			tempScanner.close();								// is an error.  Therefore we must
//			measuredValue = new UNumber(0);								// return a zero value.
//			return;
//		}
//		tempScanner.close();
//		errorMessageMV = "";
//		if (negative)										// Return the proper value based
//			measuredValue.setSign(false);					// on the state of the flag that
//	}

//	private void getErrorTermFromString(String s) {
//
//	}

	/**
	 * The following private method extracts a UNumber from a String. It's the same logic as the commented out method above.
	 * @param s - The string representation of (hopefully) a number.
	 */
	private void getNumberFromString(String s) {
		double zero = 0;
		number = new UNumber(zero, 14);
		if (s.length() == 0) {								// If there is nothing there,
			string = "";							// just return.
			return;
		}
		// If the first character is a plus sign, ignore it.
		int start = 0;										// Start at character position zero
		boolean negative = false;							// Assume the value is not negative
		if (s.charAt(start) == '+')							// See if the first character is '+'
			start++;										// If so, skip it and ignore it

			// If the first character is a minus sign, skip over it, but remember it
		else if (s.charAt(start) == '-'){					// See if the first character is '-'
			start++;											// if so, skip it
			negative = true;									// but do not ignore it
		}

		// See if the user-entered string can be converted into an integer value
		Scanner tempScanner = new Scanner(s.substring(start));// Create scanner for the digits

		// The condition in the if should be fine, since || and && in Java short-circuit. Google it.
		if (!tempScanner.hasNextDouble()) {				// See if the next token is a valid
				string = "***Error*** Invalid value"; 		// double value.  If not, signal there
				tempScanner.close();								// return a zero
				return;
			}

		// Convert the user-entered string to a integer value and see if something else is following it
		number = new UNumber(tempScanner.nextDouble());				// Convert the value and check to see
		if (tempScanner.hasNext()) {							// that there is nothing else is
			string = "***Error*** Excess data"; 		// following the value.  If so, it
			tempScanner.close();								// is an error.  Therefore we must
			number = new UNumber(0);								// return a zero value.
			return;
		}
		tempScanner.close();
		string = "";
		if (negative)										// Return the proper value based
			number.setSign(false);					// on the state of the flag that
	}

	/**
	 * This private method estimates an error term. It is called when an error term is not entered by the user
	 * and we might have to estimate one. We calculate the estimation ahead of time and use it if needed.
	 * @param s - The measured value as a string.
	 * @return - The estimated error term.
	 */
	private String getEstimatedErrorTerm(String s) {
		// Calculate the number of significant figures in the measured value.
		int numSigMV = SignificantFigures.getNumSignificantFigures(s);

		StringBuilder sb = new StringBuilder();
		boolean pointFound = false;

		int i = 0, j = 0;
		while (j < numSigMV) {							// For each char in the measured value
			if (s.charAt(i) == '.') {					// up to the number of significant digits,
				sb.append(".");							// Copy the decimal point if one exists
				pointFound = true;						// and mark having done it.
			}
			else {                                        // Put in zeros for all significant figures
				sb.append("0");
				j++;
			}

			i++;
		}

		if ((sb.toString().length() == s.length()) && !pointFound) {
			// If all the significant digits have been covered, and there was no decimal
			// point, then we are dealing with an integer, and we must add a decimal
			// point before the 5.
			sb.append(".");
		}

		// And now append a 5.
		sb.append("5");

		if ((sb.toString().length() < s.length())) {
			// If all the significant figures are covered, but the measured value is not in its entirety, then
			// pad the number with trailing zeros.
			// Example: 2000 has only one significant figure. If this block of code didn't exist, then we would
			// estimate 05 as the error term for 2000, when the error term should be 0500.
			int toGo = s.length() - sb.toString().length();
			for (i = 0; i < toGo; i++) {
				sb.append("0");
			}
		}

		// And we're done.
		return sb.toString();
	}

	/**
	 * This method facilitates the migration of a number from one unit to another. It is called when
	 * the user requests a units change.
	 * @param massScale - The new mass scale
	 * @param massUnit - The new mass unit name
	 * @param lenScale - The new length scale
	 * @param lenUnit - The new length unit name
	 * @param timeScale - The new time scale
	 * @param timeUnit - The new time unit name
	 */
	public void changeUnit(String massScale, String massUnit, String lenScale, String lenUnit, String timeScale, String timeUnit) {
		// Get the number by which the measured value and error term must be multiplied to complete
		// the units change.
		double alter_factor = this.myUnit.alter(massScale, massUnit, lenScale, lenUnit, timeScale, timeUnit);
		if (Double.isInfinite(alter_factor)) {
			// Something went wrong. So set the error message.
			errorMessageUnit = "Invalid units!";
			return;
		}

		// Now change the measured value and error term.
		UNumber temp = new UNumber(alter_factor);
		measuredValue.mpy(temp);
		errorTerm.mpy(temp);
	}

	/**
	 * This private method converts existing units to SI units. It is used during arithmetic operations.
	 * @param otherUnit - A unit.
	 * @param otherMV - A measured value.
	 * @param otherET - An error term.
	 */
	private void convertUnits(Unit otherUnit, UNumber otherMV, UNumber otherET) {
		// Validation
		if (this.myUnit == null || otherUnit == null)
			return;

		// Get the conversion factors.
		UNumber conv_mass_this = this.myUnit.getConv_mass(),
				conv_length_this = this.myUnit.getConv_length(),
				conv_time_this = this.myUnit.getConv_time(),
				conv_mass_v = otherUnit.getConv_mass(),
				conv_length_v = otherUnit.getConv_length(),
				conv_time_v = otherUnit.getConv_time();

		// If both units are equal, then we can perform arithmetic operations straightway.
		if (!this.myUnit.equals(otherUnit)) {

			// Get the normalization factors for both this object and v's units.
			// Then multiply the measured values and error terms of the objects with their
			// respective normalization factors.
			if (!conv_mass_this.isZero()) {
				if (!measuredValue.isZero())
					measuredValue.mpy(conv_mass_this);
				if (!errorTerm.isZero())
					errorTerm.mpy(conv_mass_this);
			}

			if (!conv_length_this.isZero()) {
				if (!measuredValue.isZero())
					measuredValue.mpy(conv_length_this);
				if (!errorTerm.isZero())
					errorTerm.mpy(conv_length_this);
			}

			if (!conv_time_this.isZero()) {
				if (!measuredValue.isZero())
					measuredValue.mpy(conv_time_this);
				if (!errorTerm.isZero())
					errorTerm.mpy(conv_time_this);
			}

			if (!conv_mass_v.isZero()) {
				if (!otherMV.isZero())
					otherMV.mpy(conv_mass_v);
				if (!otherET.isZero())
					otherET.mpy(conv_mass_v);
			}

			if (!conv_length_v.isZero()) {
				if (!otherMV.isZero())
					otherMV.mpy(conv_length_v);
				if (!otherET.isZero())
					otherET.mpy(conv_length_v);
			}

			if (!conv_time_v.isZero()) {
				if (!otherMV.isZero())
					otherMV.mpy(conv_time_v);
				if (!otherET.isZero())
					otherET.mpy(conv_time_v);
			}

		}
	}

	/**********************************************************************************************

	Getters and Setters
	
	**********************************************************************************************/

	/**
	 * Get the measured value error message.
	 * @return - the measured value error message
	 */
	public String getErrorMessage(){
		return errorMessageMV;
	}

	/**
	 * Get the unit error message.
	 * @return - The unit error message
	 */
	public String getUnitErrorMessage() {
		return errorMessageUnit;
	}

	/*****
	 * Get the measured value
	 */
	public UNumber getMeasuredValue(){
		return this.measuredValue;
	}

	/*****
	 * Get the error term
	 */
	public UNumber geterrorTerm(){
		return this.errorTerm;
	}
	
	/*****
	 * Get the error message for the error term
	 */
	public String geterrErrorMessage(){
		return errorMessageET;
	}
	
	/*****
	 * Set the current value of a calculator value to a specific long integer
	 */
	public void setValue(double v){
		measuredValue = new UNumber(v);
	}

	/*****
	 * Set the current error value of a calculator value to a specific long integer
	 */
	public void setErrorValue(double v){
		errorTerm = new UNumber(v);
	}

	/*****
	 * Set the current value of a calculator error message to a specific string
	 */
	public void setErrorMessage(String m){
		errorMessageMV = m;
	}
	
	/*****
	 * Set the current value of a calculator value to the value of another (copy)
	 */
	public void setValue(CalculatorValue v){
		measuredValue = v.measuredValue;
		errorMessageMV = v.errorMessageMV;
	}
	/**********************************************************************************************

	The toString() Method
	
	**********************************************************************************************/
	
	/*****
	 * This is the old toString method
	 * 
	 * When more complex calculator values are creating this routine will need to be updated
	 */
	public String toStringOld() {
//		EnhancedToString resultString = new EnhancedToString();
//		return resultString.cvToString(this);
		return measuredValue +" "+errorTerm.toDecimalString();
	}

	/*****
	 * This is the default toString() method.
	 * @return - String representation of a Unit.
	 */
	public String toString() {
		UNumber largest = new UNumber(1E12), smallest = new UNumber(1E-9);
		String etStr = "", mvStr = "";
		if (measuredValue.lessThan(largest) && measuredValue.greaterThan(smallest))
			mvStr = formatNumberString(measuredValue, pdDigitsMV);
		else
			mvStr = measuredValue.toString();

		if (errorTerm.lessThan(largest) && errorTerm.greaterThan(smallest))
			etStr = formatNumberString(errorTerm, pdDigitsET);
		else
			etStr = errorTerm.toString();

		return mvStr + " " + etStr + " " + myUnit;
	}
	
	/*****
	 * This is the debug toString method
	 * 
	 * When more complex calculator values are creating this routine will need to be updated
	 */
	public String debugToString() {
		return "measuredValue = " + measuredValue+"errorTerm = " + errorTerm + "\nerrorMessageMV = " + errorMessageMV + "\n";
	}

	
	/**********************************************************************************************

	The computation methods
	
	**********************************************************************************************/
	

	/*******************************************************************************************************
	 * The following methods implement computation on the calculator values.  These routines assume that the
	 * caller has verified that things are okay for the operation to take place.  These methods understand
	 * the technical details of the values and their reputations, hiding those details from the business 
	 * logic and user interface modules.
	 *
	 */

	/**
	 * The addition method.
	 * @param v - The term to add with.
	 */
	public void add(CalculatorValue v) {
		// Formula: errorTerm = ((errorTerm/measuredValue)+(v.errorTerm/v.measuredValue));

		// Some basic validation. If the units of the numbers do not support addition, then do not add.
		if (this.myUnit != null && !this.myUnit.canAdd(v.myUnit)) {
			errorMessageMV = "Incompatible units - " + myUnit + ", " +v.myUnit + "! Cannot add!";
			return;
		}

		// Store these numbers away in order to not change v.
		UNumber otherMV = new UNumber(v.measuredValue),
				otherET = new UNumber(v.errorTerm);

		// Convert the units.
		convertUnits(v.myUnit, otherMV, otherET);

		// Since we have done the conversion, set the appropriate unit names.
		if (this.myUnit.getUnitValue_mass() != 0 && v.myUnit.getUnitValue_mass() != 0) {
			this.myUnit.setUnitName_mass("kg");
			this.myUnit.setContainsMass(true);
		}
		if (this.myUnit.getUnitValue_length() != 0 && v.myUnit.getUnitValue_length() != 0) {
			this.myUnit.setUnitName_length("km");
			this.myUnit.setContainsLength(true);
		}
		if (this.myUnit.getUnitValue_time() != 0 && v.myUnit.getUnitValue_time() != 0) {
			this.myUnit.setUnitName_time("s");
			this.myUnit.setContainsTime(true);
		}

		// Finally, we do the sum.
		measuredValue.add(otherMV);
		errorTerm.add(otherET);
		errorMessageMV = "";

	}

	/**
	 * The subtraction method.
	 * @param v - The term to add with.
	 */
	public void sub(CalculatorValue v) {
		// Formula: errorTerm = ((errorTerm/measuredValue)+(v.errorTerm/v.measuredValue));
		// The same procedure as for addition.

		if (this.myUnit != null && v.myUnit != null && !this.myUnit.hasNoUnits() && !v.myUnit.hasNoUnits() && !this.myUnit.canAdd(v.myUnit)) {
			errorMessageMV = "Incompatible units - " + myUnit + ", " +v.myUnit + "! Cannot subtract!";
			return;
		}

		UNumber otherMV = new UNumber(v.measuredValue),
				otherET = new UNumber(v.errorTerm);

		convertUnits(v.myUnit, otherMV, otherET);

		// Since we have done the conversion, set the appropriate unit names.
		if (this.myUnit.getUnitValue_mass() != 0 && v.myUnit.getUnitValue_mass() != 0) {
			this.myUnit.setUnitName_mass("kg");
			this.myUnit.setContainsMass(true);
		}
		if (this.myUnit.getUnitValue_length() != 0 && v.myUnit.getUnitValue_length() != 0) {
			this.myUnit.setUnitName_length("km");
			this.myUnit.setContainsLength(true);
		}
		if (this.myUnit.getUnitValue_time() != 0 && v.myUnit.getUnitValue_time() != 0) {
			this.myUnit.setUnitName_time("s");
			this.myUnit.setContainsTime(true);
		}

		measuredValue.sub(v.measuredValue);
		errorTerm.add(v.errorTerm);
		errorMessageMV = "";
	}

	/**
	 * The multiplication method.
	 * @param v - The term to add with.
	 */
	public void mpy(CalculatorValue v) {
		// Rules for units:
		// 1. Multiply the scales.
		this.myUnit.setUnitScale_mass(this.myUnit.getUnitScale_mass() * v.myUnit.getUnitScale_mass());
		this.myUnit.setUnitScale_length(this.myUnit.getUnitScale_length() * v.myUnit.getUnitScale_length());
		this.myUnit.setUnitScale_time(this.myUnit.getUnitScale_time() * v.myUnit.getUnitScale_time());

		// 2. Multiply the MVs and ETs by the conversion factors.
		// Just like for addition and subtraction, store these numbers away in order to not change v.
		UNumber otherMV = new UNumber(v.measuredValue),
				otherET = new UNumber(v.errorTerm);

		convertUnits(v.myUnit, otherMV, otherET);

		// 3. Sum the values for common dimensions.
		this.myUnit.setUnitValue_mass(this.myUnit.getUnitValue_mass() + v.myUnit.getUnitValue_mass());
		this.myUnit.setUnitValue_length(this.myUnit.getUnitValue_length() + v.myUnit.getUnitValue_length());
		this.myUnit.setUnitValue_time(this.myUnit.getUnitValue_time() + v.myUnit.getUnitValue_time());

		// Since we have done the conversion, set the appropriate unit names.
		if (this.myUnit.getUnitValue_mass() != 0 || v.myUnit.getUnitValue_mass() != 0) {
			this.myUnit.setUnitName_mass("kg");
			this.myUnit.setContainsMass(true);
		}
		if (this.myUnit.getUnitValue_length() != 0 || v.myUnit.getUnitValue_length() != 0) {
			this.myUnit.setUnitName_length("km");
			this.myUnit.setContainsLength(true);
		}
		if (this.myUnit.getUnitValue_time() != 0 || v.myUnit.getUnitValue_time() != 0) {
			this.myUnit.setUnitName_time("s");
			this.myUnit.setContainsTime(true);
		}

		// Formula: errorTerm = ((errorTerm/measuredValue)+(v.errorTerm/v.measuredValue));
		UNumber errorFraction1 = new UNumber(errorTerm),
				errorFraction2 = new UNumber(otherET);
		errorFraction1.div(measuredValue);
		errorFraction2.div(otherMV);
		errorFraction1.add(errorFraction2);

		// Doing measuredValue *= v.measuredValue;
		measuredValue.mpy(otherMV);

		// Doing errorTerm *= measuredValue;
		errorFraction1.mpy(measuredValue);
		errorTerm = errorFraction1;

		errorMessageMV = "";
	}

	/**
	 * The division method.
	 * @param v - The term to add with.
	 */
	public void div(CalculatorValue v) {
		// Rules for units:
		// 1. Multiply the scales.
		this.myUnit.setUnitScale_mass(this.myUnit.getUnitScale_mass() / v.myUnit.getUnitScale_mass());
		this.myUnit.setUnitScale_length(this.myUnit.getUnitScale_length() / v.myUnit.getUnitScale_length());
		this.myUnit.setUnitScale_time(this.myUnit.getUnitScale_time() / v.myUnit.getUnitScale_time());

		// 2. Multiply the MVs and ETs by the conversion factors.
		// Just like for addition and subtraction, store these numbers away in order to not change v.
		UNumber otherMV = new UNumber(v.measuredValue),
				otherET = new UNumber(v.errorTerm);

		convertUnits(v.myUnit, otherMV, otherET);

		// 3. Subtract the values for common dimensions.
		this.myUnit.setUnitValue_mass(this.myUnit.getUnitValue_mass() - v.myUnit.getUnitValue_mass());
		this.myUnit.setUnitValue_length(this.myUnit.getUnitValue_length() - v.myUnit.getUnitValue_length());
		this.myUnit.setUnitValue_time(this.myUnit.getUnitValue_time() - v.myUnit.getUnitValue_time());

		// Since we have done the conversion, set the appropriate unit names.
		if (this.myUnit.getUnitValue_mass() != 0 || v.myUnit.getUnitValue_mass() != 0) {
			this.myUnit.setUnitName_mass("kg");
			this.myUnit.setContainsMass(true);
		}
		if (this.myUnit.getUnitValue_length() != 0 || v.myUnit.getUnitValue_length() != 0) {
			this.myUnit.setUnitName_length("km");
			this.myUnit.setContainsLength(true);
		}
		if (this.myUnit.getUnitValue_time() != 0 || v.myUnit.getUnitValue_time() != 0) {
			this.myUnit.setUnitName_time("s");
			this.myUnit.setContainsTime(true);
		}

		// Formula: errorTerm = ((errorTerm/measuredValue)+(v.errorTerm/v.measuredValue));
		UNumber errorFraction1 = new UNumber(errorTerm),
				errorFraction2 = new UNumber(otherET);
		errorFraction1.div(measuredValue);
		errorFraction2.div(otherMV);
		errorFraction1.add(errorFraction2);

		// Doing measuredValue /= v.measuredValue;
		measuredValue.div(otherMV);

		// Doing errorTerm *= measuredValue;
		errorFraction1.mpy(measuredValue);
		errorTerm = errorFraction1;

		errorMessageMV = "";
	}

	/**
	 * The square root method.
	 */
	public void sqrt() {
		// Formula: errorTerm = ((errorTerm/measuredValue)*(0.5));

		// For conversions, we also need to take square roots of the conversion factors.
		UNumber conv_mass_this = this.myUnit.getConv_mass().sqrt(),
				conv_length_this = this.myUnit.getConv_length().sqrt(),
				conv_time_this = this.myUnit.getConv_time().sqrt();

		// The same logic as in the convertUnits() method above.
		if (!conv_mass_this.isZero()) {
			if (!measuredValue.isZero()) {
				measuredValue.mpy(conv_mass_this);
			}
			if (!errorTerm.isZero())
				errorTerm.mpy(conv_mass_this);
		}

		if (!conv_length_this.isZero()) {
			if (!measuredValue.isZero())
				measuredValue.mpy(conv_length_this);
			if (!errorTerm.isZero())
				errorTerm.mpy(conv_length_this);
		}

		if (!conv_time_this.isZero()) {
			if (!measuredValue.isZero())
				measuredValue.mpy(conv_time_this);
			if (!errorTerm.isZero())
				errorTerm.mpy(conv_time_this);
		}

		// For square roots, we divide the power values.
		this.myUnit.setUnitValue_mass(this.myUnit.getUnitValue_mass() / 2);
		this.myUnit.setUnitValue_length(this.myUnit.getUnitValue_length() / 2);
		this.myUnit.setUnitValue_time(this.myUnit.getUnitValue_time() / 2);

		// Same as for multiplication.
		if (this.myUnit.getUnitValue_mass() != 0) {
			this.myUnit.setUnitName_mass("kg");
			this.myUnit.setContainsMass(true);
		}
		if (this.myUnit.getUnitValue_length() != 0) {
			this.myUnit.setUnitName_length("km");
			this.myUnit.setContainsLength(true);
		}
		if (this.myUnit.getUnitValue_time() != 0) {
			this.myUnit.setUnitName_time("s");
			this.myUnit.setContainsTime(true);
		}

		UNumber errorFraction = new UNumber(errorTerm);
		errorFraction.div(measuredValue);
		errorFraction.mpy(new UNumber(0.5));
		errorTerm = errorFraction;

		// Doing measuredValue = Math.sqrt(measuredValue);
		measuredValue = measuredValue.sqrt();

		// Doing errorTerm *= measuredValue;
		errorTerm.mpy(measuredValue);
		errorMessageMV = "";
	}
}
