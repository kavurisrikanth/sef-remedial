package calculator;

import UNumber.UNumber;

import java.util.HashMap;

/**
 * <p> Title: Unit Class. </p>
 *
 * <p> Description: The representation of a unit of measurement. </p>
 *
 * <p> Copyright: Lynn Robert Carter © 2017 </p>
 *
 * @author Kavuri Srikanth
 *
 * @version 1.00	2018-12-28 Units support addition and subtraction.
 *
 */
public class Unit {
    /* Class variables */
    // Unit names e.g., g, m, etc.
    private String unitName_mass, unitName_length, unitName_time;

    // Unit scales e.g., kilo, milli, etc.
    private String scaleStr_mass, scaleStr_length, scaleStr_time;
    private double unitScale_mass = 1, unitScale_length = 1, unitScale_time = 1;

    // Unit values. These are actually the powers to which the units are raised.
    // Like squared meter, for instance.
    private double unitValue_mass, unitValue_length, unitValue_time;

    // Convenient variables to quick-check if an element is present.
    private boolean containsMass, containsLength, containsTime;
    private boolean noUnits;

    // Unit conversion factors. These variables contain the factors for conversion to metric.
    private UNumber conv_mass = new UNumber(0.0),
                    conv_length = new UNumber(0.0),
                    conv_time = new UNumber(0.0);

    private String errorMsg;

    // Temporary variables.
    private double scale, value;
    UNumber conversionValue;
    private String name, scaleStr;

    // HashMap for conversion values.
    private static HashMap<String, Double> createForwardMap()
    {
        HashMap<String,Double> myMap = new HashMap<>();
        myMap.put("oz",  0.02834949254408346090604978170891);
        myMap.put("lb",  0.45359290943563970208017708267184);
        myMap.put("kg", 1.0);
        myMap.put("g", 0.001);
        myMap.put("ft",  3.0479999024640031211519001231392e-4);
        myMap.put("km",1.0 );
        myMap.put("yd",  9.1440275783871764157240698237946e-4);
        myMap.put("mi", 1.6093444978925633800096882538773);
        myMap.put("in",  2.5399986284007406636000416559775e-5);
        myMap.put("m", 0.001);
        myMap.put("s", 1.0);
        myMap.put("min", 60.0);
        myMap.put("day", 86400.0);
        myMap.put("hr",  3600.0);

        return myMap;
    }
    private HashMap<String,Double> forwardHash = createForwardMap();

    // HashMap for scale values.
    private HashMap<String, UNumber> createReverseMap() {
        HashMap<String,UNumber> myMap = new HashMap<String, UNumber>();
        myMap.put("", new UNumber(0));
        myMap.put("--", new UNumber(1));
        myMap.put("atto", new UNumber(1e-18));
        myMap.put("femto", new UNumber(1e-15));
        myMap.put("pico",new UNumber(1e-12));
        myMap.put("nano",new UNumber(1e-9) );
        myMap.put("micro", new UNumber(1e-6) );
        myMap.put("milli",new UNumber(1e-3));
        myMap.put("centi", new UNumber(1e-2));
        myMap.put("deci",new UNumber(1e-1));
        myMap.put("deca", new UNumber(10));
        myMap.put("hecto",new UNumber(1e2) );
        myMap.put("kilo",new UNumber(1e3) );
        myMap.put("mega",new UNumber(1e6));
        myMap.put("giga",new UNumber(1e9) );
        myMap.put("tera", new UNumber(1e12));
        myMap.put("peta",new UNumber(1e15) );
        myMap.put("exa", new UNumber(1e18));

        return myMap;
    }
    private HashMap<String, UNumber> scaleConversion = createReverseMap();

