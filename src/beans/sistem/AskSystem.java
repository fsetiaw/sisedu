package beans.sistem;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import beans.tools.Converter;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
/**
 * Session Bean implementation class askSystem
 */
@Stateless
@LocalBean
public class AskSystem {

    /**
     * Default constructor. 
     */
    public AskSystem() {
        // TODO Auto-generated constructor stub
    }
    
    public static Timestamp getCurrentTimestamp() 
    {
    	java.util.Date date= new java.util.Date();
    	return (new Timestamp(date.getTime()));
    }
    
 

    public static java.sql.Date getCurrentTime() {
    	java.util.Date today = new java.util.Date();
    	java.sql.Date sqlToday = new java.sql.Date(today.getTime());
    	return sqlToday;
    }
    
    public static String getCurrentTimeInString() {
    	java.util.Date today = new java.util.Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_hhmmss");
    	return sdf.format(today);
    }
    public static String getCurrentYear() {
    	java.util.Date today = new java.util.Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    	return sdf.format(today);
    }
    public static String getCurrentMonth() {
    	java.util.Date today = new java.util.Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM");
    	String bln =sdf.format(today);
    	bln = ""+((Integer.valueOf(bln).intValue()-1));
    	return bln;
    }

    public static String getCurrentDate() {
    	java.util.Date today = new java.util.Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String dt =sdf.format(today);
    	
    	return dt; 
    }
    
    public static java.sql.Date getTodayDate() {
    	java.util.Date today = new java.util.Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String dt =sdf.format(today);
    	java.sql.Date dt_sql = java.sql.Date.valueOf(dt);
    	return dt_sql; 
    }

    
    public static String getNowDateTimePlus(int minutes) {
    	Calendar cal = Calendar.getInstance(); // creates calendar
    	cal.setTime(new Date()); // sets calendar time/date
    	cal.add(Calendar.MINUTE , 60); // adds one hour
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String skrg = sdf.format(cal.getTime());
    	return skrg; 
    }
    /*
     * depricated
     */
    public static String getDateTimeGivenPlus(String dateTime,int minutes) {
    	String skrg = "N/A";
    	try {
    		Timestamp ts = Timestamp.valueOf(dateTime);
    		Calendar cal = Calendar.getInstance(); // creates calendar
    		cal.setTime(ts); // sets calendar time/date
    		cal.add(Calendar.MINUTE , 60); // adds one hour
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    		skrg = sdf.format(cal.getTime());
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    	return skrg; 
    }
    
    public static String getDateTimeGivenPlus(String dateTime,String minutes) {
    	int mnt = 0;
    	String skrg = "N/A";
    	//System.out.println("in");
    	boolean pass = false;
    	try {
    		mnt = Integer.valueOf(minutes).intValue();
    		pass = true;
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    	if(pass) {
    		try {
    			Timestamp ts = Timestamp.valueOf(dateTime);
    			Calendar cal = Calendar.getInstance(); // creates calendar
    			cal.setTime(ts); // sets calendar time/date
    			cal.add(Calendar.MINUTE , mnt); // adds one hour
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    			skrg = sdf.format(cal.getTime());
    		}
    		catch(Exception e) {
    			System.out.println(e);
    		}
    	}
    	//System.out.println("out");
    	return skrg; 
    }    
    
    public static String getDateTime() {
    	String long_format = ""; 
	    java.util.Date today = new java.util.Date();
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String skrg = sdf.format(today);
	    //skrg = skrg.substring(0,4)+"-"+skrg.substring(4,6)+"-"+skrg.substring(6,8)+" "+skrg.substring(9,11)+":"+skrg.substring(11,13)+":"+skrg.substring(13,skrg.length());
	    //String year = skrg.substring(0,4);
	    //String mon = skrg.substring(4,6);
	    //mon = Converter.convertIntToNamaBulan(Integer.valueOf(mon).intValue());
	    //String day = skrg.substring(6,8);
	    //long_format = day+" "+mon+" "+year;
	    //System.out.println("today="+today);
	    return skrg;
    }    
    
    public static String getCurrentTimeInLongStringFormat() {
    	String long_format = ""; 
	    java.util.Date today = new java.util.Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
	    String skrg = sdf.format(today);
	    String year = skrg.substring(0,4);
	    String mon = skrg.substring(4,6);
	    mon = Converter.convertIntToNamaBulan(Integer.valueOf(mon).intValue());
	    String day = skrg.substring(6,8);
	    long_format = day+" "+mon+" "+year;
	    return long_format;
    }
    
    public static String getClientIp(HttpServletRequest request) {
    	String ipAddress  = request.getHeader("X-FORWARDED-FOR");  
        if(ipAddress == null)  
        {  
          ipAddress = request.getRemoteAddr();  
        }  
        //System.out.println("ipAddress:"+ipAddress);  
        return ipAddress;
    }
    
    public static boolean isItSameIp(String ip1, String ip2) {
    	//only xx.xx.xx = berarti sama
    	boolean same = false;
    	if(ip1.contains(".") && ip2.contains(".")) {
    		StringTokenizer st1 = new StringTokenizer(ip1,".");
    		StringTokenizer st2 = new StringTokenizer(ip2,".");
    		if(st1.nextToken().equalsIgnoreCase(st2.nextToken())) {
    			if(st1.nextToken().equalsIgnoreCase(st2.nextToken())) {
    				if(st1.nextToken().equalsIgnoreCase(st2.nextToken())) {
    					same = true;
    				}
    			}
    		}
    		
    	}
    	return same;
    }
    
    
    
    public static String getPostdTimeInHrOrDay(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime) {
    	String tmp = "";
    	long milliseconds1 = oldTime.getTime();
    	long milliseconds2 = currentTime.getTime();

    	long diff = milliseconds2 - milliseconds1;
    	long diffSeconds = diff / 1000;
    	long diffMinutes = diff / (60 * 1000);
    	long diffHours = diff / (60 * 60 * 1000);
    	long diffDays = diff / (24 * 60 * 60 * 1000);
    	if(diffMinutes<60) {
    		tmp = diffMinutes+" menit yg lalu";
    		if(diffMinutes==0) {
    			tmp = "baru saja";
    		}
    	}
    	else {
    		if(diffHours<=24) {
    			tmp = diffHours+" jam yg lalu";
    		}
    		else {
    			tmp = diffDays+" hari yg lalu";
    		}
    	}	
        return tmp;
    }
    
}
