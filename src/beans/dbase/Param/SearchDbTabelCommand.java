package beans.dbase.Param;

import beans.dbase.SearchDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Vector;
import java.util.ListIterator;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

import java.util.StringTokenizer;
/**
 * Session Bean implementation class SearchDbParam
 */
@Stateless
@LocalBean
public class SearchDbTabelCommand extends SearchDb {
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
    public SearchDbTabelCommand() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbTabelCommand(String operatorNpm) {
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
    public SearchDbTabelCommand(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    
   
    public Vector getListAllCommand(int usr_obj_level) {
    	
    	Vector v = null;

    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from TABEL_COMMAND where OBJECT_LEVEL_ALLOWED_TO_SET like ?");
    		stmt.setString(1, "%"+usr_obj_level+"#%");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			ListIterator li = v.listIterator();
    			do {
    				String cmd_code = rs.getString("CMD_CODE");
    				String cmd_keter = rs.getString("CMD_KETER");
    				String who_use_it = rs.getString("USE_BY");
    				String dependency = rs.getString("CMD_DEPENDENCY");
    				String default_value_ = rs.getString("PILIHAN_VALUE");
    				String default_value_hak_akses = rs.getString("PILIHAN_VALUE_HAK_AKSES");
    				String default_value_scope_kampus = rs.getString("PILIHAN_VALUE_SCOPE_KAMPUS");
    				li.add(cmd_code);
    				li.add(cmd_keter);
    				li.add(who_use_it);
    				li.add(dependency);
    				li.add(default_value_);
    				li.add(default_value_hak_akses);
    				li.add(default_value_scope_kampus);
    			} while(rs.next());
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
