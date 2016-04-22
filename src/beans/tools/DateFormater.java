package beans.tools;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import java.util.LinkedHashSet;
/**
 * Session Bean implementation class Tool
 */
@Stateless
@LocalBean
public class DateFormater {

    /**
     * Default constructor. 
     */
    public DateFormater() {
        // TODO Auto-generated constructor stub
    }
    
    
    public static String convertFromDdMmYyToYyMmDd(String tglformatddMMyyyy) {
    	StringTokenizer st = null;
    	if(tglformatddMMyyyy.contains("/")) {
    		st = new StringTokenizer(tglformatddMMyyyy,"/");
    	}
    	else {
    		if(tglformatddMMyyyy.contains("-")) {
        		st = new StringTokenizer(tglformatddMMyyyy,"-");
        	}
        	else {
        		if(tglformatddMMyyyy.contains("_")) {
            		st = new StringTokenizer(tglformatddMMyyyy,"_");
            	}
            	else {
            		
            	}
        	}
    	}
    	String dd = st.nextToken();
    	if(dd.length()==1) {
    		dd = "0"+dd;
    	}
    	String mm = st.nextToken();
    	if(mm.length()==1) {
    		mm = "0"+mm;
    	}
    	String yy = st.nextToken();
    	return yy+"-"+mm+"-"+dd;
    }
    
    public static String prepStringDateToDbInputFormat(String ddmmyyyy) {
    	StringTokenizer st = null;
    	if(ddmmyyyy.contains("/")) {
    		st = new StringTokenizer(ddmmyyyy,"/");
    	}
    	else {
    		if(ddmmyyyy.contains("-")) {
        		st = new StringTokenizer(ddmmyyyy,"-");
        	}
        	else {
        		if(ddmmyyyy.contains("_")) {
            		st = new StringTokenizer(ddmmyyyy,"_");
            	}
            	else {
            		
            	}
        	}
    	}
    	String dd = st.nextToken();
    	String mm = st.nextToken();
    	String yy = st.nextToken();
    	if(dd.length()==1) {
    		dd = "0"+dd;
    	}
    	else if(dd.length()==4 && yy.length()<=2){
    		String tmp_thn = ""+dd;
    		dd = ""+yy;
    		yy = ""+tmp_thn;
    		if(dd.length()==1) {
        		dd = "0"+dd;
        	}
    	}
    	if(mm.length()==1) {
    		mm = "0"+mm;
    	}
    	return yy+"-"+mm+"-"+dd;
    }
    
    /*
     * from db format ke tampian di form
     */
    public static String prepStringFromDbDateToInputFormFormat(String ddmmyyyy) {
    	StringTokenizer st = null;
    	if(ddmmyyyy.contains("/")) {
    		st = new StringTokenizer(ddmmyyyy,"/");
    	}
    	else {
    		if(ddmmyyyy.contains("-")) {
        		st = new StringTokenizer(ddmmyyyy,"-");
        	}
        	else {
        		if(ddmmyyyy.contains("_")) {
            		st = new StringTokenizer(ddmmyyyy,"_");
            	}
            	else {
            		
            	}
        	}
    	}
    	String dd = st.nextToken();
    	String mm = st.nextToken();
    	String yy = st.nextToken();
    	if(dd.length()==1) {
    		dd = "0"+dd;
    	}
    	else if(dd.length()==4 && yy.length()<=2){
    		String tmp_thn = ""+dd;
    		dd = ""+yy;
    		yy = ""+tmp_thn;
    		if(dd.length()==1) {
        		dd = "0"+dd;
        	}
    	}
    	if(mm.length()==1) {
    		mm = "0"+mm;
    	}
    	return dd+"/"+mm+"/"+yy;
    }
    
    public static String keteranganDate(String tglformatyyyy_mm_dd) {
    	StringTokenizer st = null;
    	if(tglformatyyyy_mm_dd.contains("/")) {
    		st = new StringTokenizer(tglformatyyyy_mm_dd,"/");
    	}
    	else {
    		st = new StringTokenizer(tglformatyyyy_mm_dd,"-");
    	}
    	String thn = st.nextToken();
    	String bln = st.nextToken();
    	int month = Integer.valueOf(bln).intValue();
    	if(month==1) {
    		bln = "Januari";
    	}
    	else {
    		if(month==2) {
        		bln = "Februari";
        	}
        	else {
        		if(month==3) {
            		bln = "Maret";
            	}
            	else {
            		if(month==4) {
                		bln = "April";
                	}
                	else {
                		if(month==5) {
                    		bln = "Mei";
                    	}
                    	else {
                    		if(month==6) {
                        		bln = "Juni";
                        	}
                        	else {
                        		if(month==7) {
                            		bln = "Juli";
                            	}
                            	else {
                            		if(month==8) {
                                		bln = "Agustus";
                                	}
                                	else {
                                		if(month==9) {
                                    		bln = "September";
                                    	}
                                    	else {
                                    		if(month==10) {
                                        		bln = "Oktober";
                                        	}
                                        	else {
                                        		if(month==11) {
                                            		bln = "November";
                                            	}
                                            	else {
                                            		if(month==12) {
                                                		bln = "Desember";
                                                	}
                                                	else {
                                                		
                                                	}
                                            	}
                                        	}
                                    	}
                                	}
                            	}
                        	}
                    	}
                	}
            	}
        	}
    	}
    	String tgl = st.nextToken();
    	return tgl+" "+bln+" "+thn;
    }
    
    public static String createInputDate(String tgl_selected,String bln_selected,String thn_selected) {
    	String cmd ="<select name=\"tgl\">";
		for(int i=1;i<=31;i++) {
			if(tgl_selected.equalsIgnoreCase("0")||tgl_selected.equalsIgnoreCase("null")) {
				tgl_selected = "15";
			}
			if((""+i).equalsIgnoreCase(tgl_selected)) {
				cmd = cmd+"<option value=\""+i+"\" selected>"+i+"</option>";	
			}
			else {
				cmd = cmd+"<option value=\""+i+"\">"+i+"</option>";
			}
			
		}	
		cmd = cmd+"</select>-";
		cmd =cmd+"<select name=\"bln\">";
		for(int i=1;i<=12;i++) {
			if(bln_selected.equalsIgnoreCase("0")||bln_selected.equalsIgnoreCase("null")) {
				bln_selected = "6";
			}
			if((""+i).equalsIgnoreCase(bln_selected)) {
				cmd = cmd+"<option value=\""+i+"\" selected>"+i+"</option>";	
			}
			else {
				cmd = cmd+"<option value=\""+i+"\">"+i+"</option>";
			}	
		}	
		cmd = cmd+"</select>-";
		cmd =cmd+"<select name=\"thn\">";
		for(int i=1930;i<=2112;i++) {
			if(thn_selected.equalsIgnoreCase("0")||thn_selected.equalsIgnoreCase("null")) {
				thn_selected = "1970";
			}
			if((""+i).equalsIgnoreCase(thn_selected)) {
				cmd = cmd+"<option value=\""+i+"\" selected>"+i+"</option>";	
			}
			else {
				cmd = cmd+"<option value=\""+i+"\">"+i+"</option>";
			}	
		}	
		cmd = cmd+"</select>";
		return cmd;
    }
}
