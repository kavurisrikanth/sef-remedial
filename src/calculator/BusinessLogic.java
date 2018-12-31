
package calculator;

import UNumber.UNumber;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p> Title: BusinessLogic Class. </p>
 * 
 * <p> Description: The code responsible for performing the calculator business logic functions. 
 * This method deals with CalculatorValues and performs actions on them.  The class expects data
 * from the User Interface to arrive as Strings and returns Strings to it.  This class calls the
 * CalculatorValue class to do computations and this class knows nothing about the actual 
 * representation of CalculatorValues, that is the responsibility of the CalculatorValue class and
 * the classes it calls.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2017 </p>
 * 
 * @author Lynn Robert Carter - Baseline
 * 
 * @author Kavuri Srikanth
 * 
 * @version 4.00	2014-10-18 The JavaFX-based GUI implementation of a long integer calculator 
 * @version 4.10	2018-12-30 Implementation now supports units.
 */

public class BusinessLogic {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the major calculator values 
	private CalculatorValue operand1 = new CalculatorValue(0);
	private CalculatorValue operand2 = new CalculatorValue(0);
	private CalculatorValue result = new CalculatorValue(0);

	private String operand1ErrorMessage = "";
	private String operand1errErrorMessage = "";
	private String operand1UnitErrorMessage = "";
	private boolean operand1Defined = false;
	@SuppressWarnings("unused")
	private boolean operand1errDefined = false;
	private boolean operand1UnitDefined = false;
	
	private String operand2ErrorMessage = "";
	private String operand2errErrorMessage = "";
	private String operand2UnitErrorMessage = "";
	private boolean operand2Defined = false;
	@SuppressWarnings("unused")
	private boolean operand2errDefined = false;
	private boolean operand2UnitDefined = false;
	
	private String resultErrorMessage = "";

	private String[] unitData = new String[0];
	private boolean	massFound, lengthFound, timeFound;

	


	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/
	
	/**********
	 * This method initializes all of the elements of the business logic aspect of the calculator.
	 * There is no special computational initialization required, so the initialization of the
	 * attributes above are all that are needed.
	 */
	public BusinessLogic() {
	}

	/**********************************************************************************************

	Getters and Setters
	
	**********************************************************************************************/

	/**
	 * This method is included for backward compatibility.
	 * @param mv - Measured value string.
	 * @param et - Error term string.
	 * @return - True if there was no error; False if there was.
	 */
	public boolean setOperand1(String mv, String et) {
		return setOperand1(mv, et, "");
	}

	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into operand1, any associated error message
	 * into operand1ErrorMessage, and sets flags accordingly.
	 * 
	 * @param mv - Measured value string
	 * @param et - Error term string
	 * @param unit - Unit string
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setOperand1(String mv, String et, String unit) {
		operand1Defined = false;							// Assume the operand will not be defined
		operand1errDefined = false;							// Assume the operand will not be defined
		if (mv.length() <= 0) {						// See if the input is empty. If so no error
			operand1ErrorMessage = "";					// message, but the operand is not defined.
			return true;									// Return saying there was no error.
		}
		operand1 = new CalculatorValue(mv, et, unit);			// If there was input text, try to convert it
		operand1ErrorMessage = operand1.getErrorMessage();	// into a CalculatorValue and see if it
		if (operand1ErrorMessage.length() > 0) 			// worked. If there is a non-empty error 
			return false;								// message, signal there was a problem.
		operand1Defined = true;                         // Otherwise, set the defined flag and
		if (et.length() <= 0) {						// See if the input is empty. If so no error
			operand1errErrorMessage = "";					// message, but the operand is not defined.
			return true;									// Return saying there was no error.
		}
		operand1errErrorMessage = operand1.geterrErrorMessage();	// into a CalculatorValue and see if it
		if (operand1errErrorMessage.length() > 0) 			// worked. If there is a non-empty error 
			return false;								// message, signal there was a problem.
		// This is a bit different. Setting the wrong unit won't cause the operand
		// setting to fail. But record the error message and check for it in UserInterface.
		operand1UnitErrorMessage = operand1.getUnitErrorMessage();
		if (!et.isEmpty())
			operand1errDefined = true;							// Otherwise, set the defined flag and
		return true;										// signal that the set worked
	}

