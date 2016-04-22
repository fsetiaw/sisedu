package beans.tmp;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Session Bean implementation class IncomeSheet
 */
@Stateless
@LocalBean
public class IncomeSheet {
	Vector v;
	String fullname, nim, npm;
	String dbSchema;
	int objLevel;
	String accessLevelConditional,accessLevel;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds; 
	
    /**
     * Default constructor. 
     */
    public IncomeSheet() {
        // TODO Auto-generated constructor stub
    }
    
    public IncomeSheet(Vector v) {
        // TODO Auto-generated constructor stub
    	this.v = v;
    }
    
    public Vector tmp(String tahun) {
    	Vector vf = new Vector();
    	ListIterator lif= vf.listIterator();
    	ListIterator li = v.listIterator();
    	while(li.hasNext()) {
    		String baris = (String)li.next();
    		StringTokenizer st = new StringTokenizer(baris);
    		String idObj = st.nextToken();
    		String kdpst = st.nextToken();
    		String keter = st.nextToken();
    		String lvObj = st.nextToken();
    		lif.add(idObj);
    		lif.add(kdpst);
    		lif.add(keter);
    		lif.add(lvObj);
    		//Vector vTrs = new Vector();
    		//ListIterator liTrs = vTrs.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc/USG");
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from PYMNT where KDPSTPYMNT=? and YEAR(TGTRSPYMNT)=?");
        		stmt.setString(1, kdpst);
        		stmt.setString(2, tahun);
        		rs = stmt.executeQuery();
        		double tt = 0;
        		while(rs.next()) {
        			tt = tt+rs.getDouble("AMONTPYMNT");
        			//System.out.println(trsdt);
        		}
        		lif.add(""+tt);
    		} 
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (con!=null) {
    				try {
    					con.close();
    				}
    				catch (Exception ignore) {
    					System.out.println(ignore);
    				}
    			}
    		}
    	}
    	return vf;
    }

}
