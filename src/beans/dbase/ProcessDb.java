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
import java.util.Collections;
import org.apache.tomcat.jdbc.pool.*;

import beans.sistem.AskSystem;
import beans.setting.*;
import beans.tools.*;
import java.util.LinkedHashSet;
import java.util.Collections;
/**
 * Session Bean implementation class ProcessDb
 */
@Stateless
@LocalBean
public class ProcessDb {
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
    /**
     * Default constructor. 
     */
    public ProcessDb() {
        // TODO Auto-generated constructor stub
    }

    public ProcessDb(Connection con) {
    	this.con = con;
    }
    
    public Connection getCon() {
    	return con;
    }
    /*
     * depricated kelupaan ngga input id
     */
    public Vector prepKurikulumForViewing(String idkur) {
    	Vector v=null;
    	boolean pass = false;
    	int id = 0;
    	try {
    		id = Integer.valueOf(idkur).intValue();
    		pass = true;
    	}
    	catch(NumberFormatException e) {
    		System.out.println(e);
    	}
    	if(pass) {
    		v = new Vector();
    		ListIterator li = v.listIterator(); 
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? order by SEMESMAKUR,NAKMKMAKUL");
    			stmt.setLong(1,id);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String semes = rs.getString("SEMESMAKUR");
    				String kdpst = rs.getString("KDPSTMAKUL");
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				String nakmk = rs.getString("NAKMKMAKUL");
    				int skstm = rs.getInt("SKSTMMAKUL");
    				int skspr = rs.getInt("SKSPRMAKUL");
    				int skslp = rs.getInt("SKSLPMAKUL");
    				li.add(semes+"#&"+kdpst+"#&"+kdkmk+"#&"+nakmk+"#&"+skstm+"#&"+skspr+"#&"+skslp+"#&"+(skstm+skspr+skslp));
    				//System.out.println(semes+"#&"+kdpst+"#&"+kdkmk+"#&"+nakmk+"#&"+skstm+"#&"+skspr+"#&"+skslp+"#&"+(skstm+skspr+skslp));
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
		}	
    	return v;
    }
    
    public Vector prepKurikulumForViewingUpd1(String idkur) {
    	Vector v=null;
    	boolean pass = false;
    	int id = 0;
    	try {
    		id = Integer.valueOf(idkur).intValue();
    		pass = true;
    	}
    	catch(NumberFormatException e) {
    		System.out.println(e);
    	}
    	if(pass) {
    		v = new Vector();
    		ListIterator li = v.listIterator(); 
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? order by SEMESMAKUR,NAKMKMAKUL");
    			stmt.setLong(1,id);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String semes = rs.getString("SEMESMAKUR");
    				int idkmk = rs.getInt("IDKMKMAKUL");
    				String kdpst = rs.getString("KDPSTMAKUL");
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				String nakmk = rs.getString("NAKMKMAKUL");
    				nakmk = nakmk.replace("&", "dan");
    				int skstm = rs.getInt("SKSTMMAKUL");
    				int skspr = rs.getInt("SKSPRMAKUL");
    				int skslp = rs.getInt("SKSLPMAKUL");
    				li.add(semes+"#&"+kdpst+"#&"+kdkmk+"#&"+nakmk+"#&"+skstm+"#&"+skspr+"#&"+skslp+"#&"+(skstm+skspr+skslp)+"#&"+idkmk);
    				//System.out.println(semes+"#&"+kdpst+"#&"+kdkmk+"#&"+nakmk+"#&"+skstm+"#&"+skspr+"#&"+skslp+"#&"+(skstm+skspr+skslp));
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
		}	
    	return v;
    }
    
    
}