	/**
	 * This method is included for backward compatibility.
	 * @param mv - Measured value string
	 * @param et - Error term string
	 * @return - True if there was no error; False if there was.
	 */
	public boolean setOperand2(String mv, String et) {
		return setOperand2(mv, et, "");
	}
	
	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into operand2, any associated error message
	 * into operand1ErrorMessage, and sets flags accordingly.
	 * 
	 * The logic of this method is the same as that for operand1 above.
	 * 
	 * @param mv
	 * @param et
	 * @param unit
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setOperand2(String mv, String et, String unit) {			// The logic of this method is exactly the
		operand2Defined = false;							// same as that for operand1, above.
		operand2errDefined = false;
		if (mv.length() <= 0) {
			operand2ErrorMessage = "";
			return true;
		}
		operand2 = new CalculatorValue(mv, et, unit);
		operand2ErrorMessage = operand2.getErrorMessage();
		if (operand2ErrorMessage.length() > 0)
			return false;
		operand2Defined = true;
		if (mv.length() <= 0) {
			operand2errErrorMessage = "";
			return true;
		}
		operand2errErrorMessage = operand2.geterrErrorMessage();
		if (operand2errErrorMessage.length() > 0)
			return false;
		operand2UnitErrorMessage = operand2.getUnitErrorMessage();
		if (!et.isEmpty())
			operand2errDefined = true;
		return true;
	}

	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into result, any associated error message
	 * into resuyltErrorMessage, and sets flags accordingly.
	 * 
	 * The logic of this method is similar to that for operand1 above. (There is no defined flag.)
	 * 
	 * @param value
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setResult(String value) {				// The logic of this method is similar to
		if (value.length() <= 0) {						// that for operand1, above.
			operand2ErrorMessage = "";
			return true;
		}
		result = new CalculatorValue(value,"0.00", "");
		resultErrorMessage = operand2.getErrorMessage();
		if (operand2ErrorMessage.length() > 0)
			return false;
		return true;
	}
	
	
	
	/**********
	 * This public setter sets the String explaining the current error in operand1.
	 * 
	 * @return
	 */
	public void setOperand1ErrorMessage(String m) {
		operand1ErrorMessage = m;
		return;
	}
	
	/**********
	 * This public getter fetches the String explaining the current error in operand1, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getOperand1ErrorMessage() {
		return operand1ErrorMessage;
	}
	
	/**********
	 * This public getter fetches the String explaining the current error in operand1, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getOperand1errErrorMessage() {
		return operand1errErrorMessage;
	}
	
	/**********
	 * This public setter sets the String explaining the current error into operand1.
	 * 
	 * @return
	 */
	public void setOperand2ErrorMessage(String m) {
		operand2ErrorMessage = m;
		return;
	}
	
	/**********
	 * This public getter fetches the String explaining the current error in operand2, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getOperand2ErrorMessage() {
		return operand2ErrorMessage;
	}
	
	/**********
	 * This public getter fetches the String explaining the current error in operand2, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getOperand2errErrorMessage() {
		return operand2errErrorMessage;
	}
	
	/**********
	 * This public setter sets the String explaining the current error in the result.
	 * 
	 * @return
	 */
	public void setResultErrorMessage(String m) {
		resultErrorMessage = m;
		return;
	}
	
	/**********
	 * This public getter fetches the String explaining the current error in the result, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getResultErrorMessage() {
		return resultErrorMessage;
	}
	
	/**********
	 * This public getter fetches the defined attribute for operand1. You can't use the lack of an error 
	 * message to know that the operand is ready to be used. An empty operand has no error associated with 
	 * it, so the class checks to see if it is defined and has no error before setting this flag true.
	 * 
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getOperand1Defined() {
		return operand1Defined;
	}
	
	/**********
	 * This public getter fetches the defined attribute for operand2. You can't use the lack of an error 
	 * message to know that the operand is ready to be used. An empty operand has no error associated with 
	 * it, so the class checks to see if it is defined and has no error before setting this flag true.
	 * 
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getOperand2Defined() {
		return operand2Defined;
	}

	/**
	 * This public getter fetches the error message for the unit of operand 1.
	 * @return - The error message for unit of operand 1.
	 */
	public String getOperand1UnitErrorMessage() {
		return operand1UnitErrorMessage;
	}