    // HashMap for symbols.
    private HashMap<String, String> createSymbolMap() {
        HashMap<String,String> myMap = new HashMap<>();
        myMap.put(null, "");
        myMap.put("--", "");
        myMap.put("atto", "a");
        myMap.put("femto", "f");
        myMap.put("pico","p");
        myMap.put("nano","n" );
        myMap.put("micro", "\u03BC" );
        myMap.put("milli","m");
        myMap.put("centi", "c");
        myMap.put("deci","d");
        myMap.put("deca", "da");
        myMap.put("hecto","h" );
        myMap.put("kilo","k" );
        myMap.put("mega","M");
        myMap.put("giga","G" );
        myMap.put("tera", "T");
        myMap.put("peta","P" );
        myMap.put("exa", "E");

        return myMap;
    }
    private HashMap<String, String> symbolMap = createSymbolMap();

    /**
     * Constructor for creating a Unit from a string.
     * @param s - The string.
     */
    public Unit(String s) {
        // Split the string. It arrives separated by |s.
        String[] pieces = s.split("\\|");

        // Parse the first part and store mass variables.
        if (pieces.length > 0) {
            parseString(pieces[0]);
            scaleStr_mass = scaleStr;
            unitScale_mass = scale;
            containsMass = !(name.isEmpty() || name.equals("--"));
            unitName_mass = containsMass ? name : "";
            unitValue_mass = value;
            conv_mass = new UNumber(conversionValue);
        }

        // Parse the second part and store length variables.
        if (pieces.length > 1) {
            parseString(pieces[1]);
            scaleStr_length = scaleStr;
            unitScale_length = scale;
            containsLength = !(name.isEmpty() || name.equals("--"));
            unitName_length = containsLength ? name : "";
            unitValue_length = value;
            conv_length = new UNumber(conversionValue);
        }

        // Parse the third part and store time variables.
        if (pieces.length > 2) {
            parseString(pieces[2]);
            scaleStr_time = scaleStr;
            unitScale_time = scale;
            containsTime = !(name.isEmpty() || name.equals("--"));
            unitName_time = containsTime ? name : "";
            unitValue_time = value;
            conv_time = new UNumber(conversionValue);
        }

        noUnits = !containsMass && !containsLength && !containsTime;
    }

    /* The following is a copy constructor. */
    public Unit(Unit u) {
        this.unitName_mass = u.unitName_mass;
        this.unitName_length = u.unitName_length;
        this.unitName_time = u.unitName_time;

        this.scaleStr_mass = u.scaleStr_mass;
        this.scaleStr_length = u.scaleStr_length;
        this.scaleStr_time = u.scaleStr_time;

        this.unitScale_mass = u.unitScale_mass;
        this.unitScale_length = u.unitScale_length;
        this.unitScale_time = u.unitScale_time;

        this.unitValue_mass = u.unitValue_mass;
        this.unitValue_length = u.unitValue_length;
        this.unitValue_time = u.unitValue_time;

        this.containsMass = u.containsMass;
        this.containsLength = u.containsLength;
        this.containsTime = u.containsTime;

        this.conv_mass = new UNumber(u.conv_mass);
        this.conv_length = new UNumber(u.conv_length);
        this.conv_time = new UNumber(u.conv_time);
    }

