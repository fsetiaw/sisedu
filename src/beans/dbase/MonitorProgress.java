package beans.dbase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class MonitorProgress
 */
@Stateless
@LocalBean
public class MonitorProgress {
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
    /**
     * Default constructor. 
     */
    public MonitorProgress() {
        // TODO Auto-generated constructor stub
    }
    
    public void insertProgressKeu(String date) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//java.util.Date today = new java.util.Date();
    		//java.sql.Date sqlToday = new java.sql.Date(today.getTime());
    		java.sql.Date sqlToday = java.sql.Date.valueOf(date);;
    		System.out.println(sqlToday);
    		stmt = con.prepareStatement("select * from MSPST order by KDJENMSPST,KDPSTMSPST");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = rs.getString("KDPSTMSPST");
    			String nmpst = rs.getString("NMPSTMSPST");
    			System.out.println(kdpst+" "+nmpst);
    			li.add(kdpst);
    			li.add(nmpst);
    		}
    		
    		stmt = con.prepareStatement("select * from PYMNT where KDPSTPYMNT=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			double ttPymnt=0;
    			String kdpst = (String)li.next();
    			String nmpst = (String)li.next();
    			stmt.setString(1,kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				double tmp = rs.getDouble("AMONTPYMNT");
    				String tm = ""+rs.getTimestamp("UPDTMPYMNT");
    				StringTokenizer st = new StringTokenizer(tm);
    				String dt = st.nextToken();
    				if((""+sqlToday).equalsIgnoreCase(dt)) {
//    				if(("2012-09-19").equalsIgnoreCase(dt)) {
    					ttPymnt=ttPymnt+tmp;
    					System.out.println(tmp+" "+dt);
    				}
    			}
    			li1.add(kdpst);
    			li1.add(nmpst);
    			li1.add(""+ttPymnt);
    		}    		
    		
    		stmt = con.prepareStatement("INSERT INTO MONITOR(KDPST,CMD_CODE,KETERANGAN)VALUES(?,?,?)");
    		li1 = v1.listIterator();
    		while(li1.hasNext()) {
    			String kdpst = (String)li1.next();
    			String nmpst = (String)li1.next();
    			String total = (String)li1.next();
    			stmt.setString(1,kdpst);
    			stmt.setString(2,"kasir");
    			stmt.setString(3,"Rp. "+total);
    			stmt.executeUpdate();
    		}
    	}
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	//return v;
    }

    public void insertProgressMhs(String date) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//java.util.Date today = new java.util.Date();
    		//java.sql.Date sqlToday = new java.sql.Date(today.getTime());
    		java.sql.Date sqlToday = java.sql.Date.valueOf(date);;
    		System.out.println(sqlToday);
    		stmt = con.prepareStatement("select * from MSPST order by KDJENMSPST,KDPSTMSPST");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = rs.getString("KDPSTMSPST");
    			String nmpst = rs.getString("NMPSTMSPST");
    			System.out.println(kdpst+" "+nmpst);
    			li.add(kdpst);
    			li.add(nmpst);
    		}
    		
    		stmt = con.prepareStatement("select * from CIVITAS where KDPSTMSMHS=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			double ttMhs=0;
    			String kdpst = (String)li.next();
    			String nmpst = (String)li.next();
    			stmt.setString(1,kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String tm = ""+rs.getTimestamp("UPDTMMSMHS");
    				StringTokenizer st = new StringTokenizer(tm);
    				String dt = st.nextToken();
    				if((""+sqlToday).equalsIgnoreCase(dt)) {
//    				if(("2012-09-19").equalsIgnoreCase(dt)) {
    					ttMhs++;
    					System.out.println(ttMhs+" "+dt);
    				}
    			}
    			li1.add(kdpst);
    			li1.add(nmpst);
    			li1.add(""+ttMhs);
    		}    		
    		
    		stmt = con.prepareStatement("INSERT INTO MONITOR(KDPST,CMD_CODE,KETERANGAN)VALUES(?,?,?)");
    		li1 = v1.listIterator();
    		while(li1.hasNext()) {
    			String kdpst = (String)li1.next();
    			String nmpst = (String)li1.next();
    			String total = (String)li1.next();
    			stmt.setString(1,kdpst);
    			stmt.setString(2,"mhs");
    			stmt.setString(3,total+" MHS");
    			stmt.executeUpdate();
    		}
    	}
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	//return v;
    }
    
    
    
}
