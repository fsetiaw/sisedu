package beans.dbase.overview;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Session Bean implementation class SebaranTrlsm
 */
@Stateless
@LocalBean
public class SebaranTrlsm extends SearchDb {
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
    public SebaranTrlsm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SebaranTrlsm(String operatorNpm) {
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
    public SebaranTrlsm(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getJumMhsRegister(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms) {
    	Vector vf = null;
    	//format scope 122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
    	/*
    	String thsms_now = Checker.getThsmsNow();
		String thsms_prev = Tool.returnPrevThsmsGivenTpAntara(thsms_now);
		String thsms_next = Tool.returnNextThsmsGivenTpAntara(thsms_now);
    	*/
    	ListIterator lif = null;
    	ListIterator li = null;
    	/*
    	 * 
    	 */
    	try {
    		if(v_returnScopeProdiOnlySortByKampusWithListIdobj!=null && v_returnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
    			li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    			vf = new Vector();
    			lif = vf.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	seperate each kampus
    			
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_list_objid = null;
    				if(st.hasMoreTokens()) {
    					sql_list_objid = new String();
    					do {
    						String objid = st.nextToken();
    						sql_list_objid = sql_list_objid+"ID_OBJ="+objid;
    						if(st.hasMoreElements()) {
    							sql_list_objid = sql_list_objid + " OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_list_objid!=null) {
    					
    					stmt = con.prepareStatement("select COUNT(*) from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and ("+sql_list_objid+")");
    					stmt.setString(1, target_thsms);
    					rs = stmt.executeQuery();
    					long counter = rs.getInt(1);
    					lif.add(kmp+" "+counter);
    				}
    				else {
    				//ignore = harus ada scope id_obj
    					lif.add(kmp+" 0");
    				}
    			}
    			
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
    	return vf;
    }

}