    /**
     * This method parses a string and extracts the scale, name, and value. This method removes the
     * necessity of writing the same code once for each of mass, length, and time.
     * @param s - The string. Actually a part of the string that's received from CalculatorValue.
     */
    private void parseString(String s) {
        // Split the string into pieces
        String[] pieces = s.split(":");

        // First comes the scale.
        scaleStr = (pieces.length > 0 && pieces[0] != null) ? pieces[0] : "";
        scale = scaleStr.length() != 0 ? scaleConversion.get(scaleStr).getDouble() : 1;

        // Then the name
        name = ((pieces.length > 1 && !pieces[1].equals("null"))) ? (pieces[1].equals("--") ? "" : pieces[1]) : "";

        // Based on the name, set the conversion value.
        UNumber temp = new UNumber(name.length() > 0 ? forwardHash.get(name) : 0.0);
        double toMultiply = temp.getDouble(),
                cvDouble  = 1;
        cvDouble *= toMultiply;
        cvDouble /= scale;
        conversionValue = new UNumber(cvDouble);

        // Finally, the value
        if (pieces.length > 2 && pieces[2].matches("[+,\\-]?[0-9]+(\\.[0-9]+)?")) {
            value = Double.parseDouble(pieces[2]);

            // Now, alter the conversion value. The conversion value is raised to the power of value.
            conversionValue = new UNumber(Math.pow(conversionValue.getDouble(), value));
        } else {
            // Validation. Set a non-existent value to 0. And anything raised to 0 is 1.
            value = 0;
            conversionValue = new UNumber(1.0);
            if (pieces.length <= 2)
                errorMsg = "Power value missing for Unit. Setting to 0.";
            else
                errorMsg = "Invalid Unit value: " + pieces[2] + ". Setting to 0.";
        }
    }

    /**
     * Checks if two units are compatible for addition (or subtraction)
     * @param other - The other Unit.
     * @return - true if addable, false if not.
     */
    public boolean canAdd(Unit other) {
        // To add two units, they must represent the same dimension. Since we have "normalized"
        // the units with the multiplication factor, we can just check the individual elements.

        // If any of the pieces mismatch, return false.
        if ((this.containsMass && !other.containsMass) || (!this.containsMass && other.containsMass)) {
//            System.out.println("One");
            return false;
        }

        if ((this.containsLength && !other.containsLength) || (!this.containsLength && other.containsLength)) {
//            System.out.println("Two");
            return false;
        }

        if ((this.containsTime && !other.containsTime) || (!this.containsTime && other.containsTime)) {
//            System.out.println("Three");
            return false;
        }

        // Now check if the scales and values are the same.
        if ((this.containsMass && other.containsMass) && !(this.unitScale_mass == other.unitScale_mass && this.unitValue_mass == other.unitValue_mass)) {
//            System.out.println("Four");
//            System.out.println("Scales: this - " + this.unitScale_mass + ", other - " + other.unitScale_mass);
//            System.out.println(this.unitScale_mass == other.unitScale_mass);
//            System.out.println("Values: this - " + this.unitValue_mass + ", other - " + other.unitValue_mass);
//            System.out.println(this.unitValue_mass == other.unitValue_mass);
            return false;
        }

        if ((this.containsLength && other.containsLength) && !(this.unitScale_length == other.unitScale_length && this.unitValue_length == other.unitValue_length)) {
//            System.out.println("Five");
            return false;
        }

        if ((this.containsTime && other.containsTime) && !(this.unitScale_time == other.unitScale_time && this.unitValue_time == other.unitValue_time)) {
//            System.out.println("Six");
            return false;
        }

        // If we've reached here then everything is OK.
        return true;
    }

