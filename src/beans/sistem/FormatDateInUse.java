package beans.sistem;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.tools.Converter;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
/**
 * Session Bean implementation class askSystem
 */
@Stateless
@LocalBean
public class FormatDateInUse {

    /**
     * Default constructor. 
     */
	java.sql.Date sqlDate;
	String formatDateInUse;
	String tgl,bln,thn;
	String namaBulan;
	
    public FormatDateInUse(java.sql.Date dt) {
        // TODO Auto-generated constructor stub
    	super();
    	sqlDate = dt;
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    	if((formatDateInUse = sdf.format(sqlDate))!=null) {
    		StringTokenizer st = new StringTokenizer(formatDateInUse,"-");
        	tgl = st.nextToken();
        	bln = st.nextToken();
        	namaBulan = Converter.convertIntToNamaBulan(Integer.valueOf(bln).intValue());
        	thn = st.nextToken();
    	}
    	else {
    		tgl = "N/A";
    		bln = "N/A";
    		thn = "N/A";
    	}
    	
    }
    
    public FormatDateInUse(String strdt) {
        // TODO Auto-generated constructor stub
    	super();
    	sqlDate = java.sql.Date.valueOf(strdt);
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    	if((formatDateInUse = sdf.format(sqlDate))!=null) {
    		StringTokenizer st = new StringTokenizer(formatDateInUse,"-");
        	tgl = st.nextToken();
        	bln = st.nextToken();
        	namaBulan = Converter.convertIntToNamaBulan(Integer.valueOf(bln).intValue());
        	thn = st.nextToken();
    	}
    	else {
    		tgl = "N/A";
    		bln = "N/A";
    		thn = "N/A";
    	}
    }

    public String getFormatedStringDate() {
    	return formatDateInUse;
    }
    
    public String getTgl() {
    	return tgl;
    }

    public String getBln() {
    	return bln;
    }

    public String getThn() {
    	return thn;
    }

    public String getNamaBulan() {
    	return namaBulan;
    }

}
