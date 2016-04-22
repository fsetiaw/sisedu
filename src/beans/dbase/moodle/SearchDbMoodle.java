package beans.dbase.moodle;

import beans.dbase.SearchDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchDbMoodle
 */
@Stateless
@LocalBean
public class SearchDbMoodle extends SearchDb {
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
     * @see SearchDb#SearchDb()
     */
    public SearchDbMoodle() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbMoodle(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public SearchDbMoodle(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    
    public Vector listRole() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/MyMoodle");
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select * from mdl_role order by id");
			rs = stmt.executeQuery();
			while(rs.next()) {
				long id = rs.getLong("id");
			    String name = rs.getString("name");
			    String shortname = ""+rs.getString("shortname");
			    if(Checker.isStringNullOrEmpty(shortname)) {
			    	shortname = "null";
			    }
			    String description = ""+rs.getString("description");
			    if(Checker.isStringNullOrEmpty(description)) {
			    	description = "null";
			    }
			    long sortorder  = rs.getLong("sortorder");
			    String archetype  = ""+rs.getString("archetype");
			    li.add(id+"`"+shortname+"`"+description+"`"+sortorder+"`"+archetype);


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