	/**
	 * This public getter fetches the error message for the unit of operand 2.
	 * @return - The error message for unit of operand 2.
	 */
	public String getOperand2UnitErrorMessage() {
		return operand2UnitErrorMessage;
	}

	/**
	 * This public getter fetches the unit data for the result. This is used to
	 * set the result's unit TextFields and ComboBoxes.
	 * @return - An array of 9 strings containing indices and power values.
	 */
	public String[] getUnitData() {
		return unitData;
	}

	public void resetUnitData() {
		this.unitData = null;
	}

	/**********************************************************************************************

	The toString() Method
	
	**********************************************************************************************/
	
	/**********
	 * This toString method invokes the toString method of the result type (CalculatorValue is this 
	 * case) to convert the value from its hidden internal representation into a String, which can be
	 * manipulated directly by the BusinessLogic and the UserInterface classes.
	 */
	public String toString() {
		return result.toString();
	}
	
	/**********
	 * This public toString method is used to display all the values of the BusinessLogic class in a
	 * textual representation for debugging purposes.
	 * 
	 * @return a String representation of the class
	 */
	public String debugToString() {
		String r = "\n******************\n*\n* Business Logic\n*\n******************\n";
		r += "operand1 = " + operand1.toString() + "\n";
		r += "     operand1ErrorMessage = " + operand1ErrorMessage+ "\n";
		r += "     operand1Defined = " + operand1Defined+ "\n";
		r += "operand2 = " + operand2.toString() + "\n";
		r += "     operand2ErrorMessage = " + operand2ErrorMessage+ "\n";
		r += "     operand2Defined = " + operand2Defined+ "\n";
		r += "result = " + result.toString() + "\n";
		r += "     resultErrorMessage = " + resultErrorMessage+ "\n";
		r += "*******************\n\n";
		return r;
	}

	public void translateUnitString(String us) {
		// The unit string is of the following form: (scale.name)^value
		// Dimensions are separated by a *.

		if (us.length() == 0)
			return;

		ArrayList<String> indices;
		massFound = false;
		lengthFound = false;
		timeFound = false;

		if (us.indexOf('*') == -1) {
			// There is no asterisk. This means the string represents only one unit dimension.
			indices = new ArrayList<>();
			ArrayList<String> result = translateUnitDimension(us);
			if (massFound) {
				indices.addAll(result);
				for (int i = 0; i < 6; i++) {
					indices.add("-1");
				}
			} else if (lengthFound) {
				for (int i = 0; i < 3; i++) {
					indices.add("-1");
				}
				indices.addAll(result);
				for (int i = 0; i < 3; i++) {
					indices.add("-1");
				}
			} else {
				for (int i = 0; i < 6; i++) {
					indices.add("-1");
				}
				indices.addAll(result);
			}
		} else {
			// Two or more dimensions.
			String[] pieces = us.split("\\*");
			indices = new ArrayList<>();

			if (pieces.length == 2) {
				// Two dimensions. Three possibilities: ML, MT, LT
				ArrayList<String> result = translateUnitDimension(pieces[0]);
				if (massFound) {
					// Add mass
					indices.addAll(result);

					result = translateUnitDimension(pieces[1]);

					// Check if length found or time and add appropriately.
					if (lengthFound) {
						indices.addAll(result);
						for (int i = 0; i < 3; i++) {
							indices.add("-1");
						}
					} else {
						for (int i = 0; i < 3; i++) {
							indices.add("-1");
						}
						indices.addAll(result);
					}
				} else {
					// Just LT.
					for (int i = 0; i < 3; i++) {
						indices.add("-1");
					}
					indices.addAll(result);
					result = translateUnitDimension(pieces[1]);
					indices.addAll(result);
				}
			} else {
				indices.addAll(translateUnitDimension(pieces[0]));
				indices.addAll(translateUnitDimension(pieces[1]));
				indices.addAll(translateUnitDimension(pieces[2]));
			}
		}

		unitData = indices.toArray(new String[9]);
	}