    public double alter(String massScale, String massUnit, String lenScale, String lenUnit, String timeScale, String timeUnit) {
        double newUnitScale_mass = scaleConversion.get(massScale).getDouble(),
                newUnitScale_len = scaleConversion.get(lenScale).getDouble(),
                newUnitScale_time = scaleConversion.get(timeScale).getDouble();
        double unitScaleFactor_mass = newUnitScale_mass/unitScale_mass,
                unitScaleFactor_len = newUnitScale_len/unitScale_length,
                unitScaleFactor_time = newUnitScale_time/unitScale_time;

        System.out.println("Mass: name - " + unitName_mass + ", scale - " + massScale + ", unit - " + massUnit);
        System.out.println("Length: name - " + unitName_length + ", scale - " + lenScale + ", unit - " + lenUnit);
        System.out.println("Time: name - " + unitName_time + ", scale - " + timeScale + ", unit - " + timeUnit);

        final boolean b = massUnit == null || massUnit.length() == 0 || massUnit.equals("--");
        final boolean b1 = lenUnit == null || lenUnit.length() == 0 || lenUnit.equals("--");
        final boolean b2 = timeUnit == null || timeUnit.length() == 0 || timeUnit.equals("--");

        if ((containsMass && b) || (!containsMass && !b) ||
            (containsLength && b1) || (!containsLength && !b1) ||
            (containsTime && b2) || (!containsTime && !b2)) {
            // Add error message here.
            return Double.NEGATIVE_INFINITY;
        }

        double massConVal = massUnit != null && massUnit.length() > 0 ? forwardHash.get(massUnit) : 1,
                lenConVal = lenUnit != null && lenUnit.length() > 0 ? forwardHash.get(lenUnit) : 1,
                timeConVal = timeUnit != null && timeUnit.length() > 0 ? forwardHash.get(timeUnit) : 1;
        massConVal = Math.pow(massConVal, unitValue_mass);
        lenConVal = Math.pow(lenConVal, unitValue_length);
        timeConVal = Math.pow(timeConVal, unitValue_time);

        double massConvFactor = conv_mass.getDouble()/massConVal,
                lenConvFactor = conv_length.getDouble()/lenConVal,
                timeConvFactor = conv_time.getDouble()/timeConVal;

        // Since it is unit alteration, replace the old unit values.
        unitScale_mass = newUnitScale_mass;
        unitScale_length = newUnitScale_len;
        unitScale_time = newUnitScale_time;
        unitName_mass = massUnit;
        unitName_length = lenUnit;
        unitName_time = timeUnit;
        conv_mass = new UNumber(massConVal);
        conv_length = new UNumber(lenConVal);
        conv_time = new UNumber(timeConVal);


        return unitScaleFactor_mass * unitScaleFactor_len * unitScaleFactor_time * massConvFactor * lenConvFactor * timeConvFactor;
    }

    /* Getter and setter methods for conversion factors. */

    /**
     * Getter for the mass conversion factor.
     * @return - The mass conversion factor.
     */
    public UNumber getConv_mass() {
        return conv_mass;
    }

    /**
     * Getter for the length conversion factor.
     * @return - The length conversion factor.
     */
    public UNumber getConv_length() {
        return conv_length;
    }

    /**
     * Getter for the time conversion factor.
     * @return - The time conversion factor.
     */
    public UNumber getConv_time() {
        return conv_time;
    }

    /**
     * Gets the mass scale for the unit.
     * @return
     */
    public double getUnitScale_mass() {
        return unitScale_mass;
    }

    /**
     * Sets the mass unit scale
     * @param unitScale_mass
     */
    public void setUnitScale_mass(double unitScale_mass) {
        this.unitScale_mass = unitScale_mass;
    }

    /**
     * Gets the length unit scale
     * @return
     */
    public double getUnitScale_length() {
        return unitScale_length;
    }

    /**
     * Sets the length unit scale
     * @param unitScale_length
     */
    public void setUnitScale_length(double unitScale_length) {
        this.unitScale_length = unitScale_length;
    }

    /**
     * Gets the time unit scale
     * @return
     */
    public double getUnitScale_time() {
        return unitScale_time;
    }

    /**
     * Sets the time unit scale
     * @param unitScale_time
     */
    public void setUnitScale_time(double unitScale_time) {
        this.unitScale_time = unitScale_time;
    }

    /**
     * Gets the mass unit value.
     * @return
     */
    public double getUnitValue_mass() {
        return unitValue_mass;
    }

    /**
     * Gets the length unit value
     * @return
     */
    public double getUnitValue_length() {
        return unitValue_length;
    }

    /**
     * Gets the time unit value.
     * @return
     */
    public double getUnitValue_time() {
        return unitValue_time;
    }

    /**
     * Sets the mass unit value
     * @param unitValue_mass
     */
    public void setUnitValue_mass(double unitValue_mass) {
        this.unitValue_mass = unitValue_mass;
    }

