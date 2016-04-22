package beans.tools;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;
/**
 * Session Bean implementation class NumberFormat
 */
@Stateless
@LocalBean
public class NumberFormater {

    /**
     * Default constructor. 
     */
    public NumberFormater() {
        // TODO Auto-generated constructor stub
    }
    
    public static String omitDecimalDigitIfZero(String value) {
    	try {
    		DecimalFormat df = new DecimalFormat("#,###,##0.##");
    		value = df.format(Double.valueOf(value).doubleValue());
    	}
    	catch(Exception e) {
    		//return original value - value;
    	}
    	return value;
    }
    
    public static String indoNumberFormat(String amnt) {
    	NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    	return nf.format(Double.valueOf(amnt).doubleValue());
    }
    
    public static double return2digit(String value)throws Exception {
		value = value.replaceAll(",",".");;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		value = ""+nf.format(Double.valueOf(value).doubleValue());
		value = value.replaceAll(",",".");
		return Double.valueOf(value).doubleValue();
	}
    
}
