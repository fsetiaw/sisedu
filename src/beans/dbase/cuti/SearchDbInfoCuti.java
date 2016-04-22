package beans.dbase.cuti;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Collections;
import java.util.ListIterator;

import beans.dbase.SearchDb;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.DateFormater;
import beans.tools.Tool;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncodingException;

import java.sql.Timestamp;

/**
 * Session Bean implementation class SearchDbInfoCuti
 */
@Stateless
@LocalBean
public class SearchDbInfoCuti extends SearchDb {
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
    public SearchDbInfoCuti() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbInfoCuti(String operatorNpm) {
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
    public SearchDbInfoCuti(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getCutiAkademikRules(String target_thsms) {
    	Vector v = new Vector();
    	//String finalList="";
    	ListIterator li = v.listIterator();
    	//String listThsmsNpmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		Vector vlist_kdpst = Getter.getListProdi(); //output kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst
    		Vector vlist_kmp = Getter.getListAllKampus(); //code_campus+"`"+name_campus+"`"+nickname_campus
    		ListIterator liKdpst = vlist_kdpst.listIterator();
    		Vector vNuListKdpst = new Vector();
    		ListIterator linuk = vNuListKdpst.listIterator();
    		while(liKdpst.hasNext()) {
    			String brs1 = (String)liKdpst.next();
    			StringTokenizer st = new StringTokenizer(brs1,"`");
    			String kdpst = st.nextToken();
    			ListIterator litmp = vlist_kmp.listIterator();
    			while(litmp.hasNext()) {
    				String brs = (String)litmp.next();
    				StringTokenizer stt = new StringTokenizer(brs,"`");
    				String kode_kmp = stt.nextToken();
    				linuk.add(kdpst+"`"+kode_kmp);
    			}
    		}
    		
    		stmt = con.prepareStatement("select * from CUTI_RULES where THSMS=? order by KDPST");
    		stmt.setString(1,target_thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = rs.getString(2);
    			String tkn_ver = rs.getString(3);
    			String urutan = ""+rs.getBoolean(4);
    			String kode_kmp = ""+rs.getString(5);
    			li.add(kdpst+"`"+tkn_ver+"`"+urutan+"`"+kode_kmp);
    		}
    		
    		linuk = vNuListKdpst.listIterator();
    		while(linuk.hasNext()) {
    			String info_kdpst = (String) linuk.next();
    			StringTokenizer st = new StringTokenizer(info_kdpst,"`");
    			String kdpst1 = st.nextToken();
    			String kode_kmp1 = st.nextToken();
    			boolean match = false;
    			li = v.listIterator();
    			while(li.hasNext()&&!match) {
    				StringTokenizer st1 = new StringTokenizer((String)li.next(),"`");
    				String kdpst = st1.nextToken();
    				String ver = st1.nextToken();
    				String urut = st1.nextToken();
    				String kode_kmp = st1.nextToken();
    				if(kdpst.equalsIgnoreCase(kdpst1) && kode_kmp.equalsIgnoreCase(kode_kmp1)) {
    	    			match = true;
    	    		}
    				
    			}
    			if(!match) {
    				li.add(kdpst1+"`null`null`"+kode_kmp1);
    			}	
    			
    		}
    		Collections.sort(v);
    	}
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	}
    	//catch (Exception e) {
    	//	e.printStackTrace();
    	//}
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return v;
    }
    
    
    public String getDistinctNicknameCutiApprovee(String thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String list_tkn_approvee = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select TKN_VERIFICATOR from DAFTAR_ULANG_RULES where THSMS=?");
    		stmt.setString(1,thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String tkn_approvee = rs.getString(1);
    			StringTokenizer st = new StringTokenizer(tkn_approvee,",");
    			while(st.hasMoreTokens()) {
    				li.add(st.nextToken());
    			}
    		}
    		v = Tool.removeDuplicateFromVector(v);
    		li = v.listIterator();
    		while(li.hasNext()) {
    			list_tkn_approvee = list_tkn_approvee+","+(String)li.next();
    		}
        }
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	catch (Exception ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return list_tkn_approvee;
    }

}