    /**
     * Sets the length unit value
     * @param unitValue_length
     */
    public void setUnitValue_length(double unitValue_length) {
        this.unitValue_length = unitValue_length;
    }

    /**
     * Sets the time unit value
     * @param unitValue_time
     */
    public void setUnitValue_time(double unitValue_time) {
        this.unitValue_time = unitValue_time;
    }

    /**
     * Sets the mass unit name
     * @param unitName_mass
     */
    public void setUnitName_mass(String unitName_mass) {
        this.unitName_mass = unitName_mass;
    }

    /**
     * Sets the length unit name
     * @param unitName_length
     */
    public void setUnitName_length(String unitName_length) {
        this.unitName_length = unitName_length;
    }

    /**
     * Sets the time unit name
     * @param unitName_time
     */
    public void setUnitName_time(String unitName_time) {
        this.unitName_time = unitName_time;
    }

    /*
        The following four methods are setters and getters for different boolean values.
     */
    public void setContainsMass(boolean containsMass) {
        this.containsMass = containsMass;
    }

    public void setContainsLength(boolean containsLength) {
        this.containsLength = containsLength;
    }

    public void setContainsTime(boolean containsTime) {
        this.containsTime = containsTime;
    }

    public boolean hasNoUnits() {
        return noUnits;
    }

    /**
     * Gets the error message.
     * @return
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Check if two Unit objects represent the same unit.
     * @param other - The other unit.
     * @return - true if the same, false if not.
     */
    public boolean equals(Unit other) {
        // Null validation
        if (other == null)
            return false;

        // Luckily, addition requires that two units represent the same physical quantity.
        // So use that method instead of duplicating code.
        if (!canAdd(other))
            return false;

        // Now the extra parts for ACTUAL equality. The units' names must be the same.
        // For example, lb and kg can be added (after conversions; that's why we have
        // the conversion  factor variables), but lb and kg are not equal.
        if (containsMass && other.containsMass && !unitName_mass.equals(other.unitName_mass))
            return false;

        if (containsLength && other.containsLength && !unitName_length.equals(other.unitName_length))
            return false;

        if (containsTime && other.containsTime && !unitName_time.equals(other.unitName_time))
            return false;

        // Reached here so the units are equal.
        return true;
    }

    /**
     * The toString method for Unit.
     * @return - String representation of a Unit.
     */
    public String toString() {
        // Using StringBuilder instead of doing +/- with String because this is much less
        // computationally intensive.
        StringBuilder sb = new StringBuilder();

        // If the unit contains mass...
        if (this.containsMass) {
            sb.append("(");                             // Enclose the scale and name in parentheses
            String symbolStr = symbolMap.get(scaleStr_mass);
            sb.append(symbolStr);
            if (symbolStr.length() > 0)
                sb.append(".");
            sb.append(unitName_mass);
            sb.append(")");
            sb.append("^");
            sb.append(unitValue_mass);                  // And append the value as well.
        }

        if (this.containsMass && this.containsLength)
            sb.append("*");                             // Just add a separator for later.

        // The same as mass.
        if (this.containsLength) {
            sb.append("(");
            String symbolStr = symbolMap.get(scaleStr_length);
            sb.append(symbolStr);
            if (symbolStr.length() > 0)
                sb.append(".");
            sb.append(unitName_length);
            sb.append(")");
            sb.append("^");
            sb.append(unitValue_length);
        }

        if ((this.containsMass || this.containsLength) && this.containsTime)
            sb.append("*");                             // Another separator for later.

        // The same as mass.
        if (this.containsTime) {

            sb.append("(");
            String symbolStr = symbolMap.get(scaleStr_time);
            sb.append(symbolStr);
            if (symbolStr.length() > 0)
                sb.append(".");
            sb.append(unitName_time);
            sb.append(")");
            sb.append("^");
            sb.append(unitValue_time);
        }

        // Return the string.
        return sb.toString();
    }
}