	private ArrayList<String> translateUnitDimension(String s) {
		ArrayList<String> ans = new ArrayList<>();

		System.out.println("S: " + s);

		// Split the string by ^.
		String[] pieces = s.split("\\^");
		System.out.println("pieces length: " + pieces.length);

		if (pieces[1].equals("0.0")) {
			ans.add("-1");
			ans.add("-1");
			ans.add("-1");
		} else {
			// Remove the parentheses.
			String temp = pieces[0];
			temp = temp.substring(1, temp.length() - 1);
			System.out.println("temp: " + temp);

			// Now temp is of the format scale.name
			if (temp.indexOf('.') != -1) {
				// There is a scale
				System.out.println("scale found");
				String[] subPieces = temp.split("\\.");
				String scale = subPieces[0];
				ans.add(getScaleIndex(scale));
				ans.add(getUnitIndex(subPieces[1]));
				ans.add(pieces[1]);
			} else {
				// There is no scale
				System.out.println("No scale");
				String dimIndex = getUnitIndex(temp);
				ans.add("-1");
				ans.add(dimIndex);
				ans.add(pieces[1]);
			}

			System.out.println("mass: " + massFound + ", length: " + lengthFound + ", time: " + timeFound);
		}

		return ans;
	}

	private String getScaleIndex(String s) {
		String[] scales = {"--", "E", "P", "T", "G", "M", "k", "h", "da", "d", "c", "m", "\u03BC", "n", "p", "f", "a"};
		return Integer.toString(Arrays.asList(scales).indexOf(s));
	}

	private String getUnitIndex(String s) {
		String[] mass = {"oz", "lb", "kg", "g"},
				length = {"yd", "mi", "ft", "in","m", "km"},
				time = {"s", "min", "hr", "day"};

		for (String mass1 : mass) {
			System.out.println(mass1);
		}

		System.out.println("searching for index of: " + s);

		int massIndex = Arrays.asList(mass).indexOf(s),
				lengthIndex = Arrays.asList(length).indexOf(s),
				timeIndex = Arrays.asList(time).indexOf(s);

		System.out.println("Mass index: " + massIndex + ", length index: " + lengthIndex + ", time index: " + timeIndex);

		if (massIndex >= 0) {
			massFound = true;
			return Integer.toString(massIndex + 1);
		} else if (lengthIndex >= 0) {
			lengthFound = true;
			return Integer.toString(lengthIndex + 1);
		} else if (timeIndex >= 0) {
			timeFound = true;
			return Integer.toString(timeIndex + 1);
		} else
			return "-1";
	}

	public String changeUnits(String massScale, String massUnit, String lenScale, String lenUnit, String timeScale, String timeUnit) {
		// Logically, result should contain the old values.
		result.changeUnit(massScale, massUnit, lenScale, lenUnit, timeScale, timeUnit);
		return result.toString();
	}

	/**********************************************************************************************

	Business Logic Operations (e.g. addition)
	
	**********************************************************************************************/
	
	/*
	 * This public method computes the sum of the two operands using the CalculatorValue class method 
	 * for addition. The goal of this class is to support a wide array of different data representations 
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left operand with the 
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 * 
	 * @return a String representation of the result
	 */

	/**
	 * This method implements addition.
	 * @return - The result implementation of the result.
	 */
	public String addition() {
		// The following code checks if error terms have been defined by the user. If neither operand
		// has an error term defined, then both error terms are set to 0. On the other hand, if one
		// operand has an error term, then the other operand's error term is deduced from the
		// measured value.
		if (!operand1errDefined && !operand2errDefined) {
			operand1.errorTerm = new UNumber(0);
			operand2.errorTerm = new UNumber(0);
		}
		if (!operand1errDefined && operand2errDefined) {
			operand1.errorTerm = operand1.estimatedErrorTerm;
			operand1errDefined = true;
		}
		if (operand1errDefined && !operand2errDefined) {
			operand2.errorTerm = operand2.estimatedErrorTerm;
			operand2errDefined = true;
		}

		result = new CalculatorValue(operand1);				// Take the first operand
		result.add(operand2);								// and add to it the second operand.
		resultErrorMessage = result.getErrorMessage();
		if (resultErrorMessage.length() > 0)				// If an error occurred, then indicate it.
			return "";
		String resultString = result.toString();
		String[] pieces = resultString.split(" ");	// Take the result string and split it.
		if (pieces.length == 3)								// If there is a unit string, then translate
			translateUnitString(pieces[2]);					// it into numbers that we can use in UserInterface.
		return resultString;
	}
	
