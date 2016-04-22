package beans.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class Constant
 */
@Stateless
@LocalBean
public class Constant {

    /**
     * Default constructor. 
     */
    public Constant() {
        // TODO Auto-generated constructor stub
    }

    
    public static Vector getIt(String keterangan) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	li.add(keterangan.toUpperCase());
    	String kode=new String(keterangan.toUpperCase());     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,keterangan);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			li.add(""+rs.getString(1));
    		}
    		else {
    			li.add("null");
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
    	return v;
    }
}
