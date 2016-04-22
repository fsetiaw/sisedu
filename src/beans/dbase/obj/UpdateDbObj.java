package beans.dbase.obj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;
import java.util.ListIterator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collections;
import org.apache.tomcat.jdbc.pool.DataSource;

import beans.dbase.UpdateDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Tool;

/**
 * Session Bean implementation class UpdateDb
 */
@Stateless
@LocalBean
public class UpdateDbObj extends UpdateDb{
	String operatorNpm;
	String operatorNmm;
	String tknOperatorNickname;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
    /**
     * Default constructor. 
     */
    public UpdateDbObj() {
        // TODO Auto-generated constructor stub
    }

    public UpdateDbObj(String operatorNpm) {
        // TODO Auto-generated constructor stub
    	super(operatorNpm);
    	this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }
    
    public int updateAccessLevelObj(long targetObjId, String accesLevel, String accesLevelConditional) {
    	int i=0;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update OBJECT set ACCESS_LEVEL_CONDITIONAL=?,ACCESS_LEVEL=? where ID_OBJ=?");
    		stmt.setString(1, accesLevelConditional);
    		stmt.setString(2, accesLevel);
    		stmt.setLong(3, targetObjId);
    		//System.out.println("this.operatorNpm="+this.operatorNpm);
    		i = stmt.executeUpdate();
    		
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
    	return i;
    }
    
    public int updateAccessLevelObj(long targetObjId, String accesLevel, String accesLevelConditional, String hak_akses, String scope_kampus) {
    	int i=0;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update OBJECT set ACCESS_LEVEL_CONDITIONAL=?,ACCESS_LEVEL=?,HAK_AKSES=?,SCOPE_KAMPUS=? where ID_OBJ=?");
    		stmt.setString(1, accesLevelConditional);
    		stmt.setString(2, accesLevel);
    		stmt.setString(3, hak_akses);
    		stmt.setString(4, scope_kampus);
    		stmt.setLong(5, targetObjId);
    		//System.out.println("this.operatorNpm="+this.operatorNpm);
    		i = stmt.executeUpdate();
    		
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
    	return i;
    }
 }