	/**
	 * This method implements subtraction.
	 * @return - The string representation of the result.
	 */
	public String subtraction() {
		// The same logic as addition.

		if (!operand1errDefined && !operand2errDefined) {
			operand1.errorTerm = new UNumber(0);
			operand2.errorTerm = new UNumber(0);
		}
		if (!operand1errDefined && operand2errDefined) {
			operand1.errorTerm = operand1.estimatedErrorTerm;
			operand1errDefined = true;
		}
		if (operand1errDefined && !operand2errDefined) {
			operand2.errorTerm = operand2.estimatedErrorTerm;
			operand2errDefined = true;
		}

		result = new CalculatorValue(operand1);
		result.sub(operand2);
		resultErrorMessage = result.getErrorMessage();
		if (resultErrorMessage.length() > 0)
			return "";
		String resultString = result.toString();
		String[] pieces = resultString.split(" ");
		if (pieces.length == 3)
			translateUnitString(pieces[2]);
		return resultString;
	}

	/**
	 * This method implements multiplication.
	 * @return - The string representation of the result.
	 */
	public String multiplication() {
		if (!operand1errDefined && !operand2errDefined) {
			operand1.errorTerm = new UNumber(0);
			operand2.errorTerm = new UNumber(0);
		}
		if (!operand1errDefined && operand2errDefined) {
			operand1.errorTerm = operand1.estimatedErrorTerm;
			operand1errDefined = true;
		}
		if (operand1errDefined && !operand2errDefined) {
			operand2.errorTerm = operand2.estimatedErrorTerm;
			operand2errDefined = true;
		}

		result = new CalculatorValue(operand1);
		result.mpy(operand2);
		resultErrorMessage = result.getErrorMessage();
		if (resultErrorMessage.length() > 0)
			return "";
		String resultString = result.toString();
		String[] pieces = resultString.split(" ");
		if (pieces.length == 3)
			translateUnitString(pieces[2]);
		return resultString;
	}

	/**
	 * This method implements division.
	 * @return - The string representation of the result.
	 */
	public String division() {
		if (!operand1errDefined && !operand2errDefined) {
			operand1.errorTerm = new UNumber(0);
			operand2.errorTerm = new UNumber(0);
		}
		if (!operand1errDefined && operand2errDefined) {
			operand1.errorTerm = operand1.estimatedErrorTerm;
			operand1errDefined = true;
		}
		if (operand1errDefined && !operand2errDefined) {
			operand2.errorTerm = operand2.estimatedErrorTerm;
			operand2errDefined = true;
		}

		result = new CalculatorValue(operand1);
		if(operand2.measuredValue.isZero()) {          //if second operand is zero we need to raise error
			result.errorMessageMV="division by zero error";
			resultErrorMessage = result.getErrorMessage();
			return "";
		}
		result.div(operand2);
		resultErrorMessage = result.getErrorMessage();
		if (resultErrorMessage.length() > 0)
			return "";
		String resultString = result.toString();
		System.out.println("result str: " + resultString);
		String[] pieces = resultString.split(" ");
		if (pieces.length == 3)
			translateUnitString(pieces[2]);
		return resultString;
	}

	/**
	 * This method implements square root.
	 * @return - The string representation of the result.
	 */
	public String SquareRoot() {
		if (!operand1errDefined && !operand2errDefined) {
			operand1.errorTerm = new UNumber(0);
			operand2.errorTerm = new UNumber(0);
		}
		if (!operand1errDefined && operand2errDefined) {
			operand1.errorTerm = operand1.estimatedErrorTerm;
			operand1errDefined = true;
		}
		if (operand1errDefined && !operand2errDefined) {
			operand2.errorTerm = operand2.estimatedErrorTerm;
			operand2errDefined = true;
		}

		result = new CalculatorValue(operand1);
		if(result.measuredValue.isNegative()) {
			result.errorMessageMV="negative number gives imaginary ans";
			resultErrorMessage = result.getErrorMessage();
			return "";
		}
		result.sqrt();
		resultErrorMessage = result.getErrorMessage();
		if (resultErrorMessage.length() > 0)
			return "";
		String resultString = result.toString();
		String[] pieces = resultString.split(" ");
		if (pieces.length == 3)
			translateUnitString(pieces[2]);
		return resultString;
	}
}
