package calculator;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class EnhancedToString {
	
	public String enhancedET = "";
	public String enhancedMV = "";
	public BigDecimal one = new BigDecimal(1.0);
	public BigDecimal zero = new BigDecimal(0);
	public BigDecimal errorUpperLimit = new BigDecimal(9E3);
	public BigDecimal errorLowerLimit = new BigDecimal(1E-3);
	public BigDecimal measureUpperLimit = new BigDecimal(1e7);
	public BigDecimal measureLowerLimit = new BigDecimal(1e-5);
	
	
	
	public EnhancedToString() {
		
	}
	
	
	public String mvToString(BigDecimal measuredValue,boolean measureSign) {
		DecimalFormat decfor;
		String x= "";
		if(measuredValue.compareTo(measureUpperLimit)<0 && measuredValue.compareTo(measureLowerLimit)>0) {
			if(!measureSign) {
				measuredValue = measuredValue.negate();
			}
			System.out.println("Measured Value if:" + measuredValue.toPlainString());
			x= measuredValue.toPlainString();
		}
		else {
			String dcmlfrmt = "0.";
			int lngt = 0;
			if(measuredValue.compareTo(one)>=0) {
				System.out.println("plain string: " + measuredValue);
				lngt = measuredValue.toPlainString().length();
			}
			else {
				int sig = 0;
				String measuredString = measuredValue.toPlainString();
				lngt = measuredString.length();
				for(int i=0;i<lngt;i++) {
					if((measuredString.charAt(i)!='0'&& measuredString.charAt(i)!='.')) {
						sig = i;
						break;
					}
				}
				lngt = measuredValue.toPlainString().substring(sig).length();
			}
			System.out.println("lngt: " + lngt);
			for(int i = 0;i<lngt;i++) {
				dcmlfrmt = dcmlfrmt + "0";
			}
			dcmlfrmt = dcmlfrmt + "E00";
			decfor = new DecimalFormat(dcmlfrmt);
			if(!measureSign) {
				measuredValue = measuredValue.negate();
			}
			x = decfor.format(measuredValue);
			System.out.println("Measured Value else:" +  x);
		}
		return x;
	}
	
	
	
	
	
	public String etToString(BigDecimal error) {
		DecimalFormat decfor1;
		String x="";
		if(error.compareTo(errorUpperLimit)<0 && error.compareTo(errorLowerLimit)>0) {
			System.out.println("Error: " + error.toPlainString());
			x= error.toPlainString();
		}
		else {
			String dcmlfrmt = "0.";
			int len = 0;
			if(error.compareTo(one)>=0) {
				len = error.toPlainString().length();
			}
			else {
				len = 0;
			}
			for(int i = 0;i<len;i++) {
				dcmlfrmt = dcmlfrmt + "0";
			}
			dcmlfrmt = dcmlfrmt + "E00";
			decfor1 = new DecimalFormat(dcmlfrmt);
			x= decfor1.format(error);
			System.out.println("Error: " + x );
		}
		
		return x;
	}
	
	
	
	
	public String cvToString(CalculatorValue value) {
		BigDecimal measuredValue = new BigDecimal(value.measuredValue.getDouble());
		Double decimalError =value.errorTerm.getDouble();

		System.out.println("value: " + measuredValue + ", error: " + decimalError);

		boolean measureSign = measuredValue.compareTo(zero)>=0;
		measuredValue = measuredValue.abs();
		MathContext mc = new MathContext(3, RoundingMode.DOWN);
		BigDecimal error = new BigDecimal(decimalError, mc);
		System.out.println("error: " + error);
	    BigDecimal multiplier = new BigDecimal(10);
		String errorString = error.toPlainString();
		enhancedET = etToString(error);
		if(error.compareTo(one)<0) {
			String Character = errorString.split("\\.")[1];
			int len = Character.length();
			multiplier = multiplier.pow(len);
			measuredValue = measuredValue.multiply(multiplier);
			measuredValue = new BigDecimal(measuredValue.toPlainString().split("\\.")[0]);
			measuredValue = measuredValue.divide(multiplier);
		}
		else {
			String Character = errorString.split("\\.")[0];
			int len = Character.length()-1;
			multiplier = multiplier.pow(len);
			measuredValue = measuredValue.divide(multiplier);
			measuredValue = new BigDecimal(measuredValue.toPlainString().split("\\.")[0]);
			measuredValue = measuredValue.multiply(multiplier);
		}
		enhancedMV=mvToString(measuredValue, measureSign);
		return ""+enhancedMV+ " "+enhancedET; 
	}
	
}

